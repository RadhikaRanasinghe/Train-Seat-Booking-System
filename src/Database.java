/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all the methods related to the database (NoSQl).
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class Database {
    private static boolean isMongoDBLaunched = true;

    /**
     * This method is used to clarify if MongoDB is launched.
     * If it is not launched, the method launches MongoDB.
     */
    private static void launchingMongoDB() {
        if (isMongoDBLaunched) {
            System.out.println("\uD835\uDE13\uD835\uDE22\uD835\uDE36\uD835\uDE2F\uD835\uDE24\uD835\uDE29\uD835\uDE2A\uD835\uDE2F\uD835\uDE28 \uD835\uDE14\uD835\uDE30\uD835\uDE2F\uD835\uDE28\uD835\uDE30\uD835\uDE0B\uD835\uDE09....");
            try {
                Runtime.getRuntime().exec("C:\\Program Files\\MongoDB\\Server\\4.2\\bin\\mongod.exe");
                //Runtime.getRuntime().exec("C:\\Program Files\\MongoDB\\Server\\4.2\\bin\\mongo.exe");
            } catch (Throwable e) {
                System.out.println("Something went wrong. Please run MongoDB manually and restart the program.");
                e.printStackTrace();
            }
            isMongoDBLaunched = false;
        }
    }

    /**
     * This method is used to load the passengers to the waiting room who has already booked seats.
     *
     * @return An array of the passengers who have booked seats
     */
    public static Passenger[] loadProgramToWaitingRoom() {
        launchingMongoDB();
        Passenger[] passengerArray = new Passenger[42];

        for (int i = 0; i < 42; i++) {
            passengerArray[i] = new Passenger("", "", "");
        }

        //Disabling the console logs used by MongoDB
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        //Connecting to MongoDB
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //If exists, extracting the Database and the collections inside or creation of a new database and collection
        MongoDatabase customerDatabase = mongoClient.getDatabase("TrainSeating");
        MongoCollection<Document> collection = customerDatabase.getCollection("PassengersDetails");

        //Collecting all existing documents to loop through
        FindIterable<Document> findDocument = collection.find();

        //Checking if the collection has existing documents and updating the collection with the new set of documents
        if (collection.countDocuments() == 0) {
            System.out.println("No customers have reserved any seats in the train");
        } else if (collection.countDocuments() >= 1) {
            for (Document customer : findDocument) {
                if (customer.getInteger("index1") == 0 && customer.getInteger("index2") == 0) {
                    passengerArray[customer.getInteger("index3")].setFullName(customer.getString("Name"));
                    passengerArray[customer.getInteger("index3")].setNationalIdNumber(customer.getString("NIC"));
                    passengerArray[customer.getInteger("index3")].setSeatNum(customer.getString("Seat Number"));
                }
            }
        }
        System.out.println("Loaded the passengers to the waiting room successfully!");
        return passengerArray;
    }

    /**
     * This method is used to load the train queue from the database to the train queue.
     */
    public static void loadTrainQueue() {
        launchingMongoDB();

        //Disabling the console logs used by MongoDB
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        //Connecting to MongoDB
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //If exists, extracting the Database and the collections inside or creation of a new database and collection
        MongoDatabase customerDatabase = mongoClient.getDatabase("TrainQueue");
        MongoCollection<Document> collection = customerDatabase.getCollection("PassengersDetails");

        //Collecting all existing documents to loop through
        FindIterable<Document> findDocument = collection.find();
        //Checking if the collection has existing documents and updating the collection with the new set of documents
        if (collection.countDocuments() == 0) {
            System.out.println("Data are not stored in database.");
        } else if (collection.countDocuments() > 1) {
            int index = 0;
            TrainStation.getTrainQueue().getQueueArray().clear();
            for (Document passenger : findDocument) {
                TrainStation.getTrainQueue().addPassenger(new Passenger(passenger.getString("Name"), (passenger.getString("Seat Number")), (passenger.getString("NIC"))));
                TrainStation.getTrainQueue().getQueueArray().get(index).setSecondsInQueueFromFile(passenger.getInteger("Seconds in queue"));
                index++;

            }
        }
        mongoClient.close();

        System.out.println("Loaded the previous saved train queue successfully!");

    }

    /**
     * This method is used to save the train queue to the database.
     */
    public static void storeTrainQueue() {
        launchingMongoDB();

        System.out.println("trainQueue : " + TrainStation.getTrainQueue().getQueueArray());

        //Disabling the console logs used by MongoDB
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        //Connecting to MongoDB
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //If exists, extracting the Database and the collections inside or creation of a new database and collection
        MongoDatabase customerDatabase = mongoClient.getDatabase("TrainQueue");
        MongoCollection<Document> collection = customerDatabase.getCollection("PassengersDetails");

        //Collecting all existing documents to loop through
        FindIterable<Document> findDocument = collection.find();

        //Check if there are existing documents and deleting before saving new files so that there won't be any duplicates
        if (collection.countDocuments() > 0) {
            for (Document document : findDocument) {
                collection.deleteOne(document);
            }
        }
        for (int passenger = 0; passenger < TrainStation.getTrainQueue().getQueueArray().size(); passenger++) {
            Document passengerDocument = new Document();
            passengerDocument.append("Name", TrainStation.getTrainQueue().getQueueArray().get(passenger).getFullName())
                    .append("NIC", TrainStation.getTrainQueue().getQueueArray().get(passenger).getNationalIDNumber())
                    .append("Seat Number", TrainStation.getTrainQueue().getQueueArray().get(passenger).getSeatNum())
                    .append("Seconds in queue", TrainStation.getTrainQueue().getQueueArray().get(passenger).getSecondsInQueue());

            collection.insertOne(passengerDocument);
        }

        System.out.println("Saved the details in the database successfully!");


    }
}
