package online.bingulhan.towny.land.backup;

import online.bingulhan.towny.land.Land;

import java.util.ArrayList;

public interface IBackupManager {

    public void backup(Land land);
    public Land getLand(String presidentName);

    public void delete(Land land);


    public ArrayList<Land> getAllLands();


}
