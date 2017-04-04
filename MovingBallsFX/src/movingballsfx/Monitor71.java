package movingballsfx;

public class Monitor71 extends Monitor {	
	@Override
	public void enterReader() throws InterruptedException {
		monLock.lock();
		try {
			while(writersActive != 0) {
				readersWaiting++;
				okToRead.await();
				readersWaiting--;
			}
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
			while(writersActive > 0 || readersActive > 0) { okToWrite.await(); }
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
			if(readersWaiting > 0) { 
				okToRead.signalAll();
			} else {
				okToWrite.signal();
			}
		}
		finally {
			monLock.unlock();
		}
	}
}