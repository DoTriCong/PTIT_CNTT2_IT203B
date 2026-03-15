package ex2;

public class Ex2 {

    static class TicketPool {

        private int ticketsA;
        private int ticketsB;
        private int countA = 11;
        private int countB = 1;

        public TicketPool(int ticketsA, int ticketsB) {
            this.ticketsA = ticketsA;
            this.ticketsB = ticketsB;
        }

        public synchronized void sellTicket(String counter, String room) {
            try {
                if (room.equals("A")) {
                    while (ticketsA == 0) {
                        System.out.println(counter + ": Het ve phong A, dang cho...");
                        wait();
                    }
                    System.out.println(counter + " ban ve A-" + String.format("%03d", countA++));
                    ticketsA--;
                } else {
                    while (ticketsB == 0) {
                        wait();
                    }
                    System.out.println(counter + " ban ve B-" + String.format("%03d", countB++));
                    ticketsB--;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void addTickets(String room, int amount) {
            if (room.equals("A")) {
                ticketsA += amount;
                System.out.println("Nha cung cap: Da them " + amount + " ve vao phong A");
            }
            notifyAll();
        }
    }

    static class BookingCounter extends Thread {

        private TicketPool pool;
        private String name;
        private String room;

        public BookingCounter(String name, TicketPool pool, String room) {
            this.name = name;
            this.pool = pool;
            this.room = room;
        }

        @Override
        public void run() {
            while (true) {
                pool.sellTicket(name, room);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Supplier extends Thread {

        private TicketPool pool;

        public Supplier(TicketPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(4000);
                pool.addTickets("A", 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        TicketPool pool = new TicketPool(0, 5);

        BookingCounter c1 = new BookingCounter("Quay 1", pool, "A");
        BookingCounter c2 = new BookingCounter("Quay 2", pool, "B");
        Supplier supplier = new Supplier(pool);

        c1.start();
        c2.start();
        supplier.start();
    }
}