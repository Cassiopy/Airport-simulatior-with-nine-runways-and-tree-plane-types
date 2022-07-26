package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.resources.queues.CustomQueue;
//import ar.edu.unsl.mys.resources.*;
//import ar.edu.unsl.mys.entities.maintenance.Maintenance;
//import ar.edu.unsl.mys.entities.Entity;


public class Airstrip extends Server
{

    private static int aistripId = 0;

    private int id;
    private float surfaceDurability;
    private float initSurfaceDurability;

    public Airstrip(CustomQueue queue, float surfaceDurability)
    {
        super(queue);
        Airstrip.aistripId++;
        this.id = Airstrip.aistripId;
        this.initSurfaceDurability = surfaceDurability;
        this.surfaceDurability = surfaceDurability;
    }

    public int getId()
    {
        return this.id;
    }

    public float getInitSurfaceDurability()
    {
        return this.initSurfaceDurability;
    }

    public void setInitSurfaceDurability(float surfaceDurability)
    {
        this.surfaceDurability = surfaceDurability;
    }

    public float getSurfaceDurability()
    {
        return this.surfaceDurability;
    }

    public void setSurfaceDurability(float surfaceDurability)
    {
        this.surfaceDurability = surfaceDurability;
    }


    public void wearAwaySurface(float soilWearAmount)
    {
        this.surfaceDurability -= soilWearAmount;
    }


    public void maintainSurface(float maintenanceFactor)
    {
        this.surfaceDurability += maintenanceFactor*this.initSurfaceDurability;
        if(this.surfaceDurability >= this.initSurfaceDurability) this.surfaceDurability = this.initSurfaceDurability;
    }
    
    @Override
    public String toString()
    {
        return "Airstrip "+this.getId()+" -- busy? : "+this.isBusy()+" -- attending: "+this.getServedEntity();
    }
}