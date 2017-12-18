package API;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import java.util.ArrayList;

/**
 * Created by ryanhristovski on 2017-12-11.
 */
public class Walker {
    private Script script;

    public Walker(Script s)
    {
        this.script = s;
    }

    public boolean walkToVarrock() {
        Position varrock = new Position(3213, 3425, 0);
        return script.getWalking().webWalk(varrock);
    }

    public void walkToLumbridgeBank(){
        Position lumbridgeBank = new Position(3209,3218,0);
        script.getWalking().webWalk(lumbridgeBank);
    }

    public boolean walkToGnomeVillage() {
        Position gnomeVillage = new Position(2400,3400,0);
        return script.getWalking().webWalk(gnomeVillage);
    }

    public boolean walkToSpecifiedLocation(int x, int y, int z) {
        Position location = new Position(x,y,z);
        return script.getWalking().webWalk(location);
    }

}




















