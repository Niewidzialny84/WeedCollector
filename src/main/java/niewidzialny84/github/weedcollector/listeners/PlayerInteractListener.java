package niewidzialny84.github.weedcollector.listeners;

import niewidzialny84.github.weedcollector.crop.CropHarvest;
import niewidzialny84.github.weedcollector.WeedCollector;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class PlayerInteractListener implements Listener {
    private final WeedCollector plugin;

    private final CropHarvest cropHarvest;

    private final List<Material> defaultList;
    private final List<Material> aoeList;

    public PlayerInteractListener(WeedCollector plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        this.plugin = plugin;

        cropHarvest = new CropHarvest();

        defaultList = Arrays.asList(Material.GOLDEN_HOE, Material.IRON_HOE, Material.STONE_HOE, Material.WOODEN_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE);
        aoeList = Arrays.asList(Material.DIAMOND_HOE, Material.NETHERITE_HOE);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking())) {
            return;
        }

        Block block = e.getClickedBlock();

        if(plugin.getConfiguration().isDoHoeHarvest()) {
            PlayerInventory inventory = e.getPlayer().getInventory();

            if(plugin.getConfiguration().isDoAoeHoe() && isInAnyHand(inventory, aoeList)) {
                cropHarvest.check3x3Area(block);
                return;
            }

            if(plugin.getConfiguration().isUseAnyHoe() && isInAnyHand(inventory, defaultList)) {
                cropHarvest.checkCrop(block);
            }
        } else {
            cropHarvest.checkCrop(block);
        }
    }

    private boolean isInAnyHand(PlayerInventory inventory, List<Material> items) {
        for(Material item : items)
        {
            if (inventory.getItemInMainHand().getType().equals(item) || inventory.getItemInOffHand().getType().equals(item))
            {
                return true;
            }
        }
        return false;
    }
}
