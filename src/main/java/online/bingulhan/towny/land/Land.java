package online.bingulhan.towny.land;

import lombok.Getter;
import lombok.Setter;
import online.bingulhan.towny.Towny;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class Land {

    private String presidentData;

    private String token;
    private String landName = "Han";
    private Set<String> members = new TreeSet<>();
    private Set<LandArea> lands = new TreeSet<>();

    public Land(@NotNull String presidentData, @NotNull Set<LandArea> lands) {
        this.lands=lands;
        this.presidentData=presidentData;
        this.members.add(presidentData);

        token=createToken();
    }

    public Land(@NotNull String presidentData, @NotNull  Set<LandArea> lands, @NotNull Set<String> members) {
        this.lands=lands;
        this.presidentData=presidentData;
        this.members=members;

        token=createToken();
    }

    public Land(@NotNull String presidentData, @NotNull Set<String> members, boolean init) {
        this.lands=init();
        this.presidentData=presidentData;
        this.members=members;

        Towny.getInstance().getServer().getConsoleSender().sendMessage("Adet: "+this.lands.size());

        token=createToken();
    }

    private Set<LandArea> init() {
        Set<LandArea> areas = new TreeSet<>();
        for (LandArea area : LandArea.AREA_LIST) {
            if (area.getConnectedLand().presidentData.equals(this.presidentData)) {
                areas.add(area);
            }
        }

        return areas;
    }
    public Land setName(String newLandName) {
        this.landName=newLandName;
        return this;
    }

    public static String createToken() {
        String s = "Land"+(System.currentTimeMillis()/10000);
        return s;
    }

    public Land setToken(String token) {
        this.token=token;
        return this;
    }

}
