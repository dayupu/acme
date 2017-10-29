package com.manage.kernel.core.model.dto;

import com.manage.kernel.jpa.entity.AdMenu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuNav implements Serializable {

    private static final long serialVersionUID = 3648786481665816491L;

    private Long id;
    private String name;
    private String image;
    private String url;
    private int level;
    private List<MenuNav> subMenus;

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

    public List<MenuNav> getSubMenus() {
        if (subMenus == null) {
            subMenus = new ArrayList<MenuNav>();
        }
        return subMenus;
    }

    public void setSubMenus(List<MenuNav> subMenus) {
        this.subMenus = subMenus;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static MenuNav forMenu(AdMenu menu){
        MenuNav menuNav = new MenuNav();
        menuNav.setId(menu.getId());
        menuNav.setName(menu.getName());
        menuNav.setImage(menu.getImage());
        menuNav.setUrl(menu.getUrl());
        menuNav.setLevel(menu.getLevel());
        return menuNav;
    }

}
