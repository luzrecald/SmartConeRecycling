package recyclingsupport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

// This class is used to find (discover) a service on the local network
public class RecyclingSupportServiceDiscovery {

    // This class stores the service information (host and port)
    public static class ServiceDetails {
        public final String host;
        public final int port;

        public ServiceDetails(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    // This method tries to discover a service with the given name
    public static ServiceDetails discover(String serviceName) {
        // This helps wait until the service is found
        final CountDownLatch latch = new CountDownLatch(1);
        final ServiceDetails[] result = new ServiceDetails[1];

        try {
            // JmDNS is used for service discovery in the network
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            // Add a listener to look for services
            jmdns.addServiceListener("_grpc._tcp.local.", new ServiceListener() {

                @Override
                public void serviceAdded(ServiceEvent event) {
                    // When a service is found, request more info about it
                    jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {
                    // Called if the service is removed
                }

                @Override
                public void serviceResolved(ServiceEvent event) {
                    // When service is fully resolved, get the info
                    ServiceInfo info = event.getInfo();
                    if (info.getName().equals(serviceName)) {
                        result[0] = new ServiceDetails(info.getHostAddresses()[0], info.getPort());
                        latch.countDown();  // Stop waiting
                    }
                }
            });

            // Wait up to 5 seconds to find the service
            latch.await(5, TimeUnit.SECONDS);
            jmdns.close();  // Close the discovery

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Return the discovered service (or null if not found)
        return result[0];
    }
}
