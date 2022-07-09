package online.bingulhan.towny.cmd;

import online.bingulhan.towny.bll.PlayerManager;
import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandArea;
import online.bingulhan.towny.land.LandManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CMDLand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length>0) {
            if (args[0].equalsIgnoreCase("olustur")) {

                Player player = (Player) sender;
                PlayerManager playerManager = new PlayerManager();
                if (!playerManager.isMember(player)) {
                    LandManager landManager = new LandManager();
                    if (!landManager.isLand(player.getLocation().getChunk())) {

                        Set<LandArea> area = new TreeSet<>();
                        Land land = new Land(player.getName(), area);
                        LandArea.addArea(land, new LandArea(land, player.getPlayer().getLocation().getChunk()));
                        landManager.addLand(land);

                        player.sendTitle("", ChatColor.GREEN+"Ülken kuruldu!");
                    }
                }
            }

            if (args[0].equalsIgnoreCase("sil")) {
                Player player = (Player) sender;
                PlayerManager playerManager = new PlayerManager();
                LandManager landManager = new LandManager();

                if (playerManager.isMember(player)) {
                    Land land = playerManager.getLand(player);
                    player.sendMessage(ChatColor.RED+"Ülken tarihin tozlu raflarına kaldırıldı.");
                    player.sendTitle("", ChatColor.RED+"Ülken tarihin tozlu raflarına kaldırıldı.");
                    landManager.removeLand(land);
                }else {
                    player.sendMessage(ChatColor.RED+"Şuan da bir vatanın yok.");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
