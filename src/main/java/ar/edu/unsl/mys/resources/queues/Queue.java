package ar.edu.unsl.mys.resources.queues;

import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.resources.airstrips.Server;

public abstract class Queue
{
    private Server assignedServer;
    private java.util.Queue<Entity> queue;
    private int maxSize; //Tamaño máximo que alcanzó

    public Queue(java.util.Queue<Entity> queue)
    {
        this.queue = queue;
    }

    public Server getAssignedServer()
    {
        return this.assignedServer;
    }

    protected void setAssignedServer(Server assignedServer)
    {
        this.assignedServer = assignedServer;
    }

    protected java.util.Queue<Entity> getQueue()
    {
        return this.queue;
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public void setMaxSize()
    { 
        if(this.queue.size() > this.maxSize) this.maxSize = this.queue.size();    
    }

    public int getSize()
    {
        return queue.size();
    }

    public boolean isEmpty()
    {
        if (this.queue.isEmpty()) return true;
        else return false;
    }

    public int maxSize()
    {
        return this.maxSize;
    }
    /**
     * Queue an entity using the policy defined in the underlying 
     * implementation of this method.
     * @param entity The entity to be queued.
     */
    public abstract void enqueue(Entity entity);

    /**
     * Gets the next entity in the queue.
     * After calling this method, the entity returned is no longer in the queue.
     * @return The next entity in the queue.
     */
    public abstract Entity next();

    /**
     * Checks the next element in the queue without removing it.
     * The queue remains intact after calling this method.
     * @return The next entity in the queue.
     */
    public abstract Entity checkNext();

    public abstract boolean maintenanceOnHold();
}