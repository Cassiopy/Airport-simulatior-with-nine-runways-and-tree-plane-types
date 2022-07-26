package ar.edu.unsl.mys.policies;
import ar.edu.unsl.mys.resources.airstrips.Server;
import ar.edu.unsl.mys.resources.airstrips.Airstrip;
import java.util.List;
import ar.edu.unsl.mys.entities.Entity;

public class LowestDurabilityPolicy implements ServerSelectionPolicy {


    //POLÍTICA DE SELECCIÓN DE SERVIDOR PARA EL MANTENIMIENTO

    private static LowestDurabilityPolicy policy;

    public LowestDurabilityPolicy()
    {
        //Empty
    }

    public static LowestDurabilityPolicy getInstance()
    {
        if(LowestDurabilityPolicy.policy == null) LowestDurabilityPolicy.policy = new LowestDurabilityPolicy();
        return LowestDurabilityPolicy.policy;
    }

    @Override
    public Server selectServer(List<Server> servers, Entity entity) {
        Airstrip ret = (Airstrip)servers.get(0); //Tomo el primer servidor. ret es una variable que va a ir almacenando el servidor que tenga menor proporción de desgaste
        double surface1 = ret.getSurfaceDurability()/ret.getInitSurfaceDurability(); //Sirve para averiguar el factor de desgaste
        double surface2; //surface sirve para ir comparando dos suelos
        for(int i=1; i < servers.size(); i++)
        {
            surface2 = ((Airstrip)servers.get(i)).getSurfaceDurability()/((Airstrip)servers.get(i)).getInitSurfaceDurability();
            if(surface1 > surface2)
            {
                ret = (Airstrip)servers.get(i);
                surface1 = surface2;
            }
        }
        return ret;
    }
    
}