package ar.edu.unsl.mys.entities.aircrafts;
import ar.edu.unsl.mys.entities.*;
import ar.edu.unsl.mys.resources.airstrips.Airstrip;

public abstract class Aircraft extends Entity
{
    private static int aircraftId = 0;
    private static int aircraftProcessed = 0;
    private static int waitings = 0;
    private static double totalTransitTime;
    private static double maxTransitTime;
    private static double totalWaitingtime;
    private static double maxWaitingTime;
    
    private int id;
    private double waitingTime;
    private double transitTime;
    private float soilWearAmount;

    public Aircraft(float soilWearAmount)
    {
        super();
        Aircraft.aircraftId++;
        this.id = Aircraft.aircraftId;
        this.soilWearAmount = soilWearAmount;
    }

    public static int getProcessedAmount()
    {
        return Aircraft.aircraftProcessed;
    }

    public static double getMaxWaitingTime()
    {
        return Aircraft.maxWaitingTime;
    }

    public static double getTotalWaitingTime()
    {
        return Aircraft.totalWaitingtime;
    }

    public static double getTotalTransitTime()
    {
        return Aircraft.totalTransitTime;
    }

    public static double getMaxTransitTime()
    {
        return Aircraft.maxTransitTime;
    }

    public static int getIdCount()
    {
        return Aircraft.aircraftId;
    }

    public static int getWaitingCount()
    {
        return Aircraft.waitings;
    }

    public void setSoilWearAmount(float amount)
    {
        this.soilWearAmount = amount;
    }

    public float getSoilWearAmount()
    {
        return this.soilWearAmount;
    }

    @Override
    public int getId()
    {
        return this.id;
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
        waitings++;
        Aircraft.totalWaitingtime += this.waitingTime;
        if(this.waitingTime > Aircraft.maxWaitingTime) Aircraft.maxWaitingTime = this.waitingTime;
    }

    @Override
    public void setTransitTime(double transitTime)
    {
        super.setTransitTime(transitTime);
        this.transitTime = transitTime;
        Aircraft.totalTransitTime += this.transitTime;
        if(this.transitTime > Aircraft.maxTransitTime) Aircraft.maxTransitTime = this.transitTime;
    }

    @Override
    public void countAsProcessed()
    {
        super.countAsProcessed();
        Aircraft.aircraftProcessed++;
    }

    @Override
    public void applyEffectOnServerOnEnter()
    {
        Airstrip airstrip = (Airstrip)this.getAttendingServer();
        airstrip.wearAwaySurface(this.getSoilWearAmount());
    }

    @Override
    public void applyEffectOnServerOnExit()
    {
        //do nothing
    }

    @Override
    public String toString()
    {
        return "id = "+ this.getId()+" >> defaul aircraft type";
    }
}