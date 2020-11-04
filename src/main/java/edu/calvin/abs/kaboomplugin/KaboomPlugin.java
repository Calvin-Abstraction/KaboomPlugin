package edu.calvin.abs.kaboomplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class KaboomPlugin extends JavaPlugin {
    public static final double DEFAULT_X_MODIFIER = 0.2;
    public static final double DEFAULT_Y_MODIFIER = 0.2;
    public static final double DEFAULT_Z_MODIFIER = 0.2;

    public static double X_MODIFIER = DEFAULT_X_MODIFIER;
    public static double Y_MODIFIER = DEFAULT_Y_MODIFIER;
    public static double Z_MODIFIER = DEFAULT_Z_MODIFIER;

    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
        getCommand("explodemodifier").setExecutor(new CommandExplodeModifier());
        getLogger().info("KaboomPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getLogger().info("KaboomPlugin has been disabled!");
    }
}
