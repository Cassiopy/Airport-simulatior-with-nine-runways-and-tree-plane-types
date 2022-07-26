package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.resources.queues.CustomQueue;

public class MediumAirstrip extends Airstrip {
    
    private static final float BASE_SURFACE_RESISTANCE = 3000;
    
    private static int mediumAirstripId = -1;
    
    private int id;

    public MediumAirstrip(CustomQueue queue)
    {
        super(queue, BASE_SURFACE_RESISTANCE);
        MediumAirstrip.mediumAirstripId++;
        this.id = MediumAirstrip.mediumAirstripId;
    }

    public int getId()
    {
        return this.id;
    }
}
