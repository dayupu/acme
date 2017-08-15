package com.manage.kernel.jpa.news.base;

import com.manage.base.extend.enums.Status;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@MappedSuperclass
public class StatusBase extends EntityBase {

    @Column(name = "status", length = 2)
    @Type(type = "com.manage.base.extend.define.DBEnumType",
            parameters = {@Parameter(name = "enumClass", value = "com.manage.base.extend.enums.Status")})
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
