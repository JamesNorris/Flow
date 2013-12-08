package com.github.jamesnorris.flow.scheduled;

import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RepeatingTask {
    private Plugin plugin;
    private int interval;
    private BukkitRunnable task;

    public RepeatingTask(Plugin plugin, int interval) {
        this.plugin = plugin;
        this.interval = interval;
    }

    public void setObjective(final Object obj, final Method runMethod) {
        task = new BukkitRunnable() {
            @Override public void run() {
                try {
                    runMethod.invoke(obj, (Object[]) null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
    }

    public void cancel() {
        if (task == null) {
            return;
        }
        task.cancel();
    }

    public BukkitRunnable getTask() {
        return task;
    }

    protected void run() {
        if (task == null) {
            return;
        }
        task.runTaskTimer(plugin, interval, interval);
    }
}
