package API;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import java.util.Random;

/**
 * Created by ryanhristovski on 2017-12-12.
 */
public class NatureRuneThiever {
    private Script script;
    private long lastStatCheckTime = System.currentTimeMillis();
    private long statCheckTimeout = 60000;

    public NatureRuneThiever(Script s){
        this.script = s;
    }

    public void lootChest() {
        RS2Object chestCheck = this.script.objects.closest("Chest");
        if(chestCheck != null) {
            if (chestCheck.getPosition().distance(this.script.myPlayer().getPosition()) <= 5) {
                chestCheck.interact("Search for traps");
            }
        }
    }

    public boolean walkToChest() {
        Position position = new Position(2671, 3304, 1);
        return script.getWalking().webWalk(position);
    }

    public int random(int a, int b)
    {
        Random random = new Random();
        long range = (long)b - (long)a + 1;
        long fraction = (long)(range * random.nextDouble());
        int randomNumber =  (int)(fraction + a);
        return randomNumber;
    }

    /**
     * Checks a random Combat stat
     */

    public void checkTrainingStat()
    {
        long time = System.currentTimeMillis();
        if(time - lastStatCheckTime > statCheckTimeout)
        {
            this.script.tabs.open(Tab.SKILLS);
            Skill s = Skill.ATTACK;
            int rand = random(0,4);
            if(rand == 0)
                s = Skill.THIEVING;

            try {
                MethodProvider.sleep(random(100,1000));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                this.script.skills.hoverSkill(s);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                MethodProvider.sleep(random(1000,2500));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            lastStatCheckTime = time;

            this.script.tabs.open(Tab.INVENTORY);
            try {
                MethodProvider.sleep(random(200,600));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            this.script.mouse.move(random(0,250),random(0,250));
        }
    }
}
