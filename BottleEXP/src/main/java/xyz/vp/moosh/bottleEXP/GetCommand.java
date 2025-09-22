package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GetCommand implements BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        int currentExp = ExpUtil.getTotalExp(player);
        if (currentExp < 1) {
            player.sendMessage(prefix().append(Component.text("You Have No Experience To Store!", NamedTextColor.RED)));
            return;
        }

        if (args.length < 2) {
            player.sendMessage(prefix().append(Component.text("Usage: /bottle get <#Bottles>", NamedTextColor.RED)));
            return;
        }

        int numBottles;
        try {
            numBottles = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(prefix().append(Component.text("Number of bottles must be a number!", NamedTextColor.RED)));
            return;
        }

        if (numBottles <= 0) {
            player.sendMessage(prefix().append(Component.text("Number of bottles must be at least 1!", NamedTextColor.RED)));
            return;
        }

        // Spread all EXP evenly across bottles
        int expPerBottle = Math.max(1, currentExp / numBottles);

        // Deduct EXP from player
        ExpUtil.setTotalExp(player, currentExp - (expPerBottle * numBottles));

        List<ItemStack> bottles = new ArrayList<>();
        for (int i = 0; i < numBottles; i++) {
            ItemStack bottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
            ItemMeta meta = bottle.getItemMeta();
            if (meta != null) {
                // Store EXP in PersistentDataContainer
                meta.getPersistentDataContainer().set(
                        ExpUtil.CUSTOM_XP_KEY(BottleEXP.getPluginInstance()),
                        PersistentDataType.INTEGER,
                        expPerBottle
                );

                // Add lore showing stored EXP
                List<Component> lore = new ArrayList<>();
                lore.add(Component.text("Amount Stored: ", NamedTextColor.GRAY)
                        .append(Component.text(expPerBottle, NamedTextColor.AQUA)));
                meta.lore(lore);
                bottle.setItemMeta(meta);
            }
            bottles.add(bottle);
        }

        for (ItemStack bottle : bottles) {
            player.getInventory().addItem(bottle);
        }

        player.sendMessage(prefix().append(Component.text(
                "Successfully converted your EXP into " + numBottles + " bottle(s)!",
                NamedTextColor.GREEN
        )));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }
}
