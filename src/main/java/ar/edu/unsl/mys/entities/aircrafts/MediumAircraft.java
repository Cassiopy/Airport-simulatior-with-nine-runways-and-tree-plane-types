package ar.edu.unsl.mys.entities.aircrafts;
import ar.edu.unsl.mys.utils.CustomRandomizer;

public class MediumAircraft extends Aircraft{
    private static final int SOIL_WEAR_FACTOR_MIN = 1;
    private static final int SOIL_WEAR_FACTOR_MAX = 4;
    //Uniforme(1,4)

    private static int mediumAircraftId = 0;
    private static int mediumAircraftProcessed = 0;
    private static double totalWaitingTime;
    private static int waitings = 0;
    private static double maxWaitingTime;
    private static double totalTransitTime;
    private static double maxTransitTime;

    private int id;
    private double waitingTime;
    private double transitTime;

    public MediumAircraft()
    {
        super((float)CustomRandomizer.getInstance().nextRandom()*(SOIL_WEAR_FACTOR_MAX-SOIL_WEAR_FACTOR_MIN)+SOIL_WEAR_FACTOR_MIN); //Generador uniforme
        MediumAircraft.mediumAircraftId++;
        this.id = MediumAircraft.mediumAircraftId;
    }

    public static int getIdCount()
    {
        return MediumAircraft.mediumAircraftId;
    }


    public static int getProcessedAmount()
    {
        return MediumAircraft.mediumAircraftProcessed;
    }

    public static double getTotalWaitingTime()
    {
        return MediumAircraft.totalWaitingTime;
    }

    public static double getTotalTransitTime()
    {
        return MediumAircraft.totalTransitTime;
    }

    public static double getMaxTransitTime()
    {
        return MediumAircraft.maxTransitTime;
    }

    public static double getMaxWaitingTime()
    {
        return MediumAircraft.maxWaitingTime;
    }

    public static int getWaitingCount()
    {
        return MediumAircraft.waitings;
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
        MediumAircraft.mediumAircraftProcessed++;
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
        MediumAircraft.waitings++;
        MediumAircraft.totalWaitingTime += this.waitingTime;
        if(MediumAircraft.maxWaitingTime < this.waitingTime) MediumAircraft.maxWaitingTime = this.waitingTime;
    }

    @Override
    public void setTransitTime(double transitTime)
    {
        super.setTransitTime(transitTime);
        this.transitTime = transitTime;
        MediumAircraft.totalTransitTime += this.transitTime;
        if(MediumAircraft.maxTransitTime < this.transitTime) MediumAircraft.maxTransitTime = this.transitTime;
    }
}
