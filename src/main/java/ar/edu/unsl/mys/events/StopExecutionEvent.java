package ar.edu.unsl.mys.events;

import java.util.List;
//import javax.lang.model.SourceVersion;
//import javax.swing.text.html.parser.Entity;

import ar.edu.unsl.mys.engine.FutureEventList;
import ar.edu.unsl.mys.resources.airstrips.Server;
//import ar.edu.unsl.mys.entities.Aircraft;
import ar.edu.unsl.mys.engine.AirportSimulation;

public class StopExecutionEvent extends Event
{
    private AirportSimulation airportSimulation;

    public StopExecutionEvent(double clock, AirportSimulation airportSimulation)
    {
        super(clock, null, null);
        this.airportSimulation = airportSimulation;
        Event.END_TIME_DIGITS = ("" + this.airportSimulation.getEndTime()).length();
    }

    @Override
    public String toString()
    {
        return String.format("Type: Stop Execution -- Clock: %"+Event.END_TIME_DIGITS+"s -- entity: null", this.getClock());
    }

    @Override
    public void planificate(List<Server> servers, FutureEventList fel) {
        // TODO Auto-generated method stub
        this.airportSimulation.setStopSimulation(true);
        
    }
}