package online.bingulhan.towny;

import online.bingulhan.towny.cmd.CMDLand;
import online.bingulhan.towny.cmd.CMDLandFounder;
import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandArea;
import online.bingulhan.towny.land.LandManager;
import online.bingulhan.towny.land.backup.IBackupManager;
import online.bingulhan.towny.land.backup.YamlBackupManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Towny extends JavaPlugin {


    private static Towny instance;

    public IBackupManager backupManager;


    @Override
    public void onEnable() {
        instance=this;

        getConfig().options().copyDefaults(true);
        saveConfig();


        backupManager=new YamlBackupManager();
        LandManager.setLands(backupManager.getAllLands());


        loadLandAreas();
        registerCommands();

        getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void event(BlockBreakEvent e) {
                LandManager landManager = new LandManager();
                if (landManager.isLand(e.getBlock().getChunk())) {
                    e.getPlayer().sendMessage(ChatColor.YELLOW+"Ülkenin adı: "+landManager.getLand(e.getBlock().getChunk()).getLandName());
                }


            }

        }, this);

    }

    @Override
    public void onDisable() {

        FileConfiguration landConfig = YamlConfiguration.loadConfiguration(LandArea.AREA_FILE);

        for (LandArea land: LandArea.AREA_LIST) {
            landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".x", land.getChunk().getX());
            landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".z", land.getChunk().getZ());
            landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".wn", land.getChunk().getWorld().getName());
            landConfig.set(""+land.getChunk().getWorld().getName()+land.getChunk().getX()+land.getChunk().getZ()+".land", land.getConnectedLand().getPresidentData());
        }

        //Save File
        try {
            landConfig.save(LandArea.AREA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LandManager.getLands().forEach(land -> backupManager.backup(land));

    }

    public static Towny getInstance() {
        return instance;
    }


    public void registerCommands() {
        this.getCommand("town").setExecutor(new CMDLand());
        getCommand("townfounder").setExecutor(new CMDLandFounder());
    }

    public void loadLandAreas() {
        if (!LandArea.AREA_FILE.exists()) {
            try {
                LandArea.AREA_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        getServer().getScheduler().runTaskLater(this, () -> {

            FileConfiguration townyConfig = YamlConfiguration.loadConfiguration(LandArea.AREA_FILE);

            for (String area : townyConfig.getConfigurationSection("").getKeys(false)){
                try {

                    int x = townyConfig.getInt(area+".x");
                    int z = townyConfig.getInt(area+".z");
                    String wN = townyConfig.getString(area+".wn");
                    String presidentName = townyConfig.getString(area+".land");
                    World world = getServer().getWorld(wN);
                    Land land = new LandManager().getLand(presidentName);

                    LandArea ar = new LandArea(land, world.getChunkAt(x,z));
                    LandArea.addArea(land, ar);
                    LandArea.AREA_LIST.add(ar);

                }catch (Exception exception) {

                }
            }
        }, 20);
    }
}
