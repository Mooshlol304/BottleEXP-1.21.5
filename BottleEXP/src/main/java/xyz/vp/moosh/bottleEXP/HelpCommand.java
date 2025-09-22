package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class HelpCommand implements BaseCommand {

    @Override
    public void execute(Player player, String[] args) {

        player.sendMessage(prefix().append(Component.text("BottleEXP Help Menu", NamedTextColor.GOLD)));
        player.sendMessage(prefix().append(Component.text("---------------------------", NamedTextColor.GRAY)));

        // /bottle help
        player.sendMessage(prefix()
                .append(Component.text("/bottle help", NamedTextColor.AQUA))
                .append(Component.text(" - Shows this help menu", NamedTextColor.GRAY)));

        // /bottle stats
        player.sendMessage(prefix()
                .append(Component.text("/bottle stats", NamedTextColor.AQUA))
                .append(Component.text(" - Shows your current EXP stats", NamedTextColor.GRAY)));

        // /bottle until <level>
        player.sendMessage(prefix()
                .append(Component.text("/bottle until <level>", NamedTextColor.AQUA))
                .append(Component.text(" - Calculates XP and bottles needed to reach a level", NamedTextColor.GRAY)));

        // /bottle mend
        player.sendMessage(prefix()
                .append(Component.text("/bottle mend", NamedTextColor.AQUA))
                .append(Component.text(" - Spend your EXP to mend an item in your main hand", NamedTextColor.GRAY)));

        // /bottle store <EXP> <#Bottles>
        player.sendMessage(prefix()
                .append(Component.text("/bottle store <EXP> <#Bottles>", NamedTextColor.AQUA))
                .append(Component.text(" - Convert EXP into custom bottles", NamedTextColor.GRAY)));

        // /bottle get <#Bottles>
        player.sendMessage(prefix()
                .append(Component.text("/bottle get <#Bottles>", NamedTextColor.AQUA))
                .append(Component.text(" - Evenly splits all your EXP into the specified number of bottles", NamedTextColor.GRAY)));

        // /bottle convert
        player.sendMessage(prefix()
                .append(Component.text("/bottle convert", NamedTextColor.AQUA))
                .append(Component.text(" - Absorb held custom EXP bottles into your EXP", NamedTextColor.GRAY)));

        player.sendMessage(prefix().append(Component.text("---------------------------", NamedTextColor.GRAY)));
        player.sendMessage(prefix().append(Component.text("Made by Moosh aka Mooshbrixa on Discord", NamedTextColor.LIGHT_PURPLE)));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
