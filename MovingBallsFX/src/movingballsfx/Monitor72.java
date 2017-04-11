package movingballsfx;

import javafx.scene.paint.Color;

public class Monitor72 extends Monitor {	
	@Override
	public void enterReader() throws InterruptedException {
		monLock.lock();
		try {
			while(writersActive != 0) { okToRead.await(); }
			readersActive++;
		}
		finally {
			monLock.unlock();
		}
	}
	
	@Override
	public void exitReader() {
		monLock.lock();
		try {
			readersActive--;
			if(readersActive == 0) { okToWrite.signal(); }
		}
		finally {
			monLock.unlock();
		}
	}
	
	@Override
	public void enterWriter() throws InterruptedException {
		monLock.lock();
		try {
			while(writersActive > 0 || readersActive > 0) {
				writersWaiting++;
				okToWrite.await();
				writersWaiting--;
			}
			writersActive++;
		}
		finally {
			monLock.unlock();
		}
	}
	
	@Override
	public void exitWriter() {
		monLock.lock();
		try {
			writersActive--;
			if(writersWaiting > 0) { 
				okToWrite.signal();
			} else {
				okToRead.signalAll();
			}
		}
		finally {
			monLock.unlock();
		}
	}

	@Override
	public void ballInterrupted(int state, Color color) {
		monLock.lock();
		switch(state) {
    	case 2:
    		if(color == Color.RED) {
    			readersWaiting--;
    		} else {
    			writersWaiting--;
    		}
    		break;
    	case 3:
    		if(color == Color.RED) {
    			readersActive--;
    			if(readersActive == 0 && writersWaiting > 0) {
    				okToWrite.signal();
    			} else {
    				okToRead.signalAll();
    			}
    		} else {
    			writersActive--;
    			if(writersWaiting > 0 && readersActive == 0) {
    				okToWrite.signal();
    			} else {
    				okToRead.signalAll();
    			}
    		}
    		break;
    	}
		monLock.unlock();
	}
}
