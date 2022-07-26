
//Sofía Flores - Ingeniería en Informática

package ar.edu.unsl.mys;

import ar.edu.unsl.mys.engine.AirportSimulation;
import ar.edu.unsl.mys.engine.Engine;
//import ar.edu.unsl.mys.policies.OneServerModelPolicy;
import ar.edu.unsl.mys.policies.ShorterQueuePolicy;

public class App 
{
    private static final int MIN_PER_DAY = 1440;
    private static final int NUMBER_OF_DAYS = 28;
    private static final int EXECUTION_TIME = MIN_PER_DAY*NUMBER_OF_DAYS;

    private static final int LIGHTSERVERS_NUMBER = 3;
    private static final int MEDIUMSERVERS_NUMBER = 4;
    private static final int HEAVYSERVERS_NUMBER = 2;
    private static final int AUXILIARSERVERS_NUMBER = 1;


    public static double getExecutionTime()
    {
        return (double)EXECUTION_TIME;
    }

    public static void main( String[] args )
    {
        Engine engine = new AirportSimulation(LIGHTSERVERS_NUMBER, MEDIUMSERVERS_NUMBER, HEAVYSERVERS_NUMBER, AUXILIARSERVERS_NUMBER, EXECUTION_TIME, ShorterQueuePolicy.getInstance());
        engine.execute();
        engine.generateReport(true,"report.txt");
    }
}