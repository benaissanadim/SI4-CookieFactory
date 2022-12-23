package fr.unice.polytech.cookiefactory.connectors.timertask.timertaskclass;

import fr.unice.polytech.cookiefactory.exceptions.TimerTaskException;

public interface ITimerTask {

    public void start() throws TimerTaskException;

}
