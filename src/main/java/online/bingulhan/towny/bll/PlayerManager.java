package online.bingulhan.towny.bll;

import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandManager;
import org.bukkit.OfflinePlayer;

public class PlayerManager {

    public boolean isMember(OfflinePlayer player) {
        return LandManager.getLands().stream().anyMatch(land -> land.getMembers().contains(player.getName()));
    }

    public Land getLand(OfflinePlayer player)  throws NullPointerException{
        return LandManager.getLands().stream().filter(land -> land.getMembers().contains(player.getName())).findAny().get();
    }
}
