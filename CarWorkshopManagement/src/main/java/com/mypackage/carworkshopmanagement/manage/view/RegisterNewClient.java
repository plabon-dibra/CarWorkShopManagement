package com.mypackage.carworkshopmanagement.manage.view;

import com.mypackage.carworkshopmanagement.manage.database.MyJDBC;
import com.mypackage.carworkshopmanagement.manage.model.AutoCompleteTextField;
import com.mypackage.carworkshopmanagement.manage.model.CarModel;
import com.mypackage.carworkshopmanagement.manage.model.Client;
import com.mypackage.carworkshopmanagement.manage.model.RegistrationNumberGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.*;

import static com.mypackage.carworkshopmanagement.manage.model.RegistrationNumberGenerator.generateUniqueRegistrationNumber;
import static com.mypackage.carworkshopmanagement.manage.view.Home.*;

public class RegisterNewClient extends Application {
    List<CarModel> carModelList;
    List<Client> clientsList;
    private List<VBox> carBoxes = new ArrayList<>(); // To hold car input sections
    Set<String> suggestNumber = new HashSet<>();
    Set<String> suggestBrand = new HashSet<>();
    Map<String,String>numberNameMap = new HashMap<>();

    public RegisterNewClient(){}



    public RegisterNewClient(List<CarModel> carModelList, List<Client> clientsList) {
        this.clientsList=clientsList;
        this.carModelList = carModelList;
    }


