package API;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import API.Banking;
import API.Fighter;
import API.ItemGrabber;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import static java.lang.Thread.sleep;
import static org.osbot.rs07.script.MethodProvider.random;

/**
 * Created by ryanhristovski on 2017-12-14.
 */
public class Sleep {
    public static void doSleep(int ms, int randomizer)
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
}
