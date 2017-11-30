package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.FileSource;
import com.manage.base.database.enums.FileType;
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
        @UniqueConstraint(columnNames = { "file_id" }, name = "uk_resource_file_with_file_id") })
@SequenceGenerator(name = "seq_resource_file", sequenceName = "seq_resource_file", allocationSize = 1)
public class ResourceFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resource_file")
    private Long id;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "size")
    private Long size;

    @Column(name = "dir")
    private String dir;

    @Column(name = "path")
    private String path;

    @Column(name = "created_at")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime createdAt;

    @Column(name = "source", nullable = false)
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.FileSource") })
    private FileSource source;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FileSource getSource() {
        return source;
    }

    public void setSource(FileSource source) {
        this.source = source;
    }
}
