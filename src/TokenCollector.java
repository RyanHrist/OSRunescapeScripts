import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import API.Banking;
import API.Fighter;
import API.ItemGrabber;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import javax.swing.*;

@ScriptManifest(name = "TokenCollector", author = "Ryan", version = 1.0, info = "", logo = "")

public class TokenCollector extends Script
{
    Fighter fighter = new Fighter(this);
    ItemGrabber itemGrabber = new ItemGrabber(this);
    String Activity;
    String[] Enemies = {"Animated Black Armour"};

    public void onStart()
    {

        fighter.setEnemies(Enemies);
        fighter.unpauseFighter();

        List<String> items = new ArrayList<String>();

        items.add("Warrior guild token");
        items.add("Black full helm");
        items.add("Black platebody");
        items.add("Black platelegs");


        itemGrabber.grabAlways(items);
    }

    public void onExit()
    {

    }

    public int onLoop()
    {
        if (players.myPlayer().getHealthPercent() < 60) {
            log("Health at : " + players.myPlayer().getHealthPercent() + "... eating food ");
            Item[] inventoryList = inventory.getItems();
            for (Item item : inventoryList) {
                if (item != null) {
                    log("Inventory item: " + item.getName());
                    if (item.hasAction("Eat")) {
                        item.interact("Eat");
                        try {
                            MethodProvider.sleep(1500);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        if (inventory.getItem("Black platelegs") != null &&
                inventory.getItem("Black platebody") != null &&
                inventory.getItem("Black full helm") != null) {
            RS2Object animatorCheck = objects.closest("Magical Animator");
            if (animatorCheck != null)
                animatorCheck.interact("Animate");
            doSleep(1000,300);
        } else if (!players.myPlayer().isUnderAttack()) {
            itemGrabber.scanFloor();
        }
        if(fighter.isFighterPaused())
        {
            doSleep(1000,300);
            log("Fighter is paused");
            while(players.myPlayer().isMoving())
            {
                doSleep(1000,100);
            }
            doSleep(500,100);
            if(players.myPlayer().isAnimating() || players.myPlayer().isUnderAttack())
            {
                log("Random antiban");
                if(random(1,20) < 3)
                    mouse.move(random(0,200), random(0,200));
                if(random(1,20) < 3)
                    mouse.move(random(0,200), random(0,200));
                if(random(1,20) < 5)
                    fighter.hoverOverRandomNPC();
                if(random(1,20) < 10)
                    fighter.hoverOverRandomObject();
                if(random(1,20) < 15)
                {
                    fighter.checkTrainingStat();
                }
            }
            else
            {
                itemGrabber.scanFloor();
                fighter.unpauseFighter();
            }
        }
        else
        {
            log("Fighter is NOT paused");
            boolean success = fighter.attackEnemies();
            doSleep(200,500);
            while(players.myPlayer().isMoving())
            {
                doSleep(1000,100);
            }
            if(success)
            {
                fighter.pauseFighter();
            }
            else
            {

            }
        }
        return random(100,200);
    }

    public void doSleep(int ms, int randomizer)
    {
        if(randomizer > ms)
        {
            try
            {
                sleep(ms);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        ms = random(ms - randomizer, ms + randomizer);
        try
        {
            sleep(ms);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void onPaint(Graphics2D g)
    {
        g.drawString(Activity,12,316);
    }
}