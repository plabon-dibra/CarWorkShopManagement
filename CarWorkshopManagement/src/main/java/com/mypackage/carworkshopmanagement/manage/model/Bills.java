package com.mypackage.carworkshopmanagement.manage.model;

import java.util.Date;

public class Bills {
    private int id;
    private String executor;
    private String estimatedDuration;
    private String frameNumber;
    private String procedureIds;
    private String problemIds;
    private String partsAndMaterialsIdsWithQuantity;
    private String phoneNumber;
    private Date date;

    public Bills(String phoneNumber, String frameNumber, String procedureIds, String problemIds, String partsAndMaterialsIdsWithQuantity, String executor, String estimatedDuration) {
        this.id = 0;
        this.phoneNumber = phoneNumber;
        this.frameNumber = frameNumber;
        this.procedureIds = procedureIds;
        this.problemIds = problemIds;
        this.partsAndMaterialsIdsWithQuantity = partsAndMaterialsIdsWithQuantity;
        this.executor = executor;
        this.estimatedDuration = estimatedDuration;
    }

    public Bills(int id,String phoneNumber, String frameNumber, String procedureIds, String problemIds, String partsAndMaterialsIdsWithQuantity, String executor, String estimatedDuration, Date date) {
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.frameNumber = frameNumber;
        this.procedureIds = procedureIds;
        this.problemIds = problemIds;
        this.partsAndMaterialsIdsWithQuantity = partsAndMaterialsIdsWithQuantity;
        this.executor = executor;
        this.estimatedDuration = estimatedDuration;
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getProcedureIds() {
        return procedureIds;
    }

    public void setProcedureIds(String procedureIds) {
        this.procedureIds = procedureIds;
    }

    public String getProblemIds() {
        return problemIds;
    }

    public void setProblemIds(String problemIds) {
        this.problemIds = problemIds;
    }

    public String getPartsAndMaterialsIdsWithQuantity() {
        return partsAndMaterialsIdsWithQuantity;
    }

    public void setPartsAndMaterialsIdsWithQuantity(String partsAndMaterialsIdsWithQuantity) {
        this.partsAndMaterialsIdsWithQuantity = partsAndMaterialsIdsWithQuantity;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(String estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
