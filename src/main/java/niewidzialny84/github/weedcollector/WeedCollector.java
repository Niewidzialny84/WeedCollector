package niewidzialny84.github.weedcollector;

import niewidzialny84.github.weedcollector.listeners.PlayerInteractListener;
import niewidzialny84.github.weedcollector.listeners.PlayerSneakListener;
import niewidzialny84.github.weedcollector.utils.Commands;
import niewidzialny84.github.weedcollector.utils.Configuration;
import niewidzialny84.github.weedcollector.utils.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeedCollector extends JavaPlugin {
    private Configuration config;
    private PlayerInteractListener playerInteract;
    private PlayerSneakListener playerSneak;
    private Commands commands;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 16077);

        saveDefaultConfig();

        reload();
        commands = new Commands(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload()
    {
        this.getLogger().info("Reloading plugin...");

        HandlerList.unregisterAll(this);

        config = new Configuration(this);

        playerInteract = new PlayerInteractListener(this);
        playerSneak = new PlayerSneakListener(this);
    }

    public Configuration getConfiguration()
    {
        return config;
    }
}
