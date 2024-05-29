public class Node {
    private final Event event;
    public Node next;
    public Node previous;

    public Node(Event e) {
        this.event = e;
        this.next = null;
        this.previous = null;
    }

    public Event getEvent() {
        return this.event;
    }

}
