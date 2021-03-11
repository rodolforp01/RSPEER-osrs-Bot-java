package org.indexer;


import org.indexer.process.banking;
import org.indexer.process.floorItems;
import org.indexer.process.pickItems;
import org.indexer.process.traverse;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.StopWatch;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.runetek.api.scene.SceneObjects;
import org.rspeer.runetek.event.listeners.ChatMessageListener;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.ChatMessageEvent;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import org.rspeer.script.task.TaskScript;
import org.rspeer.ui.Log;

import java.awt.*;
import java.util.Random;

@ScriptMeta(name = "GreenDragonTest", desc = "MoneyMaking", developer = "indexer")
public class Main extends TaskScript implements ChatMessageListener, RenderListener {

    //FIXME:  i have to add death class

    StopWatch running;
    public static final Area dragonArea = Area.rectangular(2964, 3626, 2992, 3604);
    public static final Area faladorArea = Area.rectangular(2957, 3387, 2974, 3371);
    public static final Area bankArea = Area.rectangular(2945, 3368, 2949, 3368);
    public static final Area resetArea = Area.rectangular(3029, 3657, 3046, 3647);
    public static final Area gotoBankArea = Area.rectangular(2967, 3604, 2989, 3601);
    public static final Area outBankArea =  Area.rectangular(2943, 3371, 2945, 3370);

    public static final String teleport = "Falador teleport";

    public static boolean shouldAttack = false; //boolean to decide wheter to attack drags or not
    public static boolean isAttacked = false; //bolean to check if the dragon is attacked

    public static boolean NoAmmo = false;
    public static boolean doneAmmo = false;

    public static Task go[] = {new floorItems(), new traverse(), new banking(), new pickItems()};


    @Override
    public void notify(RenderEvent renderEvent) {
        Graphics d = renderEvent.getSource();
        d.drawString("RunTime: " + running.toElapsedString(),30,30 );
    }

    @Override
    public void notify(ChatMessageEvent e){
        if(e.getMessage().contains("Your cannon is out of ammo!"))
            NoAmmo = true;
        if (e.getMessage().contains("You load the cannon with 30 cannonballs"))
            doneAmmo = true;

    }

    @Override
    public void onStart(){
        running = StopWatch.start();
        Log.fine("starting");
        submit(go);
    }

    @Override
    public void onStop(){

    }

    //recharge ammo
    public static void RechargeAmmo(){
        Log.fine("Recharging cannon");
        Time.sleep(2000,3500);
        SceneObject cannon = SceneObjects.getNearest(c -> c.getName().equals("Dwarf multicannon"));
        if(cannon != null){
            cannon.interact("Fire");
            Time.sleep(4000,5000);
            NoAmmo = false;
            Time.sleepUntil(() -> doneAmmo == true,10000);
            doneAmmo = false;
        }
    }

    public static int randomInt(int min, int max){

        Random rand = new Random();

        int RandomNum = rand.nextInt((max - min) + 1) + min;

        return RandomNum;
    }

}
