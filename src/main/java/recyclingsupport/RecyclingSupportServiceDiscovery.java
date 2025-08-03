package recyclingsupport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class RecyclingSupportServiceDiscovery {

    public static class ServiceDetails {
        public final String host;
        public final int port;

        public ServiceDetails(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    public static ServiceDetails discover(String serviceName) {
        final CountDownLatch latch = new CountDownLatch(1);
        final ServiceDetails[] result = new ServiceDetails[1];

        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

            jmdns.addServiceListener("_grpc._tcp.local.", new ServiceListener() {
                @Override
                public void serviceAdded(ServiceEvent event) {
                    jmdns.requestServiceInfo(event.getType(), event.getName());
                }

                @Override
                public void serviceRemoved(ServiceEvent event) {}

                @Override
                public void serviceResolved(ServiceEvent event) {
                    ServiceInfo info = event.getInfo();
                    if (info.getName().contains(serviceName)) {
                        result[0] = new ServiceDetails(info.getHostAddresses()[0], info.getPort());
                        latch.countDown();
                    }
                }
            });

            if (!latch.await(5, TimeUnit.SECONDS)) {
                System.err.println("⚠️ [JmDNS] Timeout: No service found.");
                return null;
            }

            return result[0];

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ [JmDNS] Discovery failed: " + e.getMessage());
            return null;
        }
    }
}
