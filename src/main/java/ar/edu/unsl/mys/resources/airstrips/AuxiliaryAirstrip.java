package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.resources.queues.CustomQueue;

public class AuxiliaryAirstrip extends Airstrip {
    
    private static final float BASE_SURFACE_RESISTANCE = 0;
    
    private static int auxiliaryAirstripId = -1;
    
    private int id;

    public AuxiliaryAirstrip(CustomQueue queue)
    {
        super(queue, BASE_SURFACE_RESISTANCE);
        AuxiliaryAirstrip.auxiliaryAirstripId++;
        this.id = AuxiliaryAirstrip.auxiliaryAirstripId;
    }

    public int getId()
    {
        return this.id;
    }
}
