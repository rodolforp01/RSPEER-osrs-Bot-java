package org.indexer.process;

import org.rspeer.runetek.api.movement.position.Area;
import org.rspeer.script.task.Task;

public class death extends Task {

    //under development
    static final Area deadArea = Area.rectangular(3213, 3223, 3226, 3213);

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public int execute() {
        return 0;
    }


}
