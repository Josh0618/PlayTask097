package com.jnu.student.data;

import java.io.Serializable;

public class Mission implements Serializable {

    private String name;
    private int point;
    private int complete = 0;
    private int times;

    public Mission(String name, int point, int times) {
        this.name=name;
        this.point=point;
        this.times=times;
    }

    public String getName() {
        return name;
    }

    //返回任务积分
    public int getPoint() {
        return point;
    }

    public boolean isCompleted() {
        if (this.complete == this.times){
            return true;
        }
        else {
            return false;
        }
    }

    public void setComplete(int complete){
        this.complete = complete;
    }

    public int getComplete(){
        return this.complete;
    }

    public int getTimes(){
        return this.times;
    }
}