package world_stuff;

import java.util.ArrayList;

public class TamaWorld extends World{

    public TamaWorld(ArrayList<Node> map) {
        super(map);
    }

    @Override
    public String getType() {
        return "Tamagolem World";
    }
}
