package ar.edu.unsl.mys.behaviors;

import ar.edu.unsl.mys.events.Event;
//import ar.edu.unsl.mys.App;
import ar.edu.unsl.mys.entities.aircrafts.*;
import ar.edu.unsl.mys.entities.maintenance.*;
import ar.edu.unsl.mys.entities.Entity;
//import ar.edu.unsl.mys.utils.Randomizer;
import ar.edu.unsl.mys.utils.CustomRandomizer;
import ar.edu.unsl.mys.events.EndOfServiceEvent;

public class EndOfServiceEventBehavior extends EventBehavior
{
    private final int FACTOR_CONVERSION = 60;
    private final int A_MEDIANA_UNIFORME = 10;
    private final int B_MEDIANA_UNIFORME = 20;
    private final int A_MANTENIMIENTO_UNIFORME = 12;
    private final int B_MANTENIMIENTO_UNIFORME = 24;
    
    private static EndOfServiceEventBehavior endOfServiceEventBehavior;

    private EndOfServiceEventBehavior(CustomRandomizer randomizer)
    {
        super(randomizer);
    }

    public static EndOfServiceEventBehavior getInstance()
    {
        if(endOfServiceEventBehavior == null) endOfServiceEventBehavior = new EndOfServiceEventBehavior(CustomRandomizer.getInstance());
        return endOfServiceEventBehavior;
    }

    private EndOfServiceEvent nextEventLightAircraft(double random, Entity entity, double clock)
    {
        double time;
        if(random <= 0.363) time = 5;
        else if(random > 0.363 && random <= 0.838) time = 10;
        else time = 15;
        
        return new EndOfServiceEvent(clock + time, entity);
    }

    private EndOfServiceEvent nextEventMediumAircraft(double random, Entity entity, double clock)
    {
        double time = random*(B_MEDIANA_UNIFORME-A_MEDIANA_UNIFORME)+A_MEDIANA_UNIFORME; 

        return new EndOfServiceEvent(clock + time, entity);
    }

    private EndOfServiceEvent nextEventHeavyAircraft(double random, Entity entity, double clock)
    {
        double time;
        if(random <= 0.65) time = 40;
        else time = 50;

        return new EndOfServiceEvent(clock + time, entity);
    }

    private EndOfServiceEvent nextEventMaintenance(double random, Entity entity, double clock)
    {
        double timeH = random*(B_MANTENIMIENTO_UNIFORME-A_MANTENIMIENTO_UNIFORME)+A_MANTENIMIENTO_UNIFORME;        
        double timeM = timeH*FACTOR_CONVERSION; //Como la duración del servicio está en horas, la paso a minutos

        return new EndOfServiceEvent(clock + timeM, entity);
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {
        EndOfServiceEvent ret = null;
        double random = this.getRandomizer().nextRandom();

        if(actualEvent.getEntity() instanceof LightAircraft) ret = this.nextEventLightAircraft(random, entity, actualEvent.getClock());
        else if(actualEvent.getEntity() instanceof MediumAircraft) ret = this.nextEventMediumAircraft(random, entity, actualEvent.getClock());
        else if(actualEvent.getEntity() instanceof HeavyAircraft) ret = this.nextEventHeavyAircraft(random, entity, actualEvent.getClock());
        else if(actualEvent.getEntity() instanceof Maintenance) ret = this.nextEventMaintenance(random, entity, actualEvent.getClock());

        return ret;
    } 
}

