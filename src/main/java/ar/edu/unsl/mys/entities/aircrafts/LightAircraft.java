package ar.edu.unsl.mys.entities.aircrafts;
import ar.edu.unsl.mys.utils.CustomRandomizer;

public class LightAircraft extends Aircraft{
    private static final int SOIL_WEAR_FACTOR_MIN = 0;
    private static final int SOIL_WEAR_FACTOR_MAX = 1;
    //Uniforme(0,1)

    private static int lightAircraftId = 0;
    private static int lightAircraftProcessed = 0;
    private static double totalWaitingTime;
    private static int waitings = 0;
    private static double maxWaitingTime;
    private static double totalTransitTime;
    private static double maxTransitTime;

    private int id;
    private double waitingTime;
    private double transitTime;

    public LightAircraft()
    { 
        super((float)CustomRandomizer.getInstance().nextRandom()); //Uso directamente el randomizer porque me devuelve un nùmero de 0 a 1 que es lo que estoy buscando
        LightAircraft.lightAircraftId++;
        this.id = LightAircraft.lightAircraftId;
    }

    public static int getIdCount()
    {
        return LightAircraft.lightAircraftId;
    }

    public static int getWaitingCount()
    {
        return LightAircraft.waitings;
    }

    public static int getProcessedAmount()
    {
        return LightAircraft.lightAircraftProcessed;
    }

    public static double getTotalWaitingTime()
    {
        return LightAircraft.totalWaitingTime;
    }

    public static double getTotalTransitTime()
    {
        return LightAircraft.totalTransitTime;
    }

    public static double getMaxTransitTime()
    {
        return LightAircraft.maxTransitTime;
    }

    public static double getMaxWaitingTime()
    {
        return LightAircraft.maxWaitingTime;
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
        LightAircraft.lightAircraftProcessed++;
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
        LightAircraft.waitings++;
        LightAircraft.totalWaitingTime += this.waitingTime;
        if(LightAircraft.maxWaitingTime < this.waitingTime) LightAircraft.maxWaitingTime = this.waitingTime;
    }

    @Override
    public void setTransitTime(double transitTime)
    {
        super.setTransitTime(transitTime);
        this.transitTime = transitTime;
        LightAircraft.totalTransitTime += this.transitTime;
        if(LightAircraft.maxTransitTime < this.transitTime) LightAircraft.maxTransitTime = this.transitTime;
    }
}
