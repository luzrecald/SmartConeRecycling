package coneclassification;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class ConeClassificationServiceRegistration {

    // JmDNS object to handle service registration
    private static JmDNS jmdns;

    // Singleton instance of this class
    private static ConeClassificationServiceRegistration theRegister;

    // Private constructor to create the JmDNS instance using the local IP
    private ConeClassificationServiceRegistration() throws UnknownHostException, IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Register: InetAddress.getLocalHost() = " + InetAddress.getLocalHost());
    }

    // This method returns the same instance every time (singleton pattern)
    public static ConeClassificationServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new ConeClassificationServiceRegistration();
        }
        return theRegister;
    }

    // This method registers the service so that clients can find it
    public void registerService(String type, String name, int port, String text) throws IOException {
        // Create service information with the given details
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, text);

        // Register the service with JmDNS
        jmdns.registerService(serviceInfo);

        // Print a confirmation message
        System.out.println("Registered Service: " + serviceInfo);
    }
}
