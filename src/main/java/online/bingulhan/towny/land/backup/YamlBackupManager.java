package online.bingulhan.towny.land.backup;

import online.bingulhan.towny.Towny;
import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class YamlBackupManager implements IBackupManager {

    File folder = new File(Towny.getInstance().getDataFolder(), "backups");
    public YamlBackupManager() {
        if (!folder.exists()) folder.mkdir();
    }

    @Override
    public void backup(Land land) {
        File file = new File(folder.getPath(), land.getToken()+".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileConfiguration c = YamlConfiguration.loadConfiguration(file);
        c.set("president", land.getPresidentData());
        c.set("members", land.getMembers());
        c.set("landname", land.getLandName());
        try {
            c.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Land getLand(String presidentName) {
        for (File file : folder.listFiles()) {
            try {

                if (file.getName().equals(presidentName)) {
                    FileConfiguration c = YamlConfiguration.loadConfiguration(file);
                    String president = c.getString("president");
                    Set<String> members = (Set<String>) c.get("members");
                    String landName = c.getString("landname");
                    String token = file.getName();
                    return new Land(president, members, true).setName(landName).setToken(token);
                }

            }catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public void delete(Land land) {
        File file = new File(folder.getPath(), land.getToken()+".yml");

        if (!file.exists()) {
            return;
        }

        FileUtil.delete(file);





    }

    @Override
    public ArrayList<Land> getAllLands() {

        ArrayList<Land> lands = new ArrayList<>();

        for (File file : folder.listFiles()) {
            try {
                    FileConfiguration c = YamlConfiguration.loadConfiguration(file);
                    String president = c.getString("president");
                    Set<String> members = (Set<String>) c.get("members");
                    String landName = c.getString("landname");
                    String token = file.getName();

                    lands.add(new Land(president, members, true).setName(landName).setToken(token));


            }catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return lands;

    }
}
