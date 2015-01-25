import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class BlockingQueueTest {
    final AtomicBoolean producerFinished = new AtomicBoolean(false);
    final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public void producerLoop() {
        IntStream.range(0, 60).forEach(this::produce);
        producerFinished.set(true);
    }

    private void produce(int i) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(200));
            queue.put(i);
            System.out.printf("%s: put item #%d\n", Thread.currentThread().getName(), i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void consumerLoop() {
        while (!queue.isEmpty() || !producerFinished.get())
            consume();
    }

    private void consume() {
        try {
            System.out.printf("%s: will poll\n", Thread.currentThread().getName());
            Integer i = queue.poll(100, TimeUnit.MILLISECONDS);
            if (i == null) {
                System.out.printf("%s: got nothing\n", Thread.currentThread().getName());
                return;
            }
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            System.out.printf("%s: processed item %d\n", Thread.currentThread().getName(), i);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void test() {
        final Thread producerThread = new Thread(this::producerLoop, "producer");
        final Thread consumerThread1 = new Thread(this::consumerLoop, "consumer-1");
        final Thread consumerThread2 = new Thread(this::consumerLoop, "consumer-2");

        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();

        try {
            producerThread.join();
            consumerThread1.join();
            consumerThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
