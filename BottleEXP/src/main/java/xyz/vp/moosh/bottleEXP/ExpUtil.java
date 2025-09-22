package xyz.vp.moosh.bottleEXP;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpUtil {

    // ✅ Key for custom XP bottles
    public static NamespacedKey CUSTOM_XP_KEY;

    public static void init(JavaPlugin plugin) {
        CUSTOM_XP_KEY = new NamespacedKey(plugin, "custom_xp");
    }

        public static NamespacedKey CUSTOM_XP_KEY(JavaPlugin plugin) {
            return new NamespacedKey(plugin, "custom_xp");
        }

    // Total EXP a player currently has
    public static int getTotalExp(Player player) {
        int exp = 0;
        int level = player.getLevel();

        for (int i = 0; i < level; i++) {
            exp += getExpToLevel(i);
        }

        exp += Math.round(player.getExp() * getExpToLevel(level));
        return exp;
    }

    // EXP required to reach a specific level from 0
    public static int getExpAtLevel(int level) {
        int exp = 0;
        for (int i = 0; i < level; i++) {
            exp += getExpToLevel(i);
        }
        return exp;
    }

    // EXP required to go from level → level+1
    public static int getExpToLevel(int level) {
        if (level >= 0 && level <= 15) {
            return 2 * level + 7;
        } else if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        } else {
            return 9 * level - 158;
        }
    }

    public static void setTotalExp(Player player, int exp) {
        if (exp < 0) exp = 0;

        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        int remaining = exp;
        int level = 0;

        while (remaining > getExpToLevel(level)) {
            remaining -= getExpToLevel(level);
            level++;
        }

        player.setLevel(level);
        player.setExp(remaining / (float) getExpToLevel(level));
    }
}
