package conepurchase;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Singleton class to register a gRPC service with JmDNS
 */
public class ConePurchaseServiceRegistration {

    private static JmDNS jmdns;
    private static ConePurchaseServiceRegistration instance;

    private ConePurchaseServiceRegistration() throws IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Register: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());
    }

    public static ConePurchaseServiceRegistration getInstance() throws IOException {
        if (instance == null) {
            instance = new ConePurchaseServiceRegistration();
        }
        return instance;
    }

    public void registerService(String type, String name, int port, String description) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, description);
        jmdns.registerService(serviceInfo);
        System.out.println("Registered Service: " + serviceInfo.toString());
    }
}
