public class Message extends Event {

    String stringMessage; // either "request" or "response"
    private int srcAddress;
    private int destAddress;
    private int distance;
    private Host host;

    public Message(String stringMessage, int srcAddress, int destAddress) {
        super();
        this.stringMessage = stringMessage;
        this.srcAddress = srcAddress;
        this.destAddress = destAddress;
    }

    public void setInsertionTime(int currentTime) {
        this.insertionTime = currentTime;
    }

    public void cancel() {
        // no need
    }

    public void handle() {
        System.out.printf("[%dts] Host %d: Ping %s from host %d",
                host.getCurrentTime(), destAddress, stringMessage, srcAddress);
        if (stringMessage.equals("request")) {
            System.out.println();
        }
        this.host.receive(this);
    }

    public String getMessage() {
        return this.stringMessage;
    }

    public int getSrcAddress() {
        return this.srcAddress;
    }

    public int getDestAddress() {
        return this.destAddress;
    }

    public void setNextHop(Host host, int distance) {
        this.host = host;
        this.distance = distance;
        this.arrivalTime = host.getCurrentTime() + distance;
    }
}
