package ar.edu.unsl.mys.engine;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Comparator;

import ar.edu.unsl.mys.events.EndOfServiceEvent;
import ar.edu.unsl.mys.events.Event;

public class FutureEventList
{
    private List<Event> felImpl;
    private Comparator<Event> comparator;

    public FutureEventList()
    {
        this.felImpl = new LinkedList<Event>();
        this.comparator = new Comparator<Event>(){

            @Override
            public int compare(Event event1, Event event2) {
                // TODO Auto-generated method stub

                int ret = 0;
                if(event1.getClock()>event2.getClock()) ret = 1;
                else if(event1.getClock()<event2.getClock()) ret = -1;
                else if(event1 instanceof EndOfServiceEvent) ret =-1;
                else ret = 1;
                 
                return ret;
            }
            
        };
    }

    public Event getImminent()
    {
        Event aux = this.felImpl.get(0);
        this.felImpl.remove(0);
        this.felImpl.sort(this.comparator);
        return aux;
    }

    public void insert(Event event)
    {
        this.felImpl.add(event);
        this.felImpl.sort(this.comparator);
    }

    @Override
    public String toString()
    {
        String ret = "============================================================== F E L ==============================================================\n";

        Iterator<Event> it = this.felImpl.iterator();

        while(it.hasNext())
        {
            ret += it.next().toString()+"\n";
        }

        ret += "***********************************************************************************************************************************\n\n";

        return ret;
    }
}