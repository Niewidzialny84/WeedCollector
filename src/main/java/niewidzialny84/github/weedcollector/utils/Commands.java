package niewidzialny84.github.weedcollector.utils;

import niewidzialny84.github.weedcollector.WeedCollector;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor
{
    private final WeedCollector plugin;

    public Commands(WeedCollector plugin)
    {
        this.plugin = plugin;

        plugin.getCommand("weedcollector").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender.isOp() || sender.hasPermission("weedcollector")) {
            switch (args.length)
            {
                case 1:
                    switch(args[0].toLowerCase())
                    {
                        case "reload":
                            sender.sendMessage("Reload incoming");

                            plugin.reload();
                            return true;
                    }
                    break;
            }
        }

        return false;
    }
}
