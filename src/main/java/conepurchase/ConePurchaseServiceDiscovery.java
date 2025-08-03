package conepurchase;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

public class ConePurchaseServiceDiscovery {

    private static final String SERVICE_TYPE = "_grpc._tcp.local.";
    private static final String TARGET_SERVICE_NAME = "ConePurchase";

    public static class ServiceDetails {
        public final String host;
        public final int port;

        public ServiceDetails(String host, int port) {
            this.host = host;
            this.port = port;
        }
    }

    public static ServiceDetails discover() throws IOException, InterruptedException {
        AtomicReference<ServiceDetails> result = new AtomicReference<>(null);

        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Client: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());

        jmdns.addServiceListener(SERVICE_TYPE, new ServiceListener() {
            @Override
            public void serviceAdded(ServiceEvent event) {
                jmdns.requestServiceInfo(event.getType(), event.getName(), true);
            }

            @Override
            public void serviceRemoved(ServiceEvent event) {
                System.out.println("Service removed: " + event.getInfo());
            }

            @Override
            public void serviceResolved(ServiceEvent event) {
                ServiceInfo info = event.getInfo();
                String name = info.getName();
                if (name.contains(TARGET_SERVICE_NAME)) {
                    String host = info.getHostAddresses()[0];
                    int port = info.getPort();
                    result.set(new ServiceDetails(host, port));
                    System.out.println("Service resolved: " + name + " at " + host + ":" + port);
                }
            }
        });

        int attempts = 10;
        while (attempts-- > 0 && result.get() == null) {
            Thread.sleep(500);
        }

        if (result.get() == null) {
            System.err.println("[JmDNS] Timeout: No service found.");
        }

        return result.get();
    }
}
