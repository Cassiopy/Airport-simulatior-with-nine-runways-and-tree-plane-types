package ar.edu.unsl.mys.behaviors;

import ar.edu.unsl.mys.events.ArrivalEvent;
import ar.edu.unsl.mys.events.Event;
//import ar.edu.unsl.mys.policies.LowestDurabilityPolicy;
import ar.edu.unsl.mys.policies.ServerSelectionPolicy;
//import ar.edu.unsl.mys.policies.ShorterQueuePolicy;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import ar.edu.unsl.mys.policies.OneServerModelPolicy;
import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.entities.aircrafts.HeavyAircraft;
import ar.edu.unsl.mys.entities.aircrafts.LightAircraft;
import ar.edu.unsl.mys.entities.aircrafts.MediumAircraft;
import ar.edu.unsl.mys.entities.maintenance.Maintenance;
//import ar.edu.unsl.mys.utils.Randomizer;
//import ar.edu.unsl.mys.events.ArrivalEvent;
import ar.edu.unsl.mys.utils.CustomRandomizer;

public class ArrivalEventBehavior extends EventBehavior
{
    private static ArrivalEventBehavior arrivalEventBehavior;

    
    private final int TIME_UNIT_FACTOR = 60; //VER
    private final int DAY_LENGHT = 24*TIME_UNIT_FACTOR; //En minutos

    //Intervalos hora pico en horas
    private final int INICIO_HORA_PICO_1 = 7;
    private final int FIN_HORA_PICO_1 = 10;
    private final int INICIO_HORA_PICO_2 = 19;
    private final int FIN_HORA_PICO_2 = 22;

    //Intervalos hora pico en minutos
    private final int INICIO_HORA_PICO_INTERVALO_1 = INICIO_HORA_PICO_1*TIME_UNIT_FACTOR;
    private final int FIN_HORA_PICO_INTERVALO_1 = FIN_HORA_PICO_1*TIME_UNIT_FACTOR;
    private final int INICIO_HORA_PICO_INTERVALO_2 = INICIO_HORA_PICO_2*TIME_UNIT_FACTOR;
    private final int FIN_HORA_PICO_INTERVALO_2 = FIN_HORA_PICO_2*TIME_UNIT_FACTOR;

    //Medias y varianzas
    private final int MANTENIMIENTO_MEDIA = 5*DAY_LENGHT; //Normal en días
    private final double MANTENIMIENTO_DE = 0.5f*DAY_LENGHT;

    private final int LIVIANA_ARRIBO_NORMAL_MEDIA = 40; //Exponencial
    private final int LIVIANA_ARRIBO_PICO_MEDIA = 20;

    private final int MEDIANA_ARRIBO_NORMAL_MEDIA = 30; //Exponencial
    private final int MEDIANA_ARRIBO_PICO_MEDIA = 15;

    private final int PESADA_ARRIBO_NORMAL_MEDIA = 60; //Normal
    private final int PESADA_ARRIBO_NORMAL_DE = 2;
    private final int PESADA_ARRIBO_PICO_MEDIA = 30;
    private final int PESADA_ARRIBO_PICO_DE = 2;

    //Parámetros de la simulación
    private final int CONVOLUCION_N = 36;
    private final double CONVOLUCION_UNIFORME_L = 0;
    private final double CONVOLUCION_UNIFORME_R = 1;
    private final double CONVOLUCION_UNIFORME_MEDIA = CONVOLUCION_N*((CONVOLUCION_UNIFORME_L + CONVOLUCION_UNIFORME_R)/2);
    private final double CONVOLUCION_UNIFORME_V = CONVOLUCION_N*(Math.pow(CONVOLUCION_UNIFORME_R + CONVOLUCION_UNIFORME_L, 2));
    private final double CONVOLUCION_UNIFORME_SD = Math.sqrt(CONVOLUCION_UNIFORME_V);

    private ArrivalEventBehavior(CustomRandomizer randomizer)
    {
        super(randomizer);
    }

    public static ArrivalEventBehavior getInstance()
    {
        if(arrivalEventBehavior == null) 
        {
            arrivalEventBehavior = new ArrivalEventBehavior(CustomRandomizer.getInstance());
        }
        return arrivalEventBehavior;
    }

    private double convolucion(int n)
    {
        double ret = 0;

        for(int i = 0; i < n; i++)
        {
            ret += this.getRandomizer().nextRandom();
        }
        return ret;
    }

