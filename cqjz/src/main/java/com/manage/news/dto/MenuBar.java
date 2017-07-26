package com.manage.news.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuBar {

    private Long id;
    private String name;
    private String image;
    private String url;
    private List<String> locations;
    private List<MenuBar> subMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuBar> getSubMenus() {
        if (subMenus == null) {
            subMenus = new ArrayList<MenuBar>();
        }
        return subMenus;
    }

    public void setSubMenus(List<MenuBar> subMenus) {
        this.subMenus = subMenus;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
