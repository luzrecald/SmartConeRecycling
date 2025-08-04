
package wasteanalytics;  

import javax.jmdns.JmDNS;            
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;

public class ServiceDiscovery { 
    // This is a helper class that listens for services being added, removed, or fully available (resolved).
    public static class Listener implements ServiceListener {
        
        // This method runs when a new service is found on the network.
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        // This method runs when a service is removed or goes offline.
        @Override
        public void serviceRemoved(ServiceEvent event) {
            System.out.println("Service removed: " + event.getInfo());
        }

        // This method runs when the full details of a service are available (like its address and port).
        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
        }
    }

    // This method starts looking for services of a specific type on the local network.
    public static void discover(String serviceType) {
        try {
            // This gets the computer’s IP address and starts JmDNS to listen on the local network.
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // This adds our custom Listener to listen for services of the given type (e.g., "_grpc._tcp.local.")
            jmdns.addServiceListener(serviceType, new Listener());
        } catch (IOException e) {
            // If something goes wrong (like a network issue), this prints the error details.
            e.printStackTrace();
        }
    }
}
