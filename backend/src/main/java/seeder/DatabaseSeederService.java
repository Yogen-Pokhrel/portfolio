package seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class DatabaseSeederService {

    /** to avoid database connection issues we are limiting the number of async database calls */
    public static final int MAX_CONCURRENT_TASKS = 50;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeederService.class);

    public static <T> SeederResult seed(String module, List<T> list, Function<T, SeederResult> function) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONCURRENT_TASKS);

        CompletableFuture<SeederResult>[] futures = new CompletableFuture[list.size()];
        int index = 0;

        for (T item : list) {
            futures[index++] = CompletableFuture.supplyAsync(() -> {
                try {
                    return function.apply(item);
                } catch (Exception e) {
                    logger.error("Error processing item: {}", e.getMessage());
                    SeederResult failedResult = new SeederResult();
                    failedResult.iFailed(e.getMessage());
                    return failedResult;
                }
            }, executorService);
        }

        SeederResult finalResult = new SeederResult(module);
        try {
            List<SeederResult> results = Stream.of(futures)
                    .map(CompletableFuture::join)
                    .toList();

            results.forEach(finalResult::merge);
        } catch (Exception e) {
            logger.error("Error completing tasks: {}", e.getMessage());
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
        return finalResult;
    }
}
