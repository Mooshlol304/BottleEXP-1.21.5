
package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class BottleCommand implements CommandExecutor, TabCompleter {

    private final Map<String, BaseCommand> subCommands = new HashMap<>();

    public BottleCommand() {
        // Register subcommands here
        subCommands.put("help", new HelpCommand());
        subCommands.put("until", new UntilCommand());
        subCommands.put("stats", new StatsCommand());
        subCommands.put("mend", new MendCommand());
        subCommands.put("convert", new ConvertCommand());
        subCommands.put("store", new StoreCommand());
        subCommands.put("get", new GetCommand());
        subCommands.put("info", new InfoCommand());

    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(prefix().append(Component.text("Only players can use this command!", NamedTextColor.RED)));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(prefix().append(Component.text("No Argument Provided, Do /bottle help", NamedTextColor.RED)));
            return true;
        }

        BaseCommand sub = subCommands.get(args[0].toLowerCase());
        if (sub != null) {
            sub.execute(player, args);
        } else {
            player.sendMessage(prefix().append(Component.text("Invalid Argument Provided, Do /bottle help", NamedTextColor.RED)));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player player)) return Collections.emptyList();

        if (args.length == 1) {
            // Suggest subcommands
            return subCommands.keySet().stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .toList();
        }

        if (args.length == 2) {
            // Special tab completions
            if (args[0].equalsIgnoreCase("until")) {
                int level = player.getLevel();
                return List.of(
                        String.valueOf(level + 5),
                        String.valueOf(level + 10),
                        String.valueOf(level + 20)
                );
            } else if (args[0].equalsIgnoreCase("store")) {
                return List.of("Max"); // suggest Max for total EXP
            }
        }

        return Collections.emptyList();
    }


}
