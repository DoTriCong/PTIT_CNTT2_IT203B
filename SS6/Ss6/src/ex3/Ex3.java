package ex3;

public class Ex3 {

    static class Ticket {
        private String code;

        public Ticket(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    static class TicketPool {

        private int tickets;
        private int sold;
        private String room;
        private int counter = 1;

        public TicketPool(String room, int tickets) {
            this.room = room;
            this.tickets = tickets;
        }

        public synchronized Ticket sell() {
            if (tickets == 0)
                return null;
            tickets--;
            sold++;
            return new Ticket(room + "-" + String.format("%03d", counter++));
        }

        public synchronized void addTickets(int count) {
            tickets += count;
            System.out.println("Nha cung cap: Da them " + count + " ve vao phong " + room);
        }

        public int getSold() {
            return sold;
        }

        public int getRemaining() {
            return tickets;
        }
    }

    static class BookingCounter extends Thread {

        private TicketPool pool;
        private String name;

        public BookingCounter(String name, TicketPool pool) {
            this.name = name;
            this.pool = pool;
        }

        @Override
        public void run() {
            while (true) {
                Ticket t = pool.sell();
                if (t == null)
                    break;
                System.out.println(name + " da ban ve " + t.getCode());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class TicketSupplier implements Runnable {

        private TicketPool roomA;
        private TicketPool roomB;
        private int supplyCount;
        private int interval;
        private int rounds;

        public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
            this.roomA = roomA;
            this.roomB = roomB;
            this.supplyCount = supplyCount;
            this.interval = interval;
            this.rounds = rounds;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < rounds; i++) {
                    Thread.sleep(interval);
                    roomA.addTickets(supplyCount);
                    roomB.addTickets(supplyCount);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter c1 = new BookingCounter("Quay 1", roomA);
        BookingCounter c2 = new BookingCounter("Quay 2", roomB);

        Thread supplier = new Thread(
                new TicketSupplier(roomA, roomB, 3, 3000, 2));

        c1.start();
        c2.start();
        supplier.start();

        c1.join();
        c2.join();
        supplier.join();

        System.out.println("Quay 1 ban duoc: " + roomA.getSold() + " ve");
        System.out.println("Quay 2 ban duoc: " + roomB.getSold() + " ve");
        System.out.println("Ve con lai phong A: " + roomA.getRemaining());
        System.out.println("Ve con lai phong B: " + roomB.getRemaining());
    }
}