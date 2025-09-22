package xyz.vp.moosh.bottleEXP;

import org.bukkit.plugin.java.JavaPlugin;

public final class BottleEXP extends JavaPlugin {

    private static BottleEXP instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("BottleEXP Has Been Rendered. Made by Mooshbrixa");
        BottleCommand bottleCmd = new BottleCommand();
        getCommand("bottle").setExecutor(bottleCmd);
        getCommand("b").setExecutor(bottleCmd);

        // Register listener for custom EXP bottles
        getServer().getPluginManager().registerEvents(new BottleListener(), this);


    }

    public static BottleEXP getPluginInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("BottleEXP has Successfully been Stopped on this Environment. Made by Mooshbrixa");
    }
}
