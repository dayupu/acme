package com.manage.kernel.jpa.entity;

import com.manage.base.database.enums.ActProcess;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Created by bert on 17-10-9.
 */
@Entity
@Table(name = "act_approve_task")
@SequenceGenerator(name = "seq_act_approve_task", sequenceName = "seq_act_approve_task", allocationSize = 1)
public class ActApproveTask {

    @Id
    @Column(name = "id_")
    @GeneratedValue(generator = "seq_act_approve_task", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "proc_inst_id_")
    private String procInstId;

    @Column(name = "business_key_")
    private String businessKey;

    @Column(name = "task_id_")
    private String taskId;

    @Column(name = "task_name_")
    private String taskName;

    @Column(name = "subject_")
    private String subject;

    @Column(name = "approve_user_")
    private String approveUser;

    @Column(name = "approve_time_")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime approveTime;

    @Column(name = "approve_result_", nullable = false, length = 3)
    @Type(type = "com.manage.base.database.model.DBEnumType", parameters = {
            @Parameter(name = "enumClass", value = "com.manage.base.database.enums.ActProcess") })
    private ActProcess approveResult;

    @Column(name = "approve_comment_")
    private String approveComment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser;
    }

    public LocalDateTime getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(LocalDateTime approveTime) {
        this.approveTime = approveTime;
    }

    public ActProcess getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(ActProcess approveResult) {
        this.approveResult = approveResult;
    }

    public String getApproveComment() {
        return approveComment;
    }

    public void setApproveComment(String approveComment) {
        this.approveComment = approveComment;
    }
}
