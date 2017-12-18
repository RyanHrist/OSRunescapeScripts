/**
 * Created by ryanhristovski on 2017-12-12.
 */
import java.awt.*;
import java.awt.event.WindowEvent;

import API.*;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;

@ScriptManifest(name = "Thiever", author = "Ryan", version = 1.0, info = "", logo = "")

public class Thiever extends Script
{
    NatureRuneThiever thief = new NatureRuneThiever(this);
    public void onStart()
    {
        //if(!thief.walkToChest()) {
        //    thief.walkToChest();
        //}

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
                thief.checkTrainingStat();
            }
        }

        thief.lootChest();
        MethodProvider.sleep(thief.random(2200,4400));
        return random(100);
    }

    public void onExit()
    {
    }

    public void onPaint(Graphics2D g)
    {
    }
}