// Alisher Sultangazin
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class vase {
  public static void main(String[] args) {
    // Guest number Input area
    Scanner sc = new Scanner(System.in);
    System.out.println("Dear Minotaur, please enter the number of guests: ");
    // N = number of guests
    int N = sc.nextInt();
    System.out.printf("Number of guests is: %d\n", N);

    // START
    // Set individual visits to 0
    AtomicInteger visits = new AtomicInteger(0);
    // Set the status of the party to false = "Not finished"
    AtomicBoolean party = new AtomicBoolean(false);

    // Create a queue for the party
    VaseQueue queue = new VaseQueue(visits, party);

    // Create N Threads
    Thread thread[] = new Thread[N];

    // Start N threads
    for (int i = 0; i < N; i++) {
      thread[i] = new Thread(new Guests(queue, party, N), "Guest " + i);
      thread[i].start();
    }

    for (int i = 0; i < N; i++) {
      try {
        thread[i].join();
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }

    System.out.printf("All guests have seen the cake at least once! the party is over!\n");

  }
}

class Guests implements Runnable {
  AtomicBoolean party;
  private AtomicInteger visits;
  private VaseQueue queue;

  public Guests(VaseQueue queue, AtomicBoolean party, int N) {
    this.queue = queue;
    this.party = party;
    this.visits = new AtomicInteger(N);
  }

  public void run() {
    // while the party is not over, go into the room
    while (!party.get()) {
      queue.roomEnter(new Object(), visits);
    }
  }
}

class VaseQueue {
  // Create a new lock
  Lock lock = new ReentrantLock();
  // Create the queue of guests
  Queue<Thread> guest_queue = new LinkedList<Thread>();
  // Number of current visits
  public AtomicInteger visits = new AtomicInteger();
  // Atomic Boolean to keep track of the progress of the party (if the party is over or not)
  public AtomicBoolean party = new AtomicBoolean(false);
  // Name of the guest
  private String guestName = new String(Thread.currentThread().getName());
  // List of people who saw the vase
  ArrayList<String> happy_guests = new ArrayList<String>();

  public VaseQueue(AtomicInteger visits, AtomicBoolean party) {
    this.visits = visits;
    this.party = party;
  }

  // add to queue
  public void queue_add(Thread th) {
    lock.lock();

    try {
      if (!guest_queue.contains(th)) {
        guest_queue.add(th);
        System.out.printf("%s: entered the queue to enter the room\n", th.getName());
      }
    } finally {
      lock.unlock();
    }
  }

  // remove from queue
  public Thread queue_remove() {
    lock.lock();

    try {
      Thread th = guest_queue.poll();
      System.out.printf("\n||| %s: checked out the vase and left room |||\n\n", th.getName());

      if (!happy_guests.contains(th.getName())) {
        happy_guests.add(th.getName());
      }

      return th;
    } finally {
      lock.unlock();
    }
  }


  // check if the guest is in the queue
  public Boolean queue_contains(Thread th) {
    lock.lock();

    try {
      return guest_queue.contains(th);
    } finally {
      lock.unlock();
    }
  }

  // Process of guest entering the queue and entering the room
  public void roomEnter(Object queue, AtomicInteger max_visits) {
    // Get the guest's name
    if (!party.get()) {
      guestName = new String(Thread.currentThread().getName());
    } else {
      return;
    }

    // If the current guest is not in the queue, let him in the queue, let him in the room
    if (!queue_contains(Thread.currentThread())) {
      queue_add(Thread.currentThread());
      queue_remove();
      visits.incrementAndGet();
      try {
        Thread.sleep(100);
      } catch(InterruptedException e) {
        System.out.println(e);
      }
    }

    // If each guest have seen the vase, we give guests some more (random) number of visits before ending the party
    // Once we get to the number we set, we end the party!
		if(happy_guests.size() == max_visits.get()) {
      Random rand = new Random();
      if (visits.intValue() >= max_visits.get() + rand.nextInt(max_visits.get()))
        party.set(true);
    }
  }
}
