package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.resources.queues.CustomQueue;

public class LightAirstrip extends Airstrip {
    
    private static final float BASE_SURFACE_RESISTANCE = 1000;
    
    private static int lightAirstripId = -1;
    
    private int id;

    public LightAirstrip(CustomQueue queue)
    {
        super(queue, BASE_SURFACE_RESISTANCE);
        LightAirstrip.lightAirstripId++;
        this.id = LightAirstrip.lightAirstripId;
    }

    public int getId()
    {
        return this.id;
    }
}
