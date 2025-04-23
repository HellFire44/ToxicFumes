package hellfire.toxicFumes;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;



public class region {
    private int timeBeforeDamage;
    private int damageInterval;
    private int damageAmount;

    private String arenaName;
    private Vector min;
    private Vector max;

    public region(String arenaName, Vector min, Vector max, int timeBeforeDamage, int damageInterval, int damageAmount){
          this.arenaName = arenaName;
         this.damageInterval = damageInterval;
         this.damageAmount = damageAmount;
         this.timeBeforeDamage = timeBeforeDamage;
         this.max = max;
         this.min = min;
    }
    public String getArenaName() {
        return arenaName;
    }

    public Vector getMin() {
        return min;
    }

    public Vector getMax() {
        return max;
    }

    public int getTimeBeforeDamage() {
        return timeBeforeDamage;
    }

    public int getDamageInterval() {
        return damageInterval;
    }

    public int getDamageAmount() {
        return damageAmount;
    }
    public static region fromConfig(String arenaName, YamlConfiguration config) {
        String path = "arenas." + arenaName + ".";
        Vector min = new Vector(config.getDouble(path + "min.x"), config.getDouble(path + "min.y"), config.getDouble(path + "min.z"));
        Vector max = new Vector(config.getDouble(path + "max.x"), config.getDouble(path + "max.y"), config.getDouble(path + "max.z"));
        int timeBeforeDamage = config.getInt(path + "timeBeforeDamage");
        int damageInterval = config.getInt(path + "damageInterval");
        int damageAmount = config.getInt(path + "damageAmount");

        return new region(arenaName, min, max, timeBeforeDamage, damageInterval, damageAmount);
    }
}
