package online.bingulhan.towny.cmd;

import online.bingulhan.towny.Towny;
import online.bingulhan.towny.bll.LandFounderManager;
import online.bingulhan.towny.bll.PlayerManager;
import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDLandFounder implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        PlayerManager playerManager = new PlayerManager();
        LandFounderManager landManager = new LandFounderManager();

        if (playerManager.isMember(player)) {
            if (playerManager.getLand(player).getPresidentData().equals(player.getName())) {
                if (args.length>0) {
                    if (args[0].equals("setname")) {
                        if (args.length>1) {
                            String newName = args[1];
                            landManager.setLandName(playerManager.getLand(player), newName);
                            player.sendMessage(ChatColor.GREEN+"Ülkenin ismi artık "+newName);
                        }else {
                            player.sendMessage(ChatColor.GREEN+"Doğru kullanım: /townyfounder setname <isim>");
                        }
                    }

                    if (args[0].equals("setfounder")) {
                        if (args.length>1) {
                            OfflinePlayer target = Towny.getInstance().getServer().getOfflinePlayer(args[1]);
                            if (playerManager.isMemberInLand(target, playerManager.getLand(player))) {
                                new LandFounderManager().setLandPresident(playerManager.getLand(player), target.getName());
                                player.sendMessage(ChatColor.GREEN+"Ülkenin yeni lideri "+ChatColor.YELLOW+target.getName());
                            }else {
                                player.sendMessage(ChatColor.RED+"Ülkenin bir vatandaşı değil.");
                            }
                        }else {
                            player.sendMessage(ChatColor.GREEN+"Ülkede olan birinin adını giriniz.");
                        }
                    }

                    if (args[0].equals("invite")) {
                        if (args.length>1) {
                            OfflinePlayer target = Towny.getInstance().getServer().getOfflinePlayer(args[1]);
                            if (target.isOnline()) {

                                if (!playerManager.isMember(target)) {
                                    if (!playerManager.isReq(target, playerManager.getLand(player))) {
                                        player.sendMessage(ChatColor.GREEN+"Ülkeye katılma daveti gönderildi.");
                                        playerManager.invitePlayer(target, playerManager.getLand(player));
                                    }else {
                                        player.sendMessage(ChatColor.GREEN+"Zaten davet gönderdin!");
                                    }
                                }else {
                                    player.sendMessage(ChatColor.RED+"Bu oyuncunun bir ülkeye zaten vatandaş!");
                                }

                            }else {
                                player.sendMessage(ChatColor.RED+""+target.getName()+" Aktif değil.");
                            }
                        }
                    }
                }
            }else {
                player.sendMessage(ChatColor.RED+"Sadece ülkenin başkanı ülkede değişimler yapabilir.");
            }
        }else {
            player.sendMessage(ChatColor.RED+"Bir ülkeye sahip değilsin.");
        }
         return true;
    }
}
