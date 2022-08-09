package niewidzialny84.github.weedcollector.crop;

import niewidzialny84.github.weedcollector.utils.RandomGenerator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;

public class CropHarvest
{
    public CropHarvest()
    {

    }

    public void check3x3Area(Block block) {
        int multiply = 0;

        for(int i = -1 ; i <= 1 ;i++) {
            for (int j = -1; j <= 1; j++) {
                checkCrop(block.getLocation().add(i, 0, j).getBlock(), multiply);
            }
        }
    }

    private void checkCrop(Block block,int multiply) {
        if (block != null) {
            for(CropEnum crop : CropEnum.values()) {
                if(block.getType().equals(crop.getBlock())) {
                    setCrop(block, RandomGenerator.randomInt(crop.getMin()+multiply,crop.getMax()+multiply),crop.getDrop());
                }
            }
        }
    }

    public void checkCrop(Block block) {
        int multiply = 0;

        if (block != null) {
            for(CropEnum crop : CropEnum.values()) {
                if(block.getType().equals(crop.getBlock())) {
                    setCrop(block, RandomGenerator.randomInt(crop.getMin()+multiply,crop.getMax()+multiply),crop.getDrop());
                }
            }
        }
    }

    private void setCrop(Block block, int amount, Material material) {
        Ageable cropAge = ((Ageable) block.getBlockData());
        if (cropAge.getAge() == cropAge.getMaximumAge()) {
            cropAge.setAge(0);
            block.setBlockData(cropAge);
            block.getWorld().dropItem(block.getLocation(), new ItemStack(material, amount));
        }
    }
}
