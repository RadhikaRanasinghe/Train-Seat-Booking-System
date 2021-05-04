/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/
import java.io.Serializable;
import java.util.Random;

/**
 * Represents a Passenger of a train queue.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class Passenger extends Object implements Serializable {
    private String fullName;
    private String seatNum;
    private String nationalIDNumber;
    private int secondsInQueue;

    /**
     * This is the constructor of the object class.
     *
     * @param fullName Full name of the passenger
     * @param seatNum  Seat number of the passenger
     * @param nationalIDNumber NIC number of the passenger
     */
    public Passenger(String fullName, String seatNum, String nationalIDNumber) {
        super();
        setFullName(fullName);
        setSeatNum(seatNum);
        setNationalIdNumber(nationalIDNumber);
        setSecondsInQueue();
    }

    /**
     * Getter fullName of the object class Passenger
     * @return The full name of the passenger
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter fullName of the object class Passenger
     * @param fullName The name of the passenger to be set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter seconds in queue of the object class Passenger
     * @return the int value of Passenger's waiting time in seconds
     */
    public int getSecondsInQueue() {
        return secondsInQueue;
    }

    /**
     * Setter seconds in queue of the object class Passenger.
     * The method uses the concept of rolling three 6 sided dices,
     * to get the seconds in queue.
     */
    private void setSecondsInQueue() {
        //setting 3 dices and adding the total random seconds generated4
        Random rand = new Random();
        int totalSecondsInQueue = 0;
        int[] dice = new int[3];
        for (int die = 0; die < 3; die++) {
            dice[die] = rand.nextInt(6) + 1;
            totalSecondsInQueue = totalSecondsInQueue + dice[die];
        }
        this.secondsInQueue = totalSecondsInQueue;
    }

    /**
     * Setter of the seconds which was previously saved in the Train Queue
     * @param seconds The seconds taken from the saved train queue
     */
    public void setSecondsInQueueFromFile(int seconds) {
        this.secondsInQueue = seconds;
    }

    /**
     * Getter of the seat number in the passenger object class
     * @return the seat number of the passenger
     */
    public String getSeatNum() {
        return seatNum;
    }

    /**
     * Setter of the seat number in the passenger object class
     * @param seatNum the seat number of the passenger to be set
     */
    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    /**
     * Getter of the NIC number of the passenger object class
     * @return The NIC number of the passenger
     */
    public String getNationalIDNumber() {
        return nationalIDNumber;
    }

    /**
     * Setter of the NIC number of the passenger object class
     * @param nationalIDNumber The NIC of the passenger that is to be set
     */
    public void setNationalIdNumber(String nationalIDNumber) {
        this.nationalIDNumber = nationalIDNumber;
    }

}
