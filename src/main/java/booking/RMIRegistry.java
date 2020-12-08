package booking;

import booking.servicelayer.ContractImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@Component
public class RMIRegistry
{
    public static Registry registry;

    private static ContractImpl contractImpl;

    @Autowired
    @Qualifier("ContractImpl")
    private ContractImpl contractImplAuto;


    @PostConstruct
    private void initStaticContract () {
        contractImpl = this.contractImplAuto;
    }

    public static void createRegistry() throws Exception
    {
        try
        {
            System.out.println("RMI server localhost starts");
            // Create a server registry at default port 1099
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry created ");

            // Create engine of remote services, running on the server
            ContractImpl remoteEngine = contractImpl;
            System.out.println(remoteEngine);
            // Give a name to this engine
            String engineName = "BookingServices";
            System.out.println(engineName);
            // Register the engine by the name, which later will be given to the clients
            Naming.rebind("//localhost/" + engineName, remoteEngine);
            System.out.println("Engine " + engineName + " bound in registry");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Exception:" + e);
        }
    }
}