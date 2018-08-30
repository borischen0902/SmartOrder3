package com.example.boris.smartorder3;

import java.io.Serializable;

public class CStatus implements Serializable {
private int tableID;
private String startTime;
private String endTime;
private int currentStatus;

    public CStatus(int tableID, String startTime, String endTime, int currentStatus) {
        this.tableID = tableID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentStatus = currentStatus;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(int currentStatus) {
        this.currentStatus = currentStatus;
    }
}
