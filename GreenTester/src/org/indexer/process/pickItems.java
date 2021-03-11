package org.indexer.process;

import org.indexer.Main;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

import java.util.Arrays;

public class pickItems extends Task {


    @Override
    public boolean validate() {
        Log.fine("PickItemsClass");
        return Main.bankArea.contains(Players.getLocal()) && Bank.isOpen();
    }

    @Override
    public int execute() {

        if(Bank.isOpen()){
            Time.sleep(1200,2000);
            getCanonBall();
            Time.sleepUntil(() -> Inventory.getCount(true, 2) == 100,50000);
            Time.sleep(700,1200);
            troutCountAndGet();
            Time.sleepUntil(() -> Inventory.getCount(333) == 20, 50000);
            Time.sleep(500,1500);
            faladorTP();
            Time.sleepUntil(() -> Inventory.getCount("Falador teleport") == 1, 50000);
            getBurningAmulet();
            Time.sleepUntil(() -> Inventory.getCount(amulet()) == 1, 60000);
            Time.sleep(1500,2500);
            antiFirePotion();
            Time.sleep(1500,2000);
            getBolts();
            Time.sleep(1000,2000);
            Log.fine("Done getting inventory items");
            banking.doneTask = false;
            Bank.close();
            Time.sleepUntil(Bank::isClosed,25000);
            Movement.walkToRandomized(() -> Main.outBankArea.getTiles().get(Main.randomInt(0,Main.outBankArea.getTiles().size() - 1)));
            Time.sleepUntil(() -> Main.outBankArea.contains(Players.getLocal()), 50000);
            if(Main.outBankArea.contains(Players.getLocal())) {
                Log.fine("Ready to travel to the Bandit camp");
                Item letsGo = Inventory.getFirst(amulet());
                if(letsGo != null){
                    Time.sleep(1200,1500);
                    letsGo.interact("Rub");
                    Log.fine("Selecting the desired destination");

                    Time.sleep(2000,3000);
                    InterfaceComponent goingTo = Interfaces.getComponent(219,1,2);
                    if(goingTo != null) {
                        Log.fine("First opt ring done");
                        goingTo.interact("Continue");

                        Time.sleep(2000,3000);
                        InterfaceComponent wildernes = Interfaces.getComponent(219,1,1);
                        Time.sleepUntil(() -> wildernes.isVisible(), 10000);
                        if(wildernes != null){
                            Time.sleep(1000);
                            wildernes.interact("Continue");
                            Log.fine("Done");
                        }
                    }

                }

            }

        }

        return Main.randomInt(600,800);
    }


    //methods
   public void troutCountAndGet(){ // returns the remaining trout
        Log.fine("Getting the food");
        int trout = Inventory.getCount(333);
        if(trout != 20) {
            Time.sleep(2000);
            int max = 20;
            int troutsToGet = max - trout;
            Bank.withdraw(333, troutsToGet);
        } else if (trout == 20){
            Log.fine("You have 15 trouts");
            return;
        } else {
            Bank.withdraw(333,20);
        }
        Time.sleep(600,1200);

        return;
    }

    public void faladorTP(){
        Log.fine("Getting falador tp");
        Item falador = Inventory.getFirst("Falador teleport");
        if(falador != null){
            Log.fine("You got falador tp");
            return;
        } else if(falador == null){
            Time.sleep(1000,3000);
            Item tp = Bank.getFirst("Falador teleport");

            if(tp != null){
                tp.interact("Withdraw-1");
                Time.sleep(1200,1300);
            }

        }
        return;
    }


    public void antiFirePotion(){ // gets the antifire potion
        Log.fine("Getting the antifire potion");
        int antifire1 = Inventory.getCount(11953);
        int antifire2 = Inventory.getCount(11955);
        int antifire3 = Inventory.getCount(11957);

        if(antifire1 == 1)
            return;
        else if(antifire2 == 1)
            return;
        else if(antifire3 == 1)
            return;
        else
            Bank.withdraw(11951,1);
        return;
    }


    public void getCanonBall(){
        Log.fine("WithDrawing cannonballs");
        int cannonCount = Inventory.getCount(true, 2);
        if(cannonCount != 0 && cannonCount != 100){
            int result = 100 - cannonCount;
            Time.sleep(200,300);
            Bank.withdraw(2,result);
        }
        else if(cannonCount == 0)
            Bank.withdraw(2,100);
        else if (cannonCount == 100){
            Log.fine("100 cannonballs already");
            Time.sleep(2000,2500);
            return;
        }

        Time.sleep(1300,2000);
        return;
    }


    public void getBurningAmulet(){
        Log.fine("Getting the burning amulet");
        int burning1 = Inventory.getCount("Burning amulet(4)");
        int burning2 = Inventory.getCount("Burning amulet(3)");
        int burning3 = Inventory.getCount("Burning amulet(2)");
        int burning4 = Inventory.getCount("Burning amulet(1)");
        int burning5 = Inventory.getCount("Burning amulet(5)");

        if(burning1 == 1)
            return;
        else if(burning2 == 1)
            return;
        else if(burning3 == 1)
            return;
        else if(burning4 == 1)
            return;
        else if(burning5 == 1)
            return;
        else
            Bank.withdraw(21166,1);

        return;
    }

    public int amulet(){
        Log.fine("Getting the inventory's Amulet to teleport");
        Item amulet1 = Inventory.getFirst(21166);
        Item amulet2 = Inventory.getFirst(21169);
        Item amulet3 = Inventory.getFirst(21171);
        Item amulet4 = Inventory.getFirst(21173);
        Item amulet5 = Inventory.getFirst(21175);

        if(amulet1 != null)
            return 21166;
        else if(amulet2 != null)
            return 21169;
        else if (amulet3 != null)
            return 21171;
        else if (amulet4 != null)
            return 21173;
        else if (amulet5 != null)
            return 21175;

        return 0;
    }

    public void getBolts(){
        Item bolts = Bank.getFirst(floorItems.items[4]);
        if(bolts != null){
            Time.sleep(500,1000);
            Bank.withdraw(floorItems.items[4],Main.randomInt(90,120));
            Time.sleepUntil(() -> Inventory.getCount(true, floorItems.items[4]) > 0,30000);
            Item equiptBolt = Inventory.getFirst(floorItems.items[4]);
            if(equiptBolt != null){
                Time.sleep(1200,3000);
                equiptBolt.interact("Wield");
                Time.sleep(1500,2000);
            }

        }
        return;
    }

}
