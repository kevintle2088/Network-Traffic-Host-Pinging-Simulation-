import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;



class Main {
    public static void main(String[] args) throws IOException {
        String FILE_NAME = "simulation4.txt";

        FileInputStream file = new FileInputStream(FILE_NAME);
        Scanner inFs = new Scanner(file);

        LinkedEventList fel = new LinkedEventList();

        HashMap<Integer, SimpleHost> hosts = new HashMap<>();

        // handle first host
        int firstHostAddress = Integer.parseInt(inFs.nextLine());
        SimpleHost firstHost = new SimpleHost();
        firstHost.setHostParameters(firstHostAddress, fel);
        hosts.put(firstHostAddress, firstHost);

        // handle neighbor hosts
        String buffer = inFs.nextLine();
        while (!buffer.equals("-1")) {
            String[] hostString = buffer.split(" ");
            int hostAddress = Integer.parseInt(hostString[0]);
            int distance = Integer.parseInt(hostString[1]);

            SimpleHost host = new SimpleHost();
            host.setHostParameters(hostAddress, fel);
            firstHost.addNeighbor(host, distance);
            host.addNeighbor(firstHost, distance);
            hosts.put(hostAddress, host);

            buffer = inFs.nextLine();
        }

        // handle messages
        while (inFs.hasNextLine()) {
            buffer = inFs.nextLine();
            String[] bootstrap = buffer.split(" ");
            int requestHostAddress = Integer.parseInt(bootstrap[0]);
            int receiveHostAddress = Integer.parseInt(bootstrap[1]);
            int interval = Integer.parseInt(bootstrap[2]);
            int duration = Integer.parseInt(bootstrap[3]);

            SimpleHost requestHost = hosts.get(requestHostAddress);

            requestHost.sendPings(receiveHostAddress, interval, duration);
        }

        inFs.close();


        while (fel.size() != 0) {
            Event e = fel.removeFirst();
            e.handle();
        }
    }

}