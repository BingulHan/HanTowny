package online.bingulhan.towny.bll;

import online.bingulhan.towny.land.Land;
import online.bingulhan.towny.land.LandArea;
import online.bingulhan.towny.land.LandManager;

import java.util.ArrayList;

public class LandFounderManager extends LandManager{

    public void setLandName(Land land, String newLandName) {
        land.setLandName(newLandName);
    }

    public void setLandPresident(Land land, String newPresident) {
       land.setPresidentData(newPresident);
    }


}
