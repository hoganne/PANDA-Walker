package com.panpan.currentpackage.newinstance;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author xupan
 * @Date 2020/12/8
 **/
public class EnergySource {
    private final long MAXLEVEL = 100;
    private long level = MAXLEVEL;
    private static final ScheduledExecutorService replenishTimer = Executors.newScheduledThreadPool(10);
    private ScheduledFuture<?> replenishTask;

    private EnergySource() {
    }

    private void init() {
        replenishTask = replenishTimer.scheduleAtFixedRate(new Runnable() {
            public void run() {
                replenish();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static EnergySource create() {
        final EnergySource energySource = new EnergySource();
        energySource.init();
        return energySource;
    }

    public long getUnitsAvailable() {
        return level;
    }

    public boolean useEnergy(final long units) {
        if (units > 0 && level >= units) {
            level -= units;
            return true;
        }
        return false;
    }

    public void stopEnergySource() {
        replenishTask.cancel(false);
    }

    private void replenish() {
        if (level < MAXLEVEL) level++;
    }
}
