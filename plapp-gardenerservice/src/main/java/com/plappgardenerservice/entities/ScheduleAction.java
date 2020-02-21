package com.plappgardenerservice.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;



@Entity
@IdClass(ScheduleActionID.class)
public class ScheduleAction implements Serializable{

    //@GeneratedValue
    //@Id
    //private long id;
    //@NotNull
    @Id
    private long plantID;
    @Id
    private String action;
    @Id
    private Date date;
    private int periodicity;

    public ScheduleAction() {}

    public ScheduleAction(long plantID, Date date, String action, int periodicity) {
        this.plantID = plantID;
        this.date = date;
        this.action = action;
        this.periodicity = periodicity;
    }

    public long getPlantID() {
        return plantID;
    }

    public Date getDate() {
        return date;
    }

    public String getAction() {
        return action;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    @Override
    public String toString() {
        return "Schedule{" +
              //  "id=" + id +
                "plantID=" + plantID +
                ", date=" + date +
                ", action='" + action +
                ", periodicity='" + periodicity + '\'' +
                '}';
    }
}
