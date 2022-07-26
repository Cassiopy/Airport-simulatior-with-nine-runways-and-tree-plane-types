package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.resources.queues.CustomQueue;

public abstract class Server
{
    private static int idCount;

    //attributes 
    private int id;
    private boolean busy;

    //Para cálculos
    /*
       * init with 0 to avoid desynchronized in the firt arrival event.
    */
    private double idleTimeStartMark; //Comienzo del ocio
    /*
        * init with 0 to avoid desynchronized in the firt arrival event.
    */
    private double idleTimeFinishMark; //Fin del ocio
    private double idleTime; //Tiempo total de ocio de la pista
    private double maxIdleTime; //Tiempo máximo de ocio de la pista
    private int idleCount;

    //associations
    private Entity servedEntity;
    private CustomQueue WQueue; //Cola de espera

    public Server(CustomQueue queue)
    {
        this.WQueue = queue;
        this.busy = false;
        this.idleTimeStartMark = 0.0;
        this.idleTimeFinishMark = 0.0;
        this.idleTime = 0.0;
        this.maxIdleTime = 0.0;
        idCount++;
        this.id = idCount; //Para el caso de que hayan más servidores
    }

    public void setIdleCount()
    {
        this.idleCount++;
    }

    public int getIdleCount()
    {
        return this.idleCount;
    }
    public int getId()
    {
        return this.id;
    }

    public boolean isBusy()
    {
        return this.busy;
    }

    public void setBusy(boolean busy)
    {
        this.busy = busy;
    }

    public double getIdleTimeStartMark()
    {
        return idleTimeFinishMark;
    }

    public void setIdleTimeStartMark(double idleTimeStartMark)
    {
        this.idleTimeStartMark = idleTimeStartMark;
    }

    public void setIdleTimeFinishMark(double idleTimeFinishMark)
    {
        this.idleTimeFinishMark = idleTimeFinishMark;
        try
        {
            if(this.idleTimeStartMark != -1 && this.idleTimeFinishMark != -1 && this.idleTimeStartMark <= this.idleTimeFinishMark)
            {
                // something to do here? SE GUARDAN LOS DATOS
                this.idleTime += this.idleTimeFinishMark - this.idleTimeStartMark; //Acumulo el tiempo de ocio
                if ((this.idleTimeFinishMark - this.idleTimeStartMark) > this.maxIdleTime) this.maxIdleTime = this.idleTimeFinishMark - this.idleTimeStartMark; //Si el tiempo de ocio máximo guardado es menor que el tiempo de ocio reciente, actualizo la variable
                this.idleTimeStartMark = -1; //Se resetean las variables
                this.idleTimeFinishMark = -1;

            }
            else
            {
                throw new Exception("desynchronized idle time marks");
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }   
    }

    public double getIdleTime()
    {
        return this.idleTime;
    }

    public Entity getServedEntity()
    {
        return this.servedEntity;
    }

    public void setServedEntity(Entity servedEntity)
    {
        this.servedEntity = servedEntity;
    }

    public CustomQueue getWQueue()
    {
        return this.WQueue;
    }

    public double getMaxIdleTime()
    {
        return this.maxIdleTime;
    }
}
