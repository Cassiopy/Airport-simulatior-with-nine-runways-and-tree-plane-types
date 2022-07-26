package ar.edu.unsl.mys.entities;

import java.util.List;
//import java.util.Comparator;
import java.util.LinkedList;

import ar.edu.unsl.mys.events.Event;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.events.ArrivalEvent;
import ar.edu.unsl.mys.events.EndOfServiceEvent;

public abstract class Entity
{
    private static int idCount = 0; //Cantidad de entidades creadas
    private static int entitiesProcessed = 0;
    private static double totalWaitingTime;
    private static double maxWaitingTime;
    private static double totalTransitTime;
    private static double maxTransitTime;

    //attributes
    private int id; // falta get 
    private double waitingTime;
    private double transitTime;

    //associations
    private Server attendingServer;
    private List<Event> events; //En 0 guardo el de evento de llegada y en 1 el evento de salida
    
    // others
    /**
     * Used if it is necessary to order chronologically the events of this entity.
     */
    //private Comparator<Event> comparator;

    public Entity() //Como creo una entidad cada vez que hay un arribo, solo necesito el id ya que el tiempo de espera y de tránsito de esta entidad aún no lo tengo
    {
        idCount++;
        this.id = idCount;
        this.events = new LinkedList<Event>();
    }

    public int getId()
    {
        return this.id;
    }
    public static double getTotalWaitingTime()
    {
        return Entity.totalWaitingTime;
    }

    public static int getIdCount()
    {
        return Entity.idCount;
    }

    public static double getMaxWaitingTime()
    {
        return Entity.maxWaitingTime;
    }

    public static double getTotalTransitTime()
    {
        return Entity.totalTransitTime;
    }

    public static double getMaxTransitTime()
    {
        return Entity.maxTransitTime;
    }


    public void setWaitingTime(double waitingTime)
    {
        this.waitingTime = waitingTime;
        Entity.totalWaitingTime += this.waitingTime;
        if(this.waitingTime > Entity.maxWaitingTime) Entity.maxWaitingTime = this.waitingTime;
    }

    public void setTransitTime(double transitTime)
    {
        this.transitTime = transitTime;
        Entity.totalTransitTime += this.transitTime;
        if(this.transitTime > Entity.maxTransitTime) Entity.maxTransitTime = this.transitTime;
    }

    public ArrivalEvent getArrivalEvent()
    {
        return (ArrivalEvent)this.events.get(0);
    }

    public EndOfServiceEvent getEndOfServiceEvent()
    {
        return (EndOfServiceEvent)events.get(1);
    }

    public Server getAttendingServer()
    {
        return this.attendingServer;
    }

    public void setAttendingServer(Server attendingServer)
    {
        this.attendingServer = attendingServer;
    }

    public List<Event> getEvents()
    {
        return events;
    }

    public void setEvent(Event event)
    {
        if(event instanceof ArrivalEvent) events.add(0, event); //Evento de llegada
        else events.add(1, event); //Evento de salida
    }
    
    public void countAsProcessed()
    {
        Entity.entitiesProcessed++;
    }

    public int processedEntities()
    {
        return Entity.entitiesProcessed;
    }
    public abstract void applyEffectOnServerOnEnter();
    public abstract void applyEffectOnServerOnExit();
    public abstract double getTransitTime();
    public abstract double getWaitingTime();
}