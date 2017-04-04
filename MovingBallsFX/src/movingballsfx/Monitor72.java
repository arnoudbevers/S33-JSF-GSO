package movingballsfx;

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

}
