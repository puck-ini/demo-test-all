package org.zchzh.javademo.timewheel;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zengchzh
 * @date 2021/9/24
 */
public class TestTimeWheel {

    private TimeWheel[] wheels;

    private Queue<TimeTask> taskQueue = new LinkedBlockingQueue<>();

    private Work work = new Work();

    private Thread workThread;

    private volatile long startTime;

    private long tickDuration;

    private int mask;

    private CountDownLatch latch = new CountDownLatch(1);


    public TestTimeWheel(long tickDuration, TimeUnit unit, int ticksPerWheel) {
        this.wheels = createWheel(ticksPerWheel);
        this.tickDuration = unit.toNanos(tickDuration);
        this.mask = wheels.length - 1;
        workThread = new Thread(work);
    }


    private static TimeWheel[] createWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        TimeWheel[] wheels = new TimeWheel[normalizedTicksPerWheel];
        for (int i = 0; i < wheels.length; i++) {
            wheels[i] = new TimeWheel();
        }
        return wheels;
    }

    public TimeTask newTask(Runnable runnable, long expire, TimeUnit unit) {
        while (startTime == 0) {
            workThread.start();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long deadline = System.nanoTime() + unit.toNanos(expire) - startTime;

        TimeTask task = new TimeTask(runnable, deadline);
        taskQueue.add(task);
        return task;
    }

    class Work implements Runnable {

        private long tick;

        @Override
        public void run() {
            startTime = System.nanoTime();
            latch.countDown();
            do {
                final long deadline = waitForNextTick();
                int idx = (int) (tick & mask);
                TimeWheel wheel = wheels[idx];
                transferTimeoutsToBuckets();
                wheel.expireTask(deadline);
                tick++;
            } while (true);

        }

        private long waitForNextTick() {
            long deadline = tickDuration * (tick + 1);

            for (;;) {
                final long currentTime = System.nanoTime() - startTime;
                long sleepTimeMs = (deadline - currentTime + 999999) / 1000000;
                if (sleepTimeMs <= 0) {
                    return currentTime;
                }
                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void transferTimeoutsToBuckets() {
            // transfer only max. 100000 timeouts per tick to prevent a thread to stale the workerThread when it just
            // adds new timeouts in a loop.
            for (int i = 0; i < 100000; i++) {
                TimeTask task = taskQueue.poll();
                if (task == null) {
                    // all processed
                    break;
                }

                long calculated = task.getExpire() / tickDuration;
                task.setRound((calculated - tick) / wheels.length);

                final long ticks = Math.max(calculated, tick); // Ensure we don't schedule for past.
                int stopIndex = (int) (ticks & mask);

                TimeWheel bucket = wheels[stopIndex];
                bucket.addTask(task);
            }
        }
    }

    class TimeTask {

        TimeTask prev;

        TimeTask next;

        private long expire;

        private Runnable task;

        private long round;

        public TimeTask(Runnable task, long expire) {
            this.task = task;
            this.expire = expire;
        }

        public void doTask() {
            try {
                task.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        public long getRound() {
            return round;
        }

        public void setRound(long round) {
            this.round = round;
        }
    }


    static class TimeWheel {

        TimeTask head;

        TimeTask tail;

        public void addTask(TimeTask task) {
            if (head == null) {
                head = tail = task;
            } else {
                tail.next = task;
                task.prev = tail;
                tail = task;
            }
        }

        public void expireTask(long deadline) {
            TimeTask task = head;
            while (task != null) {
                TimeTask next = task.next;
                if (task.getRound() <= 0) {
                    next = remove(task);
                    if (task.getExpire() <= deadline) {
                        task.doTask();
                    } else {
                        throw new RuntimeException("运行失败");
                    }
                }
                task = next;
            }
        }

        public TimeTask remove(TimeTask task) {
            TimeTask next = task.next;
            if (task.prev != null) {
                task.prev.next = next;
            }
            if (task.next != null) {
                task.next.prev = task.prev;
            }

            if (task == head) {
                if (task == tail) {
                    tail = null;
                    head = null;
                } else {
                    head = next;
                }
            } else if (task == tail) {
                tail = task.prev;
            }
            task.prev = null;
            task.next = null;
            return next;
        }
    }

    public static void main(String[] args) {
        TestTimeWheel wheel = new TestTimeWheel(100, TimeUnit.MILLISECONDS, 512);
        wheel.newTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("r - " + this.hashCode() + " : " + LocalDateTime.now());
                wheel.newTask(this, 500, TimeUnit.MILLISECONDS);
            }
        },500, TimeUnit.MILLISECONDS);
    }
}
