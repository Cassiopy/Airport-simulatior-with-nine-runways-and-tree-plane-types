package ar.edu.unsl.mys.events;

import java.util.List;

//import javax.xml.namespace.QName;
//import ar.edu.unsl.mys.entities.aircrafts.Aircraft;
import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.engine.FutureEventList;
import ar.edu.unsl.mys.policies.ServerSelectionPolicy;
import ar.edu.unsl.mys.resources.airstrips.Server;
//import ar.edu.unsl.mys.resources.*;
import ar.edu.unsl.mys.App;
import ar.edu.unsl.mys.behaviors.ArrivalEventBehavior;
import ar.edu.unsl.mys.behaviors.EndOfServiceEventBehavior;
//import ar.edu.unsl.mys.events.*;
//import ar.edu.unsl.mys.resources.CustomQueue;

public class ArrivalEvent extends Event
{
    private ServerSelectionPolicy selectionPolicy;
    private EndOfServiceEventBehavior endOfServiceEventBehavior;

    public ArrivalEvent(double clock, Entity entity, ServerSelectionPolicy policy)
    {
       super(clock, entity, ArrivalEventBehavior.getInstance());
       this.getEntity().setEvent(this);
       this.selectionPolicy = policy;
       this.endOfServiceEventBehavior = EndOfServiceEventBehavior.getInstance();
    }

    public ServerSelectionPolicy getSelectionPolicy()
    {
        return this.selectionPolicy;
    }

    public EndOfServiceEventBehavior getEndOfServiceEventBehavior()
    {
        return this.endOfServiceEventBehavior;
    }

    @Override
    public String toString()
    {
        return String.format("Type: Arrival        -- Clock: %"+Event.END_TIME_DIGITS+"s -- entity: %s", this.getClock(), this.getEntity().toString());
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {

        Server server = this.selectionPolicy.selectServer(servers, this.getEntity());
        this.getEntity().setAttendingServer(server);
       
        if(server.isBusy())
        {
            server.getWQueue().enqueue(this.getEntity());
        }
        else //Caso especial
        {

            server.setBusy(true);
            server.setServedEntity(this.getEntity());

            //Apply effects on server
            this.getEntity().applyEffectOnServerOnEnter();

            //Finish idle time
            if( (!(this.getClock() > App.getExecutionTime())) && ((this.getClock() - server.getIdleTimeStartMark() > 0)))//Pregunto si el tiempo de arribo actual no supera el tiempo de ejecución
            {
                server.setIdleCount(); //Digo que hubo ocio (sumo uno cada vez que hay ocio)
                server.setIdleTimeFinishMark(this.getClock()); //Calculo el ocio parcial
            }
            
            fel.insert(this.endOfServiceEventBehavior.nextEvent(this, this.getEntity()));
        }

        fel.insert(this.getEventBehavior().nextEvent(this, null));

        this.getEntity().countAsProcessed();
    }

}