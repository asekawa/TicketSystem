package reentrantLockMethods;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPoolLock implements TicketPool {

    private Queue<Ticket> ticketQueue = new LinkedList<>();
    private int maxSize;
    private int noOfTicketsOffered = 0;
    private int noOfTicketsBought = 0;

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition spaceAvailable = lock.newCondition();     
    private final Condition ticketAvailable = lock.newCondition();   

    public TicketPoolLock(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void addTicket(Ticket ticket) {
        lock.lock();
        try {
            while (ticketQueue.size() == maxSize) {
                spaceAvailable.await();
            }
            ticketQueue.offer(ticket);
            noOfTicketsOffered++;
            System.out.println(Thread.currentThread().getName() + 
                " added TicketID: " + ticket.getTicketID() +
                " for the Concert Event");
            ticketAvailable.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Ticket purchaseTicket() {
        lock.lock();
        try {
            while (ticketQueue.isEmpty()) {
                ticketAvailable.await();
            }
            Ticket ticket = ticketQueue.poll();
            noOfTicketsBought++;
            spaceAvailable.signalAll();
            System.out.println(Thread.currentThread().getName() + 
                " purchased TicketID: " + ticket.getTicketID() +
                " from " + ticket.getVendorName() +
                " for " + ticket.getEvent());
            return ticket;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int availableTickets() {
        lock.lock();
        try {
            return ticketQueue.size();
        } finally {
            lock.unlock();
        }
    }

    public void printTicketPoolStatus() {
        lock.lock();
        try {
            System.out.println("Tickets added: " + noOfTicketsOffered +
                ", Tickets bought: " + noOfTicketsBought +
                ", Available: " + ticketQueue.size());
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return ticketQueue.isEmpty();
        } finally {
            lock.unlock();
        }
    }
}

