import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Promise {
    private CompletableFuture<String> create(String text) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
                return text.toUpperCase();
            } catch (InterruptedException e) {
                throw new CompletionException(e);
            }
        });
    }

    private void run() {
        create("hello")
                .thenCompose(word -> create(word + " world!"))
                .thenAccept(System.out::println)
                .join();
    }

    public static void main(String args[]) {
        new Promise().run();
    }
}
