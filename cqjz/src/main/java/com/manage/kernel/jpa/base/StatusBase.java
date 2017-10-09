package com.manage.kernel.jpa.base;

import com.manage.base.database.enums.Status;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@MappedSuperclass
public class StatusBase extends EntityBase {

    @Column(name = "status", length = 2)
    @Type(type = "com.manage.base.database.model.DBEnumType",
            parameters = {@Parameter(name = "enumClass", value = "com.manage.base.database.enums.Status")})
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
