package com.manage.kernel.core.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.manage.base.database.serialize.LocalDateTimeDeserializer;
import com.manage.base.database.serialize.LocalDateTimeSerializer;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/14.
 */
public class StyleDto {

    private String number;
    private String title;
    private List<StyleLine> styleLines = new ArrayList<>();

    public static class StyleLine {

        private String imageId;
        private String description;

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StyleLine> getStyleLines() {
        return styleLines;
    }

    public void setStyleLines(List<StyleLine> styleLines) {
        this.styleLines = styleLines;
    }
}
