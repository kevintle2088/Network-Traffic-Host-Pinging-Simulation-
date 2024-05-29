public class LinkedEventList implements FutureEventList {
    private Node head;
    private Node tail;
    private int size;
    private int simulationTime;

    public LinkedEventList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.simulationTime = 0;
    }

    public void printList() {
        Node curr = head;
        while (curr != null && curr != tail) {
            System.out.println(curr.getEvent());
            curr = curr.next;
        }
        if (curr != null) {
            System.out.println(curr.getEvent());
        }
    }

    public Event removeFirst() {
        if (head == null) {
            return null;
        }

        Node first = head;

        if (size == 1) {
            head = null;
        } else {
            head = head.next;
            head.previous = tail;
        }

        first.next = null;
        size--;
        this.simulationTime = first.getEvent().getArrivalTime();
        return first.getEvent();
    }

    public boolean remove(Event e) {
        if (head == null) {
            return false;
        }

        Node curr = head;

        while (curr != null) {
            if (curr.getEvent() == e) {
                if (curr == head) {
                    if (curr.next != null) {
                        head = curr.next;
                        head.previous = null;
                    } else {
                        tail = null;
                    }
                } else if (curr == tail) {
                    tail = curr.previous;
                    tail.next = null;
                } else {
                    curr.previous.next = curr.next;
                    curr.next.previous = curr.previous;
                }

                curr.previous = null;
                curr.next = null;
                this.size--;
                this.simulationTime = e.getArrivalTime();
                return true;
            }

            curr = curr.next;
        }

        return false;
    }

    public void insert(Event e) {
        e.setInsertionTime(simulationTime);
        Node newNode = new Node(e);

        if (head == null) { // list is empty
            head = newNode;
            tail = newNode;
        } else if (e.getArrivalTime() == head.getEvent().getArrivalTime()){
            newNode.previous = head;
            newNode.next = head.next;
            head.next = newNode;
        }
        else if (e.getArrivalTime() < head.getEvent().getArrivalTime()) { // set as new head
            newNode.next = head;
            newNode.previous = tail;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.getEvent().getArrivalTime() < e.getArrivalTime()) {
                current = current.next;
            }

            newNode.next = current.next;
            newNode.previous = current;

            if (current.next == null) {
                tail = newNode;
            } else {

                current.next.previous = newNode;
            }
            current.next = newNode;
        }

        this.size++;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.size;
    }

    public int getSimulationTime() {
        return this.simulationTime;
    }
}
