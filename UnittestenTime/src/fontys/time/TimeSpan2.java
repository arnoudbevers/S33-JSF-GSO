package fontys.time;

public class TimeSpan2 implements ITimeSpan{
	private ITime bt;
	private long duration;
	
	public TimeSpan2(ITime bt, ITime et){
		if (bt.compareTo(et) >= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + et);
        }
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
		if(beginTime.compareTo(this.getEndTime()) > 0){
			throw new IllegalArgumentException("Begintime" + bt +
					"must be earlier than endtime" + this.getEndTime());
		}
		this.bt = beginTime;
		
	}

	@Override
	public void setEndTime(ITime endTime) {
		if(endTime.compareTo(this.bt) < 0){
			throw new IllegalArgumentException("Endtime" + endTime +
					"must be later than begin time" + bt);
		}
		this.duration = endTime.difference(this.bt);
	}

	@Override
	public void move(int minutes) {
		this.bt = this.bt.plus(minutes);
		this.setEndTime(bt.plus((int)duration));		
	}

	@Override
	public void changeLengthWith(int minutes) {
		if(minutes <= 0){
			throw new IllegalArgumentException("minutes must be positive!");
		}
		this.setEndTime(getEndTime().plus((int)minutes));
	}

	@Override
	public boolean isPartOf(ITimeSpan timeSpan) {
		return (getBeginTime().compareTo(timeSpan.getBeginTime()) <= 0
                && getEndTime().compareTo(timeSpan.getEndTime()) >= 0);
	}

	@Override
	public ITimeSpan unionWith(ITimeSpan timeSpan) {
		if (bt.compareTo(timeSpan.getEndTime()) > 0 || this.getEndTime().compareTo(timeSpan.getBeginTime()) < 0) {
            return null;
        }
        
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) < 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }

        if (this.getEndTime().compareTo(timeSpan.getEndTime()) > 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan2(begintime, endtime);
	}

	@Override
	public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
		 	ITime begintime, endtime;
	        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
	            begintime = bt;
	        } else {
	            begintime = timeSpan.getBeginTime();
	        }
	        	
	        if (this.getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
	            endtime = this.getEndTime();
	        } else {
	            endtime = timeSpan.getEndTime();
	        }

	        if (begintime.compareTo(endtime) > 0) {
	            return null;
	        }

	        return new TimeSpan2(begintime, endtime);
	}
	
}
