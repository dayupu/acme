package com.manage.kernel.core.admin.service.impl;

import com.manage.base.extend.enums.FileSource;
import com.manage.base.utils.FileUtils;
import com.manage.kernel.core.admin.dto.FileDto;
import com.manage.kernel.core.admin.model.FileModel;
import com.manage.kernel.core.admin.service.IResourceFileService;
import com.manage.kernel.jpa.news.entity.ResourceFile;
import com.manage.kernel.jpa.news.repository.ResourceFileRepo;
import com.manage.kernel.spring.PropertySupplier;
import com.manage.kernel.spring.comm.ServiceBase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.transaction.Transactional;
import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

/**
 * Created by bert on 17-8-25.
 */
@Service
public class ResourceFileService extends ServiceBase implements IResourceFileService {

    @Autowired
    private PropertySupplier supplier;

    @Autowired
    private ResourceFileRepo resourceFileRepo;

    @Override
    @Transactional
    public FileDto upload(FileModel fileModel) {

        String localDir = localDir(fileModel.getFileSource());
        String extensionName = FileUtils.extensionName(fileModel.getFileName());
        String fileName = FileUtils.generateName(extensionName);
        String fullPath = localDir + fileName;
        FileOutputStream fos = null;
        InputStream is = null;
        try {
            fos = new FileOutputStream(new File(fullPath));
            is = fileModel.getInputStream();
            StreamUtils.copy(fileModel.getInputStream(), fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        ResourceFile resourceFile = new ResourceFile();
        resourceFile.setFileId(FileUtils.generateFileId());
        resourceFile.setFileName(fileName);
        resourceFile.setOriginName(fileModel.getFileName());
        resourceFile.setExtension(extensionName);
        resourceFile.setLocalPath(localPath(fullPath));
        resourceFile.setAccessUrl(supplier.getFileTempDir() + File.separator + fileName);
        resourceFile.setType(fileModel.getFileType());
        resourceFile.setSource(fileModel.getFileSource());
        resourceFile.setFileSize(fileModel.getFileSize());
        resourceFile.setCreatedAt(LocalDateTime.now());

        resourceFileRepo.save(resourceFile);
        return null;
    }

    @Override
    public FileDto getImage(String fileId) {

        ResourceFile resourceFile = resourceFileRepo.findByFileId(fileId);
        if (resourceFile == null) {
            return null;
        }

        String tempPath = tempPath();
        String filePath = tempPath + resourceFile.getAccessUrl();

        File image = new File(filePath);
        if (!image.exists()) {
            String localPath = supplier.getFileUploadLocal() + resourceFile.getLocalPath();
            File originFile = new File(localPath);
            if (!originFile.exists()) {
                return null;
            }
            boolean result = FileUtils.copyFile(originFile, image);
            if (!result) {
                return null;
            }
        }
        FileDto fileDto = new FileDto();
        fileDto.setAccessUrl(resourceFile.getAccessUrl());
        return fileDto;
    }

    private String localDir(FileSource source) {
        StringBuilder path = new StringBuilder();
        path.append(supplier.getFileUploadLocal());
        path.append(File.separator);
        path.append(source.getDir());
        path.append(File.separator);
        path.append(LocalDate.now().toString("yyyyMMdd"));
        path.append(File.separator);
        File sourceFile = new File(path.toString());
        if (!sourceFile.exists()) {
            sourceFile.mkdirs();
        }
        return path.toString();
    }

    private String localPath(String localPath) {
        return localPath.substring(supplier.getFileUploadLocal().length());
    }

    private String tempPath() {
        return ClassUtils.getDefaultClassLoader().getResource("public").getPath();
    }

    private synchronized String generateName() {
        return null;
    }

    public static void main(String[] args) {
        String result = FileUtils.generateName(".png");
        System.out.println(result);
    }
}
