package ar.edu.unsl.mys.resources.airstrips;

import ar.edu.unsl.mys.resources.queues.CustomQueue;

public class HeavyAirstrip extends Airstrip {
    //count ocio
    //
    private static final float BASE_SURFACE_RESISTANCE = 5000;
    
    private static int heavyAirstripId = 0;
    
    private int id;

    public HeavyAirstrip(CustomQueue queue)
    {
        super(queue, BASE_SURFACE_RESISTANCE);
        HeavyAirstrip.heavyAirstripId++;
        this.id = HeavyAirstrip.heavyAirstripId;
    }

    public int getId()
    {
        return this.id;
    }
}
