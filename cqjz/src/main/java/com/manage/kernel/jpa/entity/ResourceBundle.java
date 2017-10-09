package com.manage.kernel.jpa.entity;

import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "resource_bundle", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "key", "locale" }, name = "resource_bundle_key_local_ukey") })
@SequenceGenerator(name = "seq_resource_bundle", sequenceName = "seq_resource_bundle", allocationSize = 1)
public class ResourceBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_resource_bundle")
    private Long id;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(nullable = false)
    private Locale locale;

    @Column(nullable = true)
    private String country;

    @Column(nullable = true)
    private Boolean variant = Boolean.FALSE;

    @Column(nullable = true)
    private Boolean tooltip = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getVariant() {
        return variant;
    }

    public void setVariant(Boolean variant) {
        this.variant = variant;
    }

    public Boolean getTooltip() {
        return tooltip;
    }

    public void setTooltip(Boolean tooltip) {
        this.tooltip = tooltip;
    }
}
