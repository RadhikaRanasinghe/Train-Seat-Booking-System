/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


/**
 * ALl the methods that are used in the menu of this program are in this class.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class Methods {
    /**
     * This method is used to take an input from the user to perform a specific task
     * according to the input. The whole program runs through this method.
     */
    public static void inputMenu() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        //Showing the menu on the console
        System.out.print("\nSelect an option from the given below\n" +
                "• \uD835\uDD38 :  To add passenger to the Train Queue\n" +
                "• \uD835\uDD4D :  To view the Train Queue\n" +
                "• \uD835\uDD3B :  To delete passenger from the Train Queue\n" +
                "• ℝ :  To run simulation and to produce the report\n" +
                "• \uD835\uDD4A :  To store the Train Queue in to file\n" +
                "• \uD835\uDD43 :  To load the Train Queue from file\n" +
                "• ℚ :  To quit the program\n\n" +
                "Enter option character: ");

        //Taking user input
        String optionSelected = sc.nextLine();

        //User's input is taken into consideration for selecting which method to be executed
        if (optionSelected.equalsIgnoreCase("A")) {
            //Scene is set to add scene and the window is maximized and user is allowed to add passengers to train queue
            System.out.println("Adding passengers to train queue...");
            GUI.window.setScene(GUI.addScene);
            GUI.window.setIconified(false);
        } else if (optionSelected.equalsIgnoreCase("V")) {
            //Scene is set to view scene and the window is maximized and user is allowed to see the boarded passengers and
            //Who is in train queue and in waiting room separately in table views
            System.out.println("Viewing train queue...");
            //Updating the tables to the newest changes
            updateTables();
            GUI.window.setScene(GUI.viewScene);
            GUI.window.setIconified(false);
        } else if (optionSelected.equalsIgnoreCase("D")) {
            //Deleting a passenger according to the name given by the user
            System.out.println("Deleting passenger from train queue...");
            System.out.print("\nEnter passenger name that is to be removed: ");
            String passengerName = sc.nextLine();
            removingPassengerFromTrainQueue(passengerName);
            inputMenu();
        } else if (optionSelected.equalsIgnoreCase("R")) {
            //A report summary is shown after a simulation
            System.out.println("Simulating and producing the report...");
            Report();
            GUI.window.setScene(GUI.reportScene);
            GUI.window.setIconified(false);
        } else if (optionSelected.equalsIgnoreCase("S")) {
            //The train queue is saved to the database
            System.out.println("Saving train queue to file...");
            Database.storeTrainQueue();
            inputMenu();
        } else if (optionSelected.equalsIgnoreCase("L")) {
            //The train queue is loaded from the database
            System.out.println("Loading train queue from file...");
            load();
            inputMenu();
        } else if (optionSelected.equalsIgnoreCase("Q")) {
            //The program quits
            System.out.println("Quiting program...");
            System.exit(0);
        } else {
            //If the user enters any other except for the above
            System.out.println("Invalid Input...");
            inputMenu();
        }
    }

    /**
     * This method is used to call the train queue simulation method where the passengers
     * in the waiting room are added to the train queue to be boarded and the statistics
     * are used to come into conclusions.
     */
    public static void Report() throws IOException, ClassNotFoundException {
        //Initializing a new array for the waiting room for r
        Passenger[] waitingRoomForR;

        //Taking customers for waiting room
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter 'W' to run simulation from the existing waiting room or \nEnter 'N' to run simulation from the initial waiting room : ");
        String option = sc.nextLine();
        //Taking user input into consideration and selecting which waiting room to take
        if (option.equalsIgnoreCase("W")) {
            waitingRoomForR = TrainStation.getWaitingRoom().clone();
            simulation(waitingRoomForR);

        } else if (option.equalsIgnoreCase("N")) {
            waitingRoomForR = Database.loadProgramToWaitingRoom();
            simulation(waitingRoomForR);

        } else {
            System.out.println("Invalid Input");
            inputMenu();
        }


    }

    /**
     * This method is used to run the simulation.
     * The method also writes a text file of the report summary
     *
     * @param waitingRoomForR The waiting room selected by the user
     */
    public static void simulation(Passenger[] waitingRoomForR) {
        //Initializing the decimal formatter to set to 2 decimal points
        DecimalFormat df = new DecimalFormat("#0.00");
        Random rand = new Random();

        //Initializing passenger queues to get information
        PassengerStatisticsQueue passengerQueue = new PassengerStatisticsQueue();
        PassengerStatisticsQueue passengerQueue1 = new PassengerStatisticsQueue();
        PassengerStatisticsQueue passengerQueue2 = new PassengerStatisticsQueue();

        //Initializing observable lists for table views in the report scene
        ObservableList<PassengerStatistics> tableViewListQueue1 = FXCollections.observableArrayList();
        ObservableList<PassengerStatistics> tableViewListQueue2 = FXCollections.observableArrayList();

        ObservableList<PassengerStatistics> information = FXCollections.observableArrayList();
        int passengerCountWR = 0;

        //Initializing variables for calculation and storing them
        int maxWait = 0;
        int minWait = 108;
        int maxPersonal = 0;
        int minPersonal = 18;
        int maxWait1 = 0;
        int minWait1 = 108;
        int maxPersonal1;
        int minPersonal1;
        int maxWait2 = 0;
        int minWait2 = 108;
        int maxPersonal2;
        int minPersonal2;


        List<Integer> indexes = new ArrayList<>();
        List<Integer> indexesForSecond = new ArrayList<>();

        //Adding passengers to trainQueue
        for (int passenger = 0; passenger < waitingRoomForR.length; passenger++) {
            if (!waitingRoomForR[passenger].getFullName().equals("")) {
                passengerCountWR++;
                indexes.add(passenger);
                indexesForSecond.add(passenger);
            }
        }
        int numOfQueues = 0;
        //Looping till all the passengers in the waiting room is taken into calculations
        while (passengerCountWR > 0) {
            numOfQueues++;
            //Initializing the instantaneous queue length
            int queueLength = numOfPassengersAdded(passengerCountWR);
            int queueLength2 = queueLength;
            int waitingTimeInQueue = 0;
            int position = 0;
            //Reducing the passenger count to the randomized queue length
            passengerCountWR = passengerCountWR - queueLength;

            //Picking queue length number of customers until
            while (queueLength > 0) {
                //setting the queue position of the passenger
                position++;
                //Picking the passenger from the list
                int pickIndex = indexes.get(rand.nextInt(indexes.size()));
                //Adding passenger's waiting time in queue to whole queue waiting time
                waitingTimeInQueue = waitingTimeInQueue + waitingRoomForR[pickIndex].getSecondsInQueue();

                //Creating a passenger with the calculation information
                PassengerStatistics passenger = new PassengerStatistics(waitingRoomForR[pickIndex].getFullName(), waitingRoomForR[pickIndex].getSeatNum(),
                        waitingRoomForR[pickIndex].getNationalIDNumber(), waitingRoomForR[pickIndex].getSecondsInQueue(), position,
                        waitingTimeInQueue);
                //Adding the passenger to the queue
                passengerQueue.addPassenger(passenger);
                //Adding the passenger details to the table
                information.add(passenger);
                //Removing passenger from the list of passenger indexes
                indexes.remove((Integer) pickIndex);
                //Deducing the queue length
                queueLength--;

                //Checking if the current passenger exceeds the existing maximum waiting time in queue
                if (passenger.getWaitingTimeInQueue() > maxWait) {
                    //Only updated by the current passenger's waiting time in queue if it exceeds
                    maxWait = passenger.getWaitingTimeInQueue();
                }
                //Checking if the current passenger's waiting time in queue is lesser than the existing minimum
                if (passenger.getWaitingTimeInQueue() < minWait) {
                    //Only updated by the current passenger's waiting time in queue is lesser
                    minWait = passenger.getWaitingTimeInQueue();
                }
            }
            maxPersonal = passengerQueue.getMaxPersonalWaitingTime();
            minPersonal = passengerQueue.getMinPersonalWaitingTime();
            //Resetting the queue waiting time to zero
            waitingTimeInQueue = 0;

            //Adding passengers to two queues
            while (queueLength2 > 0) {
                //Variable for choosing between two queues
                PassengerStatisticsQueue selectedQueue;
                //Initializing a list to pick passengers randomly
                List<Passenger> randomPicks = new ArrayList<>();

                //Adding passengers randomly to the list
                for (int index = 0; index < queueLength2; index++) {
                    int pickIndex = indexesForSecond.get(rand.nextInt(indexesForSecond.size()));
                    randomPicks.add(waitingRoomForR[pickIndex]);
                    indexesForSecond.remove((Integer) pickIndex);

                }
                //Selecting which queue is shorter to add the number of randomly picked passengers
                if (passengerQueue1.getQueueArray().size() > passengerQueue2.getQueueArray().size()) {
                    selectedQueue = passengerQueue2;
                } else {
                    selectedQueue = passengerQueue1;
                }

                //Randomly picked passengers are then added to the train queue
                for (int passenger = 0; passenger < randomPicks.size(); passenger++) {
                    queueLength2--;
                    //Calculating the passenger's waiting time in the instantaneous queue
                    waitingTimeInQueue = waitingTimeInQueue + randomPicks.get(passenger).getSecondsInQueue();
                    //Setting the selected passenger's information
                    PassengerStatistics passengerStatistics = new PassengerStatistics(randomPicks.get(passenger).getFullName(),
                            randomPicks.get(passenger).getSeatNum(), randomPicks.get(passenger).getNationalIDNumber(),
                            randomPicks.get(passenger).getSecondsInQueue(), passenger + 1, waitingTimeInQueue);
                    //Adding the selected passenger to the train queue
                    selectedQueue.addPassenger(passengerStatistics);
                    //Executed only if the selected queue is the 1st queue
                    if (selectedQueue == passengerQueue1) {
                        //Adding passenger to the 1st queue table view
                        tableViewListQueue1.addAll(passengerStatistics);
                        //Checking if the current passenger exceeds the existing maximum waiting time in 1st queue
                        if (waitingTimeInQueue > maxWait1) {
                            maxWait1 = randomPicks.get(passenger).getSecondsInQueue();
                        }
                        //Checking if the current passenger's waiting time in queue is lesser than the existing minimum of the 1st queue
                        if (waitingTimeInQueue < minWait1) {
                            minWait1 = randomPicks.get(passenger).getSecondsInQueue();
                        }
                    } else { //Executed only if the selected queue is the 2nd queue
                        //Adding passenger to the 2nd queue table view
                        tableViewListQueue2.addAll(passengerStatistics);
                        //Checking if the current passenger exceeds the existing maximum waiting time in 2nd queue
                        if (waitingTimeInQueue > maxWait2) {
                            maxWait2 = randomPicks.get(passenger).getSecondsInQueue();

                        }
                        //Checking if the current passenger's waiting time in queue is lesser than the existing minimum of the 2nd queue
                        if (waitingTimeInQueue < minWait2) {
                            minWait2 = randomPicks.get(passenger).getSecondsInQueue();
                        }
                    }
                }
            }

        }

        maxPersonal1 = passengerQueue1.getMaxPersonalWaitingTime();
        minPersonal1 = passengerQueue1.getMinPersonalWaitingTime();
        maxPersonal2 = passengerQueue2.getMaxPersonalWaitingTime();
        minPersonal2 = passengerQueue2.getMinPersonalWaitingTime();

        //Updating all GUI components of the statistics accordingly
        GUI.minPQ.setText("Minimum personal wait : " + minPersonal + "s");
        GUI.maxPQ.setText("Maximum personal wait : " + maxPersonal + "s");
        GUI.maxQ.setText("Maximum wait in Queue : " + maxWait + "s");
        GUI.minQ.setText("Minimum wait in Queue : " + minWait + "s");

        GUI.minPQ1.setText("Minimum personal wait : " + minPersonal1 + "s");
        GUI.maxPQ1.setText("Maximum personal wait : " + maxPersonal1 + "s");
        GUI.maxQ1.setText("Maximum wait in Queue : " + maxWait1 + "s");
        GUI.minQ1.setText("Minimum wait in Queue : " + minWait1 + "s");

        GUI.minPQ2.setText("Minimum personal wait : " + minPersonal2 + "s");
        GUI.maxPQ2.setText("Maximum personal wait : " + maxPersonal2 + "s");
        GUI.maxQ2.setText("Maximum wait in Queue : " + maxWait2 + "s");
        GUI.minQ2.setText("Minimum wait in Queue : " + minWait2 + "s");

        //Adding passengers lists to the respective tables
        GUI.informationTable.getItems().clear();
        GUI.informationTable.getItems().addAll(information);

        GUI.passengerDetailsQ1.getItems().clear();
        GUI.passengerDetailsQ2.getItems().clear();
        GUI.passengerDetailsQ1.getItems().addAll(tableViewListQueue1);
        GUI.passengerDetailsQ2.getItems().addAll(tableViewListQueue2);

        //-------------------------------------------------------------//
        //Single queue statistics calculations

        //Calculating mean waiting time of the single queue
        double meanWaitingTimeInQueue = passengerQueue.getAverageWaitingTime();
        GUI.meanWaitQ.setText("•  Mean wait time using only one Queue : " + df.format(meanWaitingTimeInQueue) + "s");

        int numOfPassengersWhoWaitMoreThan7Seconds = 0;
        int waitingTimeOfThoseWhoWait = 0;
        int numOfPassengersWhoWait = 0;
        double sumOfDifferencesInMeanSquared = 0;
        for (PassengerStatistics passenger : information) {
            sumOfDifferencesInMeanSquared = sumOfDifferencesInMeanSquared + Math.pow((passenger.getSecondsInQueue() - meanWaitingTimeInQueue), 2);
            if (passenger.waitsMoreThan7Seconds()) {
                numOfPassengersWhoWaitMoreThan7Seconds++;
            }
            if (passenger.isWaiting()) {
                waitingTimeOfThoseWhoWait = waitingTimeOfThoseWhoWait + passenger.getSecondsInQueue();
                numOfPassengersWhoWait++;
            }

        }
        GUI.numOfPassengersQ.setText("•  Number of Passengers in Simulation : " + information.size());

        //Calculating probability of passengers who waits more than 7 seconds (Which is half of the maximum waiting time)
        double probabilityThatAPassengerWaitsMoreThan7Seconds = (double) numOfPassengersWhoWaitMoreThan7Seconds / passengerQueue.getQueueArray().size();
        GUI.probWaitPassengerQ.setText("•  Probability that a passenger that waits more than 7s : " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds));

        //Calculating the average waiting time of passengers who are not in the position 1
        double averageWaitingTimeOfThoseWhoWait = (double) waitingTimeOfThoseWhoWait / numOfPassengersWhoWait;
        GUI.averageWaitingQ.setText("•  Average waiting time of those who wait : " + df.format(averageWaitingTimeOfThoseWhoWait) + "s");

        //Calculating the variance of single queue
        double variance = sumOfDifferencesInMeanSquared / passengerQueue.getQueueArray().size();
        GUI.varianceQ.setText("•  Variance of 1 Queue : " + df.format(variance));

        //Calculating the standard variance of the single queue
        double standardDeviation = Math.round(Math.pow(variance, 0.5) * 100.0) / 100.0;
        GUI.standardDQ.setText("•  Standard Deviation of 1 queue : " + df.format(standardDeviation));

        //Selecting passengers of the who are too low or too high than the Standard deviation
        GUI.tooHighQ.setText("•  Waiting times that can be taken as too high :");
        GUI.tooLowQ.setText("•  Waiting times that can be taken as too low :");
        for (PassengerStatistics passenger : information) {
            if (passenger.getSecondsInQueue() > (meanWaitingTimeInQueue + standardDeviation)) {
                GUI.tooHighQ.setText(GUI.tooHighQ.getText() + "\n       ↑  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
            if (passenger.getSecondsInQueue() < (meanWaitingTimeInQueue - standardDeviation)) {
                GUI.tooLowQ.setText(GUI.tooLowQ.getText() + "\n       ↓  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
        }

        //-------------------------------------------------------------//
        //for queue 1 of 2

        GUI.numOfPassengersQ1.setText("•  Number of Passengers in Simulation for Queue (1 of 2) : " + tableViewListQueue1.size());

        //Calculating mean waiting time of queue (1 of 2)
        double meanWaitingTimeInQueue1 = passengerQueue1.getAverageWaitingTime();
        GUI.meanWaitQ1.setText("•  Mean wait time in queue (1 of 2) : " + df.format(meanWaitingTimeInQueue1) + "s");

        int numOfPassengersWhoWaitMoreThan7Seconds1 = 0;
        int waitingTimeOfThoseWhoWait1 = 0;
        int numOfPassengersWhoWait1 = 0;
        double sumOfDifferencesInMeanSquared1 = 0;
        for (PassengerStatistics passenger : tableViewListQueue1) {
            sumOfDifferencesInMeanSquared1 = sumOfDifferencesInMeanSquared1 + Math.pow((passenger.getSecondsInQueue() - meanWaitingTimeInQueue1), 2);
            if (passenger.waitsMoreThan7Seconds()) {
                numOfPassengersWhoWaitMoreThan7Seconds1++;
            }
            if (passenger.isWaiting()) {
                waitingTimeOfThoseWhoWait1 = waitingTimeOfThoseWhoWait1 + passenger.getSecondsInQueue();
                numOfPassengersWhoWait1++;
            }

        }
        //Calculating probability of passengers who waits more than 7 seconds (Which is half of the maximum waiting time)
        double probabilityThatAPassengerWaitsMoreThan7Seconds1 = (double) numOfPassengersWhoWaitMoreThan7Seconds1 / passengerQueue1.getQueueArray().size();
        GUI.probWaitPassengerQ1.setText("•  Probability that a passenger that waits more than 7 s : " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds1));

        //Calculating the average waiting time of passengers who are not in the position 1
        double averageWaitingTimeOfThoseWhoWait1 = (double) waitingTimeOfThoseWhoWait1 / numOfPassengersWhoWait1;
        GUI.averageWaitingQ1.setText("•  Average waiting time of those who wait : " + df.format(averageWaitingTimeOfThoseWhoWait1) + "s");

        //Calculating the variance of queue (1 of 2)
        double variance1 = (sumOfDifferencesInMeanSquared1 / passengerQueue1.getQueueArray().size());
        GUI.varianceQ1.setText("•  Variance of (1 of 2) Queue : " + df.format(variance1));

        //Calculating the standard variance of queue (1 of 2)
        double standardDeviation1 = Math.pow(variance1, 0.5);
        GUI.standardDQ1.setText("•  Standard Deviation of (1 of 2) queue : " + df.format(standardDeviation1));

        //Selecting passengers of the who are too low or too high than the Standard deviation
        GUI.tooHighQ1.setText("•  Waiting times that can be taken as too high :  ");
        GUI.tooLowQ1.setText("•  Waiting times that can be taken as too low :  ");
        for (PassengerStatistics passenger : tableViewListQueue1) {
            if (passenger.getSecondsInQueue() > (meanWaitingTimeInQueue1 + standardDeviation1)) {
                GUI.tooHighQ1.setText(GUI.tooHighQ1.getText() + "\n       ↑  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
            if (passenger.getSecondsInQueue() < (meanWaitingTimeInQueue1 - standardDeviation1)) {
                GUI.tooLowQ1.setText(GUI.tooLowQ1.getText() + "\n       ↓  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
        }
        // -------------------------------------------------------------//
        //for queue 2 of 2
        GUI.numOfPassengersQ2.setText("•  Number of Passengers in Simulation for Queue (2 of 2) :   " + tableViewListQueue2.size());

        //Calculating mean waiting time of queue (1 of 2)
        double meanWaitingTimeInQueue2 = Math.round(passengerQueue2.getAverageWaitingTime() * 100.0) / 100.0;
        GUI.meanWaitQ2.setText("•  Mean wait time in queue (2 of 2) :   " + df.format(meanWaitingTimeInQueue2) + "s");

        int numOfPassengersWhoWaitMoreThan7Seconds2 = 0;
        int waitingTimeOfThoseWhoWait2 = 0;
        int numOfPassengersWhoWait2 = 0;
        double sumOfDifferencesInMeanSquared2 = 0;
        for (PassengerStatistics passenger : tableViewListQueue2) {
            sumOfDifferencesInMeanSquared2 = sumOfDifferencesInMeanSquared2 + Math.pow((passenger.getSecondsInQueue() - meanWaitingTimeInQueue2), 2);
            if (passenger.waitsMoreThan7Seconds()) {
                numOfPassengersWhoWaitMoreThan7Seconds2++;
            }
            if (passenger.isWaiting()) {
                waitingTimeOfThoseWhoWait2 = waitingTimeOfThoseWhoWait2 + passenger.getSecondsInQueue();
                numOfPassengersWhoWait2++;
            }

        }
        //Calculating probability of passengers who waits more than 7 seconds (Which is half of the maximum waiting time)
        double probabilityThatAPassengerWaitsMoreThan7Seconds2 = (double) numOfPassengersWhoWaitMoreThan7Seconds2 / passengerQueue2.getQueueArray().size();
        GUI.probWaitPassengerQ2.setText("•  Probability that a passenger that waits more than 7 s :   " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds2));

        //Calculating the average waiting time of passengers who are not in the position 1
        double averageWaitingTimeOfThoseWhoWait2 = (double) waitingTimeOfThoseWhoWait2 / numOfPassengersWhoWait2;
        GUI.averageWaitingQ2.setText("•  Average waiting time of those who wait :   " + df.format(averageWaitingTimeOfThoseWhoWait2) + "s");

        //Calculating the variance of queue (2 of 2)
        double variance2 = sumOfDifferencesInMeanSquared2 / passengerQueue2.getQueueArray().size();
        GUI.varianceQ2.setText("•  Variance of (2 of 2) Queue :   " + df.format(variance2));

        //Calculating the standard variance of queue (2 of 2)
        double standardDeviation2 = Math.pow(variance2, 0.5);
        GUI.standardDQ2.setText("•  Standard Deviation of (2 of 2) queue :   " + df.format(standardDeviation2));

        //Selecting passengers of the who are too low or too high than the Standard deviation
        GUI.tooHighQ2.setText("•  Waiting times that can be taken as too high :   ");
        GUI.tooLowQ2.setText("•  Waiting times that can be taken as too low :   ");
        for (PassengerStatistics passenger : tableViewListQueue2) {
            if (passenger.getSecondsInQueue() > (meanWaitingTimeInQueue2 + standardDeviation2)) {
                GUI.tooHighQ2.setText(GUI.tooHighQ2.getText() + "\n       ↑  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
            if (passenger.getSecondsInQueue() < (meanWaitingTimeInQueue2 - standardDeviation2)) {
                GUI.tooLowQ2.setText(GUI.tooLowQ2.getText() + "\n       ↓  " + passenger.getFullName() + " (" + passenger.getSecondsInQueue() + "s )");
            }
        }

        // -------------------------------------------------------------//
        //Prepare XYChart.Series data objects by setting data according to the calculations
        GUI.series1 = new XYChart.Series<>();
        GUI.series1.setName("Single Queue");
        GUI.series1.getData().add(new XYChart.Data<>("No. of Passengers", information.size()));
        GUI.series1.getData().add(new XYChart.Data<>("Mean Wait", meanWaitingTimeInQueue));
        GUI.series1.getData().add(new XYChart.Data<>("Variance", variance));
        GUI.series1.getData().add(new XYChart.Data<>("Standard Deviation", standardDeviation));


        GUI.series2 = new XYChart.Series<>();
        GUI.series2.setName("Queue (1 of 2)");
        GUI.series2.getData().add(new XYChart.Data<>("No. of Passengers", tableViewListQueue1.size()));
        GUI.series2.getData().add(new XYChart.Data<>("Mean Wait", meanWaitingTimeInQueue1));
        GUI.series2.getData().add(new XYChart.Data<>("Variance", variance1));
        GUI.series2.getData().add(new XYChart.Data<>("Standard Deviation", standardDeviation1));


        GUI.series3 = new XYChart.Series<>();
        GUI.series3.setName("Queue (2 of 2)");
        GUI.series3.getData().add(new XYChart.Data<>("No. of Passengers", tableViewListQueue2.size()));
        GUI.series3.getData().add(new XYChart.Data<>("Mean Wait", meanWaitingTimeInQueue2));
        GUI.series3.getData().add(new XYChart.Data<>("Variance", variance2));
        GUI.series3.getData().add(new XYChart.Data<>("Standard Deviation", standardDeviation2));

        GUI.barChartStats.getData().clear();
        GUI.barChartStats.getData().addAll(GUI.series1, GUI.series2, GUI.series3);

        //------------------------------------------------------------------------------//

        //Creating or opening the txt file
        File file = new File("SimulationSummary.txt"); //File object to represent a file in the hard disk
        PrintWriter pw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            pw = new PrintWriter(fw, true);
            //Writing the report summary into file
            pw.println("Simulation Summary Report");
            pw.println("\nFor a single Queue\n");
            pw.println("•  Number of Passengers in Simulation : " + information.size() +
                    "\n•  Mean wait time using only one Queue : " + df.format(meanWaitingTimeInQueue) + "s" +
                    "\n•  Probability that a passenger that waits more than 7 s : " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds) +
                    "\n•  Average waiting time of those who wait : " + df.format(averageWaitingTimeOfThoseWhoWait) + "s" +
                    "\n•  Variance : " + df.format(variance) +
                    "\n•  Standard Deviation : " + standardDeviation + "\n" +
                    GUI.tooHighQ.getText() + "\n" +
                    GUI.tooLowQ.getText()
            );
            pw.println("\nFor two Queues (1 of 2)\n");
            pw.println("•  Number of Passengers in Simulation : " + tableViewListQueue1.size() +
                    "\n•  Mean wait time using only one Queue : " + df.format(meanWaitingTimeInQueue1) + "s" +
                    "\n•  Probability that a passenger that waits more than 7 s : " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds1) +
                    "\n•  Average waiting time of those who wait : " + df.format(averageWaitingTimeOfThoseWhoWait1) +
                    "\n•  Variance : " + df.format(variance1) +
                    "\n•  Standard Deviation : " + df.format(standardDeviation1) + "\n" +
                    GUI.tooHighQ1.getText() + "\n" +
                    GUI.tooLowQ1.getText()
            );
            pw.println("\nFor two Queues (2 of 2)\n");
            pw.println("•  Number of Passengers in Simulation : " + tableViewListQueue2.size() +
                    "\n•  Mean wait time using only one Queue : " + df.format(meanWaitingTimeInQueue2) + "s" +
                    "\n•  Probability that a passenger that waits more than 7 s : " + df.format(probabilityThatAPassengerWaitsMoreThan7Seconds2) +
                    "\n•  Average waiting time of those who wait : " + df.format(averageWaitingTimeOfThoseWhoWait2) + "s" +
                    "\n•  Variance : " + df.format(variance2) +
                    "\n•  Standard Deviation : " + df.format(standardDeviation2) + "\n" +
                    GUI.tooHighQ2.getText() + "\n" +
                    GUI.tooLowQ2.getText()
            );

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("No permission to write the file");
        } finally {
            try {
                assert fw != null;
                fw.close();
                assert pw != null;
                pw.close();
            } catch (IOException e) {
                System.out.println("Something went wrong when closing the file");
            }
        }
    }

    /**
     * This method is used to update tables in the user interface every time the method is called.
     */
    public static void updateTables() {
        //Adding items to the table views in view scene
        ObservableList<Passenger> tableViewListTQ = FXCollections.observableArrayList();
        GUI.trainQueueTable.getItems().clear();
        tableViewListTQ.addAll(TrainStation.getTrainQueue().getQueueArray());

        GUI.waitingRoomTable.getItems().clear();
        for (Passenger passenger : TrainStation.getWaitingRoom()) {
            if (!passenger.getFullName().equals("")) {
                GUI.waitingRoomTable.getItems().add(passenger);
            }
        }

        GUI.trainQueueTable.getItems().addAll(tableViewListTQ);
    }

    /**
     * This method is used to load the train queue from file and also update the scenes accordingly.
     */
    public static void load() {
        Database.loadTrainQueue();

        //Resetting all the GUI components and waiting room
        GUI.trainQueueList.clear();
        GUI.waitingRoomList.clear();
        GUI.trainQueueLV.getItems().clear();
        GUI.waitingRoomLV.getItems().clear();
        TrainStation.setWaitingRoom(Database.loadProgramToWaitingRoom());
        for (int i = 0; i < TrainStation.getWaitingRoom().length; i++) {
            if (!TrainStation.getWaitingRoom()[i].getFullName().equals("")) {
                GUI.waitingRoomList.add(TrainStation.getWaitingRoom()[i].getFullName() + "      (" + TrainStation.getWaitingRoom()[i].getSeatNum() + ")");
            }
        }
        GUI.waitingRoomLV.getItems().addAll(GUI.waitingRoomList);

        //Updating the seats in the view scene and the add scene according to updated train queue
        for (int i = 0; i < TrainStation.getTrainQueue().getQueueArray().size(); i++) {
            for (int j = 0; j < TrainStation.getWaitingRoom().length; j++) {
                for (Label slot : GUI.slotLabels) {
                    if (slot.getText().equals(TrainStation.getWaitingRoom()[j].getSeatNum())) {
                        if (!TrainStation.getWaitingRoom()[j].getSeatNum().equals("")) {
                            slot.setStyle("-fx-graphic: url(SeatGreen.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                        } else {
                            slot.setStyle("-fx-graphic: url(SeatWhite.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                        }
                        Tooltip slotTooltip = new Tooltip("Empty");
                        slotTooltip.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 20px;");
                        slot.setTooltip(slotTooltip);
                    }

                }
                if (TrainStation.getTrainQueue().getQueueArray().get(i).getSeatNum().equals(TrainStation.getWaitingRoom()[j].getSeatNum())) {
                    for (Label slot : GUI.slotLabels) {
                        if (slot.getText().equals(TrainStation.getWaitingRoom()[j].getSeatNum())) {
                            slot.setStyle("-fx-graphic: url(SeatBlue.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                            Tooltip slotTooltip = new Tooltip("Passenger Name : " + TrainStation.getWaitingRoom()[j].getFullName() +
                                    "\nPassenger NIC :  " + TrainStation.getWaitingRoom()[j].getNationalIDNumber());
                            slotTooltip.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 20px;");
                            slot.setTooltip(slotTooltip);
                        }

                    }
                    TrainStation.getWaitingRoom()[j].setFullName("");
                    TrainStation.getWaitingRoom()[j].setSeatNum("");
                    TrainStation.getWaitingRoom()[j].setNationalIdNumber("");
                    GUI.trainQueueList.add(TrainStation.getTrainQueue().getQueueArray().get(i).getFullName() + "      (" + TrainStation.getTrainQueue().getQueueArray().get(i).getSeatNum() + ")");
                    GUI.trainQueueLV.getItems().add(TrainStation.getTrainQueue().getQueueArray().get(i).getFullName() + "      (" + TrainStation.getTrainQueue().getQueueArray().get(i).getSeatNum() + ")");
                    GUI.waitingRoomLV.getItems().remove(TrainStation.getTrainQueue().getQueueArray().get(i).getFullName() + "      (" + TrainStation.getTrainQueue().getQueueArray().get(i).getSeatNum() + ")");
                    GUI.waitingRoomList.remove(TrainStation.getTrainQueue().getQueueArray().get(i).getFullName() + "      (" + TrainStation.getTrainQueue().getQueueArray().get(i).getSeatNum() + ")");


                }
            }
        }
    }

    /**
     * This method is used to add the removed passenger back to the waiting room.
     *
     * @param passenger the passenger to be added to the waiting room
     */
    public static void addRemovedPassengerToWaitingRoom(Passenger passenger) {
        for (int i = 0; i < TrainStation.getWaitingRoom().length; i++) {
            if (TrainStation.getWaitingRoom()[i].getFullName().equals("")) { //Finding a free position in the waiting room array
                //Updating the position with the details
                TrainStation.getWaitingRoom()[i].setFullName(passenger.getFullName());
                TrainStation.getWaitingRoom()[i].setNationalIdNumber(passenger.getNationalIDNumber());
                TrainStation.getWaitingRoom()[i].setSeatNum(passenger.getSeatNum());
                break;
            }

        }
    }

    /**
     * This method is used to delete a passenger by the searching
     * for the name given by the user in the train queue.
     *
     * @param passengerName the name to be deleted in the train queue
     */
    public static void removingPassengerFromTrainQueue(String passengerName) {
        boolean isPresent = false; //Initializing a boolean variable
        for (Passenger passenger : TrainStation.getTrainQueue().getQueueArray()) { //Looping inside the Queue Array
            if (passenger.getFullName().equals(passengerName)) { //When the passenger name is equal to the name entered by user
                isPresent = true; //Boolean value is set to true when passenger is present in the train queue
                Passenger removedPassenger = TrainStation.getTrainQueue().removePassenger(passengerName); //Removing the selected passenger from the train queue
                if (removedPassenger != null) { //If only the passenger is not null the following code block is executed
                    addRemovedPassengerToWaitingRoom(removedPassenger); //The removed passenger is then added to the waiting room
                    //Updating the list views of the add passenger page
                    GUI.waitingRoomList.add(passenger.getFullName() + "      (" + passenger.getSeatNum() + ")");
                    GUI.trainQueueList.remove(passenger.getFullName() + "      (" + passenger.getSeatNum() + ")");
                    GUI.trainQueueLV.getItems().remove(passenger.getFullName() + "      (" + passenger.getSeatNum() + ")");
                    GUI.waitingRoomLV.getItems().add(passenger.getFullName() + "      (" + passenger.getSeatNum() + ")");
                    //Updating the view scene according to the passenger deleted
                    for (Label slot : GUI.slotLabels) {
                        if (slot.getText().equalsIgnoreCase(removedPassenger.getSeatNum())) {
                            slot.setStyle("-fx-graphic: url(SeatGreen.png)");
                            slot.setTooltip(null);
                        }
                    }
                    System.out.println("Passenger " + removedPassenger.getFullName() + " in seat " + removedPassenger.getSeatNum() + " was successfully removed and added to the waiting room!");
                    break;
                }
            }
        }
        //After a deletion the list view of the waiting room is then ordered according to the seat num
        for (int i = 0; i < GUI.waitingRoomList.size(); i++) {
            String details = GUI.waitingRoomList.get(i).substring(GUI.waitingRoomList.get(i).length() - 4);
            for (int j = i + 1; j < GUI.waitingRoomList.size(); j++) {
                String details1 = GUI.waitingRoomList.get(j).substring(GUI.waitingRoomList.get(j).length() - 4);
                if (details.compareTo(details1) > 0) {
                    String temp = GUI.waitingRoomList.get(i);
                    GUI.waitingRoomList.set(i, GUI.waitingRoomList.get(j));
                    GUI.waitingRoomList.set(j, temp);
                }
            }
        }
        //Updating the ordered list view to the scene
        GUI.waitingRoomLV.getItems().clear();
        GUI.waitingRoomLV.getItems().addAll(GUI.waitingRoomList);

        //If the user entered customer name is not present in the train queue the following is printed
        if (!isPresent) {
            System.out.println("Passenger not found in train queue.");
        }
    }

    /**
     * This method is used to randomly generate the number of passengers
     * to be added next to the train queue with using 1 six sided dices.
     *
     * @param queueLength the exist length of the waiting room
     * @return int the value of the dice
     */
    public static int numOfPassengersAdded(int queueLength) {
        Random rand = new Random(); //Initializing Random for generating a random number
        int numOfPassengers; //Initializing int variable for setting of the output value
        if (queueLength > 5) { //If the waiting room has more than 5 passengers
            numOfPassengers = rand.nextInt(6) + 1; //A value between 1 - 6 is generated
        } else if (queueLength == 0) { //If waiting room has 0 passengers the value is set to 0
            numOfPassengers = 0;
        } else { //If the waiting room has passengers between 5 and 2
            numOfPassengers = rand.nextInt(queueLength) + 1;
        }

        return numOfPassengers; // returns the updated variable
    }
}