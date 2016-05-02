package com.xeranta.dev.approvalapp.model.entity;

public class ItemTask {

    private String taskId;
    private String taskName;
    private String taskDesc;
    private String taskStatus;
    private String createDate;

    private String approvedBy;
    private String approvedDate;

    public ItemTask() {

    }

    public ItemTask(String taskId, String taskName, String taskDesc, String taskStatus, String createDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskStatus = taskStatus;
        this.createDate = createDate;
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

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }
}
