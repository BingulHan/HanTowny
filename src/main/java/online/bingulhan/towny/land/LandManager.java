package online.bingulhan.towny.land;

import online.bingulhan.towny.Towny;
import org.bukkit.Chunk;

import java.util.ArrayList;

public class LandManager {

    protected static ArrayList<Land> LANDS = new ArrayList<>();

    public static ArrayList<Land> getLands() {
        return new ArrayList<>(LANDS);
    }

    public static void setLands(ArrayList<Land> lands) {

        LANDS=new ArrayList<>(lands);

    }

    public void addLand(Land land) {

        LANDS.add(land);
        Towny.getInstance().backupManager.backup(land);


    }

    public void addLand(Land land, boolean init) {

        LANDS.add(land);
        Towny.getInstance().backupManager.backup(land);

        if (init) {
            for (LandArea landArea : land.getLands()) {
                LandArea.AREA_LIST.add(landArea);
            }
        }


    }

    public void removeLand(Land land) {

        LandArea.resetAreas(land);
        Towny.getInstance().backupManager.delete(land);
        LANDS.remove(land);

    }

    public Land getLand(String presidentData) throws NullPointerException {
        for (Land land : getLands()) {
            if (land.getPresidentData().equals(presidentData)) {
                return land;
            }
        }
        return null;
    }

    public Land getLandExtented(String landName) throws NullPointerException {
        for (Land land : getLands()) {
            if (land.getLandName().equals(landName)) {
                return land;
            }
        }
        return null;
    }


    /**
     * Chunkta bir bölgeye ait olup olmadığını
     * algılayan sistem.
     * @param chunk
     * @return
     */
    public Boolean isLand(Chunk chunk) {
        for (LandArea area: LandArea.AREA_LIST) {
            if (area.getChunk().getX() == chunk.getX() && area.getChunk().getZ()==chunk.getZ()) {
                if (area.getChunk().getWorld().getName().equals(chunk.getWorld().getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Chunkta bölge varsa bağlı olduğu ülkeyi geri döndüren method.
     * @param chunk
     * @return
     * @throws NullPointerException
     */
    public Land getLand(Chunk chunk) throws NullPointerException {
        for (LandArea area : LandArea.AREA_LIST) {
            if (area.getChunk().getX() == chunk.getX() && area.getChunk().getZ()==chunk.getZ()) {
                if (area.getChunk().getWorld().getName().equals(chunk.getWorld().getName())) {
                    return area.getConnectedLand();
                }
            }
        }

        return null;
    }


    /**
     * Chunkta bir bölge varsa var olan bölgeyi döndürür.
     * @param chunk
     * @return
     * @throws NullPointerException
     */
    public LandArea getLandArea(Chunk chunk) throws NullPointerException {
        for (LandArea area : LandArea.AREA_LIST) {
            if (area.getChunk().getX() == chunk.getX() && area.getChunk().getZ()==chunk.getZ())  {
                if (area.getChunk().getWorld().getName().equals(chunk.getWorld().getName())) {
                    return area;
                }

            }
        }

        return null;
    }


}
