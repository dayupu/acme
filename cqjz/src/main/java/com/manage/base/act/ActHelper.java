package com.manage.base.act;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by bert on 2017/10/4.
 */
public class ActHelper {

    private static final Logger LOGGER = LogManager.getLogger(ActHelper.class);

    private static final String LINK = "-";

    public static String businessKeyGen(ActSource source, Long id) {
        return source.getKey() + LINK + id;
    }

    public static ActBusiness actBusiness(String businessKey) {
        try {
            String[] args = businessKey.split(LINK);
            ActBusiness business = new ActBusiness();
            business.setSource(args[0]);
            business.setId(Long.valueOf(args[1]));
            return business;
        } catch (Exception e) {
            LOGGER.error("Analysis businessKey-[{}]", businessKey, e);
        }
        return null;
    }
}
