package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

public class MendCommand implements BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(prefix().append(Component.text("Item You're Holding is Not Valid", NamedTextColor.RED)));
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasEnchant(Enchantment.MENDING) || !(meta instanceof Damageable damageable)) {
            player.sendMessage(prefix().append(Component.text("Item You're Holding is Not Valid", NamedTextColor.RED)));
            return;
        }

        int damage = damageable.getDamage();
        if (damage <= 0) {
            player.sendMessage(prefix().append(Component.text("Item is Full Durability", NamedTextColor.RED)));
            return;
        }

        int playerExp = ExpUtil.getTotalExp(player);
        if (playerExp <= 0) {
            player.sendMessage(prefix().append(Component.text("You don't have any EXP to spend!", NamedTextColor.RED)));
            return;
        }

        // Vanilla: 1 exp = 2 durability
        int maxRepairable = playerExp * 2;
        int toRepair = Math.min(damage, maxRepairable);

        int expSpent = (int) Math.ceil(toRepair / 2.0);

        // Apply repair
        damageable.setDamage(damage - toRepair);
        item.setItemMeta(damageable);

        // Deduct EXP
        ExpUtil.setTotalExp(player, playerExp - expSpent);

        // ✅ Play experience pickup sound when successfully mended
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

        String itemName = item.getItemMeta().hasDisplayName()
                ? item.getItemMeta().getDisplayName()
                : item.getType().name();

        player.sendMessage(prefix().append(Component.text(
                "Successfully Spent " + expSpent + " EXP To Repair " + itemName,
                NamedTextColor.GREEN
        )));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
