package com.manage.kernel.spring;

import com.manage.base.utils.StringUtil;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by bert on 17-10-30.
 */
@Component
@ConfigurationProperties(prefix = "ueditor")
public class UeditorSupplier {

    private String imageActionName;
    private String imageFieldName;
    private String imageMaxSize;
    private List<String> imageAllowFiles;
    private String imageCompressEnable;
    private String imageCompressBorder;
    private String imageInsertAlign;
    private String imageUrlPrefix;
    private String imagePathFormat;
    private String scrawlActionName;
    private String scrawlFieldName;
    private String scrawlPathFormat;
    private String scrawlMaxSize;
    private String scrawlUrlPrefix;
    private String snapscreenActionName;
    private String snapscreenPathFormat;
    private String snapscreenUrlPrefix;
    private String snapscreenInsertAlign;
    private List<String> catcherLocalDomain;
    private String catcherActionName;
    private String catcherFieldName;
    private String catcherPathFormat;
    private String catcherUrlPrefix;
    private String catcherMaxSize;
    private List<String> catcherAllowFiles;
    private String videoActionName;
    private String videoFieldName;
    private String videoPathFormat;
    private String videoUrlPrefix;
    private String videoMaxSize;
    private List<String> videoAllowFiles;
    private String fileActionName;
    private String fileFieldName;
    private String filePathFormat;
    private String fileUrlPrefix;
    private String fileMaxSize;
    private List<String> fileAllowFiles;
    private String imageManagerActionName;
    private String imageManagerListPath;
    private String imageManagerListSize;
    private String imageManagerUrlPrefix;
    private String imageManagerInsertAlign;
    private List<String> imageManagerAllowFiles;
    private String fileManagerActionName;
    private String fileManagerListPath;
    private String fileManagerUrlPrefix;
    private String fileManagerListSize;
    private List<String> fileManagerAllowFiles;

    public String getImageActionName() {
        return imageActionName;
    }

    public void setImageActionName(String imageActionName) {
        this.imageActionName = imageActionName;
    }

    public String getImageFieldName() {
        return imageFieldName;
    }

    public void setImageFieldName(String imageFieldName) {
        this.imageFieldName = imageFieldName;
    }

    public String getImageMaxSize() {
        return imageMaxSize;
    }

    public void setImageMaxSize(String imageMaxSize) {
        this.imageMaxSize = imageMaxSize;
    }

    public List<String> getImageAllowFiles() {
        return imageAllowFiles;
    }

    public void setImageAllowFiles(String imageAllowFiles) {
        this.imageAllowFiles = StringUtil.strArrayToList(imageAllowFiles);
    }

    public String getImageCompressEnable() {
        return imageCompressEnable;
    }

    public void setImageCompressEnable(String imageCompressEnable) {
        this.imageCompressEnable = imageCompressEnable;
    }

    public String getImageCompressBorder() {
        return imageCompressBorder;
    }

    public void setImageCompressBorder(String imageCompressBorder) {
        this.imageCompressBorder = imageCompressBorder;
    }

    public String getImageInsertAlign() {
        return imageInsertAlign;
    }

    public void setImageInsertAlign(String imageInsertAlign) {
        this.imageInsertAlign = imageInsertAlign;
    }

    public String getImageUrlPrefix() {
        return imageUrlPrefix;
    }

    public void setImageUrlPrefix(String imageUrlPrefix) {
        this.imageUrlPrefix = imageUrlPrefix;
    }

    public String getImagePathFormat() {
        return imagePathFormat;
    }

    public void setImagePathFormat(String imagePathFormat) {
        this.imagePathFormat = imagePathFormat;
    }

    public String getScrawlActionName() {
        return scrawlActionName;
    }

    public void setScrawlActionName(String scrawlActionName) {
        this.scrawlActionName = scrawlActionName;
    }

    public String getScrawlFieldName() {
        return scrawlFieldName;
    }

    public void setScrawlFieldName(String scrawlFieldName) {
        this.scrawlFieldName = scrawlFieldName;
    }

