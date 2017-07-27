package com.manage.news.dto;

import com.manage.news.jpa.kernel.entity.Menu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuNav {

    private Long id;
    private String name;
    private String image;
    private String url;

    private List<Location> locations;
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        Location.sortByLevelAsc(locations);
        this.locations = locations;
    }

    public static class Location {
        private int level;
        private String name;
        private String url;

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static void sortByLevelAsc(List<Location> relation) {
            Collections.sort(relation, (first, second) -> {
                if (first.getLevel() < second.getLevel()) {
                    return -1;
                }
                if (first.getLevel() > second.getLevel()) {
                    return 1;
                }
                return 0;
            });
        }

        public static Location forMenu(Menu menu){
            Location location = new Location();
            location.setName(menu.getName());
            location.setUrl(menu.getUrl());
            location.setLevel(menu.getLevel());
            return location;
        }

    }

    public static MenuNav forMenu(Menu menu){
        MenuNav menuNav = new MenuNav();
        menuNav.setId(menu.getId());
        menuNav.setName(menu.getName());
        menuNav.setImage(menu.getImage());
        menuNav.setUrl(menu.getUrl());
        return menuNav;
    }

}