    //Exp
    private ArrivalEvent nextEventLightAircraft(boolean pico, double random, double clock, ServerSelectionPolicy policy)
    {
        double time;

        if(pico) time = (-LIVIANA_ARRIBO_PICO_MEDIA)*(Math.log(1-random));
        else time = (-LIVIANA_ARRIBO_NORMAL_MEDIA)*(Math.log(1-random));
           
        return new ArrivalEvent(clock + time, new LightAircraft(), policy);
    }

    //Exp
    private ArrivalEvent nextEventMediumAircraft(boolean pico, double random, double clock, ServerSelectionPolicy policy)
    {
        double time;

        if(pico) time = (-MEDIANA_ARRIBO_PICO_MEDIA)*(Math.log(1-random));
        else time = (-MEDIANA_ARRIBO_NORMAL_MEDIA)*(Math.log(1-random));

        return new ArrivalEvent(clock + time, new MediumAircraft(), policy);
    }

    //Normal
    private ArrivalEvent nextEventHeavyAircraft(boolean pico, double clock, ServerSelectionPolicy policy)
    {
        double time;
        double variableNormalEstandar = (this.convolucion(CONVOLUCION_N)-CONVOLUCION_UNIFORME_MEDIA)/ CONVOLUCION_UNIFORME_SD;

        if(pico) time = (variableNormalEstandar*PESADA_ARRIBO_PICO_DE)+PESADA_ARRIBO_PICO_MEDIA; //Normal z'*desviación estándar + media 
        else time = (variableNormalEstandar*PESADA_ARRIBO_NORMAL_DE)+PESADA_ARRIBO_NORMAL_MEDIA;

        return new ArrivalEvent(clock + time, new HeavyAircraft(), policy);
    }

    //Normal
    private ArrivalEvent nextEventMaintenance(ArrivalEvent event)
    {
        double time;
        double variableNormalEstandar = (this.convolucion(CONVOLUCION_N)-CONVOLUCION_UNIFORME_MEDIA)/ CONVOLUCION_UNIFORME_SD;

        time = (variableNormalEstandar*MANTENIMIENTO_DE)+MANTENIMIENTO_MEDIA; //Normal z'*desviación estándar + media
        return new ArrivalEvent(event.getClock() + time, event.getEntity(), event.getSelectionPolicy());
    }

    private  boolean isPicoHour(double actualClock)
    {
        boolean ret = false;
        int clock = (int) Math.floor(actualClock);
        
        int clockPerDay = clock % this.DAY_LENGHT;
        ret = ((this.INICIO_HORA_PICO_INTERVALO_1 <= clockPerDay) && (clockPerDay <= this.FIN_HORA_PICO_INTERVALO_1)) || ((this.INICIO_HORA_PICO_INTERVALO_2 <= clockPerDay) && (clockPerDay <= this.FIN_HORA_PICO_INTERVALO_2));
        
        return ret;
    }

    @Override
    public Event nextEvent(Event actualEvent, Entity entity) {

        ArrivalEvent ret = null;
        double random = this.getRandomizer().nextRandom();
        boolean isPicoHour = this.isPicoHour(actualEvent.getClock());

        if(actualEvent.getEntity() instanceof LightAircraft) ret = this.nextEventLightAircraft(isPicoHour, random, actualEvent.getClock(), ((ArrivalEvent)actualEvent).getSelectionPolicy());
        else if(actualEvent.getEntity() instanceof MediumAircraft) ret = this.nextEventMediumAircraft(isPicoHour, random, actualEvent.getClock(), ((ArrivalEvent)actualEvent).getSelectionPolicy());
        else if(actualEvent.getEntity() instanceof HeavyAircraft) ret = this.nextEventHeavyAircraft(isPicoHour, actualEvent.getClock(), ((ArrivalEvent)actualEvent).getSelectionPolicy());
        else if(actualEvent.getEntity() instanceof Maintenance) ret = this.nextEventMaintenance((ArrivalEvent)actualEvent);
        //No considero otro caso más ya que no hay más tipos de entidades y no puedo instanciar una entidad de tipo entity. En el caso de que el programa escale y hayan nuevos tipos de entidades que entren a los servidores mediante este comportamiento, esta sección debería modificarse

        return ret;
    }
}