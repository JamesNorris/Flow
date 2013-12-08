package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.github.jamesnorris.flow.PermissionChecker;
import com.github.jamesnorris.flow.Setting;
import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.block.IceBlock;
import com.github.jamesnorris.flow.derived.block.SpongeBlock;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class BlockBreakResponder extends EventResponder {
    public BlockBreakResponder() {
        super(BlockBreakEvent.class);
    }

    @Override public void respond(Event evt) {
        BlockBreakEvent event = (BlockBreakEvent) evt;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        DerivedBlock derived = DerivedBlock.fromBlock(block, true);
        if (block.getType() == Material.ICE) {
            IceBlock ice = derived instanceof IceBlock ? (IceBlock) derived : new IceBlock(block);
            ice.melt();
        }
        if (block.getType() == Material.SPONGE) {
            if (!PermissionChecker.check(player, RegulatedAction.USE_SPONGES)) {
                player.sendMessage(ChatColor.RED + "You don't have permission to use sponges!");
                event.setCancelled(true);
                return;
            }
            int effectRange = (Integer) Setting.SPONGE_EFFECT_RADIUS.get();
            SpongeBlock sponge = derived instanceof SpongeBlock && ((SpongeBlock) derived).getEffectRange() == effectRange ? (SpongeBlock) derived : new SpongeBlock(block, effectRange);
            sponge.replaceWater();
            return;
        }
    }
}
