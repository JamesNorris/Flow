package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;

import com.github.jamesnorris.flow.PermissionChecker;
import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.block.SpongeBlock;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class BlockPlaceResponder extends EventResponder {
    public BlockPlaceResponder() {
        super(BlockPlaceEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockPlaceEvent event = (BlockPlaceEvent) evt;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType() == Material.SPONGE) {
            if (!PermissionChecker.check(player, RegulatedAction.USE_SPONGES)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use sponges!");
                event.setCancelled(true);
                return;
            }
            DerivedBlock derived = DerivedBlock.fromBlock(block, true);
            int effectRange = (Integer) Setting.SPONGE_EFFECT_RADIUS.get();
            SpongeBlock sponge = derived instanceof SpongeBlock && ((SpongeBlock) derived).getEffectRange() == effectRange ? (SpongeBlock) derived : new SpongeBlock(block, effectRange);
            sponge.removeWater();
            return;
        }
    }
}