    public String getScrawlPathFormat() {
        return scrawlPathFormat;
    }

    public void setScrawlPathFormat(String scrawlPathFormat) {
        this.scrawlPathFormat = scrawlPathFormat;
    }

    public String getScrawlMaxSize() {
        return scrawlMaxSize;
    }

    public void setScrawlMaxSize(String scrawlMaxSize) {
        this.scrawlMaxSize = scrawlMaxSize;
    }

    public String getScrawlUrlPrefix() {
        return scrawlUrlPrefix;
    }

    public void setScrawlUrlPrefix(String scrawlUrlPrefix) {
        this.scrawlUrlPrefix = scrawlUrlPrefix;
    }

    public String getSnapscreenActionName() {
        return snapscreenActionName;
    }

    public void setSnapscreenActionName(String snapscreenActionName) {
        this.snapscreenActionName = snapscreenActionName;
    }

    public String getSnapscreenPathFormat() {
        return snapscreenPathFormat;
    }

    public void setSnapscreenPathFormat(String snapscreenPathFormat) {
        this.snapscreenPathFormat = snapscreenPathFormat;
    }

    public String getSnapscreenUrlPrefix() {
        return snapscreenUrlPrefix;
    }

    public void setSnapscreenUrlPrefix(String snapscreenUrlPrefix) {
        this.snapscreenUrlPrefix = snapscreenUrlPrefix;
    }

    public String getSnapscreenInsertAlign() {
        return snapscreenInsertAlign;
    }

    public void setSnapscreenInsertAlign(String snapscreenInsertAlign) {
        this.snapscreenInsertAlign = snapscreenInsertAlign;
    }

    public List<String> getCatcherLocalDomain() {
        return catcherLocalDomain;
    }

    public void setCatcherLocalDomain(String catcherLocalDomain) {
        this.catcherLocalDomain = StringUtil.strArrayToList(catcherLocalDomain);
    }

    public String getCatcherActionName() {
        return catcherActionName;
    }

    public void setCatcherActionName(String catcherActionName) {
        this.catcherActionName = catcherActionName;
    }

    public String getCatcherFieldName() {
        return catcherFieldName;
    }

    public void setCatcherFieldName(String catcherFieldName) {
        this.catcherFieldName = catcherFieldName;
    }

    public String getCatcherPathFormat() {
        return catcherPathFormat;
    }

    public void setCatcherPathFormat(String catcherPathFormat) {
        this.catcherPathFormat = catcherPathFormat;
    }

    public String getCatcherUrlPrefix() {
        return catcherUrlPrefix;
    }

    public void setCatcherUrlPrefix(String catcherUrlPrefix) {
        this.catcherUrlPrefix = catcherUrlPrefix;
    }

    public String getCatcherMaxSize() {
        return catcherMaxSize;
    }

    public void setCatcherMaxSize(String catcherMaxSize) {
        this.catcherMaxSize = catcherMaxSize;
    }

    public List<String> getCatcherAllowFiles() {
        return catcherAllowFiles;
    }

    public void setCatcherAllowFiles(String catcherAllowFiles) {
        this.catcherAllowFiles = StringUtil.strArrayToList(catcherAllowFiles);
    }

    public String getVideoActionName() {
        return videoActionName;
    }

    public void setVideoActionName(String videoActionName) {
        this.videoActionName = videoActionName;
    }

    public String getVideoFieldName() {
        return videoFieldName;
    }

    public void setVideoFieldName(String videoFieldName) {
        this.videoFieldName = videoFieldName;
    }

    public String getVideoPathFormat() {
        return videoPathFormat;
    }

    public void setVideoPathFormat(String videoPathFormat) {
        this.videoPathFormat = videoPathFormat;
    }

    public String getVideoUrlPrefix() {
        return videoUrlPrefix;
    }

    public void setVideoUrlPrefix(String videoUrlPrefix) {
        this.videoUrlPrefix = videoUrlPrefix;
    }

