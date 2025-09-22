package xyz.vp.moosh.bottleEXP;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StoreCommand implements BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        int currentExp = ExpUtil.getTotalExp(player);
        if (currentExp < 1) {
            player.sendMessage(prefix().append(Component.text("You Have No Experience To Store!", NamedTextColor.RED)));
            return;
        }

        if (args.length < 3) {
            player.sendMessage(prefix().append(Component.text("Usage: /bottle store <EXP/Levels> <#Bottles>", NamedTextColor.RED)));
            return;
        }

        int totalExp;
        int numBottles;

        try {
            String expArg = args[1];

            if (expArg.equalsIgnoreCase("Max")) {
                totalExp = currentExp;
            } else {
                totalExp = Integer.parseInt(expArg);
            }

            numBottles = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage(prefix().append(Component.text("EXP and #Bottles must be numbers (or 'Max')!", NamedTextColor.RED)));
            return;
        }

        if (totalExp > currentExp) {
            player.sendMessage(prefix().append(Component.text("You do not have enough EXP!", NamedTextColor.RED)));
            return;
        }

        if (numBottles <= 0) {
            player.sendMessage(prefix().append(Component.text("Number of bottles must be at least 1!", NamedTextColor.RED)));
            return;
        }

        int expPerBottle = Math.max(1, totalExp / numBottles); // at least 1 XP per bottle

        // Deduct EXP from player
        ExpUtil.setTotalExp(player, currentExp - totalExp);

        // Give bottles with custom XP stored
        List<ItemStack> bottles = new ArrayList<>();
        for (int i = 0; i < numBottles; i++) {
            ItemStack bottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
            ItemMeta meta = bottle.getItemMeta();
            if (meta != null) {
                meta.getPersistentDataContainer().set(
                        ExpUtil.CUSTOM_XP_KEY(BottleEXP.getPluginInstance()),
                        PersistentDataType.INTEGER,
                        expPerBottle
                );

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
                "Successfully stored " + totalExp + " EXP into " + numBottles + " bottle(s)!",
                NamedTextColor.GREEN
        )));
    }

    private Component prefix() {
        return Component.text("BXP ", NamedTextColor.AQUA)
                .append(Component.text(">> ", NamedTextColor.GRAY));
    }

    public List<String> tabComplete(Player player, String[] args) {
        if (args.length == 2) {
            return List.of("Max");
        }
        return Collections.emptyList();
    }
}
