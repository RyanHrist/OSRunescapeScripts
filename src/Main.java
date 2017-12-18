import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import API.Banking;
import API.Fighter;
import API.ItemGrabber;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import javax.swing.*;

@ScriptManifest(name = "Fighter", author = "Ryan", version = 1.0, info = "", logo = "")

public class Main extends Script
{
    private long timeBegan;
    private long timeRan;
    private static final DecimalFormat k = new DecimalFormat("#.#");
    private static final int[] skill = {0, 1, 2, 3, 4, 6};
    private static final String[] skillNames = {"Attack", "Defence", "Strength", "HitPoints", "Range", "Magic"};
    private static final Color[] skillColors = {new Color(145, 25, 25).brighter(), new Color(95, 115, 185),
            Color.GREEN.darker(), Color.WHITE.darker(), new Color(70, 95, 20).brighter(), new Color(95, 115, 230)};
    private int[] startXP;


    Fighter fighter = new Fighter(this);
    ItemGrabber itemGrabber = new ItemGrabber(this);
    Banking banking = new Banking(this);

    String Activity;
    String[] Enemies = {"Ghoul"};

    public void onStart()
    {
        timeBegan = System.currentTimeMillis();
        startXP = new int[6];
        for (int i = 0; i < skill.length; i++) {
            startXP[i] = skills.getExperience(Skill.forId(skill[i]));
        }

        fighter.setEnemies(Enemies);
        fighter.unpauseFighter();

        List<String> items = new ArrayList<String>();
        items.add("Dragon bones");
        items.add("Green dragonhide");


        List<String> ignore = new ArrayList<String>();
        ignore.add("Bones");
        ignore.add("Logs");
        ignore.add("Oak logs");

        itemGrabber.ignore(ignore);
        itemGrabber.grabAlways(items);
        itemGrabber.grabIfPriceIsHigherThan(1000);
    }

    public void onExit()
    {

    }

    public int onLoop()
    {
        if (players.myPlayer().getHealthPercent() < 60) {
            log("Health at : " + players.myPlayer().getHealthPercent() + "... eating food ");
            Item[] inventoryList = inventory.getItems();
            int count = 0;
            for (Item item : inventoryList) {
                if (item != null) {
                    log("Inventory item: " + item.getName());
                    if (item.hasAction("Eat")) {
                        count++;
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
            if (count == 0) {
                RS2Object doorCheck = objects.closest("Door");
                if(doorCheck != null)
                {
                    doorCheck.interact("Open");
                    try {
                        MethodProvider.sleep(random(10000,2000));
                        players.logoutTab.logOut();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        if(fighter.isFighterPaused())
        {
            doSleep(500,300);
            log("Fighter is paused");
            while(players.myPlayer().isMoving())
            {
                doSleep(500,100);
            }
            doSleep(500,100);
            if(players.myPlayer().isAnimating() || players.myPlayer().isUnderAttack())
            {
                if (players.getTabs().getCombat().getSpecialPercentage() == 100)
                    players.getTabs().getCombat().toggleSpecialAttack(true);
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
            doSleep(200,100);
            while(players.myPlayer().isMoving())
            {
                doSleep(400,200);
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
    private String ft(long duration)
    {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                .toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                .toMinutes(duration));
        if (days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        } else {
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;
    }

    public void onPaint(Graphics2D g)
    {
        int y = 25, z = 16, w = 3, x = 5;
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point m = mouse.getPosition();
        g.drawLine((int) m.getX() - 3, (int) m.getY(), (int) m.getX() + 3, (int) m.getY());
        g.drawLine((int) m.getX(), (int) m.getY() - 3, (int) m.getX(), (int) m.getY() + 3);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        g.setColor(Color.BLACK);
        g.drawRect(w, 4, 200, 11);
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(w, 4, 200, 11);
        g.setColor(Color.WHITE);
        g.drawString(Enemies[0] + " - " + " Fighter", x, 12);
        double eph;
        int exp;
        for (int i = 0; i < 6; i++) {
            exp = (skills.getExperience(Skill.forId(skill[i])) - startXP[i]);
            if (exp > 0) {
                eph = (exp * 3600000D / (System.currentTimeMillis() - timeBegan));
                g.setColor(Color.BLACK);
                g.drawRect(w, z, 200, 11);
                g.setColor(new Color(0, 0, 0, 220));
                g.fillRect(w, z, 200, 11);
                g.setColor(skillColors[i]);
                g.drawString(skillNames[i] + ": " + k.format(exp / 1000D) + " K Earned - " + k.format(eph / 1000) + " K P/H", x, y);
                y += 11;
                z += 11;
            }
        }
    }
}