package com.manage.kernel.core.admin.service.system.impl;

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
        TreeNode<Long> treeNode;
        for (AdOrganization organ : organs) {
            treeNode = new TreeNode();
            treeNode.setId(organ.getId());
            treeNode.setPid(organ.getParentId());
            treeNode.setName(organ.getName());
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    @Override
    @Transactional
    public OrganDto getOrgan(Long id) {
        AdOrganization organ = organRepo.findOne(id);
        if (organ == null) {
            LOGGER.info("Not found the organization {}", id);
            throw new OrganNotFoundException();
        }
        return OrganParser.toDto(organ);
    }

    @Override
    @Transactional
    public OrganDto updateOrgan(OrganDto organDto) {
        AdOrganization organ = organRepo.findOne(organDto.getId());
        if (organ == null) {
            LOGGER.info("Not found the organization {}", organDto.getId());
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
                if (organ.getParentId() == null) {
                    list.add(cb.isNull(root.get("parentId")));
                } else {
                    list.add(cb.equal(root.get("parentId").as(Long.class), organ.getParentId()));
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
                if (brother.getId().equals(organ.getId())) {
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
    public void deleteOrgan(Long id) {
        AdOrganization organ = organRepo.findOne(id);
        if (organ == null) {
            LOGGER.info("Not found the organization {}", id);
            throw new OrganNotFoundException();
        }

        if (!organ.getChildrens().isEmpty()) {
            LOGGER.info("The organization {} has childrens, delete failed.", id);
            throw new CoreException(MessageErrors.ORGAN_HAS_CHILDREN);
        }

        int sequence = organ.getSequence();
        AdOrganization organParent = organ.getParent();
        if (organParent != null) {
            int seqStart = sequence;
            for (AdOrganization subOrgan : organParent.getChildrens()) {
                if (subOrgan.getSequence() < sequence || id.equals(subOrgan.getId())) {
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
        organ.setId(organDto.getId());
        organ.setName(organDto.getName());

        if (organDto.getParentId() == null) {
            List<AdOrganization> organs = organRepo.queryListByLevel(1);
            organ.setLevel(1);
            organ.setSequence(organs.size() + 1);
        } else {
            AdOrganization parent = organRepo.findOne(organDto.getParentId());
            if (parent == null) {
                LOGGER.warn("Not found the organization {}", organDto.getParentId());
                throw new OrganNotFoundException();
            }
            organ.setParent(parent);
            organ.setLevel(parent.getLevel() + 1);
            organ.setSequence(parent.getChildrens().size() + 1);
        }
        organ.setCreatedAt(LocalDateTime.now());
        organ.setCreatedUser(SessionHelper.user());
        organRepo.save(organ);
    }
}
