package ar.edu.unsl.mys.policies;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.resources.airstrips.*;
import ar.edu.unsl.mys.entities.*;
import ar.edu.unsl.mys.entities.aircrafts.*;
import ar.edu.unsl.mys.entities.maintenance.*;

import java.util.LinkedList;
import java.util.List;

public class ShorterQueuePolicy implements ServerSelectionPolicy {


    private static ShorterQueuePolicy policy;

    public ShorterQueuePolicy()
    {
        //Empty
    }

    public static ShorterQueuePolicy getInstance()
    {
        if(ShorterQueuePolicy.policy == null) ShorterQueuePolicy.policy = new ShorterQueuePolicy();
        return ShorterQueuePolicy.policy;
    }

    private List<Server> selectPropperServer(List<Server> servers, Entity entity) //Devuelve una lista de servidores del tipo del avión que arribó
    {
        List<Server> ret = new LinkedList<>(); //Se crea la lista de servidores

        if(entity instanceof LightAircraft) 
        {
            for(int i = 0; i < servers.size(); i++) if(servers.get(i) instanceof LightAirstrip) ret.add(servers.get(i)); //Almaceno las pistas que reciben aviones livianos
        }
        else if(entity instanceof MediumAircraft) 
        {
            for(int i = 0; i < servers.size(); i++) if(servers.get(i) instanceof MediumAirstrip) ret.add(servers.get(i)); //Almaceno las pistas que reciben aviones medianos
        }
        else if(entity instanceof HeavyAircraft)
        {
            for(int i = 0; i < servers.size(); i++) if(servers.get(i) instanceof HeavyAirstrip) ret.add(servers.get(i)); //Almaceno las pistas que reciben aviones pesados
        }

        return ret;
    }

    private Server shorterQueueNoMaintenanceServer(List<Server> servers) //Devuelve la pista que tiene menor cola de espera
    {
        Server ret = null;
        List<Server> serversNotBeingMaintanined = new LinkedList<>(); //Servers que no están siendo mantenidos pero no necesariamente que no van a entrar en mantenimiento
        List<Server> maintenanceFreeServers = new LinkedList<>(); //Servers que no van a entrar en mantenimiento

        //Filtro los servers en mantenimiento
        for(int i = 0; i < servers.size(); i++)
        {
            if(!(servers.get(i).getServedEntity() instanceof Maintenance)) serversNotBeingMaintanined.add(servers.get(i)); //Esto es para que el avión no se caiga y se mueran todos como dijo el profe
        }

        //Filtro los servers que esperan mantenimiento
        for(int i = 0; i < serversNotBeingMaintanined.size(); i++) 
        {
            if(!serversNotBeingMaintanined.get(i).getWQueue().maintenanceOnHold()) maintenanceFreeServers.add(serversNotBeingMaintanined.get(i));
        }

        //Busca el servidor del tipo de la entidad que tenga la lista de espera más corta y que no espere ningún mantenimiento
        if(maintenanceFreeServers.size() > 0)
        {
            ret = maintenanceFreeServers.get(0);
            for(int i = 1; i < maintenanceFreeServers.size(); i++)
            {
                if(ret.getWQueue().getSize() > maintenanceFreeServers.get(i).getWQueue().getSize()) ret = maintenanceFreeServers.get(i);
            }
        }
        
        return ret;
    }

    private Server getAuxServer(List<Server> server)
    {
        Server aux = null;

        for(Server o: server)
        {
            if(o instanceof AuxiliaryAirstrip) aux = o;
        }

        return aux;
    }

    @Override
    public Server selectServer(List<Server> servers, Entity entity) {

        Server ret = null;

        List<Server> propperIdleServers = new LinkedList<>();
        List<Server> propperBusyServers = new LinkedList<>();

        List<Server> propperServers = this.selectPropperServer(servers, entity); //Genero una lista de servidores que sean del mismo tipo que la entidad

        for(int i = 0; i < propperServers.size(); i++) //Genero dos listas más que separen aquellos servidores que están atendiendo de aquellos que no
        {
            if(propperServers.get(i).isBusy()) propperBusyServers.add(propperServers.get(i)); 
            else propperIdleServers.add(propperServers.get(i));
        }

        if(propperIdleServers.size() > 0) ret = propperIdleServers.get(0); //Devuelvo el primer servidor que encuentro en ocio
        else ret = this.shorterQueueNoMaintenanceServer(propperBusyServers); //Si ningún servidor está en ocio, devuelvo el servidor que tenga la cola de espera más corta y que no vaya a entrar en mantenimiento
        
        if(ret == null) ret = this.getAuxServer(servers); //Si todas las colas de espera de un mismo tipo no se pueden seleccionar porque tienen o van a tener mantenimiento, selecciono la psita auxiliar

        return ret;
    }
}
    