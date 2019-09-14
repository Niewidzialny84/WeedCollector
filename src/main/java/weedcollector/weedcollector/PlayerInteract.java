package weedcollector.weedcollector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class PlayerInteract implements Listener {
    private Plugin plugin_;

    private boolean doHoeHarverst;
    private boolean useAnyHoe;

    PlayerInteract(Plugin plugin) {

        Bukkit.getPluginManager().registerEvents(this,plugin);
        doHoeHarverst = plugin.getConfig().getBoolean("doHoeHarvest",true);
        useAnyHoe = plugin.getConfig().getBoolean("useAnyHoe",false);
        plugin_ = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) {
            if ((e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.DIAMOND_HOE) && doHoeHarverst) {

                checkCrop(e);
                //System.out.println(e.getPlayer().getName() + " " + e.getClickedBlock());

            } else if(doHoeHarverst && useAnyHoe) {
                if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.DIAMOND_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.DIAMOND_HOE
                || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GOLDEN_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.GOLDEN_HOE
                || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.IRON_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.IRON_HOE
                || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STONE_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.STONE_HOE
                || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.WOODEN_HOE
                || e.getPlayer().getInventory().getItemInOffHand().getType() == Material.WOODEN_HOE) {
                    checkCrop(e);
                }
            } else if(doHoeHarverst == false) {
                checkCrop(e);
            }

        }
    }

    private void checkCrop(PlayerInteractEvent e) {
        if (e.getClickedBlock() != null) {
            if (e.getClickedBlock().getType() == Material.WHEAT) {
                setCrop(e.getClickedBlock(), randomInt(1, 2), Material.WHEAT);
            } else if (e.getClickedBlock().getType() == Material.CARROTS) {
                setCrop(e.getClickedBlock(), randomInt(1, 4), Material.CARROT);
            } else if (e.getClickedBlock().getType() == Material.POTATOES) {
                setCrop(e.getClickedBlock(), randomInt(1, 4), Material.POTATO);
            } else if (e.getClickedBlock().getType() == Material.BEETROOTS) {
                setCrop(e.getClickedBlock(), randomInt(1, 2), Material.BEETROOT);
            } else if (e.getClickedBlock().getType() == Material.NETHER_WART) {
                setCrop(e.getClickedBlock(), randomInt(1, 4), Material.NETHER_WART);
            }
        }
    }

    private void setCrop(Block block,int amount,Material material) {
        Ageable cropAge = ((Ageable) block.getBlockData());
        if (cropAge.getAge() == cropAge.getMaximumAge()) {
            cropAge.setAge(0);
            block.setBlockData(cropAge);
            block.getWorld().dropItem(block.getLocation(), new ItemStack(material, amount));
        }
    }

    private int randomInt(int min,int max) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(max) + min;
    }

}
