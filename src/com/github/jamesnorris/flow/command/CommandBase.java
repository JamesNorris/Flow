package com.github.jamesnorris.flow.command;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.jamesnorris.flow.Flow;
import com.github.jamesnorris.flow.PermissionChecker;
import com.github.jamesnorris.flow.PermissionChecker.RegulatedAction;
import com.github.jamesnorris.flow.Stat;
import com.github.jamesnorris.flow.derived.DerivedBlock;
import com.github.jamesnorris.flow.derived.block.LiquidBlock;
import com.github.jamesnorris.flow.event.responder.inherent.PlayerInteractResponder;
import com.github.jamesnorris.flow.util.ChatMenu;
import com.github.jamesnorris.flow.util.FlowUtility;
import com.github.jamesnorris.flow.util.LiquidUtility;

public class CommandBase implements CommandExecutor {
    //@formatter:off
    public static final ChatMenu BASE_MENU = new ChatMenu(ChatColor.AQUA + "Flow Help", new String[] {
            ChatColor.AQUA + "/flow " + ChatColor.BOLD + "info <page 1-2>" + ChatColor.RESET + " - Provides info and statistics on the plugin",
            ChatColor.AQUA + "/flow " + ChatColor.BOLD + "fix <radius>" + ChatColor.RESET + " - Fixes an area of water",
            ChatColor.AQUA + "/flow " + ChatColor.BOLD + "height <level 1(tall)-7(short)>" + ChatColor.RESET + " - Sets water height"
    });
    public static final ChatMenu INFO_MENU = new ChatMenu(ChatColor.AQUA + "Flow Info", new String[] {
            ChatColor.AQUA + "Plugin:" + ChatColor.BLUE + " Flow",
            ChatColor.AQUA + "Created by: " + ChatColor.BLUE + "JamesNorris",
            ChatColor.AQUA + "Use: " + ChatColor.BLUE + "Adding more functionality to liquids.",
            "",
            "",//to start a new page
            "This session, flow has successfully controlled " + ChatColor.LIGHT_PURPLE + Stat.CONTROLLED_BUCKET_COUNT + ChatColor.RESET +
            " buckets from being placed, made " + ChatColor.LIGHT_PURPLE + Stat.FINITE_LIQUID_COUNT + ChatColor.RESET + " liquids finite,"
            + " run the proximity change " + ChatColor.LIGHT_PURPLE + Stat.PROXIMITY_CHANGE_COUNT + ChatColor.RESET + " times,"
            + " filled " + ChatColor.LIGHT_PURPLE + Stat.RAIN_FILLED_BUCKET_COUNT + ChatColor.RESET + " buckets via rain, allowed effects on "
            + ChatColor.LIGHT_PURPLE + Stat.SPONGE_REMOVE_WATER_COUNT + ChatColor.RESET + " sponges, and fixed " + ChatColor.LIGHT_PURPLE + 
            Stat.STREAM_FIX_COUNT + ChatColor.RESET + " streams."
    });
    //@formatter:on
    @Override public boolean onCommand(CommandSender sender, Command cmd, String inf, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("flow")) {
            return true;
        }
        if (!PermissionChecker.check(sender, RegulatedAction.USE_BASE_COMMAND)) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
            return true;
        }
        if (args[0].equalsIgnoreCase("info")) {
            if (!PermissionChecker.check(sender, RegulatedAction.USE_INFO_COMMAND)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                return true;
            }
            int page = args.length == 2 ? parseInt(args[1]) : 1;
            if (page == -1) {
                sender.sendMessage(ChatColor.RED + "The value entered for the page number must be an integer!");
                return true;
            }
            INFO_MENU.showPage(sender, page);
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        final Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("fix")) {
            if (!PermissionChecker.check(sender, RegulatedAction.USE_FIX_COMMAND)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                return true;
            }
            int radius = args.length == 2 ? parseInt(args[1]) : -1;
            if (radius == -1) {
                sender.sendMessage(ChatColor.RED + "The value entered for the radius must be an integer greater than 0!");
                return true;
            }
            FlowUtility.fixAreaWaterStreams(player.getLocation().getBlock(), radius);
            return true;
        }
        if (args[0].equalsIgnoreCase("height")) {
            if (!PermissionChecker.check(sender, RegulatedAction.USE_HEIGHT_COMMAND)) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
                return true;
            }
            final int height = args.length == 2 ? parseInt(args[1]) : -1;
            if (height == -1) {
                sender.sendMessage(ChatColor.RED + "The value entered for the height must be an integer from 0 to 7!");
                return true;
            }
            new PlayerInteractResponder() {
                {
                    register();// on instantiation
                }

                @Override public void respond(Event evt) {
                    super.respond(evt);
                    PlayerInteractEvent event = (PlayerInteractEvent) evt;
                    Block block = event.getClickedBlock();
                    DerivedBlock derived = DerivedBlock.fromBlock(block, true);
                    if (!LiquidUtility.isLiquid(block)) {
                        unregister();
                        return;
                    }
                    LiquidBlock liquid = derived instanceof LiquidBlock ? (LiquidBlock) derived : new LiquidBlock(block);
                    liquid.setHeight((byte) (liquid.isLava() ? (int) Math.round(4 / 3 * height) : height));// scaled if lava to 4/3x
                    unregister();
                }

                private void register() {
                    Flow.getInstance().getEvent().addResponder(this);
                    Flow.getInstance().getData().add(this);
                }

                private void unregister() {
                    Flow.getInstance().getEvent().removeResponder(this);
                    Flow.getInstance().getData().remove(this);
                }
            };
            return true;
        }
        INFO_MENU.showPage(sender, 1);// show default help page
        return true;
    }

    private int parseInt(String text) {
        int parsed = -1;
        try {
            parsed = Integer.parseInt(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parsed;
    }
}
