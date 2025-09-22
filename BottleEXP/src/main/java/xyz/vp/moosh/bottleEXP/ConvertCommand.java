package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ConvertCommand implements BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.EXPERIENCE_BOTTLE) {
            player.sendMessage(prefix().append(Component.text("Not Holding an Experience Bottle", NamedTextColor.RED)));
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            player.sendMessage(prefix().append(Component.text("Not Holding an Experience Bottle", NamedTextColor.RED)));
            return;
        }

        // Read custom XP stored in the bottle
        int xpPerBottle = 7; // default vanilla XP
        if (meta.getPersistentDataContainer().has(
                ExpUtil.CUSTOM_XP_KEY(BottleEXP.getPluginInstance()),
                PersistentDataType.INTEGER
        )) {
            xpPerBottle = meta.getPersistentDataContainer().get(
                    ExpUtil.CUSTOM_XP_KEY(BottleEXP.getPluginInstance()),
                    PersistentDataType.INTEGER
            );
        }

        int amount = item.getAmount();
        int totalXp = xpPerBottle * amount;

        // Add total XP to player
        int newExp = ExpUtil.getTotalExp(player) + totalXp;
        ExpUtil.setTotalExp(player, newExp);

        // Remove bottles from hand
        player.getInventory().setItemInMainHand(null);

        player.sendMessage(prefix().append(Component.text(
                amount + " Experience Bottle(s) Successfully Absorbed! (" + totalXp + " XP)",
                NamedTextColor.GREEN
        )));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
