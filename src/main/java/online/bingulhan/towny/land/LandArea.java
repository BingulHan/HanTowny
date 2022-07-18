package online.bingulhan.towny.land;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import online.bingulhan.towny.Towny;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Getter
@Setter
public class LandArea implements Comparable<LandArea>{

    public static ArrayList<LandArea> AREA_LIST = new ArrayList<>();

    public static File AREA_FILE = new File(Towny.getInstance().getDataFolder(), "lands.yml");
    private Land connectedLand;


    @Setter(AccessLevel.NONE)
    private Chunk chunk;

    /**
     * Bağlı olduğyu land ve kurulacak chunk seçilir.
     * @param connectedLand
     * @param chunk
     */
    public LandArea(Land connectedLand, Chunk chunk) {
        this.connectedLand=connectedLand;
        this.chunk=chunk;

    }


    public static void addArea(Land land, LandArea area) {
        land.getLands().add(area);
        addBackup(area);
    }

    public static void addBackup(LandArea land) {
        FileConfiguration landConfig = YamlConfiguration.loadConfiguration(LandArea.AREA_FILE);


        landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".x", land.getChunk().getX());
        landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".z", land.getChunk().getZ());
        landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".wn", land.getChunk().getWorld().getName());
        landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".land", land.getConnectedLand().getPresidentData());

        try {
            landConfig.save(AREA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetAreas(Land l) {

        FileConfiguration landConfig = YamlConfiguration.loadConfiguration(LandArea.AREA_FILE);

        for (LandArea area : l.getLands()) {
            resetArea(area);
        }

        try {
            landConfig.save(AREA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetArea(LandArea area) {
        FileConfiguration landConfig = YamlConfiguration.loadConfiguration(LandArea.AREA_FILE);

        landConfig.set(""+area.getChunk().getWorld().getName()+area.getChunk().getX()+area.getChunk().getZ(), null);
        area.getConnectedLand().getLands().remove(area);

        AREA_LIST.remove(area);

        try {
            landConfig.save(AREA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
    }

    @Override
    public int compareTo(@NotNull LandArea o) {
        return 0;
    }
}
