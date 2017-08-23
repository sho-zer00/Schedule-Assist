package com.example.android.sample.sotuken;

/**
 * Created by sho on 2017/08/02.
 */

public class PlanListItem  {

    private int id = 0;
    private String title = null;
    private String time = null;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTime(){
        return time;
    }


    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setTime(String time){
        this.time = time;
    }

}
