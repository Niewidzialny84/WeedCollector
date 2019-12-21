package weedcollector.weedcollector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.Plugin;

public class PlayerSneak implements Listener {
    private Plugin plugin;

    public PlayerSneak(Plugin plugin) {
        if(plugin.getConfig().getBoolean("doSneakGrow",true)) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        if(!e.isCancelled() && e.isSneaking()) {
            int food = e.getPlayer().getFoodLevel();
            float saturation = e.getPlayer().getSaturation();

            if(food > 6) {
                if(saturation > 0) {
                    if(grow(e.getPlayer())) {
                        e.getPlayer().setSaturation(saturation-0.1f);
                    }
                } else {
                    if(grow(e.getPlayer())) {
                        e.getPlayer().setFoodLevel(food-1);
                    }
                }
            }
        }
    }

    private boolean grow(Player p) {
        boolean lowerFood = false;

        for(int x = -2 ; x <= 2 ;x++) {
            for (int z = -2; z <= 2; z++) {
                for (int y = -2; y <= 2; y++) {
                    Block block = p.getLocation().add(x,y,z).getBlock();

                    if(block.getBlockData() instanceof Ageable) {
                        Ageable cropAge = ((Ageable) block.getBlockData());

                        if (cropAge.getAge() == cropAge.getMaximumAge())
                            continue;

                        int chance = Rand.randomInt(1, 20);
                        if (chance == 10) {
                            cropAge.setAge(cropAge.getAge() + 1);
                            block.setBlockData(cropAge);

                            block.getWorld().spawnParticle(Particle.COMPOSTER,block.getLocation(),10,0.2,0.4,0.2);

                            if(Rand.randomInt(1, 20) > 15) {
                                lowerFood = true;
                            }
                        }

                    }

                    if(block.getBlockData() instanceof Sapling) {
                        Sapling sapling = ((Sapling) block.getBlockData());

                        if(sapling.getStage() == sapling.getMaximumStage()) {
                            generateTree(block);
                            block.getWorld().spawnParticle(Particle.COMPOSTER,block.getLocation(),15,0.2,0.4,0.2);
                            lowerFood = true;
                        }


                        if (Rand.randomInt(1, 40) == 20) {
                            sapling.setStage(sapling.getStage() + 1);
                            block.setBlockData(sapling);

                            block.getWorld().spawnParticle(Particle.COMPOSTER,block.getLocation(),10,0.2,0.4,0.2);
                            lowerFood = true;
                        }
                    }
                }
            }
        }
        return lowerFood;
    }

    private void generateTree(Block block) {
        switch (block.getType()) {
            case OAK_SAPLING:
                block.setType(Material.AIR);
                if(Rand.randomInt(1,20) == 10) {
                    block.getWorld().generateTree(block.getLocation(), TreeType.BIG_TREE);
                } else {
                    block.getWorld().generateTree(block.getLocation(), TreeType.TREE);
                }
                break;
            case ACACIA_SAPLING:
                block.setType(Material.AIR);
                block.getWorld().generateTree(block.getLocation(), TreeType.ACACIA);
                break;
            case BIRCH_SAPLING:
                block.setType(Material.AIR);
                block.getWorld().generateTree(block.getLocation(), TreeType.BIRCH);
                break;
            case SPRUCE_SAPLING:
                if(block.getLocation().add(-1,0,0).getBlock().getType().equals(Material.SPRUCE_SAPLING)
                        || block.getLocation().add(0,0,-1).getBlock().getType().equals(Material.SPRUCE_SAPLING)
                        || block.getLocation().add(1,0,0).getBlock().getType().equals(Material.SPRUCE_SAPLING)
                        || block.getLocation().add(0,0,1).getBlock().getType().equals(Material.SPRUCE_SAPLING)
                ) {
                    break;
                } else {
                    block.setType(Material.AIR);
                    block.getWorld().generateTree(block.getLocation(), TreeType.REDWOOD);
                }
                break;
            case JUNGLE_SAPLING:
                if(block.getLocation().add(-1,0,0).getBlock().getType().equals(Material.JUNGLE_SAPLING)
                        || block.getLocation().add(0,0,-1).getBlock().getType().equals(Material.JUNGLE_SAPLING)
                        || block.getLocation().add(1,0,0).getBlock().getType().equals(Material.JUNGLE_SAPLING)
                        || block.getLocation().add(0,0,1).getBlock().getType().equals(Material.JUNGLE_SAPLING)
                ) {
                    break;
                } else {
                    block.setType(Material.AIR);
                    block.getWorld().generateTree(block.getLocation(), TreeType.SMALL_JUNGLE);
                }
                break;
            case DARK_OAK_SAPLING:
                break;
            case BAMBOO_SAPLING:
                break;
        }
    }
}

