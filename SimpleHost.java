public class SimpleHost extends Host {

    private int interval;
    private int duration;
    private int destAddress;

    private int intervalTimer;
    private int durationTimer;

    public SimpleHost() {
        super();
    }

    protected void receive(Message msg) {
        if (msg.getMessage().equals("request")) {
            Message responseMessage = new Message("response", getHostAddress(), msg.getSrcAddress());
            sendToNeighbor(responseMessage);
        } else if (msg.getMessage().equals("response")) {
            System.out.printf(" (RTT = %dts)\n", (getCurrentTime() - msg.getInsertionTime()) * 2);
        } else {
            throw new EventException("invalid message");
        }
    }

    protected void timerExpired(int eventId) {
        if (eventId == this.intervalTimer) {
            String msgString = String.format("[%sts] Host %d: Sent ping to host %d", this.getCurrentTime(),
                    this.getHostAddress(), this.destAddress);
            System.out.println(msgString);

            Message msg = new Message("request", this.getHostAddress(), destAddress);

            this.intervalTimer = newTimer(this.interval);
            this.sendToNeighbor(msg);
        } else if (eventId == this.durationTimer) {
            timerCancelled(this.intervalTimer);
            cancelTimer(this.intervalTimer);
        }
    }

    protected void timerCancelled(int eventId) {
        System.out.printf("[%dts] Host %d: Stopped sending pings\n", getCurrentTime(), this.getHostAddress());
    }

    public void sendPings(int destAddress, int interval, int duration) {
        // create new interval timer
        this.intervalTimer = this.newTimer(interval);

        // call newTimer for duration
        this.durationTimer = this.newTimer(duration);

        this.interval = interval;
        this.duration = duration;
        this.destAddress = destAddress;
    }

}
