package ex6;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Ex6 {

    static class Ticket {
        String id;
        boolean sold;

        public Ticket(String id) {
            this.id = id;
        }
    }

    static class Room {
        String name;
        List<Ticket> tickets = new ArrayList<>();
        int soldCount;

        public Room(String name, int total) {
            this.name = name;
            for (int i = 1; i <= total; i++) {
                tickets.add(new Ticket(name + "-" + String.format("%03d", i)));
            }
        }

        public synchronized Ticket sell() {
            for (Ticket t : tickets) {
                if (!t.sold) {
                    t.sold = true;
                    soldCount++;
                    return t;
                }
            }
            return null;
        }

        public synchronized int remaining() {
            return tickets.size() - soldCount;
        }
    }

    static class BookingCounter implements Runnable {

        String name;
        List<Room> rooms;
        AtomicBoolean running;
        AtomicBoolean paused;
        Random random = new Random();

        public BookingCounter(String name, List<Room> rooms, AtomicBoolean running, AtomicBoolean paused) {
            this.name = name;
            this.rooms = rooms;
            this.running = running;
            this.paused = paused;
        }

        @Override
        public void run() {
            try {
                while (running.get()) {
                    synchronized (paused) {
                        while (paused.get()) {
                            paused.wait();
                        }
                    }
                    Room room = rooms.get(random.nextInt(rooms.size()));
                    Ticket t = room.sell();
                    if (t != null) {
                        System.out.println(name + " da ban ve " + t.id);
                    }
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    static class DeadlockDetector implements Runnable {

        AtomicBoolean running;

        public DeadlockDetector(AtomicBoolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            try {
                while (running.get()) {
                    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                    long[] ids = bean.findDeadlockedThreads();
                    if (ids != null) {
                        System.out.println("PHAT HIEN DEADLOCK!");
                        return;
                    }
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    static ExecutorService counterPool;
    static ExecutorService systemPool;
    static List<Room> rooms = new ArrayList<>();
    static AtomicBoolean running = new AtomicBoolean(false);
    static AtomicBoolean paused = new AtomicBoolean(false);

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n1. Bat dau mo phong");
                System.out.println("2. Tam dung mo phong");
                System.out.println("3. Tiep tuc mo phong");
                System.out.println("4. Them ve vao phong");
                System.out.println("5. Xem thong ke");
                System.out.println("6. Phat hien deadlock");
                System.out.println("7. Thoat");
                System.out.print("Chon: ");

                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        System.out.print("Nhap so phong: ");
                        int p = sc.nextInt();
                        System.out.print("Nhap so ve/phong: ");
                        int t = sc.nextInt();
                        System.out.print("Nhap so quay: ");
                        int c = sc.nextInt();

                        rooms.clear();
                        for (int i = 0; i < p; i++) {
                            rooms.add(new Room(String.valueOf((char) ('A' + i)), t));
                        }

                        running.set(true);
                        paused.set(false);

                        counterPool = Executors.newFixedThreadPool(c);
                        systemPool = Executors.newSingleThreadExecutor();

                        for (int i = 1; i <= c; i++) {
                            counterPool.submit(new BookingCounter("Quay " + i, rooms, running, paused));
                            System.out.println("Quay " + i + " bat dau ban ve...");
                        }

                        systemPool.submit(new DeadlockDetector(running));

                        System.out.println("Da khoi tao he thong voi " + p + " phong, " + (p * t) + " ve, " + c + " quay");
                        break;

                    case 2:
                        paused.set(true);
                        System.out.println("Da tam dung tat ca quay ban ve.");
                        break;

                    case 3:
                        synchronized (paused) {
                            paused.set(false);
                            paused.notifyAll();
                        }
                        System.out.println("Da tiep tuc hoat dong.");
                        break;

                    case 4:
                        System.out.print("Nhap phong: ");
                        String r = sc.next();
                        System.out.print("Nhap so ve them: ");
                        int add = sc.nextInt();
                        for (Room room : rooms) {
                            if (room.name.equals(r)) {
                                synchronized (room) {
                                    int start = room.tickets.size() + 1;
                                    for (int i = 0; i < add; i++) {
                                        room.tickets.add(new Ticket(room.name + "-" + String.format("%03d", start + i)));
                                    }
                                }
                            }
                        }
                        System.out.println("Da them ve vao phong " + r);
                        break;

                    case 5:
                        System.out.println("=== THONG KE HIEN TAI ===");
                        int revenue = 0;
                        for (Room room : rooms) {
                            System.out.println("Phong " + room.name + ": Da ban " + room.soldCount + "/"
                                    + room.tickets.size() + " ve");
                            revenue += room.soldCount * 125000;
                        }
                        System.out.println("Tong doanh thu: " + revenue + " VND");
                        break;

                    case 6:
                        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                        long[] ids = bean.findDeadlockedThreads();
                        System.out.println("Dang quet deadlock...");
                        if (ids == null) {
                            System.out.println("Khong phat hien deadlock.");
                        } else {
                            System.out.println("PHAT HIEN DEADLOCK!");
                        }
                        break;

                    case 7:
                        running.set(false);
                        if (counterPool != null)
                            counterPool.shutdownNow();
                        if (systemPool != null)
                            systemPool.shutdownNow();
                        System.out.println("Dang dung he thong...");
                        System.out.println("Ket thuc chuong trinh.");
                        return;
                }
            }
        }
    }
}
