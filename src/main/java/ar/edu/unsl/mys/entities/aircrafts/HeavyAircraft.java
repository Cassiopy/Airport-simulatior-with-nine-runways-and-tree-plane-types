package ar.edu.unsl.mys.entities.aircrafts;
import ar.edu.unsl.mys.utils.CustomRandomizer;

public class HeavyAircraft extends Aircraft{
    private static final int SOIL_WEAR_FACTOR_MIN = 3;
    private static final int SOIL_WEAR_FACTOR_MAX = 6;
    //Uniforme(3,6)

    private static int heavyAircraftId = 0;
    private static int heavyAircraftProcessed = 0;
    private static double totalWaitingTime;
    private static int waitings = 0;
    private static double maxWaitingTime;
    private static double totalTransitTime;
    private static double maxTransitTime;

    private int id;
    private double waitingTime;
    private double transitTime;

    public HeavyAircraft()
    {
        super((float)CustomRandomizer.getInstance().nextRandom()*(SOIL_WEAR_FACTOR_MAX-SOIL_WEAR_FACTOR_MIN)+SOIL_WEAR_FACTOR_MIN);
        HeavyAircraft.heavyAircraftId++;
        this.id = HeavyAircraft.heavyAircraftId;
    }

    public static int getIdCount()
    {
        return HeavyAircraft.heavyAircraftId;
    }

    public static int getProcessedAmount()
    {
        return HeavyAircraft.heavyAircraftProcessed;
    }

    public static double getTotalWaitingTime()
    {
        return HeavyAircraft.totalWaitingTime;
    }

    public static double getTotalTransitTime()
    {
        return HeavyAircraft.totalTransitTime;
    }

    public static double getMaxTransitTime()
    {
        return HeavyAircraft.maxTransitTime;
    }

    public static double getMaxWaitingTime()
    {
        return HeavyAircraft.maxWaitingTime;
    }

    public static int getWaitingCount()
    {
        return HeavyAircraft.waitings;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public void countAsProcessed()
    {
        super.countAsProcessed();;
        HeavyAircraft.heavyAircraftProcessed++;
    }
    
    @Override
    public double getWaitingTime()
    {
        return this.waitingTime;
    }

    @Override
    public double getTransitTime()
    {
        return this.transitTime;
    }

    @Override
    public void setWaitingTime(double waitingTime)
    {
        super.setWaitingTime(waitingTime);
        this.waitingTime = waitingTime;
        HeavyAircraft.waitings++;
        HeavyAircraft.totalWaitingTime += this.waitingTime;
        if(HeavyAircraft.maxWaitingTime < this.waitingTime) HeavyAircraft.maxWaitingTime = this.waitingTime;
    }

    @Override
    public void setTransitTime(double transitTime)
    {
        super.setTransitTime(transitTime);
        this.transitTime = transitTime;
        HeavyAircraft.totalTransitTime += this.transitTime;
        if(HeavyAircraft.maxTransitTime < this.transitTime) HeavyAircraft.maxTransitTime = this.transitTime;
    }
}
