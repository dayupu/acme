package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.database.enums.Status;
import com.manage.base.exception.StyleNotFoundException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.admin.service.business.IStyleService;
import com.manage.kernel.core.model.dto.StyleDto;
import com.manage.kernel.core.model.parser.StyleParser;
import com.manage.kernel.jpa.entity.JzStyle;
import com.manage.kernel.jpa.entity.JzStyleLine;
import com.manage.kernel.jpa.repository.JzStyleLineRepo;
import com.manage.kernel.jpa.repository.JzStyleRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import javax.persistence.criteria.Predicate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/11/5.
 */
@Service
public class StyleService implements IStyleService {

    @Autowired
    private JzStyleRepo styleRepo;
    @Autowired
    private JzStyleLineRepo styleLineRepo;

    @Override
    @Transactional
    public StyleDto saveStyle(StyleDto styleDto) {

        JzStyle style = new JzStyle();
        if (StringHandler.isBlank(styleDto.getNumber())) {
            style.setNumber(CoreUtil.nextRandomID());
            style.setTitle(styleDto.getTitle());
            style.setCreatedAt(LocalDateTime.now());
            style.setCreatedUser(SessionHelper.user());
            style.setStatus(Status.ENABLE);
        } else {
            style = styleRepo.findOne(styleDto.getNumber());
            if (style == null) {
                throw new StyleNotFoundException();
            }
            style.setTitle(styleDto.getTitle());
            style.setUpdatedAt(LocalDateTime.now());
            style.setUpdatedUser(SessionHelper.user());
        }

        List<JzStyleLine> styleLines = style.getStyleLines();
        for (JzStyleLine styleLine : styleLines) {
            styleLineRepo.delete(styleLine);
        }

        int sequence = 0;
        JzStyleLine jzStyleLine;
        styleLines.clear();
        for (StyleDto.StyleLine styleLineDto : styleDto.getStyleLines()) {
            jzStyleLine = new JzStyleLine();
            jzStyleLine.setNumber(style.getNumber());
            jzStyleLine.setImageId(styleLineDto.getImageId());
            jzStyleLine.setDescription(styleLineDto.getDescription());
            jzStyleLine.setSequence(++sequence);
            styleLines.add(jzStyleLine);
        }
        style = styleRepo.save(style);
        return StyleParser.toDto(style);
    }

    @Override
    @Transactional
    public PageResult pageList(PageQuery page, StyleDto query) {
        Page<JzStyle> stylePage = styleRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(root.get("status").in(Status.enableList()));
            if (StringHandler.isNotBlank(query.getTitle())) {
                list.add(cb.like(root.get("title"), "%" + query.getTitle() + "%"));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("createdAt"));

        PageResult<StyleDto> pageResult = new PageResult<>();
        pageResult.setTotal(stylePage.getTotalElements());
        pageResult.setRows(StyleParser.toDtoList(stylePage.getContent()));
        return pageResult;
    }

    @Override
    @Transactional
    public StyleDto styleDetail(String number) {
        JzStyle style = styleRepo.findOne(number);
        if (style == null) {
            throw new StyleNotFoundException();
        }
        return StyleParser.toDto(style);
    }

    @Override
    @Transactional
    public void dropStyle(String number) {
        JzStyle style = styleRepo.findOne(number);
        if (style == null) {
            throw new StyleNotFoundException();
        }

        style.setStatus(Status.DELETE);
        style.setUpdatedAt(LocalDateTime.now());
        style.setUpdatedUser(SessionHelper.user());
        styleRepo.save(style);
    }
}
