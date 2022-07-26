package ar.edu.unsl.mys.events;

import java.util.List;
import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.engine.FutureEventList;
import ar.edu.unsl.mys.behaviors.EventBehavior;

public abstract class Event
{
    //attributes
    private double clock;

    //associations
    private Entity entity;
    private EventBehavior eventBehavior;

    //other
    /**
     * Used to format toString output
     */
    protected static int END_TIME_DIGITS;

    public Event(double clock, Entity entity, EventBehavior eventBehavior)
    {
        this.clock = clock;
        this.entity = entity;
        this.eventBehavior = eventBehavior;
    }

    public double getClock()
    {
        return this.clock;
    }

    public void setClock(double clock)
    {
        this.clock = clock;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    public void setEntity(Entity entity)
    {
        this.entity = entity;
    }

    public EventBehavior getEventBehavior()
    {
        return this.eventBehavior;
    }

    public void setEventBehavior(EventBehavior eventBehavior)
    {
        this.eventBehavior = eventBehavior;
    }


    /**
     * This method performs the necessary planifications that this event 
     * has to do for the proper execution of bootstrapping.
     * @param servers The list of servers needed to do the planification.
     * @param fel The future event list to insert the next events.
     */
    public abstract void planificate(List<Server> servers, FutureEventList fel);
    
}