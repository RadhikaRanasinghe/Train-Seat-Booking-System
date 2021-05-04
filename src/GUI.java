/*
I declare that my work contains no examples of misconduct, such as plagiarism, or collusion.
Any code taken from other sources is referenced within my code solution.
Student ID: w1761764 (2018199)
Date: 22nd of April 2020
*/
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Represents all the GUI components of the program.
 *
 * @author Radhika Ranasinghe
 * @version 1.0
 */
public class GUI extends Application {

    public static Stage window;
    public static Scene viewScene;
    public static Scene addScene;
    public static Scene reportScene;
    public static ObservableList<String> waitingRoomList;
    public static ObservableList<String> trainQueueList;
    public static TableView<Passenger> trainQueueTable;
    public static TableView<Passenger> waitingRoomTable;
    public static TableView<PassengerStatistics> informationTable;
    public static TableView<PassengerStatistics> passengerDetailsQ1;
    public static TableView<PassengerStatistics> passengerDetailsQ2;
    public static XYChart.Series<String, Number> series1;
    public static XYChart.Series<String, Number> series2;
    public static XYChart.Series<String, Number> series3;
    public static ListView<String> trainQueueLV;
    public static ListView<String> waitingRoomLV;
    public static Label[] slotLabels;
    public static Label numOfPassengersQ;
    public static Label meanWaitQ;
    public static Label probWaitPassengerQ;
    public static Label averageWaitingQ;
    public static Label varianceQ;
    public static Label standardDQ;
    public static Label tooHighQ;
    public static Label tooLowQ;
    public static Label minPQ;
    public static Label maxPQ;
    public static Label maxQ;
    public static Label minQ;
    public static Label minPQ1;
    public static Label maxPQ1;
    public static Label maxQ1;
    public static Label minQ1;
    public static Label minPQ2;
    public static Label maxPQ2;
    public static Label maxQ2;
    public static Label minQ2;
    public static Label numOfPassengersQ1;
    public static Label meanWaitQ1;
    public static Label probWaitPassengerQ1;
    public static Label averageWaitingQ1;
    public static Label varianceQ1;
    public static Label standardDQ1;
    public static Label tooHighQ1;
    public static Label tooLowQ1;
    public static Label numOfPassengersQ2;
    public static Label meanWaitQ2;
    public static Label probWaitPassengerQ2;
    public static Label averageWaitingQ2;
    public static Label varianceQ2;
    public static Label standardDQ2;
    public static Label tooHighQ2;
    public static Label tooLowQ2;
    public static BarChart barChartStats;

    int numPassenger;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Setting stage properties
        window = primaryStage;
        window.setHeight(900);
        window.setWidth(1330);
        window.setResizable(false);
        window.setTitle("Boarding Program for Denuwara Menike");

        //Initialization of the list views in the 'A' scene
        waitingRoomList = FXCollections.observableArrayList();
        trainQueueList = FXCollections.observableArrayList();
        waitingRoomLV = new ListView<>();
        trainQueueLV = new ListView<>();

        //Setting selection mode to multiple to add multiple customers at a time
        waitingRoomLV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Setting a fixed width and style to the list views
        waitingRoomLV.setStyle("-fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-text-alignment: center;");
        waitingRoomLV.setPrefWidth(400);

        trainQueueLV.setStyle("-fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-text-alignment: center;");
        trainQueueLV.setPrefWidth(400);

        //Adding the previously saved customers in the waiting room to the waiting room list view
        for (int i = 0; i < TrainStation.getWaitingRoom().length; i++) {
            if (!TrainStation.getWaitingRoom()[i].getFullName().equals("")) {
                waitingRoomList.add(TrainStation.getWaitingRoom()[i].getFullName() + "      (" + TrainStation.getWaitingRoom()[i].getSeatNum() + ")");
            }
        }

