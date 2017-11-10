package com.manage.base.act.support;

import com.manage.base.database.enums.FlowSource;
import com.manage.base.database.enums.NewsType;
import com.manage.kernel.core.admin.service.business.INewsService;
import com.manage.kernel.core.admin.service.business.impl.NewsService;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.SpringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by bert on 2017/10/4.
 */
public class ActBusiness {

    private static final Logger LOGGER = LogManager.getLogger(ActBusiness.class);
    private static final String LINK = "-";

    private FlowSource source;
    private String businessId;
    private String typeName;

    private static INewsService newsService;

    public static INewsService getNewsService() {
        if (newsService == null) {
            newsService = SpringUtils.getBean(NewsService.class);
        }
        return newsService;
    }

    private void setSource(String flowSource) {
        for (FlowSource source : FlowSource.values()) {
            if (source.getCode().equals(flowSource)) {
                this.source = source;
                break;
            }
        }
    }

    public static String join(Object... args) {
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(String.valueOf(arg)).append(LINK);
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    protected static String businessKey(ActFlowInfo flowInfo) {
        StringBuilder businessId = new StringBuilder();
        businessId.append(flowInfo.getFlowSource().getCode());
        businessId.append(LINK);
        businessId.append(flowInfo.getType());
        businessId.append(LINK);
        if (flowInfo.getSubType() != null) {
            businessId.append(flowInfo.getSubType());
            businessId.append(LINK);
        }
        businessId.append(flowInfo.getTargetId());
        return businessId.toString();
    }

    public static ActBusiness fromBusinessKey(String businessKey) {
        try {
            String[] args = businessKey.split(LINK);
            ActBusiness business = new ActBusiness();
            business.setSource(args[0]);
            business.businessId = businessKey;

            if (business.getSource() == FlowSource.NEWS) {
                StringBuilder builder = new StringBuilder();
                builder.append(getNewsService().typeTopicName(Integer.parseInt(args[1])));
                builder.append(LINK);
                builder.append(getNewsService().typeTopicName(Integer.parseInt(args[2])));
                business.typeName = builder.toString();
            }
            return business;
        } catch (Exception e) {
            LOGGER.error("Analysis businessKey-[{}]", businessKey, e);
            return null;
        }
    }

    public FlowSource getSource() {
        return source;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getTypeName() {
        return typeName;
    }
}
