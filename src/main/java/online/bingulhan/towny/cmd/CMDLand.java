package online.bingulhan.towny.cmd;

import online.bingulhan.towny.Towny;
import online.bingulhan.towny.bll.LandFounderManager;
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
                        land.getLands().add(new LandArea(land, player.getPlayer().getLocation().getChunk()));

                        landManager.addLand(land, true);

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

            if (args[0].equalsIgnoreCase("accept")) {
                Player player = (Player) sender;
                PlayerManager playerManager = new PlayerManager();
                LandManager landManager = new LandManager();
                if (!playerManager.isMember(player)) {

                    if (args.length>1) {
                        String l = args[1];
                        if (landManager.getLandExtented(l)!=null) {
                            if (playerManager.isReq(player, landManager.getLandExtented(l))) {
                                playerManager.acceptPlayer(player, landManager.getLandExtented(l));
                                player.sendMessage(ChatColor.GREEN+"Artık vatandaşsın!");
                                player.sendTitle("", ChatColor.GREEN+"Yeni Vatandaşlık: "+l);


                            }else {
                                player.sendMessage(ChatColor.GREEN+l+" Ülkesinden gelen bir vatandaşlık davetin yok.");
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+""+l+" Adında bir ülke yok!");
                        }
                    }else {
                        player.sendMessage(ChatColor.GREEN+"Ülke adı giriniz. Kullanım: /land accept <isim>");
                    }


                }else {
                    player.sendMessage(ChatColor.RED+"Zaten bir ülkeye üyesin.");
                }
            }

            if (args[0].equalsIgnoreCase("leave")) {
                Player player = (Player) sender;
                PlayerManager playerManager = new PlayerManager();
                LandManager landManager = new LandManager();
                if (!playerManager.isMember(player)) {

                    Land land = playerManager.getLand(player);
                    land.getMembers().remove(player.getName());
                    player.sendTitle("", ChatColor.RED+"Vatandaşlıktan ayrıldın!");

                    if (land.getPresidentData().equals(player.getName())) {
                        for (String member : land.getMembers()) {

                            LandFounderManager landFounderManager = new LandFounderManager();
                            landFounderManager.setLandPresident(land, member);

                            OfflinePlayer offlinePlayer = Towny.getInstance().getServer().getOfflinePlayer(member);
                            if (offlinePlayer.isOnline()) {
                                offlinePlayer.getPlayer().sendMessage(ChatColor.GREEN+"Artık ülkenin liderisin!");
                            }
                            return true;
                        }
                    }


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
