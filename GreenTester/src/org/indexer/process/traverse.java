package org.indexer.process;

import org.indexer.Main;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.task.Task;
import org.rspeer.ui.Log;

public class traverse extends Task {

    @Override
    public boolean validate() {
        Log.fine("Traversing class");
        return (Main.dragonArea != null && Main.bankArea != null) && Main.faladorArea.contains(Players.getLocal()) || Main.resetArea.contains(Players.getLocal());
    }

    @Override
    public int execute() {

        if(traverseToBank()) {
            while (!Main.bankArea.contains(Players.getLocal())) {
                Log.fine("Checking if im on the bank");
                Movement.walkToRandomized(Main.bankArea.getTiles().get(Main.randomInt(0, Main.bankArea.getTiles().size() - 1)));
                Time.sleep(1700,2000);
            }
        }
        if(traverseToDragons()) {
            Log.fine("Traversing to dragons");
            while(!Main.dragonArea.contains(Players.getLocal())) {
                Movement.walkToRandomized(Main.dragonArea.getTiles().get(Main.randomInt(0, Main.dragonArea.getTiles().size() - 1)));
                Time.sleep(1600,2400);
            }
        }

        return Main.randomInt(1200,2000);
    }

    public boolean traverseToBank(){
        return Main.faladorArea.contains(Players.getLocal());
    }

    public boolean traverseToDragons(){
        return Main.resetArea.contains(Players.getLocal());
    }



}
