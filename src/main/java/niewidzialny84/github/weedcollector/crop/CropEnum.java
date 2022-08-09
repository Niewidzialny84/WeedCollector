package niewidzialny84.github.weedcollector.crop;

import org.bukkit.Material;

public enum CropEnum
{
    WHEAT(Material.WHEAT,Material.WHEAT,1,2),
    CARROT(Material.CARROTS,Material.CARROT,1,4),
    POTATO(Material.POTATOES,Material.POTATO,1,4),
    BEETROOT(Material.BEETROOTS,Material.BEETROOT,1,2),
    NETHER_WART(Material.NETHER_WART,Material.NETHER_WART,1,4),
    COCOA_BEANS(Material.COCOA,Material.COCOA_BEANS,1,2),
    ;

    final int max;
    final int min;
    final Material block;
    final Material drop;

    CropEnum(Material block, Material drop, int min, int max) {
        this.max = max;
        this.min = min;
        this.block = block;
        this.drop = drop;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public Material getBlock() {
        return block;
    }

    public Material getDrop() {
        return drop;
    }
}
