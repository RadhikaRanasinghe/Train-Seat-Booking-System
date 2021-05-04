/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/

/**
 * Represents a Train Station which contains a waiting room and a train queue.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class TrainStation {
    private static Passenger[] waitingRoom = new Passenger[42];
    private static PassengerQueue trainQueue;

    public static void main(String[] args) {
        System.out.println("                \uD835\uDD4E \uD835\uDD3C \uD835\uDD43 ℂ \uD835\uDD46 \uD835\uDD44 \uD835\uDD3C   \uD835\uDD4B \uD835\uDD46   \uD835\uDD4B ℝ \uD835\uDD38 \uD835\uDD40 ℕ   \uD835\uDD39 \uD835\uDD46 \uD835\uDD38 ℝ \uD835\uDD3B \uD835\uDD40 ℕ \uD835\uDD3E   ℙ ℝ \uD835\uDD46 \uD835\uDD3E ℝ \uD835\uDD38 \uD835\uDD44   \uD835\uDD46 \uD835\uDD3D   \uD835\uDD3B \uD835\uDD3C ℕ \uD835\uDD4C \uD835\uDD4E \uD835\uDD38 ℝ \uD835\uDD38   \uD835\uDD44 \uD835\uDD3C ℕ \uD835\uDD40 \uD835\uDD42 \uD835\uDD3C        \n");
        //Initializing elements of the waiting room array
        for (int i = 0; i < 42; i++) {
            TrainStation.getWaitingRoom()[i] = new Passenger("", "", "");
        }
        //Loading the passengers who have booked the train seats to the waiting room
        waitingRoom = Database.loadProgramToWaitingRoom();
        trainQueue = new PassengerQueue();
        GUI.main(args);

    }

    /**
     * @return Passenger array of the waiting room with 42 elements
     */
    public static Passenger[] getWaitingRoom() {
        return waitingRoom;
    }

    /**
     * @param waitingRoom Passenger array of 42 elements to be set as the waiting room
     */
    public static void setWaitingRoom(Passenger[] waitingRoom) {
        TrainStation.waitingRoom = waitingRoom;
    }

    /**
     * @return the passenger queue object
     */
    public static PassengerQueue getTrainQueue() {
        return trainQueue;
    }

}
