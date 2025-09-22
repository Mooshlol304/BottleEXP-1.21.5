package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class InfoCommand implements BaseCommand {

    private static final String VERSION = "1.1";

    @Override
    public void execute(Player player, String[] args) {
        player.sendMessage(prefix().append(Component.text("BottleEXP Info", NamedTextColor.GOLD)));
        player.sendMessage(prefix().append(Component.text("----------------------------", NamedTextColor.GRAY)));

        player.sendMessage(prefix()
                .append(Component.text("Author: ", NamedTextColor.AQUA))
                .append(Component.text("Moosh aka Mooshbrixa", NamedTextColor.LIGHT_PURPLE)));

        player.sendMessage(prefix()
                .append(Component.text("Architecture: ", NamedTextColor.AQUA))
                .append(Component.text("Paper", NamedTextColor.GREEN)));

        player.sendMessage(prefix()
                .append(Component.text("Version: ", NamedTextColor.AQUA))
                .append(Component.text("V " + VERSION, NamedTextColor.YELLOW)));

        player.sendMessage(prefix()
                .append(Component.text("Description: ", NamedTextColor.AQUA))
                .append(Component.text("A Remake of the EarthMC BottleEXP Mod Made in a Day", NamedTextColor.GRAY)));

        player.sendMessage(prefix().append(Component.text("----------------------------", NamedTextColor.GRAY)));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
