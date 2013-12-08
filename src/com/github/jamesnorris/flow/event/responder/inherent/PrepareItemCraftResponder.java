package com.github.jamesnorris.flow.event.responder.inherent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.permissions.Permission;

import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.event.responder.EventResponder;

public class PrepareItemCraftResponder extends EventResponder {
    public PrepareItemCraftResponder() {
        super(PrepareItemCraftEvent.class);
    }

    @Override public void respond(Event evt) {
        PrepareItemCraftEvent event = (PrepareItemCraftEvent) evt;
        preventCraft(event, RegulatedAction.CRAFT_SPONGE.getPermission(), Material.SPONGE);
        preventCraft(event, RegulatedAction.CRAFT_ICE.getPermission(), Material.ICE);
    }

    private void preventCraft(PrepareItemCraftEvent event, Permission permission, Material type) {
        Player crafter = (Player) event.getView().getPlayer();
        CraftingInventory inventory = event.getInventory();
        if (!crafter.hasPermission(permission) && inventory.getResult().getType() == type) {
            crafter.sendMessage(ChatColor.RED + "You don't have permission to craft " + type.toString() + "!");
            event.getInventory().setResult(null);
            return;
        }
    }
}
