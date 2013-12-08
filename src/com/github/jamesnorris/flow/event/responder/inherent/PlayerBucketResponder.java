package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerBucketEvent;

import com.github.jamesnorris.flow.PermissionChecker;
import com.github.jamesnorris.flow.Stat;
import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class PlayerBucketResponder extends EventResponder {
    public PlayerBucketResponder() {
        super(PlayerBucketEvent.class);
    }

    @Override public void respond(Event evt) {
        PlayerBucketEvent event = (PlayerBucketEvent) evt;
        Player player = event.getPlayer();
        Material bucket = event.getBucket();
        if ((!PermissionChecker.check(player, RegulatedAction.USE_WATER_BUCKET) && bucket == Material.WATER_BUCKET)
                || (!PermissionChecker.check(player, RegulatedAction.USE_LAVA_BUCKET) && bucket == Material.LAVA_BUCKET)) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this type of bucket!");
            event.setCancelled(true);
            Stat.CONTROLLED_BUCKET_COUNT.increment();
            return;
        }
    }
}
