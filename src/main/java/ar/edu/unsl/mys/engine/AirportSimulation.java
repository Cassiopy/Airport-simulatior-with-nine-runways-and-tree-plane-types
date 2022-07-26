package ar.edu.unsl.mys.engine;

import java.util.LinkedList;
import java.util.List;
//import ar.edu.unsl.mys.resources.CustomQueue;
//import java.util.Scanner;
//import java.util.Iterator;
import java.io.FileWriter;
//import java.util.ArrayList;
import java.io.BufferedWriter;

import ar.edu.unsl.mys.App;
//import ar.edu.unsl.mys.App;
//import ar.edu.unsl.mys.entities.Entity;
import ar.edu.unsl.mys.entities.aircrafts.*;
import ar.edu.unsl.mys.entities.maintenance.*;
import ar.edu.unsl.mys.events.ArrivalEvent;
import ar.edu.unsl.mys.events.StopExecutionEvent;
import ar.edu.unsl.mys.resources.airstrips.*;
import ar.edu.unsl.mys.policies.LowestDurabilityPolicy;
//import ar.edu.unsl.mys.events.ArrivalEvent;
//import ar.edu.unsl.mys.resources.CustomQueue;
//import ar.edu.unsl.mys.events.StopExecutionEvent;
import ar.edu.unsl.mys.policies.ServerSelectionPolicy;
import ar.edu.unsl.mys.resources.airstrips.LightAirstrip;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.resources.queues.CustomQueue;

/**
 * Event oriented simulation of an airport
 */
public class AirportSimulation implements Engine
{
    private String report;
    private double endTime;
    private boolean stopSimulation;
    private FutureEventList fel;
    private List<Server> servers;

    /**
     * Creates the execution engine for the airport simulator.
     * @param airstripQuantity The number of airstrips (servers).
     * @param endTime The amount of time the simulator will simulate (run length).
     * @param policy The object that defines the airstrip selection policy 
     * each time an arrival occurs.
     */
    public AirportSimulation(int lightAirstripQuantity, int mediumAirstripQuantity, int heavyAirstripQuantity, int auxiliaryAirstripQuantity, double endTime, ServerSelectionPolicy policy)
    {
        this.endTime = endTime;
        this.fel = new FutureEventList();
        this.stopSimulation = false;
        this.servers = new LinkedList<>();
        
        for(int i = 0; i < lightAirstripQuantity; i++) this.servers.add(new LightAirstrip(new CustomQueue()));
        for(int i = 0; i < mediumAirstripQuantity; i++) this.servers.add(new MediumAirstrip(new CustomQueue()));
        for(int i = 0; i < heavyAirstripQuantity; i++) this.servers.add(new HeavyAirstrip(new CustomQueue()));
        for(int i = 0; i < auxiliaryAirstripQuantity; i++) this.servers.add(new AuxiliaryAirstrip(new CustomQueue()));
        
        this.fel.insert(new StopExecutionEvent(endTime, this));
        this.fel.insert(new ArrivalEvent(0.0, new LightAircraft(), policy));
        this.fel.insert(new ArrivalEvent(0.0, new MediumAircraft(), policy));
        this.fel.insert(new ArrivalEvent(0.0, new HeavyAircraft(), policy));
        this.fel.insert(new ArrivalEvent(0.0, new Maintenance(), LowestDurabilityPolicy.getInstance()));
    }

    public double getEndTime()
    {
        return this.endTime;
    }

    public void setStopSimulation(boolean stopSimulation)
    {
        this.stopSimulation = stopSimulation;
    }

    @Override
    public void execute() {
        //Incialización de variables
        while(!this.stopSimulation)
        {
            this.fel.getImminent().planificate(this.servers, this.fel);
        }
        
        System.out.println("\nSimulation completed successfully\n");
    }

