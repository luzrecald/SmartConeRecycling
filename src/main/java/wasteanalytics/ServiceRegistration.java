package wasteanalytics;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

public class ServiceRegistration {
    public static void register(String serviceName, int port) {
        try {
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            ServiceInfo serviceInfo = ServiceInfo.create("_grpc._tcp.local.", serviceName, port, "path=index.html");
            jmdns.registerService(serviceInfo);
            System.out.println("Service registered: " + serviceName + " on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



