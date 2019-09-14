package weedcollector.weedcollector;

import org.bukkit.plugin.java.JavaPlugin;

public final class WeedCollector extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PlayerInteract playerInteract = new PlayerInteract(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
