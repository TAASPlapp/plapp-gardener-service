package com.plappgardenerservice.entities;
import java.io.Serializable;
import java.util.Date;

/* This class is used to describe a composite primary key */
public class ScheduleActionID implements Serializable {
    private long userId;
    private long plantId;
    private String action;
    private Date date;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPlantId() {
        return plantId;
    }

    public void setPlantId(long plantId) {
        this.plantId = plantId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
