package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.database.enums.Status;
import com.manage.base.exception.SuperstarNotFoundException;
import com.manage.base.supplier.Pair;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.FileUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.model.dto.SuperstarDto;
import com.manage.kernel.core.model.parser.SuperstarParser;
import com.manage.kernel.core.admin.service.business.ISuperStarService;
import com.manage.kernel.jpa.entity.JzSuperStar;
import com.manage.kernel.jpa.repository.JzSuperStarRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bert on 2017/10/14.
 */
@Service
public class SuperstarService implements ISuperStarService {

    @Autowired
    private JzSuperStarRepo superStarRepo;

    @Override
    public PageResult pageList(PageQuery page, SuperstarDto query) {
        Page<JzSuperStar> results = superStarRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("status"), Status.ENABLE));
            if (StringUtil.isNotBlank(query.getName())) {
                list.add(cb.like(root.get("name"), "%" + query.getName() + "%"));
            }
            if (StringUtil.isNotBlank(query.getYear())) {
                list.add(cb.equal(root.get("year"), query.getYear()));
            }
            if (StringUtil.isNotBlank(query.getMonth())) {
                list.add(cb.equal(root.get("month"), query.getMonth()));
            }
            if (StringUtil.isNotBlank(query.getHonor())) {
                list.add(cb.equal(root.get("honor"), query.getHonor()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("id"));

        PageResult<SuperstarDto> pageResult = new PageResult<>();
        pageResult.setTotal(results.getTotalElements());
        pageResult.setRows(SuperstarParser.toDtoList(results.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public SuperstarDto saveSuperstar(SuperstarDto superstarDto) {

        JzSuperStar superStar = null;
        if (superstarDto.getId() == null) {
            superStar = new JzSuperStar();
            setSuperStarInfo(superStar, superstarDto);
            superStar.setCreatedAt(LocalDateTime.now());
            superStar.setCreatedUser(SessionHelper.user());
            superStar.setStatus(Status.ENABLE);
        } else {
            superStar = superStarRepo.findOne(superstarDto.getId());
            if (superStar == null) {
                throw new SuperstarNotFoundException();
            }
            setSuperStarInfo(superStar, superstarDto);
            superStar.setUpdatedAt(LocalDateTime.now());
            superStar.setUpdatedUser(SessionHelper.user());
        }

        superStar = superStarRepo.save(superStar);
        return SuperstarParser.toDto(superStar);
    }

    private void setSuperStarInfo(JzSuperStar superStar, SuperstarDto superstarDto) {
        superStar.setYear(superstarDto.getYear());
        superStar.setMonth(superstarDto.getMonth());
        superStar.setHonor(superstarDto.getHonor());
        superStar.setName(superstarDto.getName());
        superStar.setStory(superstarDto.getStory());
        Pair<byte[], String> imagePair = FileUtil.imageBase64ToByte(superstarDto.getImageBase64());
        if (imagePair != null) {
            superStar.setPhoto(imagePair.getLeft());
            superStar.setSuffix(imagePair.getRight());
        }
    }

    @Override
    @Transactional
    public SuperstarDto detail(Long id) {

        JzSuperStar superStar = superStarRepo.findOne(id);
        if (superStar == null) {
            throw new SuperstarNotFoundException();
        }

        SuperstarDto superstarDto = SuperstarParser.toDto(superStar);
        if (superStar.getPhoto() != null) {
            superstarDto.setImageBase64(FileUtil.imageByteToBase64(superStar.getPhoto(), superStar.getSuffix()));
        }
        superstarDto.setCreatedAt(superStar.getCreatedAt());
        superstarDto.setUpdatedAt(superStar.getUpdatedAt());
        superstarDto.setCreatedBy(superStar.getCreatedUserName());
        superstarDto.setUpdatedBy(superStar.getUpdatedUserName());
        return superstarDto;
    }

    @Override
    @Transactional
    public void drop(Long id) {
        JzSuperStar superStar = superStarRepo.findOne(id);
        if (superStar == null) {
            throw new SuperstarNotFoundException();
        }
        superStar.setStatus(Status.DELETE);
        superStarRepo.save(superStar);
    }
}
