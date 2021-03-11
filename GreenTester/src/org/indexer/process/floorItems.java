package org.indexer.process;

import org.indexer.Main;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Pickable;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Pickables;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;


public class floorItems extends Task  {

    int boltsAttemp = 0; //a timer to pick bolts(a variable to increase)

    public static final String items[] = {"Green dragonhide","Dragon bones","Grimy cadantine","Ensouled dragon head","Iron bolts","Nature rune"};


    @Override
    public boolean validate() {
        Log.fine("fLOOR ITEMS CLASS");
        return Main.dragonArea.contains(Players.getLocal());
    }

    @Override
    public int execute() {
        Log.fine("This's the floor items class && fighting class whatever");
        pickItems();
        //goes to banking if no food or has items
        if(Inventory.getCount(items[0]) >= Main.randomInt(1,3) || Inventory.getCount("Trout") == 0){
            traverseOutDragons();
            Time.sleepUntil(() -> Main.gotoBankArea.contains(Players.getLocal()),6000);
            teleport();
        }
        //Eats
        Player me = Players.getLocal();
        if(me != null){
            if(me.getHealthPercent() < Main.randomInt(40,75)){
                rechargeHealth();
            }
        }
        //recharges ammo
        if(Main.NoAmmo){
            Main.RechargeAmmo();
        }

        return Main.randomInt(600,1000);
    }



    public void pickItems(){ // this is the main function that picks all the items ors checks
        pickDragonHide();
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        pickBones();
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        pickHerb();
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        pickDragonHead();
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        pickRunes();
        Time.sleepUntil(() -> !Players.getLocal().isMoving(), 5000);
        if(boltsAttemp < 12) {
            boltsAttemp++;
            Time.sleep(500);
            Log.fine("Im not taking the bolts yet: " + boltsAttemp + "out of 6");
            if(boltsAttemp >= 9) {
                pickBolts();
                boltsAttemp = 0;
            }
        }
        return;
    }

    public void pickDragonHide(){
        Pickable dragonHide = Pickables.getNearest(items[0]);
        if(dragonHide != null && Main.dragonArea.contains(dragonHide)){
            Time.sleep(1500,2000);
            dragonHide.interact("Take");

        } else
            Log.fine("DragonHide not avaiabla");
        return;
    }

    public void pickBones(){
        Pickable bones = Pickables.getNearest(items[1]);
        if(bones != null && Main.dragonArea.contains(bones)){
            Time.sleep(1500,2000);
            bones.interact("Take");

        } else
            Log.fine("bones not avaiabla");
        return;
    }

    public void pickDragonHead(){
        Pickable dragonHead = Pickables.getNearest(items[3]);
        if(dragonHead != null && Main.dragonArea.contains(dragonHead)) {
            Time.sleep(1000,1500);
            dragonHead.interact("Take");

        } else
            Log.fine("DragonHead not there");

        return;
    }

    public void pickHerb(){
        Pickable herb = Pickables.getNearest(items[2]);
        if(herb != null && Main.dragonArea.contains(herb)){
            Time.sleep(1200,2000);
            herb.interact("Take");

        } else
            Log.fine("Herb not there.");
        return;
    }

    public void pickBolts(){
        Pickable bolts = Pickables.getNearest(items[4]);
        if(bolts != null && Main.dragonArea.contains(bolts)){
            Log.fine("Picking bolts");
            bolts.interact("Take");
        } else
            Log.fine("There are not bolts");
        return;
    }

    void rechargeHealth(){
        Item fish = Inventory.getFirst(333);
        if(fish != null){
            Time.sleep(200,300);
            fish.interact("Eat");
        } else {
            Log.fine("You have no food");
            return;
        }
        return;
    }

    void pickRunes(){
        Pickable runes = Pickables.getNearest(items[5]);
        if(runes != null && Main.dragonArea.contains(runes)){
            Log.fine("Taking runes");
            runes.interact("Take");
        } else {
            Log.fine("Runes not there");
        }
    }


    void traverseOutDragons(){
        Movement.walkTo(Main.gotoBankArea.getCenter());
    }

    public void teleport(){
        if(Main.gotoBankArea.contains(Players.getLocal())) {
            Item tp = Inventory.getFirst(Main.teleport);
            if (tp != null)
                tp.interact("Break");
            Time.sleepUntil(() -> Main.faladorArea.contains(Players.getLocal()), 30000);
            return;
        }
    }

}
