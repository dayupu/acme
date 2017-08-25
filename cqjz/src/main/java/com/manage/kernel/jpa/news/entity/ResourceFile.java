package com.manage.kernel.jpa.news.entity;

import com.manage.base.extend.enums.FileSource;
import com.manage.base.extend.enums.FileType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-8-25.
 */
@Entity
@Table(name = "resource_file", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "file_id" }, name = "resource_file_key_file_id") })
@SequenceGenerator(name = "seq_resource_file", sequenceName = "seq_resource_file", allocationSize = 1)
public class ResourceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resource_file")
    private Long id;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "source", nullable = false)
    @Type(type = "com.manage.base.extend.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.extend.enums.FileSource") })
    private FileSource source;

    @Column(name = "type", nullable = false)
    @Type(type = "com.manage.base.extend.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.extend.enums.FileType") })
    private FileType type;

    @Column(name = "local_path")
    private String localPath;

    @Column(name = "access_url")
    private String accessUrl;

    @Column(name = "created_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public FileSource getSource() {
        return source;
    }

    public void setSource(FileSource source) {
        this.source = source;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
