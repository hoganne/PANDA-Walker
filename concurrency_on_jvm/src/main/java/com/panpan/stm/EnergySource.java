package com.panpan.stm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import akka.actor.package$;
import akka.stm.*;


/**
 * @Description
 * @Author xupan
 * @Date2020/12/14 13:56
 * @Version V1.0
 **/
public class EnergySource {
    private final long MAXLEVEL = 100;
    final Ref<Long> level = new Ref<Long>(MAXLEVEL);
    final Ref<Long> usageCount = new Ref<Long>(0L);
    final Ref<Boolean> keepRunning = new Ref<Boolean>(true);
    private static final ScheduledExecutorService replenishTimer = Executors.newScheduledThreadPool(10);

    private EnergySource() {
    }

    private void init() {
        replenishTimer.schedule(new Runnable() {
            public void run() {
                replenish();
                if (keepRunning.get())
                    replenishTimer.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 1, TimeUnit.SECONDS);
    }

    public static EnergySource create() {
        final EnergySource energySource = new EnergySource();
        energySource.init();
        return energySource;
    }

    public void stopEnergySource() {
        keepRunning.swap(false);
    }

    public long getUnitsAvailable() {
        return level.get();
    }

    public long getUsageCount() {
        return usageCount.getOrWait();
    }

    public boolean useEnergy(final long units) {
        return new Atomic<Boolean>() {
            @Override
            public Boolean atomically() {
                long currentLevel = level.get();
                if (units > 0 && currentLevel >= units) {
                    level.swap(currentLevel - units);
                    usageCount.swap(usageCount.get() + 1);
                    return true;
                } else {
                    return false;
                }
            }
        }.execute();
    }

    private void replenish() {
        new Atomic() {
            @Override
            public Object atomically() {
                long currentLevel = level.get();
                if (currentLevel < MAXLEVEL) level.swap(currentLevel + 1);
                return null;
            }
        }.execute();
    }


}