    @Override
    public void start(Stage primaryStage) {
//        for(int i=0;i<500;i++) System.out.println(generateUniqueRegistrationNumber());
        for(Client c:clientsList){
            suggestNumber.add(c.getPhoneNo());
            numberNameMap.put(c.getPhoneNo(),c.getName());
        }
        for(CarModel c:carModelList){
            suggestBrand.add(c.getBrand());
        }

        Label messageLabel = new Label("");
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        double navBarWidth = 0.18 * screenWidth;

        // Left: Navigation Bar
        GridPane navigationBar = new GridPane();
        navigationBar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        navigationBar.setMinWidth(navBarWidth);
        navigationBar.setMaxWidth(navBarWidth);
        navigationBar.setMinHeight(screenHeight);
        navigationBar.setMaxHeight(screenHeight);
        navigationBar.setAlignment(Pos.CENTER);
        Button homeBtn = createNavigationButton("\u2302", "Home", navBarWidth);
        Button profileBtn = createNavigationButton("\u263A", "Client Profiles", navBarWidth);
        Button makeABillBtn = createNavigationButton("\u270D", "Make a Bill", navBarWidth);
        Button registerBtn = createNavigationButton("\u2713", "Register New Client", navBarWidth);
        setInitialBackground(homeBtn);
        setInitialBackground(profileBtn);
        setInitialBackground(makeABillBtn);
        setInitialBackground(registerBtn);
        navigationBar.add(homeBtn, 0, 0);
//        navigationBar.add(profileBtn, 0, 1);
        navigationBar.add(registerBtn, 0, 3);
        navigationBar.add(makeABillBtn, 0, 4);
        homeBtn.setMaxWidth(Double.MAX_VALUE);
        profileBtn.setMaxWidth(Double.MAX_VALUE);
        makeABillBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        addHoverEffect(homeBtn);
        addHoverEffect(profileBtn);
        addHoverEffect(makeABillBtn);
        addHoverEffect(registerBtn);

        homeBtn.setOnAction(e -> {
            Home display = new Home(carModelList,clientsList);
            display.start(primaryStage);
            cleanUp();
        });
        makeABillBtn.setOnAction(e -> {
            MakeBill display = new MakeBill(carModelList,clientsList);
            display.start(primaryStage);
            cleanUp();
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        Background silverColor
        Background backgroundSilverColor = new Background(new BackgroundFill(Color.SILVER, null, null));
        Background background_f0f0f0 = new Background(new BackgroundFill(Color.valueOf("#f0f0f0"), null, null));

        // Top: Welcome Label
        Label welcomeLabel = new Label("DEVIZ   AUTOCLEAVER   SRL");
        welcomeLabel.setFont(Font.font(30));
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);
        VBox welcome = new VBox(welcomeLabel);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPrefHeight(50);
        welcome.setPrefWidth(screenWidth - navBarWidth);
        welcome.setBackground(backgroundSilverColor);

        VBox mainBody = new VBox(welcome);
        mainBody.setPrefHeight(screenHeight);
        mainBody.setPrefWidth(screenWidth - navBarWidth);
        mainBody.setBackground(background_f0f0f0);

        // Create a GridPane layout for the registration form
        GridPane grid = new GridPane();
        grid.setPrefWidth(300);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);



        Label headlineLabel = new Label("Client Registration");
        headlineLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        headlineLabel.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Name:");
        nameLabel.setStyle("-fx-font-size: 18px;");
        grid.setConstraints(nameLabel, 0, 1);
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter name");
        nameInput.setStyle("-fx-font-size: 16px; ");
        VBox nameVbox = new VBox(nameInput);
        nameVbox.setPadding(new Insets(5));
        nameVbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        GridPane.setConstraints(nameVbox, 1, 1);



        Label phoneNumberLabel = new Label("Client Phone Number:");
        phoneNumberLabel.setStyle("-fx-font-size: 18px;");
        grid.setConstraints(phoneNumberLabel, 0, 0);
        AutoCompleteTextField phoneNumberInput = new AutoCompleteTextField();
        ObservableList<String> pro = FXCollections.observableArrayList(suggestNumber);
        phoneNumberInput.setEntries(pro);
        phoneNumberInput.setPromptText("Enter phone number");
        phoneNumberInput.setStyle("-fx-font-size: 16px; ");
        VBox phoneVbox = new VBox(phoneNumberInput);
        phoneVbox.setPadding(new Insets(5));
        phoneVbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        GridPane.setConstraints(phoneVbox, 1, 0);

        phoneNumberInput.setOnTextChanged(text -> {
            if(numberNameMap.containsKey(text)){
                nameInput.setText(numberNameMap.get(text));
            }
        });









        Label carLabel = new Label("Cars:");
        carLabel.setStyle("-fx-font-size: 18px;");
        grid.setConstraints(carLabel, 0, 2);

        VBox carVBox = new VBox(5); // VBox to hold car input fields
        carVBox.setPadding(new Insets(5));
        carVBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");
        grid.setConstraints(carVBox, 1, 2);

        addCarInputFields(carVBox);
        Button addCarButton = new Button("+");
        addCarButton.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        addCarButton.setOnAction(e -> addCarInputFields(carVBox));
        grid.setConstraints(addCarButton, 2, 2);

        Button registerButton = new Button("Register");
        registerButton.setAlignment(Pos.CENTER);
        registerButton.setStyle("-fx-font-size: 20px; ");
        registerButton.setBackground(new Background(new BackgroundFill(Color.SILVER,new CornerRadii(10), Insets.EMPTY)));
        registerButton.setOnMouseEntered(e -> {
            registerButton.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, new CornerRadii(10), Insets.EMPTY)));
            registerButton.setStyle("-fx-font-size: 20px;");
        });

        registerButton.setOnMouseExited(e -> {
            registerButton.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
            registerButton.setStyle("-fx-font-size: 20px;");
        });



        registerButton.setOnAction(e -> {
            // Retrieve all input values
            String name = nameInput.getText().trim();
            String phoneNumber = phoneNumberInput.getText();

            if (name.isEmpty() || phoneNumber.isEmpty()) {
                // Check if any required field is empty
                messageLabel.setText("Please fill out all fields.");
                messageLabel.setTextFill(Color.RED);
                return; // Exit method if any field is empty
            } else {
                messageLabel.setText("");
            }
            List< List<String> > carInformationList = new ArrayList<>();
            // Process car information
            for (VBox carBox : carBoxes) {
                TextField brandInput = (TextField) carBox.lookup("#brandInput");
                TextField frameNumberInput = (TextField) carBox.lookup("#frameNumberInput");
                TextField milageInput = (TextField) carBox.lookup("#milageInput");

                String brand = brandInput.getText().trim();
                String frameNumber = frameNumberInput.getText().trim();
                String milage = milageInput.getText().trim();
                double milageDouble;
                try {
                    milageDouble = Double.parseDouble(milage);
                    System.out.println("The double value is: " + milageDouble);

                } catch (NumberFormatException er) {
//                    System.out.println("Invalid string for conversion to double.");
                    messageLabel.setText("Milage should contain double value!");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }


                if (brand.isEmpty() || frameNumber.isEmpty()  || milage.isEmpty()) {
                    messageLabel.setText("Please fill out all car fields.");
                    messageLabel.setTextFill(Color.RED);
                    return; // Exit method if any car field is empty
                }
                List<String> carInfo = new ArrayList<>();
                carInfo.add(frameNumber);
                carInfo.add(brand);
                carInfo.add(milage);

                carInformationList.add(carInfo);

                // Handle car registration (example)
//                System.out.println("Car Brand: " + brand);
//                System.out.println("Frame Number: " + frameNumber);
//                System.out.println("Milage: " + milage);
//
//                System.out.println("------------------------");
            }
            String regNo = RegistrationNumberGenerator.generateUniqueRegistrationNumber();



//
//            System.out.println("Name        : "+ name);
//            System.out.println("Phone Number: "+ phoneNumber);
//            System.out.println("Reg No      : "+ regNo);

            Client clientObject = new Client(name,regNo,phoneNumber);
            int status = MyJDBC.insertClient(clientObject);

            if(status == 1){
                clientObject = MyJDBC.getClient(phoneNumber);
                clientsList.add(clientObject);
//                System.out.println("+++++++++++> client data stored! <+++++++++++++");
            }
            else if(status == -1){
                clientObject = MyJDBC.getClient(phoneNumber);
//                System.out.println("-----------> client data Already exist! <----------");
            }
            else{
                messageLabel.setText("Database Connection Failed!");
                messageLabel.setTextFill(Color.RED);
//                System.out.println("===========> Client Database Connection Failed! <===========");
                return;
            }

            int successfullyCarStored=0, failedToSave=0;
            for(List<String> carInfo:carInformationList){
                double milage=0.0;
                try {
                    milage = Double.parseDouble(carInfo.get(2));
//                    System.out.println("The double value is: " + milage);
                    String frame = carInfo.get(0);
                    String brand = carInfo.get(1);
                    CarModel carModel = new CarModel(frame, brand, milage, clientObject);
                    status = MyJDBC.insertCar(carModel);

                    if(status == 1){
                        successfullyCarStored++;
                        carModel = MyJDBC.getCar(frame, clientsList);
                        carModelList.add(carModel);
//                        System.out.println("+++++++++++> Car data stored! <+++++++++++++");
                    }
                    else if(status == -1){
//                        System.out.println("-----------> Car data Already exist! <----------");
                    }
                    else{
                        failedToSave++;
//                        System.out.println("===========> Car Database Connection Failed! <===========");
                    }

//                    System.out.println("carModel created!");
                } catch (NumberFormatException er) {
//                    System.out.println("Invalid string for conversion to double.");
                }

            }

            if(successfullyCarStored!=0){
                if(failedToSave==0){

                    messageLabel.setText("Registration Successfully Recorded!");
                    messageLabel.setTextFill(Color.GREEN);
                }
                else{
                    messageLabel.setText(failedToSave+" car info failed to save!\n");
                    messageLabel.setTextFill(Color.ORANGE);
                }
            }
            else if(failedToSave==0){
                messageLabel.setText("Data already exist!");
                messageLabel.setTextFill(Color.RED);
            }
            else{
                messageLabel.setText("Registration Failed!");
                messageLabel.setTextFill(Color.RED);
            }
//            System.out.println("Register function done: " + carInformationList.size());
        });

        HBox buttonBoxes = new HBox(registerButton);
        buttonBoxes.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttonBoxes, 0, 4);
        GridPane.setColumnSpan(buttonBoxes,3);

        // Add all elements to the grid
        grid.getChildren().addAll(
                nameLabel, nameVbox,
                phoneNumberLabel, phoneVbox,
                carLabel, carVBox,
                addCarButton,
                buttonBoxes
        );


        VBox vbox = new VBox(10, headlineLabel, grid, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setPrefWidth(screenWidth - navBarWidth);
        vbox.setPrefHeight(screenHeight - 100);

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setPrefHeight(screenHeight - 50);
//        scrollPane.setPrefWidth(screenWidth-navBarWidth);
        scrollPane.setFitToWidth(true);

        mainBody.getChildren().add(scrollPane);
//        mainBody.setAlignment(Pos.CENTER);

        HBox mainPane = new HBox(navigationBar, mainBody);
//        mainPane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(mainPane, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register New Client");
        primaryStage.show();
    }

    private void addCarInputFields(VBox carVBox) {
        VBox carBox = new VBox(10);

        AutoCompleteTextField brandInput = new AutoCompleteTextField();
        brandInput.setPromptText("Enter car brand");
        brandInput.setId("brandInput");
        brandInput.setPrefWidth(200);
        brandInput.setStyle("-fx-font-size: 16px; ");
        ObservableList<String> pro = FXCollections.observableArrayList(suggestBrand);
        brandInput.setEntries(pro);



        TextField frameNumberInput = new TextField();
        frameNumberInput.setPromptText("Enter frame number");
        frameNumberInput.setId("frameNumberInput");
        frameNumberInput.setPrefWidth(200);
        frameNumberInput.setStyle("-fx-font-size: 16px; ");

        TextField milageInput = new TextField();
        milageInput.setPromptText("Enter milage");
        milageInput.setId("milageInput");
        milageInput.setPrefWidth(110);
        milageInput.setStyle("-fx-font-size: 16px; ");




        Button delete = new Button("Delete");
        delete.setAlignment(Pos.CENTER);
        delete.setStyle("-fx-font-size: 18px; ");
        delete.setBackground(new Background(new BackgroundFill(Color.SILVER,new CornerRadii(10), Insets.EMPTY)));
        delete.setOnMouseEntered(e -> {
            delete.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
            delete.setStyle("-fx-font-size: 18;");
        });
        delete.setOnMouseExited(e -> {
            delete.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
            delete.setStyle("-fx-font-size: 18;");
        });




        HBox carRow1 = new HBox(10, brandInput, frameNumberInput, milageInput,delete);
        carRow1.setPadding(new Insets(5));
        carRow1.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));

        carBox.getChildren().add(carRow1);
        carVBox.getChildren().add(carBox);
        carBoxes.add(carBox);

        delete.setOnAction(e -> {
            carBoxes.remove(carBox);
//            System.out.println("here");
            carVBox.getChildren().remove(carBox);
            carBox.getChildren().remove(carRow1);
        });
    }

    private void cleanUp(){
        carBoxes=null;
        suggestNumber=null;
        suggestBrand=null;

        carModelList=null;
        clientsList=null;
        numberNameMap=null;
    }

}

