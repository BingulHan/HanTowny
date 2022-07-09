package online.bingulhan.towny.bll;

import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandManager;
import org.bukkit.OfflinePlayer;

public class PlayerManager {

    public boolean isMember(OfflinePlayer player) {
        for (Land land : LandManager.getLands()) {
            for (String membership : land.getMembers()) {
                if (membership.equals(player.getName())) return true;
            }
        }

        return false;
    }

    public Land getLand(OfflinePlayer player)  throws NullPointerException{

        for (Land land : LandManager.getLands()) {
            for (String membership : land.getMembers()) {
                if (membership.equals(player.getName())) return land;
            }
        }

        return null;

    }
}
