package org.indexer.process;

import org.indexer.Main;
import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class banking extends Task {

    public static boolean doneTask = false;

    @Override
    public boolean validate() {
        Log.fine("Banking class");
        return Main.bankArea.contains(Players.getLocal()) && doneTask == false;
    }

    @Override
    public int execute() {
        Log.fine("Banking");
        Npc bank = Npcs.getNearest("Banker");
        if(bank != null){
            Time.sleep(800,1200);
            if(bank.interact("Bank")){
                Log.fine("Interacting with the bank");
                Time.sleep(600,1200);

                if(randomCase() == 1){
                    Time.sleep(1000);
                    inventoryDeposit();
                    Time.sleep(3000,4000);
                    lootBagDeposit();
                    Log.fine("Finished");
                    doneTask = true;

                } else if(randomCase() == 0){
                    Time.sleep(1000);
                    lootBagDeposit();
                    Time.sleep(3000,4000);
                    inventoryDeposit();
                    Log.fine("Finished");
                    doneTask = true;

                }
            }
        }
        return 600;
    }

    public static int randomCase(){ //returns a random number so randomizes the order how it deposits the things
        return (int) (Math.random() * 2);
    }

    public void inventoryDeposit(){
        if(randomCase() == 1){
            Bank.getInventory(b -> b.getName().equals("Dragon bones") && b.interact("Deposit-All"));
            Time.sleepUntil(() -> Inventory.getCount("Dragon bones") == 0, 10000);
            Time.sleep(900,2000);
            Bank.getInventory(g -> g.getName().equals("Green dragonhide") && g.interact("Deposit-All"));
            Time.sleepUntil(() -> Inventory.getCount("Green dragonhide") == 0,10000);
            Time.sleep(1200,3000);

        }
        else if(randomCase() == 0){
            Bank.getInventory(b -> b.getName().equals("Green dragonhide") && b.interact("Deposit-All"));
            Time.sleepUntil(() -> Inventory.getCount("Green dragonhide") == 0,10000);
            Time.sleep(1500,1600);
            Bank.getInventory(g -> g.getName().equals("Dragon bones") && g.interact("Deposit-All"));
            Time.sleepUntil(() -> Inventory.getCount("Dragon bones") == 0, 10000);
            Time.sleep(2000,3500);
        }

        return;
    }

    public void lootBagDeposit(){
        Item[] lootBag = Bank.getInventory(l -> l.getName().equals("Looting bag") && l.interact("View"));
        Item lootbag2 = Inventory.getFirst("Looting bag");
        if(lootbag2 != null) {
            Log.fine("Loot bag not null");

            if (lootBag != null) {
                Log.fine("Loot bag is openned");
                Time.sleep(1200, 2000);
                InterfaceComponent DepositLoot = Interfaces.getComponent(15, 8);
                if (DepositLoot != null) {
                    Log.fine("Deposting loot");
                    Time.sleep(600, 1200);
                    DepositLoot.interact("Deposit loot");
                    Time.sleep(1200, 2000);
                    InterfaceComponent closeLot = Interfaces.getComponent(15, 10);
                    if (closeLot != null) {
                        Time.sleep(1200, 1300);
                        closeLot.interact("Dissmiss");
                        Time.sleep(1000, 1200);
                    }
                }
            }
        } else {
            Log.fine("Lootbag is not in the inventory");
        }
        return;
    }



}

