package online.bingulhan.towny.land;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class LandRequest {

    public static ArrayList<LandRequest> INVITE_LIST = new ArrayList<>();

    private String name;
    private Land land;

    public LandRequest(String name, Land land) {
        this.name=name;
        this.land=land;
    }
}
