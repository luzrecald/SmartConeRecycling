package wasteanalytics; 

import javax.jmdns.JmDNS;        
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

public class ServiceRegistration {  
    
    // This method is used to make a service known on the local network.
    public static void register(String serviceName, int port) {
        try {
            // This line gets the computer’s IP address and uses it to create a JmDNS object.
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // This creates information about the service we want to share:
            // It sets the type (_grpc._tcp.local.), the name, the port, and some extra details.
            ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", serviceName, port, "path=index.html");

            // This registers (shares) the service on the network so others can discover it.
            jmdns.registerService(serviceInfo);

            // This line shows a message on the screen to confirm the service was registered.
            System.out.println("Service registered: " + serviceName + " on port " + port);
        
        } catch (IOException e) {
            // If there is a problem (for example, a network error), this shows the error details.
            e.printStackTrace();
        }
    }
}
