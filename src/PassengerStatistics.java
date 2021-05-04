/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/

/**
 * Represents a Passenger's different statistics used in calculations.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class PassengerStatistics extends Passenger {

    private int secondsInQueue;
    private int position;
    private int waitingTimeInQueue;

    /**
     * This is the constructor of the object class PassengerStatistics.
     *
     * @param fullName           The full name of the passenger taken into simulation.
     * @param seatNum            The seat number of the passenger taken into simulation.
     * @param NationalIDNumber   NIC number of the passenger taken into simulation.
     * @param secondsInQueue     seconds stayed in queue of the passenger taken into simulation.
     * @param position           Position of the instantaneous queue of the passenger taken into simulation.
     * @param waitingTimeInQueue Waiting Time of the instantaneous queue of the passenger taken into simulation.
     */
    public PassengerStatistics(String fullName, String seatNum, String NationalIDNumber, int secondsInQueue, int position, int waitingTimeInQueue) {
        super(fullName, seatNum, NationalIDNumber);
        setSecondsInQueue(secondsInQueue);
        setPosition(position);
        setWaitingTimeInQueue(waitingTimeInQueue);
    }

    /**
     * Getter of the seconds in queue of the object class Passenger statistics
     * @return An int value of seconds waited in queue
     */
    @Override
    public int getSecondsInQueue() {
        return secondsInQueue;
    }

    /**
     * Setter of the seconds in queue of the object class Passenger Statistics
     * @param secondsInQueue An int value to be set as the seconds in queue
     */
    public void setSecondsInQueue(int secondsInQueue) {
        this.secondsInQueue = secondsInQueue;
    }

    /**
     * Getter of the position of the Passenger in the object class Passenger statistics
     * @return the int value of the position of the passenger
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setter of the position of the Passenger in the object class Passenger statistics
     * @param position the int value of the position of the passenger that is to be set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Getter of the waiting time of the passenger in the object class Passenger statistics
     * @return the int value of seconds
     */
    public int getWaitingTimeInQueue() {
        return waitingTimeInQueue;
    }

    /**
     * Setter of the waiting time of the passenger in the object class Passenger statistics
     * @param waitingTimeInQueue the int value of seconds that is to be set
     */
    public void setWaitingTimeInQueue(int waitingTimeInQueue) {
        this.waitingTimeInQueue = waitingTimeInQueue;
    }

    /**
     * This methods if used to know if the passenger has a waiting time of more than half of the max waiting time.
     *
     * @return A boolean if the passenger waits more than 7 seconds
     */
    public boolean waitsMoreThan7Seconds() {
        return getWaitingTimeInQueue() > 7;
    }

    /**
     * This method is used to know if the passenger is in the position 1 in the instantaneous queue.
     *
     * @return A boolean if the passenger's position is 1st
     */
    public boolean isWaiting() {
        return getPosition() > 1;
    }
}
