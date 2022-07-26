package ar.edu.unsl.mys.resources.queues;
import java.util.Iterator;
import java.util.LinkedList;
import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.entities.maintenance.*;

public class CustomQueue extends Queue
{

    public CustomQueue()
    {
        super(new LinkedList<Entity>());
    }

    @Override
    public boolean maintenanceOnHold()
    {
        for(Entity o: this.getQueue())
        {
            if(o instanceof Maintenance) return true;
        }
        return false;
    }

    @Override
    public String toString()
    {
        String ret = "server queue "+this.getAssignedServer().getId()+":\n\t";

        Iterator<Entity> it = this.getQueue().iterator();

        while(it.hasNext())
        {
            ret += it.next().toString();
        }

        return ret;
    }

    public java.util.Queue<Entity> getQueue()
    {
        return super.getQueue();
    }

    @Override
    public void enqueue(Entity entity) {
        this.getQueue().add(entity);
        this.setMaxSize();
    }

    @Override
    public Entity next() {
        // TODO Auto-generated method stub
        return this.getQueue().poll();
    }

    @Override
    public Entity checkNext() {
        // TODO Auto-generated method stub
        return this.getQueue().peek();
    }
}