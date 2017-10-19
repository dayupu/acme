package com.manage.base.act;

import com.manage.base.act.enums.ActSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by bert on 2017/10/4.
 */
public class ActBusiness {

    private static final Logger LOGGER = LogManager.getLogger(ActBusiness.class);
    private static final String LINK = "-";

    private Long id;
    private ActSource source;
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ActSource getSource() {
        return source;
    }

    public void setSource(String source) {
        for (ActSource actSource : ActSource.values()) {
            if (actSource.getKey().equals(source)) {
                this.source = actSource;
                break;
            }
        }
    }

    public void setSource(ActSource source) {
        this.source = source;
    }

    public String businessKey() {
        return source.getKey() + LINK + id + LINK + number;
    }

    public static ActBusiness fromBusinessKey(String businessKey) {
        try {
            String[] args = businessKey.split(LINK);
            ActBusiness business = new ActBusiness();
            business.setSource(args[0]);
            business.setId(Long.valueOf(args[1]));
            business.setNumber(args[2]);
            return business;
        } catch (Exception e) {
            LOGGER.error("Analysis businessKey-[{}]", businessKey, e);
        }
        return null;
    }
}
