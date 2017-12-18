/**
 * Created by ryanhristovski on 2017-12-12.
 */
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

@ScriptManifest(name = "SuperForger", author = "Ryan", version = 1.0, info = "", logo = "")

public class SuperForger extends Script
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
            boolean runeFlag = false;
            boolean ironFlag = false;
            boolean coalFlag = false;
            Item[] inventoryList = inventory.getItems();
            for (Item item : inventoryList) {
                if (item != null) {
                    log("Inventory item: " + item.getName());
                    if(item.getName().equals("Iron ore"))
                        ironFlag = true;
                    else if (item.getName().equals("Nature rune"))
                        runeFlag = true;
                    else if (item.getName().equals("Coal"))
                        coalFlag = true;
                }
            }
            if (runeFlag && ironFlag && coalFlag) {
                Spells.NormalSpells forge = Spells.NormalSpells.SUPERHEAT_ITEM;
                players.magic.castSpell(forge);
                for (Item item: inventoryList) {
                    if (item.getName().equals("Iron ore")) {
                        item.interact("Use");
                        break;
                    }
                    Sleep.doSleep(500,20);
                }
            } else {
                NPC banker = npcs.closest("Banker");
                banker.interact("Bank");
                Sleep.doSleep(500,20);
                if(banking.withdrawCoal())
                    banking.withdrawIron();
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