package PerformanceTests;

import reentrantLockMethods.Customer;
import reentrantLockMethods.TicketPoolLock;
import reentrantLockMethods.Ticket;
import reentrantLockMethods.Vendor;

public class ReentrantLockPerfTest {

	public static void main(String[] args) throws InterruptedException {
		int ticketCount = 100; // Number of tickets to produce

		TicketPoolLock pool = new TicketPoolLock(10); // Queue capacity
		Vendor vendor = new Vendor(pool, "Vendor-PerfTest", ticketCount);
		Customer customer = new Customer(pool);

		Thread vendorThread = new Thread(vendor, "Vendor-Thread");
		Thread customerThread = new Thread(customer, "Customer-Thread");

		long startTime = System.nanoTime();

		vendorThread.start();
		customerThread.start();
		vendorThread.join();
		Thread.sleep(3000);
		customer.stop();
		customerThread.interrupt();

		customerThread.join();

		long endTime = System.nanoTime();
		long durationInMs = (endTime - startTime) / 1_000_000;

		System.out.println("\n✅ Version 1 Execution Time: " + durationInMs + " ms");
	}
}
