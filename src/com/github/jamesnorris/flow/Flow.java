package com.github.jamesnorris.flow;

import java.io.File;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.jamesnorris.flow.command.CommandBase;
import com.github.jamesnorris.flow.event.Event;
import com.github.jamesnorris.flow.event.responder.EventResponder;
import com.github.jamesnorris.flow.event.responder.inherent.*;
import com.github.jamesnorris.flow.scheduled.inherent.RainFillTask;
import com.github.jamesnorris.flow.util.DataList;
import com.github.jamesnorris.flow.yaml.YAMLFileManager;
import com.github.jamesnorris.flow.yaml.YAMLValue;

/* TODO BlockPistonExtendResponder and FlowUtility.pushLiquid() - TEST
 * TODO Flow issue #5 - Water flow to bottom (make limited liquid settle at the bottom) - TEST
 * TODO test thoroughly */
public class Flow extends JavaPlugin {
    private static Flow instance;
    private DataList<Object> storage;
    private CommandBase commandBase;
    private Event event;

    public static void main(String[] args) {
        try {
            new Flow().ensureConfig();
        } catch (Exception ex) {
            System.err.println("Flow encountered an issue during startup.\n" + ex.getMessage());
        }
    }

    @Override public void onEnable() {
        instance = this;
        storage = new DataList<Object>();// no size limit, watch it!
        commandBase = new CommandBase();
        instance.getCommand("flow").setExecutor(commandBase);
        ensureConfig();
        if ((Boolean) Setting.ENABLE_RAIN_ACCUMULATION.get()) {
            for (World world : Bukkit.getWorlds()) {
                storage.add(new RainFillTask(world, (Integer) Setting.RAIN_ACCUMULATION_DELAY.get(), 1, (Integer) Setting.RAIN_ACCUMULATION_AMOUNT.get()));
            }
        }
        EventResponder[] responders = new EventResponder[] {new BlockBreakResponder(), new BlockFadeResponder(), new BlockFormResponder(), new BlockFromToResponder(), new BlockPhysicsResponder(), new BlockPlaceResponder(), new PlayerBucketResponder(), new PrepareItemCraftResponder()};
        storage.addAll(responders);
        event = new Event(responders);
    }

    private File ensureConfig() {
        File file = new File(getDataFolder(), "config.yml");
        YAMLFileManager configCreator = new YAMLFileManager(file, Setting.yamlValues.toArray(new YAMLValue[Setting.yamlValues.size()]));
        configCreator.setHeader("Flow Configuration\nAuthor: JNorr44 (James Norris)"/* \nVersion: " + this.getDescription().getVersion() */);
        configCreator.setFooter("End of Flow Configuration");
        if (configCreator.hasBeenCreated()) {
            for (Setting setting : Setting.values()) {
                String key = setting.getYAMLValue().getValue();
                int index = configCreator.find(0, key, false, true, false);
                String[] mapping = configCreator.getLines().get(index).split(Pattern.quote(":"));
                setting.setYAMLValue(new YAMLValue(mapping[0] + ": ", mapping[1], setting.getYAMLValue().getComment()));
                // TODO doesn't work for lists, maps, etc, just simple 1-line values
            }
            return file;
        }
        configCreator.writeValuesToFile(true);
        return file;
    }

    public static Flow getInstance() {
        return instance;
    }

    public DataList<Object> getData() {
        return storage;
    }

    public CommandBase getCommandBase() {
        return commandBase;
    }

    public Event getEvent() {
        return event;
    }
}
