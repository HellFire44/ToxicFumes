package hellfire.toxicFumes;

import com.google.common.collect.Maps;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.foreign.Arena;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    private ToxicFumes plugin;
    private Map<String, region> arenas = new HashMap<>();
    private File regionsfile;

    public ArenaManager(ToxicFumes plugin) {
    this.plugin = plugin;
    this.regionsfile = new File(plugin.getDataFolder(), "regions.yml");
    }
    public void createArenas(){
        if (!regionsfile.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(regionsfile);
        for (String arenaName : config.getConfigurationSection("arenas").getKeys(false)) {
            region r = region.fromConfig(arenaName, config);
            arenas.put(arenaName, r);
        }


    }
    public void saveArenas(){
        YamlConfiguration config = new YamlConfiguration();
        for(Map.Entry<String, region> entry : arenas.entrySet()){
            String arenaName = entry.getKey();
            region region = entry.getValue();

            config.set("arenas." + arenaName + ".min.x", region.getMin().getX());
            config.set("arenas." + arenaName + ".min.y", region.getMin().getY());
            config.set("arenas." + arenaName + ".min.z", region.getMin().getZ());
            config.set("arenas." + arenaName + ".max.x", region.getMax().getX());
            config.set("arenas." + arenaName + ".max.y", region.getMax().getY());
            config.set("arenas." + arenaName + ".max.z", region.getMax().getZ());
            config.set("arenas." + arenaName + ".timeBeforeDamage", region.getTimeBeforeDamage());
            config.set("arenas." + arenaName + ".damageInterval", region.getDamageInterval());
            config.set("arenas." + arenaName + ".damageAmount", region.getDamageAmount());
        }
        try {
            config.save(regionsfile);
        } catch (Exception e) {
            plugin.getLogger().warning("Could not save arenas file.");
        }

    }
    public Map<String, region> getArenas() {
        return arenas;
    }

}
