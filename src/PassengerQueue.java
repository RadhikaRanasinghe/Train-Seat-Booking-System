/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/

import java.util.LinkedList;

/**
 * Represents a Passenger's statistics who is in the train queue.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class PassengerQueue implements java.io.Serializable {
    private final LinkedList<Passenger> queueArray = new LinkedList<>();

    /**
     * Getter of the Queue Array of the object class PassengerQueue
     * @return The linkedList of Passenger objects as a queue array
     */
    public LinkedList<Passenger> getQueueArray() {
        return queueArray;
    }

    /**
     * This method is used to add a customer to the Passenger Queue
     *
     * @param passenger An object of passenger class which is to be added to the queue
     */
    public void addPassenger(Passenger passenger) {
        this.queueArray.add(passenger);
    }

    /**
     * This method is used to remove a passenger from the train queue.
     *
     * @param passengerName The name given by the user to be removed from the train queue
     * @return the removed passenger
     */
    public Passenger removePassenger(String passengerName) {
        boolean isPresent = false;
        Passenger removedPassenger = null;
        for (int i = 0; i < getMaxQueueLength(); i++) {
            //Checking for the passenger name in the queue array
            if (this.queueArray.get(i).getFullName().equalsIgnoreCase(passengerName)) {
                removedPassenger = this.queueArray.get(i);
                isPresent = true;
                //Removing the passenger from the queue
                this.queueArray.remove(i);
                break;
            }
        }
        if (!isPresent) {
            System.out.println("\nThe Passenger name you entered is not in the Train Queue.");
        }
        return removedPassenger;
    }

    /**
     * This method is used to check if the train queue is full.
     *
     * @return A boolean either true or false
     */
    public Boolean isFull() {
        boolean isFull = false;
        if (this.queueArray.size() == 42) {
            isFull = true;
        }
        return isFull;
    }

    /**
     * This method is used to get the maximum queue length
     *
     * @return an int which is the maximum queue length
     */
    public int getMaxQueueLength() {
        return this.queueArray.size();
    }


}
