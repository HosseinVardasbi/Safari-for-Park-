
package jurassicpark_v02;

import java.util.concurrent.Semaphore;

public class JurassicPark implements Runnable{
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
                new Thread(Passenger(i)).start();
                new Thread(new JurassicPark().Passenger(i));
            }
            for (int i = 0; i < main.numCars; i++) {
                new Thread(Car(i)).start();
            }
            Thread.sleep(1000 * main.runTime);
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(JurassicPark.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static int Passenger(int _id){
        return _id;
    }
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
