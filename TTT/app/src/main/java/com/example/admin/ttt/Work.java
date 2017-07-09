package com.example.admin.ttt;

import com.example.admin.ttt.Employer;
import com.example.admin.ttt.Location;
import com.example.admin.ttt.Position;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 7/9/2017.
 */

public class Work {
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("employer")
    @Expose
    private Employer employer;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("position")
    @Expose
    private Position position;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("id")
    @Expose
    private String id;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
