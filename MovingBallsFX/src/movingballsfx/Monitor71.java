package movingballsfx;

import javafx.scene.paint.Color;

public class Monitor71 extends Monitor {

    @Override
    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive != 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                okToWrite.await();
            }
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (readersWaiting > 0) {
                okToRead.signalAll();
            } else {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public synchronized void remove(int state, Color ballColor) {
        switch (state) {
            case 2:
                if (ballColor == Color.RED) {
                    this.readersWaiting--;
                    if (readersWaiting == 0 && writersWaiting > 0 && readersActive == 0 && writersActive == 0) {
                        okToWrite.signal();
                    }
                } else {
                    this.writersWaiting--;
                    if (writersWaiting == 0 && readersWaiting > 0 && writersActive == 0 && readersActive >= 0) {
                        okToRead.signalAll();
                    }
                }
                break;
            case 3:
                if (ballColor == Color.RED) {
                    this.readersActive--;
                    if (readersActive == 0 && readersWaiting == 0 && writersWaiting > 0) {
                        okToWrite.signal();
                    }
                } else {
                    this.writersActive--;
                    if (writersActive == 0 && readersWaiting > 0) {
                        okToRead.signalAll();
                    }
                    else if(writersWaiting > 0 && readersActive == 0){
                        okToWrite.signal();
                    }
                    
                }
                break;
        }
    }
}
