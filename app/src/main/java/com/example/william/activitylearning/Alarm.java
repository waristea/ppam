package com.example.william.activitylearning;

/**
 * Created by William on 02/02/2018.
 */

public class Alarm {
    String[] daysCode = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    public String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    protected int[] activeDays = {1,2,3,4,5,6,7}; // 1-7
    protected int hour = 5; // 0-23
    protected int minute = 0; // 0-59
    protected String description = "";
    protected boolean isActive = false;

    Alarm(int[] activeDays, int hour, int minute){
        boolean found_err = false;
        for (int d : activeDays) {
            if (!found_err){
                found_err = (d<1||d>7);
            }
        }

        if (!found_err){
            this.activeDays = activeDays;
        }

        if (hour>=0 && hour<=23){
            this.hour = hour;
        }
        if (minute>=0 && minute<=59){
            this.minute = minute;
        }

        this.isActive = true;
        setDescriptionAuto();
    }

    public void setActiveDays(int[] activeDays) {
        this.activeDays = activeDays;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDescriptionAuto(){
        String descriptionTemp = "";

        for (int d : activeDays) {
            descriptionTemp += daysCode[d] + " ";
        }

        this.description = descriptionTemp;
    }

    public void toggleAlarm(){
        this.isActive = !this.isActive;
    }

    public int[] getActiveDays() {
        return activeDays;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTime(){
        String h = Integer.toString(hour);
        String m = Integer.toString(minute);
        if (hour<10){
            h = "0" + h;
        }
        if (minute<10){
            m = "0" + m;
        }

        return h+":"+m;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }
}