    public String getVideoMaxSize() {
        return videoMaxSize;
    }

    public void setVideoMaxSize(String videoMaxSize) {
        this.videoMaxSize = videoMaxSize;
    }

    public List<String> getVideoAllowFiles() {
        return videoAllowFiles;
    }

    public void setVideoAllowFiles(String videoAllowFiles) {
        this.videoAllowFiles = StringUtil.strArrayToList(videoAllowFiles);
    }

    public String getFileActionName() {
        return fileActionName;
    }

    public void setFileActionName(String fileActionName) {
        this.fileActionName = fileActionName;
    }

    public String getFileFieldName() {
        return fileFieldName;
    }

    public void setFileFieldName(String fileFieldName) {
        this.fileFieldName = fileFieldName;
    }

    public String getFilePathFormat() {
        return filePathFormat;
    }

    public void setFilePathFormat(String filePathFormat) {
        this.filePathFormat = filePathFormat;
    }

    public String getFileUrlPrefix() {
        return fileUrlPrefix;
    }

    public void setFileUrlPrefix(String fileUrlPrefix) {
        this.fileUrlPrefix = fileUrlPrefix;
    }

    public String getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(String fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    public List<String> getFileAllowFiles() {
        return fileAllowFiles;
    }

    public void setFileAllowFiles(String fileAllowFiles) {
        this.fileAllowFiles = StringUtil.strArrayToList(fileAllowFiles);
    }

    public String getImageManagerActionName() {
        return imageManagerActionName;
    }

    public void setImageManagerActionName(String imageManagerActionName) {
        this.imageManagerActionName = imageManagerActionName;
    }

    public String getImageManagerListPath() {
        return imageManagerListPath;
    }

    public void setImageManagerListPath(String imageManagerListPath) {
        this.imageManagerListPath = imageManagerListPath;
    }

    public String getImageManagerListSize() {
        return imageManagerListSize;
    }

    public void setImageManagerListSize(String imageManagerListSize) {
        this.imageManagerListSize = imageManagerListSize;
    }

    public String getImageManagerUrlPrefix() {
        return imageManagerUrlPrefix;
    }

    public void setImageManagerUrlPrefix(String imageManagerUrlPrefix) {
        this.imageManagerUrlPrefix = imageManagerUrlPrefix;
    }

    public String getImageManagerInsertAlign() {
        return imageManagerInsertAlign;
    }

    public void setImageManagerInsertAlign(String imageManagerInsertAlign) {
        this.imageManagerInsertAlign = imageManagerInsertAlign;
    }

    public List<String> getImageManagerAllowFiles() {
        return imageManagerAllowFiles;
    }

    public void setImageManagerAllowFiles(String imageManagerAllowFiles) {
        this.imageManagerAllowFiles = StringUtil.strArrayToList(imageManagerAllowFiles);
    }

    public String getFileManagerActionName() {
        return fileManagerActionName;
    }

    public void setFileManagerActionName(String fileManagerActionName) {
        this.fileManagerActionName = fileManagerActionName;
    }

    public String getFileManagerListPath() {
        return fileManagerListPath;
    }

    public void setFileManagerListPath(String fileManagerListPath) {
        this.fileManagerListPath = fileManagerListPath;
    }

    public String getFileManagerUrlPrefix() {
        return fileManagerUrlPrefix;
    }

    public void setFileManagerUrlPrefix(String fileManagerUrlPrefix) {
        this.fileManagerUrlPrefix = fileManagerUrlPrefix;
    }

    public String getFileManagerListSize() {
        return fileManagerListSize;
    }

    public void setFileManagerListSize(String fileManagerListSize) {
        this.fileManagerListSize = fileManagerListSize;
    }

    public List<String> getFileManagerAllowFiles() {
        return fileManagerAllowFiles;
    }

    public void setFileManagerAllowFiles(String fileManagerAllowFiles) {
        this.fileManagerAllowFiles = StringUtil.strArrayToList(fileManagerAllowFiles);
    }
}
