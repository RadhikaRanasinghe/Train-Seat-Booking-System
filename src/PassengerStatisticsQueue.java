/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/

/**
 * Represents a PassengerQueue's different statistics used in calculations.
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class PassengerStatisticsQueue extends PassengerQueue {

    /**
     * This method is used to add a Passenger taken into simulation.
     * @param passenger An object of PassengerStatistics is added to the passenger queue simulation
     */
    public void addPassenger(PassengerStatistics passenger) {
        super.addPassenger(passenger);
    }

    /**
     * This method is used to get the maximum personal waiting time of the passengers in the simulation queue.
     * @return The maximum personal wait of the queue
     */
    public int getMaxPersonalWaitingTime() {
        int max = 0;
        for (Passenger passenger : this.getQueueArray()) {
            if (passenger.getSecondsInQueue() > max) {
                max = passenger.getSecondsInQueue();
            }
        }
        return max;
    }

    /**
     * This method is used to get the minimum personal waiting time of the passengers in the simulation queue.
     * @return The minimum personal wait of the queue
     */
    public int getMinPersonalWaitingTime() {
        int min = 18;
        for (Passenger passenger : this.getQueueArray()) {
            if (passenger.getSecondsInQueue() < min) {
                min = passenger.getSecondsInQueue();
            }
        }
        return min;
    }

    /**
     * This method is used to get the total waiting time of the
     * last passenger that is the total waiting time of the whole queue.
     * @return The total wait time of the last passenger
     */
    public int getTotalWaitingTimeOfTheLastPassenger() {
        int totalWaitingTime = 0;
        for (Passenger passenger : this.getQueueArray()) {
            totalWaitingTime = totalWaitingTime + passenger.getSecondsInQueue();
        }
        return totalWaitingTime;
    }

    /**
     * This method is used to get the the length of the queue.
     * @return The length of the queue
     */
    public int getQueueLength() {
        return this.getQueueArray().size();
    }

    /**
     * This method is used to get the average waiting time of the whole passenger queue.
     * @return Average passenger waiting time
     */
    public double getAverageWaitingTime() {
        if (!this.getQueueArray().isEmpty()) {
            return (double) getTotalWaitingTimeOfTheLastPassenger() / this.getQueueLength();
        } else {
            return 0;
        }
    }

}

