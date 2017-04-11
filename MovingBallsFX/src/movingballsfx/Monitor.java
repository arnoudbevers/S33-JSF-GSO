package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.scene.paint.Color;

public abstract class Monitor {
	public int readersActive;
	public int readersWaiting;
	public int writersActive;
	public int writersWaiting;
	
	protected Lock monLock = new ReentrantLock();
	protected Condition okToRead = monLock.newCondition();
	protected Condition okToWrite = monLock.newCondition();
	
	public abstract void enterReader() throws InterruptedException;
	public abstract void exitReader();
	public abstract void enterWriter() throws InterruptedException;
	public abstract void exitWriter();
	
	public abstract void ballInterrupted(int state, Color color);
}
