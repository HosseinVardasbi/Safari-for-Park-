
package jurrasicpark;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
class Passenger extends JurassicPark implements Runnable{
    private int id = 0;
    public Passenger(int id) { this.id = id; }
    public void run() {
        Random random = new Random();
        while (passengerFinished != numPassengers) {
            try {
                Thread.sleep(1 + random.nextInt(1000 * rideTime));
                System.out.println("name=" + id + " wants to ride");
                carAvail.acquire();
                carTaken.release();
                carFilled.release();
                passengerReleased.acquire();
//                carAvail.release();
//                carTaken.acquire();
//                carFilled.release();
                System.out.println("name=" + id + " taking a ride");
//                passengerReleased.release();
                carAvail.release();
                carTaken.acquire();
                carFilled.acquire();
                System.out.println("name=" + id + " finished riding");
                passengerFinished++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Passenger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
class Car extends JurassicPark implements Runnable{
    private int id = 0;
    public Car(int id) { this.id = id; }
    public void run() {
        Random random = new Random();
        while (passengerFinished != numPassengers) {
            try {
                System.out.println("car=" + id + " ready to load");
//                carAvail.release();
//                carTaken.acquire();
                carFilled.release();
                carAvail.acquire();
                carTaken.release();
//                carFilled.acquire();
                System.out.println("car=" + id + " going on safari");
                Thread.sleep(1 + random.nextInt(1000 * rideTime));
                carAvail.release(); carTaken.acquire(); carFilled.acquire();
                System.out.println("car=" + id + " has returned");
                
                passengerFinished++;
                //passengerReleased.acquire();
//                passengerReleased.release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
public class JurassicPark{
    int p = 0;
    final int numPassengers = 6, numCars = 3, wanderTime = 5, rideTime = 4, runTime = 60;
    int passengerFinished = 0;
    final Semaphore carAvail = new Semaphore(3);
    final Semaphore carTaken = new Semaphore(0);
    final Semaphore carFilled = new Semaphore(0);
    final Semaphore passengerReleased = new Semaphore(6);
    public static void main(String[] args) {
        try {
            JurassicPark main = new JurassicPark();
            if (main.numPassengers == main.passengerFinished) {
                System.exit(0);
            }
            for (int i = 0; i < main.numPassengers; i++) {
                new Thread(new Passenger(i)).start();
            }
            for (int i = 0; i < main.numCars; i++) {
                new Thread(new Car(i)).start();
            }
            Thread.sleep(1000 * main.runTime);
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(JurassicPark.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
