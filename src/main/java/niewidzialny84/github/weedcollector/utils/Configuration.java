package niewidzialny84.github.weedcollector.utils;

import niewidzialny84.github.weedcollector.WeedCollector;

import java.util.logging.Logger;

public class Configuration
{
    private final WeedCollector plugin;

    private boolean doHoeHarvest;
    private boolean useAnyHoe;
    private boolean doAoeHoe;
    private boolean doSneakGrow;

    private final Logger logger;

    public Configuration(WeedCollector plugin)
    {
        this.plugin = plugin;
        logger = plugin.getLogger();

        readConfig();
    }

    public void readConfig()
    {
        logger.info("Reading config...");

        plugin.reloadConfig();

        doHoeHarvest = plugin.getConfig().getBoolean("doHoeHarvest",true);
        useAnyHoe = plugin.getConfig().getBoolean("useAnyHoe",false);
        doAoeHoe = plugin.getConfig().getBoolean("doAoeHoe",false);
        doSneakGrow = plugin.getConfig().getBoolean("doSneakGrow",true);

        printConsoleConfig();

        logger.info("Reading config...COMPLETE");
    }

    public void printConsoleConfig()
    {
        logger.info("doHoeHarvest: " + doHoeHarvest);
        logger.info("useAnyHoe: " + useAnyHoe);
        logger.info("doAoeHoe: " + doAoeHoe);
        logger.info("doSneakGrow: " + doSneakGrow);
    }

    public boolean isDoHoeHarvest()
    {
        return doHoeHarvest;
    }

    public boolean isUseAnyHoe()
    {
        return useAnyHoe;
    }

    public boolean isDoAoeHoe()
    {
        return doAoeHoe;
    }

    public boolean isDoSneakGrow()
    {
        return doSneakGrow;
    }
}
