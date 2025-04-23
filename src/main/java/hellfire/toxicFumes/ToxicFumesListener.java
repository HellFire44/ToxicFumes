package hellfire.toxicFumes;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ToxicFumesListener implements Listener {
    private final ToxicFumes plugin;
    private final ArenaManager arenaManager;
    private final Set<UUID> playersInRegion = new HashSet<>();


    public ToxicFumesListener(ToxicFumes plugin, ArenaManager arenaManager) {
        this.plugin = plugin;
        this.arenaManager = arenaManager;
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        UUID playerId = player.getUniqueId();

        boolean isInAnyRegion = false;
        for (region reg : arenaManager.getArenas().values()) {
            if (isInsideArena(loc, reg.getMin(), reg.getMax())) {
                isInAnyRegion = true;

                if (!playersInRegion.contains(playerId)) {

                    //Title
                    String titleMainText = plugin.getConfig().getString("title-main");
                    String titleSubText = plugin.getConfig().getString("title-sub");

                    // Apply Minecraft color codes (like &a, &b) using ChatColor
                    titleMainText = ChatColor.translateAlternateColorCodes('&', titleMainText);
                    titleSubText = ChatColor.translateAlternateColorCodes('&', titleSubText);


                    int fadeIn = plugin.getConfig().getInt("title-fade-in", 10);
                    int stay = plugin.getConfig().getInt("title-stay", 40);
                    int fadeOut = plugin.getConfig().getInt("title-fade-out", 10);

                    //Warning-Sound
                    String toxicWarningSound = plugin.getConfig().getString("warning-sound", "BLOCK_FIRE_EXTINGUISH");

                    //PotionEffects
                    String effects1 = plugin.getConfig().getString("effect");
                    PotionEffectType effectsType1 = PotionEffectType.getByName(effects1);
                    int effectDuration = plugin.getConfig().getInt("effects-duration");
                    int effectLevel = plugin.getConfig().getInt("effects-level");



                    playersInRegion.add(playerId);
                    player.sendTitle(titleMainText,titleSubText, fadeIn, stay, fadeOut);

                    try {
                        Sound sound = Sound.valueOf(toxicWarningSound);
                        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                    } catch (IllegalArgumentException e) {
                        plugin.getLogger().info("Sounds can't be played");
                }
                    player.addPotionEffect(new PotionEffect(effectsType1, effectDuration, effectLevel ));
                }
                Color startingColor = Color.fromRGB(0x13e704);
                Color endingColor = Color.fromRGB(0x31ed23);
                Particle.DustTransition dustTransition = new Particle.DustTransition(startingColor, endingColor, 2.0f );
                player.spawnParticle(Particle.DUST_COLOR_TRANSITION, player.getLocation(), 30, 0.5, 1.0, 0.5, dustTransition);

                if (player.getHealth() > reg.getDamageAmount()) {
                    player.damage(reg.getDamageAmount());
                } else {
                    player.setHealth(1.0);
                }
                break;
            }
        }
        if (!isInAnyRegion && playersInRegion.contains(playerId)) {
            playersInRegion.remove(playerId);
            String message = plugin.getConfig().getString("arena-leave-text");
            message = ChatColor.translateAlternateColorCodes('&', message);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

        }
    }

    private boolean isInsideArena(Location loc, Vector min, Vector max) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        return x >= Math.min(min.getX(), max.getX()) && x <= Math.max(min.getX(), max.getX())
                && y >= Math.min(min.getY(), max.getY()) && y <= Math.max(min.getY(), max.getY())
                && z >= Math.min(min.getZ(), max.getZ()) && z <= Math.max(min.getZ(), max.getZ());
    }
    private void spawnPaticles(Player player){

    }
}