    @Override
    public String generateReport(boolean intoFile, String fileName) {

        this.report = "==============================================================================================\n"+
                      "                                        R E P O R T                                           \n"+
                      "==============================================================================================\n"+
                      "\n" +
                      "Reporte general: \n" +
                      "Cantidad de aviones que aterrizaron = " + Aircraft.getIdCount() +  " aviones" + "\n" +
                      "Tiempo total de espera en cola = " + Aircraft.getTotalWaitingTime() +  " minutos" + "\n" +
                      "Tiempo medio de espera en cola = " + Aircraft.getTotalWaitingTime()/Aircraft.getWaitingCount() +  " minutos" + "\n" +
                      "Tiempo máximo de espera en cola = " + Aircraft.getMaxWaitingTime() +  " minutos" + "\n" +
                      "Tiempo total de tránsito = " + Aircraft.getTotalTransitTime() +  " minutos" + "\n" +
                      "Tiempo medio de tránsito = " + Aircraft.getTotalTransitTime()/Aircraft.getProcessedAmount() +  " minutos" + "\n" +
                      "Tiempo máximo de tránsito = " + Aircraft.getMaxTransitTime() +  " minutos" + "\n" +
                      "\nA V I O N E S\n" +
                      "     Livianos:\n" +
                      "         Cantidad total de aviones livianos que aterrizaron = " + LightAircraft.getIdCount() + " aviones" + "\n" +
                      "         Tiempo total de espera en cola = " + LightAircraft.getTotalWaitingTime() + " minutos" + "\n" +
                      "         Tiempo medio de espera en cola = " + LightAircraft.getTotalWaitingTime()/LightAircraft.getWaitingCount() + " minutos" + "\n" +
                      "         Tiempo máximo de espera en cola = " + LightAircraft.getMaxWaitingTime() +  " minutos" + "\n" +
                      "         Tiempo total de tránsito = " + LightAircraft.getTotalTransitTime() +  " minutos" + "\n" +
                      "         Tiempo medio de tránsito = " + LightAircraft.getTotalTransitTime()/LightAircraft.getProcessedAmount() +  " minutos" + "\n" +
                      "         Tiempo máximo de tránsito = "  + LightAircraft.getMaxTransitTime() + "\n\n" +
                      "     Medianos:\n" +
                      "         Cantidad total de aviones medianos que aterrizaron = " + MediumAircraft.getIdCount() +  " aviones" + "\n" +
                      "         Tiempo total de espera en cola = " + MediumAircraft.getTotalWaitingTime() + " minutos" + "\n" +
                      "         Tiempo medio de espera en cola = " + MediumAircraft.getTotalWaitingTime()/MediumAircraft.getWaitingCount() +  " minutos" + "\n" +
                      "         Tiempo máximo de espera en cola = " + MediumAircraft.getMaxWaitingTime() + "\n" +
                      "         Tiempo total de tránsito = " + MediumAircraft.getTotalTransitTime() + "\n" +
                      "         Tiempo medio de tránsito = " + MediumAircraft.getTotalTransitTime()/MediumAircraft.getProcessedAmount() +  " minutos" + "\n" +
                      "         Tiempo máximo de tránsito = "  + MediumAircraft.getMaxTransitTime() +  " minutos" + "\n\n" +
                      "     Pesados:\n" +
                      "         Cantidad total de aviones pesados que aterrizaron = " + HeavyAircraft.getIdCount() +  " aviones" + "\n" +
                      "         Tiempo total de espera en cola = " + HeavyAircraft.getTotalWaitingTime() +  " minutos" + "\n" +
                      "         Tiempo medio de espera en cola = " + HeavyAircraft.getTotalWaitingTime()/HeavyAircraft.getWaitingCount() +  " minutos" + "\n" +
                      "         Tiempo máximo de espera en cola = " + HeavyAircraft.getMaxWaitingTime() +  " minutos" + "\n" +
                      "         Tiempo total de tránsito = " + HeavyAircraft.getTotalTransitTime() +  " minutos" + "\n" +
                      "         Tiempo medio de tránsito = " + HeavyAircraft.getTotalTransitTime()/HeavyAircraft.getProcessedAmount() +  " minutos" + "\n" +
                      "         Tiempo máximo de tránsito = "  + HeavyAircraft.getMaxTransitTime() +  " minutos" + "\n\n" +
                      "\nP I S T A S\n";
                    List<LightAirstrip> lightAirstrips = new LinkedList<LightAirstrip>();
                    List<MediumAirstrip> mediumAirstrips = new LinkedList<MediumAirstrip>();
                    List<HeavyAirstrip> heavyAirstrips = new LinkedList<HeavyAirstrip>();
                    int aux = 0;
                    for(Server o: servers)
                    {
                        if(o instanceof LightAirstrip) lightAirstrips.add((LightAirstrip)o);
                        else if(o instanceof MediumAirstrip) mediumAirstrips.add((MediumAirstrip)o);
                        else if(o instanceof HeavyAirstrip) heavyAirstrips.add((HeavyAirstrip)o);
                    }
                    report += "     Livianas:\n";
                    for(int i = 0; i < lightAirstrips.size(); i++)
                    {
                        aux = i + 1;
                        report += "         Pista " + aux + ":\n" +
                                  "             Tiempo total de ocio = " + lightAirstrips.get(i).getIdleTime() +  " minutos" + "\n" +
                                  "             %  tiempo de ocio respecto tiempo de simulación = " + (lightAirstrips.get(i).getIdleTime()*100)/App.getExecutionTime() +  " %" + "\n" +
                                  "             Tiempo máximo de ocio de la pista = " + lightAirstrips.get(i).getMaxIdleTime() +  " minutos" + "\n" +
                                  "             % Tiempo máximo de ocio respecto tiempo total de ocio = " + (lightAirstrips.get(i).getMaxIdleTime()*100)/lightAirstrips.get(i).getIdleTime() +  " %" + "\n" + 
                                  "             Tamaño máximo de la cola de espera = " + lightAirstrips.get(i).getWQueue().getMaxSize() +  " aviones" + "\n" +
                                  "             Durabilidad del suelo restante = " + lightAirstrips.get(i).getSurfaceDurability() +  " minutos" + "\n\n";
                    }
                    report += "     Medianas:\n";
                    aux = 0;
                    for(int i = 0; i < mediumAirstrips.size(); i++)
                    {
                        aux = i + 1;
                        report += "         Pista " + aux + ":\n" +
                                  "             Tiempo total de ocio = " + mediumAirstrips.get(i).getIdleTime() +  " minutos" + "\n" +
                                  "             %  tiempo de ocio respecto tiempo de simulación = " + (mediumAirstrips.get(i).getIdleTime()*100)/App.getExecutionTime() + " %" +  "\n" +
                                  "             Tiempo máximo de ocio de la pista = " + mediumAirstrips.get(i).getMaxIdleTime() +  " minutos" + "\n" +
                                  "             % Tiempo máximo de ocio respecto tiempo total de ocio = " + (mediumAirstrips.get(i).getMaxIdleTime()*100)/mediumAirstrips.get(i).getIdleTime() +  " %" + "\n" + 
                                  "             Tamaño máximo de la cola de espera = " + mediumAirstrips.get(i).getWQueue().getMaxSize() +  " aviones" + "\n" +
                                  "             Durabilidad del suelo restante = " + mediumAirstrips.get(i).getSurfaceDurability()  + "\n\n";
                    }
                    report += "     Pesadas:\n";
                    aux = 0;
                    for(int i = 0; i < heavyAirstrips.size(); i++)
                    {
                        aux = i + 1;
                        report += "         Pista " + aux + ":\n" +
                                  "             Tiempo total de ocio = " + heavyAirstrips.get(i).getIdleTime() +  " minutos" + "\n" +
                                  "             %  tiempo de ocio respecto tiempo de simulación = " + (heavyAirstrips.get(i).getIdleTime()*100)/App.getExecutionTime() + " %" + "\n" +
                                  "             Tiempo máximo de ocio de la pista = " + heavyAirstrips.get(i).getMaxIdleTime() +  " minutos" + "\n" +
                                  "             % Tiempo máximo de ocio respecto tiempo total de ocio = " + (heavyAirstrips.get(i).getMaxIdleTime()*100)/heavyAirstrips.get(i).getIdleTime() + " %" + "\n" + 
                                  "             Tamaño máximo de la cola de espera = " + heavyAirstrips.get(i).getWQueue().getMaxSize() + " aviones"+ "\n" +
                                  "             Durabilidad del suelo restante = " + heavyAirstrips.get(i).getSurfaceDurability() + "\n\n";
                    }
        //
        if(intoFile)
        {
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
                writer.write(this.report);
                writer.close();
            }
            catch (Exception exception)
            {
                System.out.println("Error when trying to write the report into a file.");
                System.out.println("Showing on screen...");
                System.out.println(this.report);
            }
        }

        System.out.println(this.report);
        return this.report;
    }
    
}