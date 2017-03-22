/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author arnoudbevers
 */
public class TimeSpan2 implements ITimeSpan{

    private ITime bt;
    private long duration;
    
    public TimeSpan2(ITime bt, ITime et){
        this.bt = bt;
        this.duration = et.difference(bt);
    }
    
    @Override
    public ITime getBeginTime() {
        return bt;
    }

    @Override
    public ITime getEndTime() {
        return this.bt.plus((int)duration);
    }

    @Override
    public int length() {
        return (int)this.duration;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if(beginTime.compareTo(this.getEndTime()) >= 0){
            throw new IllegalArgumentException("Begin time" +
                bt + "must be earlier than end time" + this.getEndTime());
        }
    }

    @Override
    public void setEndTime(ITime endTime) {
       if(this.getEndTime().compareTo(this.getBeginTime()) <= 0){
            throw new IllegalArgumentException("End time" +
                this.getEndTime() + "must be later than begin time"+ bt);
        } 
    }

    @Override
    public void move(int minutes) {
        this.setBeginTime(this.getBeginTime().plus(minutes));
        this.duration += minutes;
        this.setEndTime(this.bt.plus((int)duration));
    }

    @Override
    public void changeLengthWith(int minutes) {
        this.duration += minutes;
        this.setEndTime(this.bt.plus((int)duration));
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return (getBeginTime().compareTo(timeSpan.getBeginTime()) >= 0
                && getEndTime().compareTo(timeSpan.getEndTime()) <= 0);
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        if (bt.compareTo(timeSpan.getEndTime()) < 0 || getEndTime().compareTo(timeSpan.getBeginTime()) > 0) {
            return null;
        }
        
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan(begintime, endtime);
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        if (begintime.compareTo(endtime) >= 0) {
            return null;
        }

        return new TimeSpan(begintime, endtime);
    }
    
}
