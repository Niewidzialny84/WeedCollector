package weedcollector.weedcollector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class PlayerInteract implements Listener {
    private Plugin plugin_;

    private boolean doHoeHarverst;
    private boolean useAnyHoe;
    private boolean doAoeHoe;

    PlayerInteract(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this,plugin);
        doHoeHarverst = plugin.getConfig().getBoolean("doHoeHarvest",true);
        useAnyHoe = plugin.getConfig().getBoolean("useAnyHoe",false);
        doAoeHoe = plugin.getConfig().getBoolean("doAoeHoe",false);
        plugin_ = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) {
            PlayerInventory inventory = e.getPlayer().getInventory();

            if(doHoeHarverst) {
                if(isInAnyHand(inventory,Material.DIAMOND_HOE)) {
                    if(doAoeHoe) {
                        check3x3Area(e);
                    } else {
                        checkCrop(e);
                    }
                } else if (useAnyHoe) {
                    if(isInAnyHand(inventory,Material.GOLDEN_HOE)) {
                        checkCrop(e);
                    } else if(isInAnyHand(inventory,Material.IRON_HOE)) {
                        checkCrop(e);
                    } else if(isInAnyHand(inventory,Material.STONE_HOE)) {
                        checkCrop(e);
                    } else if(isInAnyHand(inventory,Material.WOODEN_HOE)) {
                        checkCrop(e);
                    }
                }
            } else {
                checkCrop(e);
            }
        }
    }

    private void check3x3Area(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        int multiply = 0;

        if(isInAnyHand(e.getPlayer().getInventory(),Material.DIAMOND_HOE)) {
            int mainHand = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            int offHand = e.getPlayer().getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if(mainHand>offHand) {
                multiply = mainHand;
            } else {
                multiply = offHand;
            }
        }

        for(int i = -1 ; i <= 1 ;i++) {
            for (int j = -1; j <= 1; j++) {
                checkCrop(block.getLocation().add(i, 0, j).getBlock(), multiply);
            }
        }
    }

    private void checkCrop(Block block,int multiply) {
        if (block != null) {
            for(Crop crop : Crop.values()) {
                if(block.getType().equals(crop.getBlock())) {
                    setCrop(block,Rand.randomInt(crop.getMin()+multiply,crop.getMax()+multiply),crop.getDrop());
                }
            }
        }
    }

    private void checkCrop(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();

        int multiply = 0;

        if(isInAnyHand(e.getPlayer().getInventory(),Material.DIAMOND_HOE)) {
            int mainHand = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            int offHand = e.getPlayer().getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
            if(mainHand>offHand) {
                multiply = mainHand;
            } else {
                multiply = offHand;
            }
        }

        if (block != null) {

           /* if(block.getType().equals(Material.CACTUS)) {
                block.setType(Material.AIR);
                block.getWorld().dropItem(block.getLocation(), new ItemStack(Material.CACTUS, 1));
                e.getPlayer().damage(0.5);
                return;
            } */

            for(Crop crop : Crop.values()) {
                if(block.getType().equals(crop.getBlock())) {
                    setCrop(block,Rand.randomInt(crop.getMin()+multiply,crop.getMax()+multiply),crop.getDrop());
                }
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

    private boolean isInAnyHand(PlayerInventory inventory, Material item) {
        return inventory.getItemInMainHand().getType().equals(item) || inventory.getItemInOffHand().getType().equals(item);
    }
}