        //Initializing and setting style and tooltip for the 'Add customers' button
        Button addPassengerButton = new Button("+");
        addPassengerButton.setStyle("-fx-text-fill: white; -fx-background-color: #029399; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 37px; " +
                "-fx-text-alignment: center; -fx-background-radius: 5em; -fx-min-width: 70px;-fx-min-height: 70px;" +
                " -fx-max-width: 70px;-fx-max-height: 70px;");
        Tooltip addPassengerToolTip = new Tooltip("Add the selected passengers");
        addPassengerToolTip.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; " +
                "-fx-text-alignment: center;");
        addPassengerButton.setTooltip(addPassengerToolTip);

        //Calling the randomized method of getting number of customer to add to list
        numPassenger = Methods.numOfPassengersAdded(waitingRoomList.size());

        //Initializing and setting properties of the No.of Passengers to be added label
        Label numOfPassengersLabel = new Label("No. of Passengers to be added:   " + numPassenger);
        numOfPassengersLabel.setPadding(new Insets(15));
        numOfPassengersLabel.setPrefWidth(300);
        numOfPassengersLabel.setAlignment(Pos.CENTER);
        numOfPassengersLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing and setting properties of the No.of Passengers selected label
        Label numberSelectedLabel = new Label("No. of Passengers Selected :     " + 0);
        numberSelectedLabel.setPadding(new Insets(15));
        numberSelectedLabel.setPrefWidth(300);
        numberSelectedLabel.setAlignment(Pos.CENTER);
        numberSelectedLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Setting a listener to dynamically change with the multiple selections
        waitingRoomLV.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            int numberOfSelections = waitingRoomLV.getSelectionModel().getSelectedItems().size();
            numberSelectedLabel.setText("No. of Passengers Selected :     " + numberOfSelections);
        });

        //Initializing and setting properties of the seat labels in 'V' scene
        slotLabels = new Label[42];
        for (int label = 0; label < slotLabels.length; label++) {
            slotLabels[label] = new Label();
            slotLabels[label].setPrefHeight(90);
            slotLabels[label].setPrefWidth(90);
            slotLabels[label].setContentDisplay(ContentDisplay.TOP);

        }

        //lambda function of the add passengers button
        addPassengerButton.setOnAction(event -> {
            if (!TrainStation.getTrainQueue().isFull()) { //Checking if the train queue if full before adding
                if (!waitingRoomList.isEmpty()) {//Checking if the waiting room if empty before adding
                    //Adding the selected passenger in the list view to an observable list
                    ObservableList<String> selectedPassengers = waitingRoomLV.getSelectionModel().getSelectedItems();
                    if (selectedPassengers.size() == numPassenger) { // If the selected number is correct the following code block is executed
                        for (String selectedPassenger : selectedPassengers) { //Looping within the selectedPassengers list
                            //Adding to trainQueue list and list view
                            trainQueueLV.getItems().add(selectedPassenger);
                            trainQueueList.add(selectedPassenger);

                            //Regex to break the string
                            Pattern p = Pattern.compile(" {6}");
                            String[] details = p.split(selectedPassenger);

                            //Looping through the waiting room
                            for (int i = 0; i < TrainStation.getWaitingRoom().length; i++) {
                                if (details[0].equals(TrainStation.getWaitingRoom()[i].getFullName())) {
                                    Passenger passenger = TrainStation.getWaitingRoom()[i];
                                    //Adding the selected passenger to the train queue
                                    TrainStation.getTrainQueue().addPassenger(new Passenger(passenger.getFullName(), passenger.getSeatNum(), passenger.getNationalIDNumber()));

                                    //Updating the seat slots in the view scene accordingly
                                    for (Label slot : slotLabels) {
                                        if (slot.getText().equals(passenger.getSeatNum())) {
                                            slot.setStyle("-fx-graphic: url(SeatBlue.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                                            Tooltip slotTooltip = new Tooltip("Passenger Name :   " + passenger.getFullName() + "\nPassenger NIC :  " + passenger.getNationalIDNumber());
                                            slotTooltip.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 17px;");
                                            slot.setTooltip(slotTooltip);
                                        }
                                    }
                                    //Removing passenger from the waiting room
                                    TrainStation.getWaitingRoom()[i].setSeatNum("");
                                    TrainStation.getWaitingRoom()[i].setFullName("");
                                    TrainStation.getWaitingRoom()[i].setNationalIdNumber("");
                                }

                            }
                        }
                        //Removing passenger passenger from the waiting room list view
                        for (int i = 0; i < numPassenger; i++) {
                            waitingRoomList.remove(selectedPassengers.get(i));
                        }
                        waitingRoomLV.getItems().clear();
                        waitingRoomLV.getItems().addAll(waitingRoomList);

                        //Regenerating the number of passengers t be added next
                        numPassenger = Methods.numOfPassengersAdded(waitingRoomList.size());
                        numOfPassengersLabel.setText("No. of Passengers to be added:   " + numPassenger);
                    } else {
                        //Pops up when the selected number of passengers is incorrect
                        Alert wrongSelection = new Alert(Alert.AlertType.WARNING);
                        wrongSelection.setHeaderText(null);
                        wrongSelection.setContentText("You have not selected the correct number of passengers!");
                        wrongSelection.showAndWait();
                    }
                } else {
                    //Pops up when the waiting room becomes empty
                    Alert waitingRoomIsEmpty = new Alert(Alert.AlertType.WARNING);
                    waitingRoomIsEmpty.setHeaderText(null);
                    waitingRoomIsEmpty.setContentText("There are no customers in the waiting room to be added!");
                    waitingRoomIsEmpty.showAndWait();

                }
            } else {
                //Pops up when the train queue becomes full
                Alert trainQueueIsFull = new Alert(Alert.AlertType.WARNING);
                trainQueueIsFull.setHeaderText(null);
                trainQueueIsFull.setContentText("Train Queue is full!");
                trainQueueIsFull.showAndWait();
            }

        });

        //Adding all elements to the list views after updating
        waitingRoomLV.getItems().addAll(waitingRoomList);
        trainQueueLV.getItems().addAll(trainQueueList);

        //Headers of the adding scene and properties
        Label headerALabel = new Label("A D D I N G   P A S S E N G E R S   T O   T R A I N   Q U E U E . . . ");
        headerALabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 30px;");
        headerALabel.setPadding(new Insets(60, 10, 0, 10));

        Label trainQLabel = new Label("Train Queue :");
        trainQLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center;");

        Label waitingRLabel = new Label("Waiting Room :");
        waitingRLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center;");

        //Initializing the grid pane containing the list views
        GridPane gridPaneLV = new GridPane();
        gridPaneLV.setVgap(15);
        gridPaneLV.setHgap(60);
        gridPaneLV.setAlignment(Pos.CENTER);

        GridPane.setConstraints(waitingRoomLV, 2, 2, 2, 1);
        GridPane.setConstraints(trainQueueLV, 0, 2, 2, 1);
        GridPane.setConstraints(waitingRLabel, 2, 1, 2, 1);
        GridPane.setConstraints(trainQLabel, 0, 1, 2, 1);
        gridPaneLV.getChildren().addAll(waitingRoomLV, trainQueueLV, waitingRLabel, trainQLabel);

        //Initializing and setting properties of the back to menu button in add scene
        Button backConsoleButton = new Button(" C O N S O L E ");
        backConsoleButton.setPrefWidth(300);
        backConsoleButton.setPadding(new Insets(10));
        backConsoleButton.setOnAction(event -> {
            window.setIconified(true);
            try {
                Methods.inputMenu();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        backConsoleButton.setStyle("-fx-graphic: url(Menu_Green.png);-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ;");
        backConsoleButton.setPadding(new Insets(10));
        backConsoleButton.setAlignment(Pos.CENTER);

        Label label = new Label();
        label.setPrefWidth(30);

        Label label1 = new Label();
        label1.setPrefWidth(700);
        label1.setStyle("-fx-background-color: #006064;");

        //Setting constraints of the grid pane of main add scene grid
        GridPane.setConstraints(label, 7, 6);
        GridPane.setConstraints(label1, 3, 20);
        GridPane.setConstraints(headerALabel, 1, 0, 5, 1);
        GridPane.setConstraints(gridPaneLV, 1, 2, 4, 10);
        GridPane.setConstraints(addPassengerButton, 4, 12, 1, 1);
        GridPane.setConstraints(numOfPassengersLabel, 5, 6, 2, 1);
        GridPane.setConstraints(numberSelectedLabel, 5, 7, 2, 1);
        GridPane.setConstraints(backConsoleButton, 5, 8, 2, 1);

        //Initializing and setting properties of the main grid of the add scene
        GridPane mainAdd = new GridPane();
        mainAdd.setAlignment(Pos.CENTER);
        mainAdd.setHgap(30);
        mainAdd.setVgap(30);
        mainAdd.getChildren().addAll(headerALabel, gridPaneLV, addPassengerButton, label, label1, numOfPassengersLabel, numberSelectedLabel, backConsoleButton);

        mainAdd.setStyle("-fx-background-color: #CFD8DC;");

        VBox vBox1 = new VBox();
        vBox1.setPrefWidth(30);
        vBox1.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2 = new VBox();
        vBox2.setPrefWidth(30);
        vBox2.setStyle("-fx-background-color: #006064;");

        HBox mainHBox = new HBox();
        mainHBox.getChildren().addAll(vBox2, vBox1, mainAdd);

        addScene = new Scene(mainHBox, 1400, 900);


        //----------------------------V I E W  S C E N E ----------------------------//

        //Grid pane of the seat slots in view scene initializing and setting properties
        GridPane slotsGrid = new GridPane();
        slotsGrid.setPadding(new Insets(10));
        slotsGrid.setVgap(10);
        slotsGrid.setHgap(10);

        //Initializing header of the view scene and setting properties
        Label headerVLabel = new Label("V I E W I N G   P A S S E N G E R S   I N   T R A I N   Q U E U E . . . ");
        headerVLabel.setPadding(new Insets(10));
        headerVLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 24px;");
        GridPane.setConstraints(headerVLabel, 0, 0, 2, 1);

        //Initializing the letters array for seat numbers
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G"};
        //Initializing the grid pane array for the seat slots
        GridPane[] gridArray = {new GridPane(), new GridPane()};

        //Looping in the letters array to set button text
        for (int letter = 0; letter < letters.length; letter++) {
            for (int number = 1; number < 7; number++) {
                int elementNum = (letter * 6) + number - 1;
                String seatNum = letters[letter] + number;
                slotLabels[elementNum].setText(seatNum);
                if ((TrainStation.getTrainQueue().getMaxQueueLength() == 0)) {
                    if (!TrainStation.getWaitingRoom()[elementNum].getFullName().equals("")) {
                        slotLabels[elementNum].setStyle("-fx-graphic: url(SeatGreen.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                    } else {
                        slotLabels[elementNum].setStyle("-fx-graphic: url(SeatWhite.png); -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-background-color: #CAD3D7;");
                    }
                    slotLabels[elementNum].setAlignment(Pos.CENTER);
                    Tooltip tooltip = new Tooltip("Empty");
                    slotLabels[elementNum].setTooltip(tooltip);
                    tooltip.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 20px;");
                }
                //Setting the constraints of the seats in the grid pane
                if (number < 4) {
                    GridPane.setConstraints(slotLabels[elementNum], number - 1, letter + 1);
                    gridArray[0].getChildren().add(slotLabels[elementNum]);
                    gridArray[0].setVgap(10);
                    gridArray[0].setHgap(10);
                } else {
                    GridPane.setConstraints(slotLabels[elementNum], number - 4, letter + 1);
                    gridArray[1].getChildren().add(slotLabels[elementNum]);
                    gridArray[1].setVgap(10);
                    gridArray[1].setHgap(10);
                }
            }
        }
        gridArray[0].setPadding(new Insets(20));
        gridArray[1].setPadding(new Insets(20));

        //Initializing and setting properties of the side box of view scene
        GridPane sideBox = new GridPane();
        sideBox.setAlignment(Pos.CENTER);
        sideBox.setVgap(30);

        //Initializing labels in the side labels and setting properties
        Label instLabel = new Label("   H O V E R   F O R   D E T A I L S");
        instLabel.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 16.5px; -fx-text-alignment: center;");
        instLabel.setPadding(new Insets(10));
        instLabel.setPrefWidth(280);
        instLabel.setContentDisplay(ContentDisplay.CENTER);

        Label boardedSeatLabel = new Label("    Boarded Seat");
        boardedSeatLabel.setPadding(new Insets(15));
        boardedSeatLabel.setPrefWidth(280);
        boardedSeatLabel.setStyle("-fx-text-fill: #006064; -fx-graphic: url(SeatBlue.png); -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center; -fx-border-color: #B4B6B9;" +
                "    -fx-border-width: 1 ; ");
        boardedSeatLabel.setContentDisplay(ContentDisplay.LEFT);

        Label emptySeatLabel = new Label("    Not Boarded Seat");
        emptySeatLabel.setPadding(new Insets(15));
        emptySeatLabel.setPrefWidth(280);
        emptySeatLabel.setStyle("-fx-text-fill: #006064; -fx-graphic: url(SeatGreen.png); -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center; -fx-border-color: #B4B6B9;" +
                "    -fx-border-width: 1 ; ");
        emptySeatLabel.setContentDisplay(ContentDisplay.LEFT);

        Label notReservedLabel = new Label("    Not Reserved Seat");
        notReservedLabel.setPadding(new Insets(15));
        notReservedLabel.setPrefWidth(280);
        notReservedLabel.setStyle("-fx-text-fill: #006064; -fx-graphic: url(SeatWhite.png); -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center; -fx-border-color: #B4B6B9;" +
                "    -fx-border-width: 1 ; ");
        notReservedLabel.setContentDisplay(ContentDisplay.LEFT);

        //Initializing menu button in the view scene
        Button backToConsoleV = new Button("Console");
        backToConsoleV.setPadding(new Insets(5));
        backToConsoleV.setPrefWidth(280);
        backToConsoleV.setPrefHeight(40);
        backToConsoleV.setStyle("-fx-text-fill: #006064; -fx-graphic: url(Menu_Green.png); -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center;");
        backToConsoleV.setOnAction(event -> {
            window.setIconified(true);
            try {
                Methods.inputMenu();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        //Setting constraints of the side box grid pane
        GridPane.setConstraints(instLabel, 0, 0);
        GridPane.setConstraints(boardedSeatLabel, 0, 1);
        GridPane.setConstraints(emptySeatLabel, 0, 2);
        GridPane.setConstraints(notReservedLabel, 0, 3);
        GridPane.setConstraints(backToConsoleV, 0, 4);

        //Adding the nodes to the side box grid pane
        sideBox.getChildren().addAll(instLabel, boardedSeatLabel, emptySeatLabel, notReservedLabel, backToConsoleV);

        //Initializing the main grid and setting constraints
        GridPane mainGridPaneV = new GridPane();
        GridPane.setConstraints(gridArray[0], 0, 1);
        GridPane.setConstraints(gridArray[1], 1, 1);
        GridPane.setConstraints(sideBox, 2, 1);

        //Adding the nodes to the main grid pane in the view scene and setting properties
        mainGridPaneV.getChildren().addAll(headerVLabel, gridArray[0], gridArray[1], sideBox);
        mainGridPaneV.setStyle("-fx-background-color: #CFD8DC;");
        mainGridPaneV.setPadding(new Insets(10));
        mainGridPaneV.setAlignment(Pos.CENTER);
        mainGridPaneV.setHgap(30);

        VBox vBox1V = new VBox();
        vBox1V.setPrefWidth(30);
        vBox1V.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2V = new VBox();
        vBox2V.setPrefWidth(30);
        vBox2V.setStyle("-fx-background-color: #006064;");

        VBox vBox3V = new VBox();
        vBox3V.setPrefWidth(120);
        vBox3V.setStyle("-fx-background-color: #CFD8DC;");

        VBox vBox4V = new VBox();
        vBox4V.setPrefWidth(100);
        vBox4V.setStyle("-fx-background-color: #CFD8DC;");

        HBox mainHBoxV = new HBox();
        mainHBoxV.getChildren().addAll(vBox2V, vBox1V, vBox3V, mainGridPaneV, vBox4V);

        //Initializing train queue table in the view scene and setting properties
        trainQueueTable = new TableView<>();
        trainQueueTable.setStyle("-fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 15px; -fx-text-alignment: center;");
        trainQueueTable.setEditable(false);

        //Initializing all table columns and setting the properties for those
        TableColumn<Passenger, String> nameColumn = new TableColumn<>("Full Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumn.setPrefWidth(470);
        nameColumn.setResizable(false);

        TableColumn<Passenger, String> nicColumn = new TableColumn<>("NIC Number");
        nicColumn.setCellValueFactory(new PropertyValueFactory<>("nationalIDNumber"));
        nicColumn.setPrefWidth(450);
        nicColumn.setStyle("-fx-alignment: center;");
        nicColumn.setResizable(false);

        TableColumn<Passenger, String> seatColumn = new TableColumn<>("Seat Number");
        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        seatColumn.setPrefWidth(350);
        seatColumn.setStyle("-fx-alignment: center;");
        seatColumn.setResizable(false);

        //Adding all columns to the train queue table
        trainQueueTable.getColumns().addAll(nameColumn, nicColumn, seatColumn);

        VBox vBox1TQTable = new VBox();
        vBox1TQTable.setPrefWidth(30);
        vBox1TQTable.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2TQTable = new VBox();
        vBox2TQTable.setPrefWidth(30);
        vBox2TQTable.setStyle("-fx-background-color: #006064;");

        HBox mainHBoxTQTable = new HBox();
        mainHBoxTQTable.getChildren().addAll(vBox2TQTable, vBox1TQTable, trainQueueTable);

        //Initializing waiting table in the view scene and setting properties
        waitingRoomTable = new TableView<>();
        waitingRoomTable.setStyle("-fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 15px;");

        //Initializing all table columns and setting the properties for those
        TableColumn<Passenger, String> nameColumnWR = new TableColumn<>("Full Name");
        nameColumnWR.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumnWR.setPrefWidth(470);
        nameColumnWR.setResizable(false);

        TableColumn<Passenger, String> nicColumnWR = new TableColumn<>("NIC Number");
        nicColumnWR.setCellValueFactory(new PropertyValueFactory<>("nationalIDNumber"));
        nicColumnWR.setPrefWidth(450);
        nicColumnWR.setResizable(false);
        nicColumnWR.setStyle("-fx-alignment: center;");

        TableColumn<Passenger, String> seatColumnWR = new TableColumn<>("Seat Number");
        seatColumnWR.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        seatColumnWR.setPrefWidth(350);
        seatColumnWR.setResizable(false);
        seatColumnWR.setStyle("-fx-alignment: center;");

        //Adding all columns to the waiting room table
        waitingRoomTable.getColumns().addAll(nameColumnWR, nicColumnWR, seatColumnWR);

        VBox vBox1WRTable = new VBox();
        vBox1WRTable.setPrefWidth(30);
        vBox1WRTable.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2WRTable = new VBox();
        vBox2WRTable.setPrefWidth(30);
        vBox2WRTable.setStyle("-fx-background-color: #006064;");

        HBox mainHBoxWRTable = new HBox();
        mainHBoxWRTable.getChildren().addAll(vBox2WRTable, vBox1WRTable, waitingRoomTable);

        //Initializing the tab pane and tabs for the view scene
        TabPane tabPaneV = new TabPane();

        //Initializing the tabs for tab pane
        Tab tabSeats = new Tab("View Seats", mainHBoxV);
        Tab tabDetailsTQ = new Tab("Train Queue Passenger Details", mainHBoxTQTable);
        Tab tabDetailsWR = new Tab("Waiting Room Passenger Details", mainHBoxWRTable);

        //Adding the tabs to the tab pane and setting properties
        tabPaneV.getTabs().addAll(tabSeats, tabDetailsTQ, tabDetailsWR);
        tabPaneV.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Initializing the view scene and adding the tab pane to the scene
        viewScene = new Scene(tabPaneV, 1200, 700);


        //------------------------------R E P O R T   S C E N E---------------------------------//

        //Initializing personal statistics table for single queue in the report scene and setting properties
        informationTable = new TableView<>();

        //Initializing all table columns and setting the properties
        TableColumn<PassengerStatistics, String> nameColumnQ = new TableColumn<>("Full Name");
        nameColumnQ.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumnQ.setPrefWidth(250);
        nameColumnQ.setResizable(false);

        TableColumn<PassengerStatistics, String> nicColumnQ = new TableColumn<>("NIC Number");
        nicColumnQ.setCellValueFactory(new PropertyValueFactory<>("nationalIDNumber"));
        nicColumnQ.setPrefWidth(180);
        nicColumnQ.setResizable(false);
        nicColumnQ.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, String> seatColumnQ = new TableColumn<>("Seat Number");
        seatColumnQ.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        seatColumnQ.setPrefWidth(120);
        seatColumnQ.setResizable(false);
        seatColumnQ.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> queueLengthQ = new TableColumn<>("Queue Length");
        queueLengthQ.setCellValueFactory(new PropertyValueFactory<>("queueLength"));
        queueLengthQ.setPrefWidth(120);
        queueLengthQ.setResizable(false);
        queueLengthQ.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> positionQ = new TableColumn<>("Position");
        positionQ.setCellValueFactory(new PropertyValueFactory<>("position"));
        positionQ.setResizable(false);
        positionQ.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> secondsQ = new TableColumn<>("Personal Wait");
        secondsQ.setCellValueFactory(new PropertyValueFactory<>("secondsInQueue"));
        secondsQ.setPrefWidth(120);
        secondsQ.setResizable(false);
        secondsQ.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> secondsInQueueTotalQ = new TableColumn<>("Queue Wait");
        secondsInQueueTotalQ.setCellValueFactory(new PropertyValueFactory<>("waitingTimeInQueue"));
        secondsInQueueTotalQ.setPrefWidth(120);
        secondsInQueueTotalQ.setResizable(false);
        secondsInQueueTotalQ.setStyle("-fx-alignment: center;");

        //Adding all columns to the personal statistics for single queue
        informationTable.getColumns().addAll(nameColumnQ, nicColumnQ, seatColumnQ, queueLengthQ, positionQ, secondsQ, secondsInQueueTotalQ);

        //Initializing label for maximum personal wait and setting properties
        maxPQ = new Label();
        maxPQ.setPadding(new Insets(10));
        maxPQ.setPrefWidth(250);
        maxPQ.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum personal wait and setting properties
        minPQ = new Label();
        minPQ.setPrefWidth(250);
        minPQ.setPadding(new Insets(10));
        minPQ.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for maximum wait in queue and setting properties
        maxQ = new Label();
        maxQ.setPrefWidth(250);
        maxQ.setPadding(new Insets(10));
        maxQ.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum wait in queue and setting properties
        minQ = new Label();
        minQ.setPrefWidth(250);
        minQ.setPadding(new Insets(10));
        minQ.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing the vBox for the stats labels and setting properties
        VBox statsQVBox = new VBox();
        statsQVBox.setPadding(new Insets(200, 10, 10, 10));
        statsQVBox.setSpacing(30);
        statsQVBox.setStyle("-fx-background-color: #CFD8DC;");

        //Adding the labels to the stats vBox
        statsQVBox.getChildren().addAll(maxPQ, minPQ, maxQ, minQ);

        VBox vBox1Table = new VBox();
        vBox1Table.setPrefWidth(30);
        vBox1Table.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2Table = new VBox();
        vBox2Table.setPrefWidth(30);
        vBox2Table.setStyle("-fx-background-color: #006064;");


        HBox tableHBox = new HBox();
        tableHBox.getChildren().addAll(vBox2Table, vBox1Table, informationTable, statsQVBox);

        //----------------------------------------------------//

        //Initializing label for the heading and setting properties
        Label headerQ = new Label("Statistics for a single Queue");
        headerQ.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 30px;");

        //Initializing label for number of passengers and setting properties
        numOfPassengersQ = new Label();
        numOfPassengersQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for mean wait and setting properties
        meanWaitQ = new Label();
        meanWaitQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for probability of waiting and setting properties
        probWaitPassengerQ = new Label();
        probWaitPassengerQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for average wait and setting properties
        averageWaitingQ = new Label();
        averageWaitingQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for variance and setting properties
        varianceQ = new Label();
        varianceQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for standard deviation and setting properties
        standardDQ = new Label();
        standardDQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for higher than standard deviation and setting properties
        tooHighQ = new Label("Waiting times that can be taken as too high :");
        tooHighQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for lower than standard deviation and setting properties
        tooLowQ = new Label("Waiting times that can be taken as too low :");
        tooLowQ.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");


        VBox vBox1Q = new VBox();
        vBox1Q.setPrefWidth(30);
        vBox1Q.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2Q = new VBox();
        vBox2Q.setPrefWidth(30);
        vBox2Q.setStyle("-fx-background-color: #006064;");

        //Initializing a VBox to add the stats labels of single queue implementation
        VBox statsQ = new VBox();
        statsQ.setPadding(new Insets(20));
        statsQ.setSpacing(20);
        statsQ.getChildren().addAll(headerQ, numOfPassengersQ, meanWaitQ, probWaitPassengerQ, averageWaitingQ, varianceQ, standardDQ, tooHighQ, tooLowQ);

        HBox statsHBox = new HBox();
        statsHBox.getChildren().addAll(vBox2Q, vBox1Q, statsQ);

        //Initializing tab pane for a single queue stats and tabs
        TabPane tabPaneQueue = new TabPane();
        Tab detailsOfPassengersQ = new Tab("Passengers' Details", tableHBox);
        Tab statisticsQ = new Tab("Overall Statistics", statsHBox);
        tabPaneQueue.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Getting tabs to the tab pane
        tabPaneQueue.getTabs().addAll(detailsOfPassengersQ, statisticsQ);

        //----------------------------------------------------//
        //Initializing personal statistics table for (1 0f 2) queue in the report scene and setting properties
        passengerDetailsQ2 = new TableView<>();

        //Initializing all table columns and setting the properties
        TableColumn<PassengerStatistics, String> nameColumn2Q = new TableColumn<>("Full Name");
        nameColumn2Q.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumn2Q.setPrefWidth(250);
        nameColumn2Q.setResizable(false);

        TableColumn<PassengerStatistics, String> nicColumn2Q = new TableColumn<>("NIC Number");
        nicColumn2Q.setCellValueFactory(new PropertyValueFactory<>("nationalIDNumber"));
        nicColumn2Q.setPrefWidth(180);
        nicColumn2Q.setResizable(false);
        nicColumn2Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, String> seatColumn2Q = new TableColumn<>("Seat Number");
        seatColumn2Q.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        seatColumn2Q.setPrefWidth(120);
        seatColumn2Q.setResizable(false);
        seatColumn2Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> queueLength2Q = new TableColumn<>("Queue Length");
        queueLength2Q.setCellValueFactory(new PropertyValueFactory<>("queueLength"));
        queueLength2Q.setPrefWidth(120);
        queueLength2Q.setResizable(false);
        queueLength2Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> position2Q = new TableColumn<>("Position");
        position2Q.setCellValueFactory(new PropertyValueFactory<>("position"));
        position2Q.setResizable(false);
        position2Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> seconds2Q = new TableColumn<>("Personal Wait");
        seconds2Q.setCellValueFactory(new PropertyValueFactory<>("secondsInQueue"));
        seconds2Q.setPrefWidth(120);
        seconds2Q.setResizable(false);
        seconds2Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> secondsInQueueTotal2Q = new TableColumn<>("Queue Wait");
        secondsInQueueTotal2Q.setCellValueFactory(new PropertyValueFactory<>("waitingTimeInQueue"));
        secondsInQueueTotal2Q.setPrefWidth(120);
        secondsInQueueTotal2Q.setResizable(false);
        secondsInQueueTotal2Q.setStyle("-fx-alignment: center;");

        //Adding all columns to the personal statistics for (1 0f 2) queue
        passengerDetailsQ2.getColumns().addAll(nameColumn2Q, nicColumn2Q, seatColumn2Q, queueLength2Q, position2Q, seconds2Q, secondsInQueueTotal2Q);

        //Initializing personal statistics table for (2 0f 2) queue in the report scene and setting properties
        passengerDetailsQ1 = new TableView<>();

        //Initializing all table columns and setting the properties for those
        TableColumn<PassengerStatistics, String> nameColumn1Q = new TableColumn<>("Full Name");
        nameColumn1Q.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumn1Q.setPrefWidth(250);
        nameColumn1Q.setResizable(false);

        TableColumn<PassengerStatistics, String> nicColumn1Q = new TableColumn<>("NIC Number");
        nicColumn1Q.setCellValueFactory(new PropertyValueFactory<>("nationalIDNumber"));
        nicColumn1Q.setPrefWidth(180);
        nicColumn1Q.setResizable(false);
        nicColumn1Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, String> seatColumn1Q = new TableColumn<>("Seat Number");
        seatColumn1Q.setCellValueFactory(new PropertyValueFactory<>("seatNum"));
        seatColumn1Q.setPrefWidth(120);
        seatColumn1Q.setResizable(false);
        seatColumn1Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> queueLength1Q = new TableColumn<>("Queue Length");
        queueLength1Q.setCellValueFactory(new PropertyValueFactory<>("queueLength"));
        queueLength1Q.setPrefWidth(120);
        queueLength1Q.setResizable(false);
        queueLength1Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> position1Q = new TableColumn<>("Position");
        position1Q.setCellValueFactory(new PropertyValueFactory<>("position"));
        position1Q.setResizable(false);
        position1Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> seconds1Q = new TableColumn<>("Personal Wait");
        seconds1Q.setCellValueFactory(new PropertyValueFactory<>("secondsInQueue"));
        seconds1Q.setPrefWidth(120);
        seconds1Q.setResizable(false);
        seconds1Q.setStyle("-fx-alignment: center;");

        TableColumn<PassengerStatistics, Integer> secondsInQueueTotal1Q = new TableColumn<>("Queue Wait");
        secondsInQueueTotal1Q.setCellValueFactory(new PropertyValueFactory<>("waitingTimeInQueue"));
        secondsInQueueTotal1Q.setPrefWidth(120);
        secondsInQueueTotal1Q.setResizable(false);
        secondsInQueueTotal1Q.setStyle("-fx-alignment: center;");

        //Adding all columns to the train queue table
        passengerDetailsQ1.getColumns().addAll(nameColumn1Q, nicColumn1Q, seatColumn1Q, queueLength1Q, position1Q, seconds1Q, secondsInQueueTotal1Q);

        //Initializing label for maximum personal (1 of 2) wait and setting properties
        maxPQ1 = new Label();
        maxPQ1.setPadding(new Insets(10));
        maxPQ1.setPrefWidth(250);
        maxPQ1.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum personal wait (1 of 2) and setting properties
        minPQ1 = new Label();
        minPQ1.setPrefWidth(250);
        minPQ1.setPadding(new Insets(10));
        minPQ1.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for maximum wait (1 of 2) in queue and setting properties
        maxQ1 = new Label();
        maxQ1.setPrefWidth(250);
        maxQ1.setPadding(new Insets(10));
        maxQ1.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum wait (1 of 2) in queue and setting properties
        minQ1 = new Label();
        minQ1.setPrefWidth(250);
        minQ1.setPadding(new Insets(10));
        minQ1.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing the vBox for the stats labels (1 of 2) and setting properties
        VBox statsQ1VBox = new VBox();
        statsQ1VBox.setPadding(new Insets(200, 10, 10, 10));
        statsQ1VBox.setSpacing(30);
        statsQ1VBox.getChildren().addAll(maxPQ1, minPQ1, maxQ1, minQ1);
        statsQ1VBox.setStyle("-fx-background-color: #CFD8DC;");


        VBox vBox1Table1 = new VBox();
        vBox1Table1.setPrefWidth(30);
        vBox1Table1.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2Table1 = new VBox();
        vBox2Table1.setPrefWidth(30);
        vBox2Table1.setStyle("-fx-background-color: #006064;");

        HBox tableHBox1 = new HBox();
        tableHBox1.getChildren().addAll(vBox2Table1, vBox1Table1, passengerDetailsQ1, statsQ1VBox);

        //Initializing label for maximum personal (2 of 2) wait and setting properties
        maxPQ2 = new Label();
        maxPQ2.setPadding(new Insets(10));
        maxPQ2.setPrefWidth(250);
        maxPQ2.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum personal wait (2 of 2) and setting properties
        minPQ2 = new Label();
        minPQ2.setPrefWidth(250);
        minPQ2.setPadding(new Insets(10));
        minPQ2.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for maximum wait (2 of 2) in queue and setting properties
        maxQ2 = new Label();
        maxQ2.setPrefWidth(250);
        maxQ2.setPadding(new Insets(10));
        maxQ2.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing label for minimum wait (2 of 2) in queue and setting properties
        minQ2 = new Label();
        minQ2.setPrefWidth(250);
        minQ2.setPadding(new Insets(10));
        minQ2.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS'," +
                " Helvetica, sans-serif; -fx-font-size: 17px; -fx-border-color: #B4B6B9; -fx-border-width: 1 ; ");

        //Initializing the vBox for the stats labels (2 of 2) and setting properties
        VBox statsQ2VBox = new VBox();
        statsQ2VBox.setPadding(new Insets(200, 10, 10, 10));
        statsQ2VBox.setSpacing(30);
        statsQ2VBox.getChildren().addAll(maxPQ2, minPQ2, maxQ2, minQ2);
        statsQ2VBox.setStyle("-fx-background-color: #CFD8DC;");


        VBox vBox1Table2 = new VBox();
        vBox1Table2.setPrefWidth(30);
        vBox1Table2.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2Table2 = new VBox();
        vBox2Table2.setPrefWidth(30);
        vBox2Table2.setStyle("-fx-background-color: #006064;");

        HBox tableHBox2 = new HBox();
        tableHBox2.getChildren().addAll(vBox2Table2, vBox1Table2, passengerDetailsQ2, statsQ2VBox);
        // ----------------------------------------------------//

        //Initializing label for header (1 of 2) and setting properties
        Label headerQ1 = new Label("Statistic of Queue (1 of 2)");
        headerQ1.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 30px;");
        headerQ1.setPadding(new Insets(10));
        headerQ1.setAlignment(Pos.CENTER);

        //Initializing label for number of passengers (1 of 2) and setting properties
        numOfPassengersQ1 = new Label();
        numOfPassengersQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for mean wait (1 of 2) and setting properties
        meanWaitQ1 = new Label();
        meanWaitQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for probability of waiting (1 of 2) and setting properties
        probWaitPassengerQ1 = new Label();
        probWaitPassengerQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for average wait (1 of 2) and setting properties
        averageWaitingQ1 = new Label();
        averageWaitingQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for variance (1 of 2) and setting properties
        varianceQ1 = new Label();
        varianceQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for standard deviation (1 of 2) and setting properties
        standardDQ1 = new Label();
        standardDQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for higher than standard deviation (1 of 2) and setting properties
        tooHighQ1 = new Label();
        tooHighQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-line-spacing: 2em;");

        //Initializing label for lower than standard deviation (1 of 2) and setting properties
        tooLowQ1 = new Label();
        tooLowQ1.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-line-spacing: 2em;");

        //Initializing a VBox to add the stats labels of dual queue implementation
        VBox statsQ1 = new VBox();
        statsQ1.setSpacing(20);
        statsQ1.setPadding(new Insets(20, 20, 20, 70));
        statsQ1.setPrefWidth(700);
        statsQ1.getChildren().addAll(headerQ1, numOfPassengersQ1, meanWaitQ1, probWaitPassengerQ1, averageWaitingQ1, varianceQ1, standardDQ1, tooHighQ1, tooLowQ1);

        //Initializing label for header (2 of 2) and setting properties
        Label headerQ2 = new Label("Statistic of Queue (2 of 2)");
        headerQ2.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 30px;");
        headerQ2.setPadding(new Insets(10));
        headerQ2.setAlignment(Pos.CENTER);

        //Initializing label for number of passengers (2 of 2) and setting properties
        numOfPassengersQ2 = new Label();
        numOfPassengersQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for mean wait (2 of 2) and setting properties
        meanWaitQ2 = new Label();
        meanWaitQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for probability of waiting (2 of 2) and setting properties
        probWaitPassengerQ2 = new Label();
        probWaitPassengerQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for average wait (2 of 2) and setting properties
        averageWaitingQ2 = new Label();
        averageWaitingQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for variance (2 of 2) and setting properties
        varianceQ2 = new Label();
        varianceQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for standard deviation (2 of 2) and setting properties
        standardDQ2 = new Label();
        standardDQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px;");

        //Initializing label for higher than standard deviation (2 of 2) and setting properties
        tooHighQ2 = new Label();
        tooHighQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-line-spacing: 1em;");

        //Initializing label for lower than standard deviation (2 of 2) and setting properties
        tooLowQ2 = new Label();
        tooLowQ2.setStyle("-fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 16px; -fx-line-spacing: 1em;");

        //Initializing a VBox to add the stats labels of dual queue implementation
        VBox statsQ2 = new VBox();
        statsQ2.setSpacing(20);
        statsQ2.setPadding(new Insets(20));
        statsQ2.setPrefWidth(600);
        statsQ2.getChildren().addAll(headerQ2, numOfPassengersQ2, meanWaitQ2, probWaitPassengerQ2, averageWaitingQ2, varianceQ2, standardDQ2, tooHighQ2, tooLowQ2);

        VBox vBox1Q2 = new VBox();
        vBox1Q2.setPrefWidth(30);
        vBox1Q2.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2Q2 = new VBox();
        vBox2Q2.setPrefWidth(30);
        vBox2Q2.setStyle("-fx-background-color: #006064;");

        HBox statsQHBox = new HBox();
        statsQHBox.getChildren().addAll(vBox2Q2, vBox1Q2, statsQ1, statsQ2);
        // ----------------------------------------------------//

        //Initializing tab pane for dual queues and the tabs
        TabPane tabPaneQueues = new TabPane();
        Tab detailsOfPassengers1Q = new Tab("Passengers' Details for Queue 1", tableHBox1);
        Tab detailsOfPassengers2Q = new Tab("Passengers' Details for Queue 2", tableHBox2);
        Tab statistics2Q = new Tab("Overall Statistics", statsQHBox);
        tabPaneQueues.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Adding tabs to the tab pane
        tabPaneQueues.getTabs().addAll(detailsOfPassengers1Q, detailsOfPassengers2Q, statistics2Q);

        // ----------------------------------------------------//

        //Initializing bar chart for the comparison of single queue and dual queue implementation
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Statistics");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of.");

        barChartStats = new BarChart(xAxis, yAxis);

        //----------------------------------------------------//

        //Initializing the menu tab for the report scene
        HBox menuTab = new HBox();

        //Initialing menu button for the report scene
        Button backToConsoleR = new Button("C O N S O L E");
        backToConsoleR.setPadding(new Insets(5));
        backToConsoleR.setPrefWidth(280);
        backToConsoleR.setPrefHeight(40);
        backToConsoleR.setStyle("-fx-text-fill: #006064; -fx-graphic: url(Menu_Green.png); -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 18px; -fx-text-alignment: center;");
        backToConsoleR.setOnAction(event -> {
            window.setIconified(true);
            try {
                Methods.inputMenu();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        VBox vBox1M = new VBox();
        vBox1M.setPrefWidth(30);
        vBox1M.setStyle("-fx-background-color: #0097A7;");

        VBox vBox2M = new VBox();
        vBox2M.setPrefWidth(30);
        vBox2M.setStyle("-fx-background-color: #006064;");

        Label summary = new Label("Train Queue Simulation Report Summary");
        summary.setPadding(new Insets(50));
        summary.setStyle("-fx-text-fill: #006064; -fx-spacing: 20; -fx-font-family: 'Trebuchet MS', Helvetica, sans-serif; -fx-font-size: 30px;");


        VBox menuVBox = new VBox();
        menuVBox.getChildren().addAll(summary, backToConsoleR);
        menuVBox.setAlignment(Pos.CENTER);
        menuVBox.setStyle("-fx-background-color: #CFD8DC;");
        menuVBox.setPadding(new Insets(150, 310, 200, 310));

        menuTab.getChildren().addAll(vBox2M, vBox1M, menuVBox);


        //----------------------------------------------------//

        //Initializing the main tab pane for the report scene
        TabPane reportTabPane = new TabPane();
        reportTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Initializing tabs for the report scene tab pane
        Tab menu = new Tab(" Menu ", menuTab);
        Tab personal = new Tab("Statistics of One Queue", tabPaneQueue);
        Tab stats = new Tab("Statistics of Two Queues", tabPaneQueues);
        Tab compare = new Tab("Compare queues", barChartStats);

        //Adding the tabs for the tab pane
        reportTabPane.getTabs().addAll(menu, personal, stats, compare);
        reportScene = new Scene(reportTabPane, 1200, 700);

        //---------------------------------------------------------------------//
        //showing the User interface
        window.initStyle(StageStyle.UNDECORATED);
        window.show();
        window.setIconified(true);
        Methods.inputMenu();

    }

}
