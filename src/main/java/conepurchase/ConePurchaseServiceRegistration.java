package conepurchase;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

/**
 * This class is responsible for registering the gRPC service
 * so it can be discovered by other devices on the local network.
 * It uses the singleton pattern to make sure only one instance exists.
 */
public class ConePurchaseServiceRegistration {

    private static JmDNS jmdns;
    private static ConePurchaseServiceRegistration instance;

    // Constructor: connects to the local network using the machine's IP
    private ConePurchaseServiceRegistration() throws IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Register: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());
    }

    // Returns the single instance of this class (creates it if needed)
    public static ConePurchaseServiceRegistration getInstance() throws IOException {
        if (instance == null) {
            instance = new ConePurchaseServiceRegistration();
        }
        return instance;
    }

    // This method registers the service with a name, type, port, and description
    public void registerService(String type, String name, int port, String description) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, description);
        jmdns.registerService(serviceInfo);
        System.out.println("Registered Service: " + serviceInfo.toString());
    }
}
