package com.panpan.currentpackage.newinstance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/12/8
 **/
public class EnergySource2 {
    private final long MAXLEVEL = 100;
    private final AtomicLong level = new AtomicLong(MAXLEVEL);
    private static final ScheduledExecutorService replenishTimer =
            Executors.newScheduledThreadPool(10);
    private ScheduledFuture<?> replenishTask;

    private EnergySource2() {
    }

    private void init() {
        replenishTask = replenishTimer.scheduleAtFixedRate(new Runnable() {
            public void run() {
                replenish();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static EnergySource2 create() {
        final EnergySource2 energySource = new EnergySource2();
        energySource.init();
        return energySource;
    }

    public long getUnitsAvailable() {
        return level.get();
    }

    public boolean useEnergy(final long units) {
        final long currentLevel = level.get();
        if (units > 0 && currentLevel >= units) {
            return level.compareAndSet(currentLevel, currentLevel - units);
        }
        return false;
    }

    public synchronized void stopEnergySource() {
        replenishTask.cancel(false);
    }

    private void replenish() {
        if (level.get() < MAXLEVEL) level.incrementAndGet();
    }
}
