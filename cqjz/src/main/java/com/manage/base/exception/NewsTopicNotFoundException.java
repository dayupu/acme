package com.manage.base.exception;

import com.manage.base.supplier.msgs.MessageErrors;

/**
 * Created by bert on 17-8-8.
 */
public class NewsTopicNotFoundException extends NotFoundException {

    public NewsTopicNotFoundException() {
        super(MessageErrors.NEWS_TOPIC_NOT_FOUND);
    }

}
