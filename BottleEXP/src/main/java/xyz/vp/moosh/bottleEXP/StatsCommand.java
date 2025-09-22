package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

public class StatsCommand implements BaseCommand {
    @Override
    public void execute(Player player, String[] args) {
        int currentExp = ExpUtil.getTotalExp(player);
        int currentLevel = player.getLevel();

        int nextLevelExp = ExpUtil.getExpAtLevel(currentLevel + 1);
        int neededExp = nextLevelExp - currentExp;
        int bottles = (int) Math.ceil(neededExp / 7.0);

        player.sendMessage(prefix().append(Component.text("Total EXP: ", NamedTextColor.AQUA))
                .append(Component.text(currentExp + " EXP or " + currentLevel + " EXP Levels", NamedTextColor.GREEN)));

        player.sendMessage(prefix().append(Component.text("Next Level: ", NamedTextColor.AQUA))
                .append(Component.text(neededExp + " EXP or " + bottles + " bottles till Next Experience Level", NamedTextColor.GREEN)));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
