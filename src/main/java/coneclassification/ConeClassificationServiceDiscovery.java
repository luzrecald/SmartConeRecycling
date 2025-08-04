package coneclassification;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConeClassificationServiceDiscovery {

    // Class to hold the host and port of the discovered service
    public static class ServiceDetails {
        public final String host;
        public final int port;

        public ServiceDetails(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    // This method searches for a gRPC service using JmDNS
    public static ServiceDetails discover(String serviceName) {
        final CountDownLatch latch = new CountDownLatch(1); // Waits until the service is found
        final ServiceDetails[] result = new ServiceDetails[1]; // Store the service info

        try {
            // Create a JmDNS instance using the local machine's IP
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            System.out.println("Client: InetAddress.getLocalHost() = " + InetAddress.getLocalHost());

            // Listen for gRPC services on the local network
            jmdns.addServiceListener("_grpc._tcp.local.", new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    // Ask for more details about the service
                    jmdns.requestServiceInfo(event.getType(), event.getName());
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    // We do nothing here if a service is removed
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    // When the service is found and has full info
                    ServiceInfo info = event.getInfo();

                    // Check if the service name matches the one we are looking for
                    if (info.getName().contains(serviceName)) {
                        // Save the host address and port
                        result[0] = new ServiceDetails(info.getHostAddresses()[0], info.getPort());
                        latch.countDown(); // Stop waiting
                    }
                }
            });

            // Wait for the service to be discovered (up to 5 seconds)
            if (!latch.await(5, TimeUnit.SECONDS)) {
                System.err.println("[JmDNS] Timeout: Service not found");
                return null;
            }

            // Return the service info
            return result[0];

        } catch (IOException | InterruptedException e) {
            // If something goes wrong, show an error and return null
            System.err.println("[JmDNS] Service discovery failed: " + e.getMessage());
            return null;
        }
    }
}
