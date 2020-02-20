package com.plappgardenerservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;



@Entity
@IdClass(ScheduleID.class)
public class Schedule {

    //@GeneratedValue
    //@Id
    //private long id;
    //@NotNull
    @Id
    private long plantID;
    @Id
    private String action;
    private Date date;
    private int periodicity;

    public Schedule() {}

    public Schedule(long plantID, Date date, String action, int periodicity) {
        this.plantID = plantID;
        this.date = date;
        this.action = action;
        this.periodicity = periodicity;
    }

    /*<
    public long getId() {
        return id;
    }
     */

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
                ", plantID=" + plantID +
                ", date=" + date +
                ", action='" + action +
                ", periodicity='" + periodicity + '\'' +
                '}';
    }
}
