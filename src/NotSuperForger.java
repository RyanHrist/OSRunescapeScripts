import java.awt.*;
import java.awt.event.WindowEvent;

import API.*;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.MagicSpell;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;

@ScriptManifest(name = "NotSuperForger", author = "Ryan", version = 1.0, info = "", logo = "")

public class NotSuperForger extends Script
{

    Banking banking = new Banking(this);

    public void onStart()
    {

    }

    public int onLoop() throws InterruptedException {
        if(players.myPlayer().isAnimating())
        {
            log("Random antiban");
            if(random(1,20) < 3)
                mouse.move(random(0,200),random(0,200));
            if(random(1,20) < 3)
                mouse.move(random(0,200),random(0,200));
            if(random(1,20) < 15)
            {
                players.getSkills().hoverSkill(Skill.MAGIC);
            }
        } else {
            boolean tinFlag = false;
            boolean copperFlag = false;
            boolean runeFlag = false;
            for (Item item : inventory.getItems()) {
                if (item != null) {
                    log("Inventory item: " + item.getName());
                    if(item.getName().equals("Tin ore"))
                        tinFlag = true;
                    else if (item.getName().equals("Nature rune"))
                        runeFlag = true;
                    else if (item.getName().equals("Copper ore"))
                        copperFlag = true;
                }
            }
            if (runeFlag && tinFlag && copperFlag) {
                Sleep.doSleep(88, 24);
                Spells.NormalSpells forge = Spells.NormalSpells.SUPERHEAT_ITEM;
                players.magic.castSpell(forge);
                for (Item item: inventory.getItems()) {
                    if (item.getName().equals("Copper ore") || item.getName().equals("Tin ore")) {
                        item.interact("Cast");
                        item.interact("Use");
                        break;
                    }
                    Sleep.doSleep(100,20);
                }
            } else {
                NPC banker = npcs.closest("Banker");
                banker.interact("Bank");
                Sleep.doSleep(500,20);
                bank.depositAll("Bronze bar");
                Sleep.doSleep(145, 18);
                bank.withdraw("Copper ore", 10);
                Sleep.doSleep(15,10);
                bank.withdraw("Tin ore", 10);
            }
        }
        return random(100);
    }

    public void onExit()
    {

    }

    public void onPaint(Graphics2D g)
    {
    }
}