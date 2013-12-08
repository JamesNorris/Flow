package com.github.jamesnorris.flow;

import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class PermissionChecker {
    public enum RegulatedAction {
        USE_BASE_COMMAND(new Permission("flow.user", "Allows the user to use the base command (/flow).", PermissionDefault.OP)),
        USE_INFO_COMMAND(new Permission("flow.info", "Allows the user to use the info command (/flow info).", PermissionDefault.OP)),
        USE_FIX_COMMAND(new Permission("flow.fix", "Allows the user to use the fix command (/flow fix).", PermissionDefault.OP)),
        USE_HEIGHT_COMMAND(new Permission("flow.height", "Allows the user to use the height command (/flow height).", PermissionDefault.OP)),
        USE_WATER_BUCKET(new Permission("flow.waterbucket", "Allows the user to use a bucket to obtain/release water.", PermissionDefault.OP)),
        USE_LAVA_BUCKET(new Permission("flow.lavabucket", "Allows the user to use a bucket to obtain/release lava.", PermissionDefault.OP)),
        FILL_BUCKETS_IN_RAIN(new Permission("flow.fill", "Enables rain-filling buckets for this player.", PermissionDefault.OP)),
        USE_SPONGES(new Permission("flow.sponges", "Allows the player to place/remove sponges.", PermissionDefault.OP)),
        CRAFT_ICE(new Permission("flow.craft.ice", "Allows the player to craft ice.", PermissionDefault.OP)),
        CRAFT_SPONGE(new Permission("flow.craft.sponge", "Allows the player to craft sponge.", PermissionDefault.OP));
        private Permission perm;

        RegulatedAction(Permission perm) {
            this.perm = perm;
        }

        public Permission getPermission() {
            return perm;
        }

        public String getPermissionString() {
            return perm.getName();
        }
    }

    public static boolean check(Permissible permissible, RegulatedAction action) {
        return permissible.hasPermission(action.getPermission());
    }
}
