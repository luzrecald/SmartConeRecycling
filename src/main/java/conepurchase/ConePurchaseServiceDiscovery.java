package conepurchase;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

public class ConePurchaseServiceDiscovery {

    // Type of service we are looking for on the local network
    private static final String SERVICE_TYPE = "_grpc._tcp.local.";
    private static final String TARGET_SERVICE_NAME = "ConePurchase";

    // This class holds the host and port where the service was found
    public static class ServiceDetails {
        public final String host;
        public final int port;

        public ServiceDetails(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    // This method tries to find the "ConePurchase" service on the local network
    public static ServiceDetails discover() throws IOException, InterruptedException {
        // This will store the result when the service is found
        AtomicReference<ServiceDetails> result = new AtomicReference<>(null);

        // Create an mDNS client using the local IP address
        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Client: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());

        // Start listening for services of the correct type
        jmdns.addServiceListener(SERVICE_TYPE, new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent event) {
                // Ask for more info about the service
                jmdns.requestServiceInfo(event.getType(), event.getName(), true);
            }

            @Override
            public void serviceRemoved(ServiceEvent event) {
                // Show a message if the service disappears
                System.out.println("Service removed: " + event.getInfo());
            }

            @Override
            public void serviceResolved(ServiceEvent event) {
                // When we have full info about a service, check if it's the one we want
                ServiceInfo info = event.getInfo();
                String name = info.getName();
                if (name.contains(TARGET_SERVICE_NAME)) {
                    // Store the host and port in our result
                    String host = info.getHostAddresses()[0];
                    int port = info.getPort();
                    result.set(new ServiceDetails(host, port));
                    System.out.println("Service resolved: " + name + " at " + host + ":" + port);
                }
            }
        });

        // Wait up to 5 seconds (10 attempts, 500ms each) to find the service
        int attempts = 10;
        while (attempts-- > 0 && result.get() == null) {
            Thread.sleep(500);
        }

        // If the service wasn't found in time, show an error
        if (result.get() == null) {
            System.err.println("[JmDNS] Timeout: No service found.");
        }

        return result.get();
    }
}
