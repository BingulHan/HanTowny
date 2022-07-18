package online.bingulhan.towny.bll;

import online.bingulhan.towny.Towny;
import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandManager;
import online.bingulhan.towny.land.LandRequest;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public class PlayerManager {

    public boolean isMemberInLand(OfflinePlayer player, Land land) {

        for (String s : land.getMembers()) {
            if (s.equals(player.getName())) {
                return true;
            }
        }

        return false;
    }
    public boolean isMember(OfflinePlayer player) {
        return LandManager.getLands().stream().anyMatch(land -> land.getMembers().contains(player.getName()));
    }

    public Land getLand(OfflinePlayer player)  throws NullPointerException{
        return LandManager.getLands().stream().filter(land -> land.getMembers().contains(player.getName())).findAny().get();
    }

    public void invitePlayer(OfflinePlayer player, Land land) {
        LandRequest landRequest = new LandRequest(player.getName(), land);
        LandRequest.INVITE_LIST.add(landRequest);
        player.getPlayer().sendMessage(ChatColor.GREEN+""+land.getLandName()+" Ülkesinden gelen bir katılma davetin var!");


        Towny.getInstance().getServer().getScheduler().runTaskLater(Towny.getInstance(), () -> {
            if (isReq(player, land)) {
                LandRequest.INVITE_LIST.remove(landRequest);
                player.getPlayer().sendMessage(ChatColor.RED+"Ülke davetini kabul etmediğin için reddedildi.");
            }
        }, 20*10);
    }

    public void acceptPlayer(OfflinePlayer player, Land land) {
        land.getMembers().add(player.getName());

        for (LandRequest landRequest : LandRequest.INVITE_LIST) {
            if (landRequest.getLand().getPresidentData().equals(land.getPresidentData()) && landRequest.getName().equals(player.getName())) {
                LandRequest.INVITE_LIST.remove(landRequest);
            }
        }

    }

    public boolean isReq(OfflinePlayer player, Land land) {
        for (LandRequest landRequest : LandRequest.INVITE_LIST) {
            if (landRequest.getLand().getPresidentData().equals(land.getPresidentData()) && landRequest.getName().equals(player.getName())) {
                return true;
            }
        }

        return false;
    }
}
