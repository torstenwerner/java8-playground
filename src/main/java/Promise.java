import java.util.concurrent.CompletableFuture;

public class Promise {
    private CompletableFuture<String> create(String text) {
        final CompletableFuture<String> promise = new CompletableFuture<>();
        try {
            Thread.sleep(100);
            promise.complete(text.toUpperCase());
        } catch (InterruptedException e) {
            promise.completeExceptionally(e);
        }
        return promise;
    }

    private void run() {
        create("hello")
                .thenCompose(word -> create(word + " world!"))
                .thenAccept(System.out::println);
    }

    public static void main(String args[]) {
        new Promise().run();
    }
}
