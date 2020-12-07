package com.panpan.currentpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/12/5
 **/
public class NaivelyConcurrentTotalFileSize {
    private long getTotalSizeOfFile(final String fileName)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            return getTotalSizeOfFilesInDir2(service, new File(fileName));
        } finally {
            service.shutdown();
        }
    }
    private long getTotalSizeOfFilesInDir2(final ExecutorService service, final File file)
            throws InterruptedException, ExecutionException, TimeoutException {
        if (file.isFile())
            return file.length();
        long total = 0;
        final File[] children = file.listFiles();
        if (children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
            for (final File child : children) {
                partialTotalFutures.add(service.submit(
                        new Callable<Long>() {
                    public Long call()
                            throws InterruptedException, ExecutionException, TimeoutException {
                        return getTotalSizeOfFilesInDir2(service, child);
                    }
                }));
            }
            for (final Future<Long> partialTotalFuture : partialTotalFutures)
                total += partialTotalFuture.get(100, TimeUnit.SECONDS);
        }
        return total;
    }

    public static void main(final String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        final long start = System.nanoTime();
        final long total = new NaivelyConcurrentTotalFileSize().getTotalSizeOfFile(args[0]);
        final long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start) / 1.0e9);
    }
}
