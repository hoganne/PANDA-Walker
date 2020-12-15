package com.panpan.stm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author xupan
 * @Date2020/12/15 10:58
 * @Version V1.0
 **/
public class UseEnergySource {
    private static final EnergySource energySource = EnergySource.create();
    public static void main(final String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Energy level at start: " + energySource.getUnitsAvailable());
        List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new Callable<Object>() {
                public Object call() {
                    for (int j = 0; j < 7; j++) energySource.useEnergy(1);
                    return null;
                }
            });
        }
        final ExecutorService service = Executors.newFixedThreadPool(10);
        service.invokeAll(tasks);
        System.out.println("Energy level at end: " + energySource.getUnitsAvailable());
        System.out.println("Usage: " + energySource.getUsageCount());
        energySource.stopEnergySource();
        service.shutdown();
    }
}