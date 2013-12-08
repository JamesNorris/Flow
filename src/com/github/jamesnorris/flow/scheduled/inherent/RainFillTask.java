package com.github.jamesnorris.flow.scheduled.inherent;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.jamesnorris.flow.Flow;
import com.github.jamesnorris.flow.PermissionChecker;
import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.Stat;
import com.github.jamesnorris.flow.scheduled.RepeatingTask;

public class RainFillTask extends RepeatingTask {
    private Map<String, Integer> counts = new HashMap<String, Integer>();
    private World world;
    private int countRequired, amountToFill;

    public RainFillTask(World world, int interval, int countRequired, int amountToFill) {
        super(Flow.getInstance(), interval);
        this.world = world;
        this.countRequired = countRequired;
        this.amountToFill = amountToFill;
        try {
            super.setObjective(this, this.getClass().getMethod("tick", this.getClass()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tick() {
        if (world.hasStorm()) {
            for (Player player : world.getPlayers()) {
                if (!PermissionChecker.check(player, RegulatedAction.FILL_BUCKETS_IN_RAIN)) {
                    continue;
                }
                String name = player.getName();
                if (!counts.containsKey(name)) {
                    counts.put(name, 0);
                }
                int previousCount = counts.get(name);
                counts.remove(name);
                counts.put(name, previousCount + 1);
                if (previousCount + 1 >= countRequired) {
                    int amountFilled = 0;
                    for (ItemStack item : player.getInventory().getContents()) {
                        if (item.getType() != Material.BUCKET) {
                            continue;
                        }
                        item.setType(Material.WATER_BUCKET);
                        Stat.RAIN_FILLED_BUCKET_COUNT.increment();
                        if (amountFilled >= amountToFill) {
                            break;
                        }
                    }
                    counts.remove(name);
                }
            }
        }
    }
}
