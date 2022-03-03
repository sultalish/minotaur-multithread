// Alisher Sultangazin
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class birthday {
  public static void main(String[] args) {
    // Guest number Input area
    Scanner sc = new Scanner(System.in);
    System.out.println("Dear Minotaur, please enter the number of guests: ");
    int N = sc.nextInt();
    System.out.printf("Number of guests is: %d\n", N);

    // START
    // Set individual visits to 0
    AtomicInteger visits = new AtomicInteger(0);
    // Set the status of the game to false = "Not finished"
    AtomicBoolean game = new AtomicBoolean(false);
    // Queue for visiting the Minotaur's Labyrinth
    GameQueue queue = new GameQueue(visits, game);
    // Create N Threads
    Thread thread[] = new Thread[N];

    // start N Threads
    for (int i = 0; i < N; i++) {
      thread[i] = new Thread(new Guests(queue, game, N), "Guest " + (i + 1));
      thread[i].start();
    }

    for (int i = 0; i < N; i++) {
      try {
        thread[i].join();
      }
      catch(Exception e) {
        System.out.println("ERROR");
      }
    }
  }
}

class Guests implements Runnable {
  AtomicBoolean game;
  private AtomicInteger guest;
  private GameQueue queue;
  String leader = new String("Guest 0");

  public Guests(GameQueue queue, AtomicBoolean game, int N) {
    this.queue = queue;
    this.game = game;
    this.guest = new AtomicInteger(N);
  }

  public void run() {
    while (!game.get()) {
      System.out.printf("%s: Going into the labyrinth\n", Thread.currentThread().getName());
			queue.labyrinthEnter(new Object(), guest);
    }
  }
}

class GameQueue {
  // create ReentrantLock
  private final Lock lock = new ReentrantLock();
  // Number of current visits
  public AtomicInteger visits = new AtomicInteger();
  // ArrayList of the guests who ate the cake
  ArrayList<String> happy_guests = new ArrayList<String>();
  // Atomic Boolean to keep track if the cake is present or eaten
  public static AtomicBoolean cake = new AtomicBoolean(true);
  // Atomic Boolean to keep track of the progress of the game (if the game is finished or not)
  public AtomicBoolean game = new AtomicBoolean(false);

  public GameQueue(AtomicInteger visits, AtomicBoolean game) {
    this.visits = visits;
    this.game = game;
  }

  // When guest enters the labyrinth
  public void labyrinthEnter(Object queue, AtomicInteger guest) {
    // Set the Guest 0 to be the Leader
    String leader = new String("Guest 1");
    // Get current guest's name
    String guestName = new String(Thread.currentThread().getName());

    // Block the current operation until it's finished (until the lock is released)
    lock.lock();

    try {
      // If the current guest is the leader
      if (guestName.equals(leader)) {
        // If cake is eaten
        if (!cake.get()) {
          // Leader now knows that some of the guests ate the cake and increments the number of "Happy guests"
          visits.incrementAndGet();
          // Ask for a new cake (but don't eat it)
          cake.set(true);
          // Inform about the update
          System.out.println("Leader: There are " + visits.get() + " number of guests visits the labryinth");
        }
        // If cake is present
        else {
          // Looks like nobody ate the cake :(
          System.out.println("Leader: There are " + visits.get() + " number of guests visits the labryinth");
        }

        // That means that the leader is the only one who didn't eat the cake. That means he can now eat it and finish the party!
        if (visits.get() == guest.get() - 1)
        {
          // Add leader's name to the "Happy Guests" list
          happy_guests.add(guestName);
          // Eat the cake
          cake.set(false);
          // Finish the game(birthday party)
          game.set(true);
          // Inform that the game is finished
          System.out.printf("============\n");
          System.out.println("Leader eats the last cake! The birthday party is finished!!!");
          System.out.printf("============\n");
        }
      }
      // If the guest is not the leader
      else {
        // If the cake is present and the guest haven't eaten the cake
        if (cake.get() && !happy_guests.contains(guestName)) {
          // Inform that the cake is eaten
          System.out.printf("============\n");
          System.out.printf("%s: Eat the cake\n", guestName);
          System.out.printf("============\n");
          // Add the current guest to the "Happy Guests List"
          happy_guests.add(guestName);
          // Eat the cake!
          cake.set(false);
        }
      }
    } finally {
      // Inform that the labyrinth entry is done and the guest left the labyrinth
      System.out.printf("%s: Left the labyrinth\n", guestName);
      // release the lock
			lock.unlock();
    }
  }
}
