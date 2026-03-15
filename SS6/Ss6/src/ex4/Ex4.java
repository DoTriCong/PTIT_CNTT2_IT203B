package ex4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ex4 {

    static class Ticket {
        private String ticketId;
        private boolean isSold;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.isSold = false;
        }

        public boolean isSold() {
            return isSold;
        }

        public void sell() {
            this.isSold = true;
        }

        public String getTicketId() {
            return ticketId;
        }
    }

    static class TicketPool {

        private List<Ticket> tickets;

        public TicketPool(String roomName, int count) {
            tickets = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                tickets.add(new Ticket(
                        roomName + "-" + String.format("%03d", i),
                        roomName));
            }
        }

        public synchronized Ticket sellTicket() {
            for (Ticket t : tickets) {
                if (!t.isSold()) {
                    t.sell();
                    return t;
                }
            }
            return null;
        }

        public int remainingTickets() {
            int c = 0;
            for (Ticket t : tickets) {
                if (!t.isSold())
                    c++;
            }
            return c;
        }
    }

    static class BookingCounter implements Runnable {

        private String counterName;
        private TicketPool roomA;
        private TicketPool roomB;
        private int soldCount;
        private Random random = new Random();

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {
            while (true) {
                if (roomA.remainingTickets() == 0 && roomB.remainingTickets() == 0) {
                    break;
                }

                Ticket sold = null;

                if (random.nextBoolean()) {
                    sold = roomA.sellTicket();
                    if (sold == null) {
                        sold = roomB.sellTicket();
                    }
                } else {
                    sold = roomB.sellTicket();
                    if (sold == null) {
                        sold = roomA.sellTicket();
                    }
                }

                if (sold != null) {
                    soldCount++;
                    System.out.println(counterName + " da ban ve " + sold.getTicketId());
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public int getSoldCount() {
            return soldCount;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter bc1 = new BookingCounter("Quay 1", roomA, roomB);
        BookingCounter bc2 = new BookingCounter("Quay 2", roomA, roomB);

        Thread t1 = new Thread(bc1);
        Thread t2 = new Thread(bc2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Quay 1 ban duoc: " + bc1.getSoldCount() + " ve");
        System.out.println("Quay 2 ban duoc: " + bc2.getSoldCount() + " ve");
        System.out.println("Ve con lai phong A: " + roomA.remainingTickets());
        System.out.println("Ve con lai phong B: " + roomB.remainingTickets());
    }
}