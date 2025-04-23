package hellfire.toxicFumes;

import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectionManager {
    private static final Map<UUID, Vector> minPoints = new HashMap<>();
    private static final Map<UUID, Vector> maxPoints = new HashMap<>();

    public static void setMin(UUID uuid, Vector vec) {
        minPoints.put(uuid, vec);
    }

    public static void setMax(UUID uuid, Vector vec) {
        maxPoints.put(uuid, vec);
    }

    public static Vector getMin(UUID uuid) {
        return minPoints.get(uuid);
    }

    public static Vector getMax(UUID uuid) {
        return maxPoints.get(uuid);
    }

    public static void clear(UUID uuid) {
        minPoints.remove(uuid);
        maxPoints.remove(uuid);
    }
}
