package com.manage.kernel.core.admin.service.system.impl;

import com.manage.base.constant.Constants;
import com.manage.base.exception.CoreException;
import com.manage.base.exception.OrganNotFoundException;
import com.manage.base.supplier.msgs.MessageErrors;
import com.manage.base.supplier.page.TreeNode;
import com.manage.kernel.core.model.dto.OrganDto;
import com.manage.kernel.core.model.parser.OrganParser;
import com.manage.kernel.core.admin.service.system.IOrganService;
import com.manage.kernel.jpa.entity.AdOrganization;
import com.manage.kernel.jpa.repository.AdOrganRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/11.
 */
@Service
public class OrganService implements IOrganService {

    private static final Logger LOGGER = LogManager.getLogger(OrganService.class);
    @Autowired
    private AdOrganRepo organRepo;

    @Override
    @Transactional
    public List<TreeNode> organTree() {
        Iterable<AdOrganization> organs = organRepo.queryListAll();
        List<TreeNode> treeNodes = new ArrayList<>();
        TreeNode<String> treeNode;
        for (AdOrganization organ : organs) {
            treeNode = new TreeNode();
            treeNode.setId(organ.getCode());
            treeNode.setPid(organ.getParentCode());
            treeNode.setName(organ.getName());
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    @Override
    @Transactional
    public OrganDto getOrgan(String code) {
        AdOrganization organ = organRepo.findOne(code);
        if (organ == null) {
            LOGGER.info("Not found the organization {}", code);
            throw new OrganNotFoundException();
        }
        return OrganParser.toDto(organ);
    }

    @Override
    @Transactional
    public OrganDto updateOrgan(OrganDto organDto) {
        AdOrganization organ = organRepo.findOne(organDto.getCode());
        if (organ == null) {
            LOGGER.info("Not found the organization {}", organDto.getCode());
            throw new OrganNotFoundException();
        }

        organ.setName(organDto.getName());
        organ.setDescription(organDto.getDescription());
        List<AdOrganization> brotherOrgans;

        int seqNew = organDto.getSequence();
        int seqOld = organ.getSequence();
        if (seqNew != seqOld) {
            brotherOrgans = organRepo.findAll((root, cq, cb) -> {
                List<Predicate> list = new ArrayList<>();
                if (organ.getParentCode() == null) {
                    list.add(cb.isNull(root.get("parentCode")));
                } else {
                    list.add(cb.equal(root.get("parentCode"), organ.getParentCode()));
                }
                return cb.and(list.toArray(new Predicate[0]));
            }, new Sort(Sort.Direction.ASC, "sequence"));

            if (seqNew > brotherOrgans.size()) {
                seqNew = brotherOrgans.size();
            }
            organ.setSequence(seqNew);
            int start = seqNew > seqOld ? seqOld : seqNew;
            int end = seqNew > seqOld ? seqNew : seqOld;
            int sequence = start;
            boolean isDown = true;
            for (AdOrganization brother : brotherOrgans) {
                if (seqNew < seqOld && isDown) {
                    sequence++;
                    isDown = false;
                }
                if (brother.getCode().equals(organ.getCode())) {
                    continue;
                }
                if (brother.getSequence() >= start && brother.getSequence() <= end) {
                    brother.setSequence(sequence++);
                    organRepo.save(brother);
                    continue;
                }
            }
        }
        organ.setUpdatedAt(LocalDateTime.now());
        organ.setUpdatedUser(SessionHelper.user());
        organRepo.save(organ);
        return OrganParser.toDto(organ);
    }

    @Override
    @Transactional
    public void deleteOrgan(String code) {
        AdOrganization organ = organRepo.findOne(code);
        if (organ == null) {
            LOGGER.info("Not found the organization {}", code);
            throw new OrganNotFoundException();
        }

        if (!organ.getChildrens().isEmpty()) {
            LOGGER.info("The organization {} has childrens, delete failed.", code);
            throw new CoreException(MessageErrors.ORGAN_HAS_CHILDREN);
        }

        int sequence = organ.getSequence();
        AdOrganization organParent = organ.getParent();
        if (organParent != null) {
            int seqStart = sequence;
            for (AdOrganization subOrgan : organParent.getChildrens()) {
                if (subOrgan.getSequence() < sequence || code.equals(subOrgan.getCode())) {
                    continue;
                }
                subOrgan.setSequence(seqStart++);
                organRepo.save(subOrgan);
            }
        }

        organRepo.delete(organ);
    }

    @Override
    @Transactional
    public void addOrgan(OrganDto organDto) {
        AdOrganization organ = new AdOrganization();
        if (organDto.getParentCode() == null) {
            List<AdOrganization> organs = organRepo.queryListByLevel(1);
            organ.setCode(nextOrganCode(null));
            organ.setLevel(1);
            organ.setSequence(organs.size() + 1);
        } else {
            AdOrganization parent = organRepo.findOne(organDto.getParentCode());
            if (parent == null) {
                LOGGER.warn("Not found the organ {}", organDto.getParentCode());
                throw new OrganNotFoundException();
            }
            organ.setCode(nextOrganCode(parent.getCode()));
            organ.setParent(parent);
            organ.setLevel(parent.getLevel() + 1);
            organ.setSequence(parent.getChildrens().size() + 1);
        }
        organ.setName(organDto.getName());
        organ.setCreatedAt(LocalDateTime.now());
        organ.setCreatedUser(SessionHelper.user());
        organRepo.save(organ);
    }


    private String nextOrganCode(String parentCode) {
        int level = 1;
        String maxCode;
        if (parentCode == null) {
            maxCode = organRepo.findMaxCodeByLevel(level);
        } else {
            level = (parentCode.length() / Constants.ORGAN_LENTH) + 1;
            maxCode = organRepo.findMaxCodeByLevel(level, parentCode);
        }
        if (maxCode == null) {
            return (parentCode == null ? "" : parentCode) + (level == 1 ? "1001" : "0001");
        }

        String nextCode = String.valueOf(Long.valueOf(maxCode) + 1);
        if (nextCode.length() % Constants.ORGAN_LENTH != 0) {
            throw new CoreException(MessageErrors.ORGAN_CODE_ERROR);
        }
        return nextCode;
    }
}
