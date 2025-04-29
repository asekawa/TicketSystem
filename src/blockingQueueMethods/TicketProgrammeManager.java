package blockingQueueMethods;

import java.util.*;

public class TicketProgrammeManager {

    public static void main(String[] args) {
        TicketPoolBlocking ticketPool = new TicketPoolBlocking(5);

        List<Vendor> vendors = new ArrayList<>();
        List<Customer> consumers = new ArrayList<>();
        List<Reader> readers = new ArrayList<>();
        List<Writer> writers = new ArrayList<>();

        final int MAX_VENDORS = 5;
        final int MAX_WRITERS = 2;
        final int NORMAL_TICKETS_PER_VENDOR = 10;
        final int SPECIAL_TICKETS_PER_WRITER = 5;

        int vendorCounter = 1;
        int customerCounter = 1;
        int readerCounter = 1;
        int writerCounter = 1;

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n--- Ticket Pool Simulation Started ---");
        System.out.println("Use CLI commands to add Vendors, Consumers, Readers, Writers.\n");

        while (running) {
            System.out.println("Commands: addVendor, addCustomer, addReader, addWriter, removeVendor, removeCustomer, removeReader, removeWriter, showTickets, exit");
            System.out.print("Command: ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "addVendor":
                    if (vendors.size() < MAX_VENDORS) {
                        Vendor vendor = new Vendor(ticketPool, "Vendor-" + vendorCounter, NORMAL_TICKETS_PER_VENDOR);
                        vendors.add(vendor);
                        new Thread(vendor, "Vendor-" + vendorCounter).start();
                        System.out.println("✅ New Vendor added. Producing tickets...");
                        vendorCounter++;
                    } else {
                        System.out.println("⚠️ Vendor limit reached.");
                    }
                    break;

                case "addCustomer":
                    Customer customer = new Customer(ticketPool);
                    consumers.add(customer);
                    new Thread(customer, "Customer-" + customerCounter).start();
                    System.out.println("✅ New Customer added.");
                    customerCounter++;
                    break;

                case "addReader":
                    Reader reader = new Reader(ticketPool);
                    readers.add(reader);
                    new Thread(reader, "Reader-" + readerCounter).start();
                    System.out.println("✅ New Reader added.");
                    readerCounter++;
                    break;

                case "addWriter":
                    if (writers.size() < MAX_WRITERS) {
                        Writer writer = new Writer(ticketPool, SPECIAL_TICKETS_PER_WRITER);
                        writers.add(writer);
                        new Thread(writer, "Writer-" + writerCounter).start();
                        System.out.println("✅ New Writer added. Producing special tickets...");
                        writerCounter++;
                    } else {
                        System.out.println("⚠️ Writer limit reached.");
                    }
                    break;

                case "removeVendor":
                    if (!vendors.isEmpty()) {
                        Vendor v = vendors.remove(vendors.size() - 1);
                        v.stop();
                        System.out.println("🛑 Vendor stopped.");
                    } else {
                        System.out.println("⚠️ No Vendors to remove.");
                    }
                    break;

                case "removeCustomer":
                    if (!consumers.isEmpty()) {
                        Customer c = consumers.remove(consumers.size() - 1);
                        c.stop();
                        System.out.println("🛑 Customer stopped.");
                    } else {
                        System.out.println("⚠️ No Customers to remove.");
                    }
                    break;

                case "removeReader":
                    if (!readers.isEmpty()) {
                        Reader r = readers.remove(readers.size() - 1);
                        r.stop();
                        System.out.println("🛑 Reader stopped.");
                    } else {
                        System.out.println("⚠️ No Readers to remove.");
                    }
                    break;

                case "removeWriter":
                    if (!writers.isEmpty()) {
                        Writer w = writers.remove(writers.size() - 1);
                        w.stop();
                        System.out.println("🛑 Writer stopped.");
                    } else {
                        System.out.println("⚠️ No Writers to remove.");
                    }
                    break;

                case "showTickets":
                    ticketPool.printTicketPoolStatus();
                    break;

                case "exit":
                    System.out.println("🛑 Stopping all threads...");
                    for (Vendor v : vendors) v.stop();
                    for (Customer c : consumers) c.stop();
                    for (Reader r : readers) r.stop();
                    for (Writer w : writers) w.stop();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println("✅ All threads stopped. Exiting...");
                    running = false;
                    break;

                default:
                    System.out.println("⚠️ Invalid command. Please try again.");
            }

            // Auto shutdown if pool is empty and all producers are done
            if (vendors.isEmpty() && writers.isEmpty() && ticketPool.isEmpty() && consumers.isEmpty()) {
                for (Customer c : consumers) c.stop();
                for (Reader r : readers) r.stop();
                System.out.println("🎯 All tickets processed. Stopping remaining threads...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("✅ System shutdown complete.");
                running = false;
            }
        }
    }
}
