package ar.edu.unsl.mys.events;

import java.util.List;
import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.engine.FutureEventList;
import ar.edu.unsl.mys.App;
import ar.edu.unsl.mys.behaviors.EndOfServiceEventBehavior;

public class EndOfServiceEvent extends Event
{
    public EndOfServiceEvent(double clock, Entity entity)
    {
        super(clock,entity,EndOfServiceEventBehavior.getInstance());
        this.getEntity().setEvent(this);
        
    }

    @Override
    public String toString()
    {
        return String.format("Type: End of Service -- Clock: %"+Event.END_TIME_DIGITS+"s -- entity: %s", this.getClock(), this.getEntity().toString());
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {
        
        Server server = this.getEntity().getAttendingServer();
        Entity entity = null;

        if(!(server.getWQueue().isEmpty()))
        {
            entity = server.getWQueue().next();
            server.setServedEntity(entity);
            entity.applyEffectOnServerOnEnter();

            //if(!(this.getClock() > App.getExecutionTime())) entity.setWaitingTime(this.getClock() - entity.getArrivalEvent().getClock()); //Wtime
           
            fel.insert(this.getEventBehavior().nextEvent(this, entity)); //Próximo fin de servicio
        }
        else
        {
            server.setBusy(false);
            server.setServedEntity(null);
            server.setIdleTimeStartMark(this.getClock());

            //Aplicar efectos 
            this.getEntity().applyEffectOnServerOnExit();
        }
        
        if(entity != null && !(this.getClock() > App.getExecutionTime())) entity.setWaitingTime(this.getClock() - entity.getArrivalEvent().getClock());

        this.getEntity().setTransitTime(this.getClock() - this.getEntity().getArrivalEvent().getClock());
    }
}