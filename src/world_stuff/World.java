package world_stuff;

import utils_bs.XmlUtils;

import java.util.ArrayList;

public class World {

    private ArrayList<Node> map = new ArrayList<>();

    public World() {
        this.map = XmlUtils.readMap("Mappe.xml");
    }



}
