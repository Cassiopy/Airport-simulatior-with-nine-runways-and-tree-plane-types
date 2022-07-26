package ar.edu.unsl.mys.entities.maintenance;
import ar.edu.unsl.mys.resources.airstrips.*;
import ar.edu.unsl.mys.entities.*;

public class Maintenance extends Entity{

    private static final float SURFACE_REPAIR_FACTOR = 0.15f;

    private static int maintenanceId;

    private int id;

    public Maintenance()
    {
        super();
        Maintenance.maintenanceId++;
        this.id = Maintenance.maintenanceId;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public void countAsProcessed()
    {
        //nothing
    }

    @Override
    public double getWaitingTime()
    {
        return -1.0;
    }

    @Override
    public void setWaitingTime(double waitingTime)
    {
        //nothing
    }

    @Override
    public double getTransitTime()
    {
        return -1.0;
    }

    @Override
    public void applyEffectOnServerOnEnter() {

        ((Airstrip)this.getAttendingServer()).maintainSurface(SURFACE_REPAIR_FACTOR);
    }

    @Override
    public void applyEffectOnServerOnExit() {
        
    }

}


