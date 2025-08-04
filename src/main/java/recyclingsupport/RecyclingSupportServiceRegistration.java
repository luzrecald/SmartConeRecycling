package recyclingsupport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

// This class is used to register the service on the local network
public class RecyclingSupportServiceRegistration {

    // JmDNS object used for registering
    private static JmDNS jmdns;

    // Singleton instance of this class
    private static RecyclingSupportServiceRegistration theRegister;

    // Private constructor to set up JmDNS
    private RecyclingSupportServiceRegistration() throws UnknownHostException, IOException {
        // JmDNS is used for service discovery and registration
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Register: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());
    }

    // This method ensures only one instance is created (singleton pattern)
    public static RecyclingSupportServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new RecyclingSupportServiceRegistration();
        }
        return theRegister;
    }

    // This method registers the service with the given type, name, port and description
    public void registerService(String type, String name, int port, String text) throws IOException {
        // Create the service info object with metadata
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, text);

        // Register the service on the local network
        jmdns.registerService(serviceInfo);

        System.out.println("Registered service: " + name + " at port: " + port);
    }
}
