package ex5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ex5 {

    static class Ticket {
        String id;
        boolean isHeld;
        long holdExpiryTime;
        boolean isVIP;

        public Ticket(String id) {
            this.id = id;
        }
    }

    static class TicketPool {

        String room;
        List<Ticket> tickets = new ArrayList<>();

        public TicketPool(String room, int capacity) {
            this.room = room;
            for (int i = 1; i <= capacity; i++) {
                tickets.add(new Ticket(room + "-" + String.format("%03d", i)));
            }
        }

        public synchronized Ticket holdTicket(boolean vip) {
            long now = System.currentTimeMillis();
            for (Ticket t : tickets) {
                if (!t.isHeld) {
                    t.isHeld = true;
                    t.isVIP = vip;
                    t.holdExpiryTime = now + 5000;
                    return t;
                }
            }
            return null;
        }

        public synchronized boolean sellHeldTicket(Ticket t) {
            if (t != null && t.isHeld) {
                tickets.remove(t);
                return true;
            }
            return false;
        }

        public synchronized void releaseExpiredTickets() {
            long now = System.currentTimeMillis();
            for (Ticket t : tickets) {
                if (t.isHeld && now > t.holdExpiryTime) {
                    t.isHeld = false;
                    t.isVIP = false;
                    System.out.println("TimeoutManager: Ve " + t.id + " het han giu, da tra lai kho");
                }
            }
        }
    }

    static class BookingCounter implements Runnable {

        String name;
        List<TicketPool> pools;
        Random random = new Random();

        public BookingCounter(String name, List<TicketPool> pools) {
            this.name = name;
            this.pools = pools;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    boolean vip = random.nextBoolean();
                    TicketPool pool = pools.get(random.nextInt(pools.size()));
                    Ticket t = pool.holdTicket(vip);
                    if (t != null) {
                        System.out.println(name + ": Da giu ve " + t.id + (vip ? " (VIP)" : "")
                                + ". Vui long thanh toan trong 5s");
                        Thread.sleep(3000);
                        if (pool.sellHeldTicket(t)) {
                            System.out.println(name + ": Thanh toan thanh cong ve " + t.id);
                        }
                    }
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    static class TimeoutManager implements Runnable {

        List<TicketPool> pools;

        public TimeoutManager(List<TicketPool> pools) {
            this.pools = pools;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    for (TicketPool p : pools) {
                        p.releaseExpiredTickets();
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 6);
        TicketPool roomC = new TicketPool("C", 7);

        List<TicketPool> pools = new ArrayList<>();
        pools.add(roomA);
        pools.add(roomB);
        pools.add(roomC);

        for (int i = 1; i <= 5; i++) {
            new Thread(new BookingCounter("Quay " + i, pools)).start();
        }

        new Thread(new TimeoutManager(pools)).start();
    }
}
