package com.manage.kernel.core.admin.service.business;

import com.manage.kernel.core.admin.dto.NewsDto;
import com.manage.kernel.core.admin.model.FileModel;
import java.io.IOException;

import com.manage.kernel.jpa.news.entity.News;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by bert on 17-8-25.
 */
public interface INewsService {

    public NewsDto addNews(NewsDto newsDto);
}
