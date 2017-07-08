package com.example.admin.ttt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ADMIN on 7/8/2017.
 */

public class Edu {
    @SerializedName("education")
    @Expose
    private List<Education> education = null;
    @SerializedName("id")
    @Expose
    private String id;

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
