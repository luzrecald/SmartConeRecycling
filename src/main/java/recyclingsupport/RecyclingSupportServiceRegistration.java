package recyclingsupport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class RecyclingSupportServiceRegistration {

    private static JmDNS jmdns;
    private static RecyclingSupportServiceRegistration theRegister;

    private RecyclingSupportServiceRegistration() throws UnknownHostException, IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        System.out.println("Register: InetAddress.getLocalHost(): " + InetAddress.getLocalHost());
    }

    public static RecyclingSupportServiceRegistration getInstance() throws IOException {
        if (theRegister == null) {
            theRegister = new RecyclingSupportServiceRegistration();
        }
        return theRegister;
    }

    public void registerService(String type, String name, int port, String text) throws IOException {
        ServiceInfo serviceInfo = ServiceInfo.create(type, name, port, text);
        jmdns.registerService(serviceInfo);
        System.out.println("✅ Registered Service: " + serviceInfo);
    }
}
