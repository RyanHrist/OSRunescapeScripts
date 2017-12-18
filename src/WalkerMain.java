import java.awt.*;
import java.awt.event.WindowEvent;

import API.Banking;
import API.Fighter;
import API.ItemGrabber;
import API.Walker;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import javax.swing.*;

@ScriptManifest(name = "Traveler", author = "Ryan", version = 1.0, info = "", logo = "")

public class WalkerMain extends Script
{
    Walker walker = new Walker(this);
    String Activity;
    JButton varrock, lumbridge, gnomeVillage;
    JFrame f;
    JTextField xField, yField, zField;
    JButton ok;
    public void onStart()
    {
        f = new JFrame("Select Travel Location");
        JPanel panel=new JPanel();
        panel.setBounds(40,80,200,200);
        varrock =new JButton("Varrock");
        varrock.setBounds(50,100,80,30);
        lumbridge=new JButton("Lumbridge");
        lumbridge.setBounds(100,100,80,30);
        gnomeVillage = new JButton("Gnome Village");
        gnomeVillage.setBounds(150,100,80,30);

        xField = new JTextField(4);
        xField.setBounds(200, 100, 30,30);
        yField = new JTextField(4);
        yField.setBounds(200, 150, 30,30);
        zField = new JTextField(1);
        zField.setBounds(200, 200, 30, 30);
        ok = new JButton("Submit");
        ok.setBounds(235, 150, 30,30);

        panel.add(xField);
        panel.add(yField);
        panel.add(zField);
        panel.add(ok);


        panel.add(varrock);
        panel.add(lumbridge);
        panel.add(gnomeVillage);
        f.add(panel);
        f.setSize(400,400);
        f.setLayout(null);
        f.setVisible(true);
    }

    public int onLoop()
    {
        int count = 0;
        if(varrock.getModel().isPressed()) {
            if(count == 0)
                walker.walkToVarrock();
            count++;
        } else if (lumbridge.getModel().isPressed()) {
            if(count == 0)
                walker.walkToLumbridgeBank();
            count++;
        } else if (gnomeVillage.getModel().isPressed()) {
            if(count == 0)
                walker.walkToGnomeVillage();
            count++;
        } else if (ok.getModel().isPressed()) {
            if(count ==0)
                walker.walkToSpecifiedLocation(Integer.parseInt(xField.getText()),
                        Integer.parseInt(yField.getText()),
                        Integer.parseInt(zField.getText()));
            count++;
        }
        return 0;
    }

    public void onExit()
    {
        f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }

    public void onPaint(Graphics2D g)
    {
        g.drawString(Activity,12,316);
    }
}