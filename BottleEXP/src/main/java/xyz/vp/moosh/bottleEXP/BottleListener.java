package xyz.vp.moosh.bottleEXP;

import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BottleListener implements Listener {

    @EventHandler
    public void onExpBottleThrow(ExpBottleEvent event) {
        ThrownExpBottle bottleEntity = event.getEntity();
        ItemStack bottleItem = bottleEntity.getItem();
        if (bottleItem == null) return;

        ItemMeta meta = bottleItem.getItemMeta();
        if (meta == null) return;

        Integer customXp = meta.getPersistentDataContainer().get(
                ExpUtil.CUSTOM_XP_KEY(BottleEXP.getPluginInstance()),
                PersistentDataType.INTEGER
        );

        if (customXp != null) {
            event.setExperience(customXp);
        }
    }
}
