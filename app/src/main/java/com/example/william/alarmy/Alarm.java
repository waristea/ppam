package com.example.william.alarmy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by William on 02/02/2018.
 */
public class Alarm implements Parcelable {
    private int id = 0; // Rule : request code setiap alarm = id*10+(1~jumlah active days)
    private int[] activeDays = {1,2,3,4,5,6,7}; // 1-7
    private int hour = 5; // 0-23
    private int minute = 0; // 0-59
    private String description = "";
    private boolean isActive = true;
    private String ringtone = "default_ringtone"; // simpan tanpa format
    private String game = "default_game"; //'no' for no game

    Alarm(){
        this.isActive = true;
        setDescriptionAuto();
    }

    Alarm(int[] activeDays, int hour, int minute){
        boolean found_err = false;
        for (int d : activeDays) {
            if (!found_err){
                found_err = (d<0||d>7);
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

    //Setter
    public void setId(int id){
        this.id = id;
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
    public void setActive(boolean active) {
        isActive = active;
    }
    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }
    public void setGame(String game) {
        this.game = game;
    }

    private void setDescriptionAuto(){
        String descriptionTemp = "";

        for (int d : activeDays) {
            if(d!=0) {
                descriptionTemp += Constant.daysCode[d - 1] + " ";
            }
        }

        this.description = descriptionTemp;
    }

    public void toggleAlarm(){
        this.isActive = !this.isActive;
    }

    // Getter
    public int getId(){
        return id;
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
    public String getDescription() {
        return description;
    }
    public boolean isActive() {
        return isActive;
    }
    public String getRingtone() {
        return ringtone;
    }
    public String getGame() {
        return game;
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

    public ArrayList<Calendar> getCalendarList(){
        ArrayList<Calendar> calendarList = new ArrayList<Calendar>();

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.HOUR_OF_DAY, hour);
        alarmCalendar.set(Calendar.MINUTE, minute);
        alarmCalendar.set(Calendar.SECOND, 0);

        for(int d : activeDays){
            if(d!=0){
                alarmCalendar.set(Calendar.DAY_OF_WEEK, Constant.dayOfWeek[d-1]);
                calendarList.add(alarmCalendar);
            }
        }

        return calendarList;
    }

    // Parcelling part
    // isActive gimana?
    protected Alarm(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
        int[] tempActiveDays = new int[7];
        in.readIntArray(tempActiveDays);

        ringtone = in.readString();
        game = in.readString();

        boolean found_err = false;
        for (int d : tempActiveDays) {
            if (!found_err){
                found_err = (d<0||d>7);
            }
        }

        if (!found_err){
            this.activeDays = tempActiveDays;
        }
        setDescriptionAuto();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeIntArray(activeDays);
        dest.writeString(ringtone);
        dest.writeString(game);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
