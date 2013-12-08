package com.github.jamesnorris.flow;

import java.util.ArrayList;
import java.util.List;

import com.github.jamesnorris.flow.yaml.YAMLValue;

public enum Setting {
    ENABLED(new YAMLValue("enabled", true, "Enables/Disables the use of the plugin.")),
    ENABLE_WATER(new YAMLValue("enable_water", true, "Enables/Disables use of water in the plugin.")),
    ENABLE_LAVA(new YAMLValue("enable_lava", false, "Enables/Disables use of lava in the plugin.")),
    ENABLE_COMMANDS(new YAMLValue("enable_commands", true, "Enables/Disables the use of the '/flow' command.")),
    LIMIT_FIX_TO_BELOW_PLAYER(new YAMLValue("limit_fix_to_below_player_level", true, "Limits the fix of blocks to a semi-spherical area below the player. \nBy default, the area is a full sphere around the player.")),
    LIMIT_FIX_TO_BELOW_PLAYER_ONE_BLOCK(new YAMLValue("limit_fix_to_below_player_one_block", false, "Limits the fix of blocks to a circular (flat) area below the player. \nBy default, the area is a full sphere around the player. \n(overrides 'limit_fix_to_below_player_level')")),
    MAXIMUM_FIX_RADIUS(new YAMLValue("max_fix_radius", 10, "The maximum radius that a player using the '/flow fix' command can run the fix with.")),
    ENABLE_HEIGHT_SETTING(new YAMLValue("enable_liquid_height_setting", true, "Enables/Disables the use of the liquid height setting feature.")),
    ENABLE_COMMAND_FIX(new YAMLValue("enable_command_fix", true, "Enables/Disables the use of the '/flow fix' command.")),
    CONTROL_MELTING(new YAMLValue("control_melting", false, "Whether or not melting control should be enabled on the server. \nThis influences the next few settings.")),
    PREVENT_ICE_MELT(new YAMLValue("prevent_ice_melting", false, "Whether or not ice should be prevented from melting on the server. \n(depends on 'control_melting')")),
    PREVENT_WATER_FREEZE(new YAMLValue("prevent_water_freezing", false, "Whether or not water should be prevented from freezing on the server. \n(depends on 'control_melting')")),
    PREVENT_SNOW_MELT(new YAMLValue("prevent_snow_melting", false, "Whether or not snow should be prevented from melting on the server. \nSnow does not melt to yield liquids. \n(depends on 'control_melting')")),
    // TODO create a feature that allows melted snow to become a temporary liquid of equal height to that of the snow
    ENABLE_AUTOMATIC_FIX(new YAMLValue("enable_automatic_fix", true, "Enables/Disables the automatic fix feature, in which\nthe plugin attempts to fix all player-created water glitches.")),
    USE_BUCKET_PERMISSIONS(new YAMLValue("use_bucket_permissions", true, "Whether or not to require permission to dispense the liquids in buckets.")),
    ENABLE_PROXIMITY_BLOCK_CHANGE(new YAMLValue("enable_proximity_change", false, "Enables/Disables changes to blocks that are directly touching or nearby liquids.\nThis only affects blocks modified by the plugin.")),
    ENABLE_GRAVEL_TO_CLAY_CHANGE(new YAMLValue("enable_gravel_to_clay_change", false, "Enables/Disables the gravel to clay proximity change caused when water touches gravel.")),
    GRAVEL_TO_CLAY_CHANGE_PROBABILITY(new YAMLValue("gravel_to_clay_change_probability", 100, "(1-100; for gravel only) The percent probability that gravel will change to clay when water is updated next to it.")),
    ENABLE_SAND_TO_GLASS_CHANGE(new YAMLValue("enable_sand_to_glass_change", false, "Enables/Disables the sand to glass proximity change caused when lava touches sand.")),
    SAND_TO_GLASS_CHANGE_PROBABILITY(new YAMLValue("sand_to_glass_change_probability", 25, "(1-100; for sand only) The percent probability that sand will change to glass when lava is updated next to it.")),
    ENABLE_SAND_TO_OBSIDIAN_CHANGE(new YAMLValue("enable_sand_to_obsidian_change", false, "Enables/Disables the sand to obsidian proximity change caused when lava touches sand.")),
    SAND_TO_OBSIDIAN_CHANGE_PROBABILITY(new YAMLValue("sand_to_obsidian_change_probability", 25, "(1-100; for sand only) The percent probability that sand will change to obsidian when lava is updated next to it.")),
    ENABLE_SAND_TO_STONE_CHANGE(new YAMLValue("enable_sand_to_stone_change", false, "Enables/Disables the sand to stone proximity change caused when lava touches sand.")),
    SAND_TO_STONE_CHANGE_PROBABILITY(new YAMLValue("sand_to_stone_change_probability", 25, "(1-100; for sand only) The percent probability that sand will change to stone when lava is updated next to it.")),
    ENABLE_SAND_TO_QUARTZ_CHANGE(new YAMLValue("enable_sand_to_quartz_change", false, "Enables/Disables the sand to quartz proximity change caused when lava touches sand.")),
    SAND_TO_QUARTZ_CHANGE_PROBABILITY(new YAMLValue("sand_to_quartz_change_probability", 25, "(1-100; for sand only) The percent probability that sand will change to quartz when lava is updated next to it.")),
    ENABLE_SANDSTONE_TO_QUARTZ_CHANGE(new YAMLValue("enable_sandstone_to_quartz_change", false, "Enables/Disables the sandstone to quartz proximity change caused when lava touches sandstone.")),
    SANDSTONE_TO_QUARTZ_CHANGE_PROBABILITY(new YAMLValue("sandstone_to_quartz_change_probability", 100, "(1-100; for sandstone only) The percent probability that sandstone will change to quartz when lava is updated next to it.")),
    ENABLE_SPONGES(new YAMLValue("enable_sponges", false, "Enables/Disables the effects of sponges created by the plugin.")),
    SPONGE_EFFECT_RADIUS(new YAMLValue("sponge_effect_radius", 2, "The radius of water that is removed when a sponge is replaced.")),
    ENABLE_PHYSICS_AROUND_REMOVED_SPONGE(new YAMLValue("enable_physics_around_removed_sponge", true, "Enables/Disables physics is triggered when a songe is removed, fixing the water cleared by the sponge.")),
    ENABLE_FINITE_LIQUIDS(new YAMLValue("enable_finite_liquids", false, "Enables/Disables finite liquids, which will be placed and flow with the path of least resistance.")),
    // ENABLE_FINITE_LIQUID_EVAPORATION(new YAMLValue("enable_finite_liquid_evaporation", false,
    // "Enables/Disables the ability of finite liquids to evaporate when standing still at the lowest height for a period of time. \n(depends on 'enable_finite_liquids')")),
    ENABLE_FINITE_LIQUID_ACCUMULATION(new YAMLValue("enable_finite_liquid_accumulation", false, "Enables/Disables the ability of finite liquids to accumulate into a source block when not already done so, and when no move can be made. \n(depends on 'enable_finite_liquids')")),
    ENABLE_REDSTONE_LIQUID_CONTROL(new YAMLValue("enable_redstone_liquid_control", false, "Enables/Disables redstone liquid controls, which modify liquid behavior based on redstone signals.")),
    ENABLE_REDSTONE_STREAM_EXTENSION(new YAMLValue("enable_redstone_stream_extension", false, "Enables/Disables redstone stream extension, extending a stream if a signal is detected.\n(depends on 'enable_redstone_liquid_control')")),
    ENABLE_PISTONS_PUSHING_LIQUIDS(new YAMLValue("enable_piston_pushing_liquid", false, "Enables/Disables the ability of pistons to push liquids, \nand the ability of liquids to push other liquids when pushed by a piston. \n(depends on 'enable_redstone_liquid_control')")),
    USE_CRAFTING_RECIPE_PERMISSIONS(new YAMLValue("use_crafting_recipe_permissions", true, "Whether or not to use Flow crafting permissions for recipes added by Flow.")),
    ENABLE_SPONGE_CRAFTING(new YAMLValue("enable_sponge_crafting", true, "Enables/Disables sponge crafting. \n(depends on 'use_crafting_recipe_permissions')")),
    ENABLE_ICE_CRAFTING(new YAMLValue("enable_ice_crafting", true, "Enables/Disables ice crafting. \n(depends on 'use_crafting_recipe_permissions')")),
    ENABLE_RAIN_ACCUMULATION(new YAMLValue("enable_rain_accumulation", false, "Enables/Disables the accumulation of rain into player-held buckets.")),
    RAIN_ACCUMULATION_DELAY(new YAMLValue("rain_accumulation_delay", 60, "The delay before 'rain_accumulation_amount' buckets are filled via rain accumulation. \n(depends on 'enable_rain_accumulation')")),
    RAIN_ACCUMULATION_AMOUNT(new YAMLValue("rain_accumulation_amount", 1, "The amount of buckets filled after 'rain_accumulation_delay' with rain accumulation enabled. \n(depends on 'enable_rain_accumulation')"));
    public static List<YAMLValue> yamlValues = new ArrayList<YAMLValue>();
    private YAMLValue value;

    public Object get() {
        return value.get();
    }

    public static Object get(Setting setting) {
        return setting.getYAMLValue().get();
    }

    public static Object byName(String name) {
        return byName(name, false);
    }

    public static Object byName(String name, boolean caseSensitive) {
        if (!caseSensitive) {
            name = name.toLowerCase();
        }
        for (Setting setting : values()) {
            String valueName = setting.getYAMLValue().getValue();
            if (!caseSensitive) {
                valueName = valueName.toLowerCase();
            }
            if (valueName.equals(name)) {
                return setting.getYAMLValue().get();
            }
        }
        return null;
    }

    Setting(YAMLValue value) {
        this.value = value;
    }

    public YAMLValue getYAMLValue() {
        return value;
    }

    public void setYAMLValue(YAMLValue value) {
        this.value = value;
    }

    static {
        // File file = new File(Flow.getInstance().getDataFolder(), "config.yml");//TODO uncomment when testing and releasing
        // if (file.exists()) {
        // for (Setting setting : values()) {
        // setting.value.set(Flow.getInstance().getConfig().get(setting.value.getValue()));
        // }
        // }
        for (Setting setting : values()) {
            yamlValues.add(setting.value);
        }
    }
}
