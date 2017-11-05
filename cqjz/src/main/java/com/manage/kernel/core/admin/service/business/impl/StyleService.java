package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.exception.StyleNotFoundException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.service.business.IStyleService;
import com.manage.kernel.core.model.dto.StyleDto;
import com.manage.kernel.core.model.parser.StyleParser;
import com.manage.kernel.jpa.entity.JzStyle;
import com.manage.kernel.jpa.entity.JzStyleLine;
import com.manage.kernel.jpa.repository.JzStyleRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    @Transactional
    public StyleDto saveStyle(StyleDto styleDto) {

        JzStyle style = new JzStyle();
        if (StringUtil.isBlank(styleDto.getNumber())) {
            style.setNumber(CoreUtil.nextRandomID());
        } else {
            style = styleRepo.findOne(styleDto.getNumber());
            if (style == null) {
                throw new StyleNotFoundException();
            }
        }
        List<JzStyleLine> styleLines = style.getStyleLines();
        JzStyleLine jzStyleLine;
        int sequence = 0;
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
    public PageResult pageList(PageQuery page, StyleDto query) {

        return null;
    }
}
