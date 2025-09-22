package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class UntilCommand implements BaseCommand {
    @Override
    public void execute(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(prefix().append(Component.text("Usage: /bottle until <level>", NamedTextColor.RED)));
            return;
        }

        int targetLevel;
        try {
            targetLevel = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(prefix().append(Component.text("You must provide a number for level!", NamedTextColor.RED)));
            return;
        }

        int currentLevel = player.getLevel();
        if (targetLevel <= currentLevel) {
            player.sendMessage(prefix().append(Component.text("Specified level must be greater than your current Experience Level", NamedTextColor.RED)));
            return;
        }

        int currentExp = ExpUtil.getTotalExp(player);
        int targetExp = ExpUtil.getExpAtLevel(targetLevel);
        int neededExp = targetExp - currentExp;

        int bottles = (int) Math.ceil(neededExp / 7.0);

        player.sendMessage(prefix().append(Component.text(
                "You need " + neededExp + " XP or " + bottles + " Normal bottles to reach level " + targetLevel,
                NamedTextColor.GREEN
        )));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
