package com.example.android.sample.sotuken.simpleline;

/**
 * Created by sho on 2017/11/29.
 */

public class GraphItem {

    private int id = 0;
    private String achievement;
    private String resist;

    public void setId(int id) {
        this.id = id;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public void setResist(String resist) {
        this.resist = resist;
    }

    public long getId() {
        return id;
    }

    public String getAchievement() {
        return achievement;
    }

    public String getResist() {
        return resist;
    }
}
