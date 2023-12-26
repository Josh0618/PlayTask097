package com.jnu.student.data;

import java.io.Serializable;

public class Reward implements Serializable {
    private String name;
    private int point;
    private int complete = 0;
    private int type;

    public Reward(String name, int point, int type) {
        this.name=name;
        this.point=point;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    //返回任务积分
    public int getPoint() {
        return point;
    }


    public void setComplete(int complete){
        this.complete = complete;
    }

    public int getComplete(){
        return this.complete;
    }

    public int getType(){
        return this.type;
    }
}
