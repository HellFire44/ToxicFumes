package hellfire.toxicFumes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public final class ToxicFumes extends JavaPlugin {
    private ArenaManager arenamanager;

    @Override
    public void onEnable() {
      saveDefaultConfig();
      arenamanager = new ArenaManager(this);
        getCommand("setmin").setExecutor(this);
        getCommand("setmax").setExecutor(this);
        getLogger().info("Loaded regions: " + arenamanager.getArenas().size());
        getCommand("createregion").setExecutor(this);
        getServer().getPluginManager().registerEvents(new ToxicFumesListener(this, arenamanager), this);
    }

    public ArenaManager getArenaManager() {
        return arenamanager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("setmin")) {
            SelectionManager.setMin(player.getUniqueId(), player.getLocation().toVector());
            player.sendMessage("§aMin position set.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setmax")) {
            SelectionManager.setMax(player.getUniqueId(), player.getLocation().toVector());
            player.sendMessage("§aMax position set.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("createregion")) {
            if (args.length != 1) {
                player.sendMessage("§cUsage: /createregion <name>");
                return true;
            }

            Vector min = SelectionManager.getMin(player.getUniqueId());
            Vector max = SelectionManager.getMax(player.getUniqueId());

            if (min == null || max == null) {
                player.sendMessage("§cPlease set both min and max positions first.");
                return true;
            }

            String name = args[0];
            region reg = new region(name, min, max, 5, 2, 1); // Default values


            ArenaManager arenaManager = getArenaManager();
            arenaManager.getArenas().put(name, reg);
            arenaManager.saveArenas();

            player.sendMessage("§aRegion " + name + " created and saved.");
            SelectionManager.clear(player.getUniqueId());
            return true;
        }

        return false;
    }

}
