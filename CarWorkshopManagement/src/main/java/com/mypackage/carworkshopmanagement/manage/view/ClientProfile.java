package com.mypackage.carworkshopmanagement.manage.view;

import com.mypackage.carworkshopmanagement.manage.database.MyJDBC;
import com.mypackage.carworkshopmanagement.manage.model.*;
import javafx.application.Application;
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
import java.util.*;
import java.util.stream.Collectors;
import static com.mypackage.carworkshopmanagement.manage.view.Home.*;

public class ClientProfile extends Application {
    List<CarModel> carModelList;
    List<Client> clientsList;
    int highestClientId=0, highestCarId=0, tmpClientId, tmpCarId;

    double screenWidth,screenHeight,navBarWidth;
    private Stage primaryStage;
    private Map<Integer, String> problemAndSolutionMap;
    private CarModel selectedCar;

    public ClientProfile(){}

    public ClientProfile(List<CarModel> carModelList, List<Client> clientsList, CarModel selectedCar) {
        this.clientsList=clientsList;
        this.carModelList = carModelList;
        this.selectedCar = selectedCar;
    }

    @Override
    public void start(Stage primaryStage) {
        problemAndSolutionMap = new HashMap<>();
        List<ProblemAndSolution>  problemAndSolutionList = MyJDBC.getProblemAndSolutionList();
        if(problemAndSolutionList !=null){
            for(ProblemAndSolution p: problemAndSolutionList){
                problemAndSolutionMap.put(p.getId(),p.getProblem());
            }
        }

        this.primaryStage = primaryStage;
//        for(int i=0;i<500;i++) System.out.println(generateUniqueRegistrationNumber());

        if(carModelList != null){
            for(Client c:clientsList){
                if(c.getId()>highestClientId)highestClientId=c.getId();
            }
        }
        if(carModelList != null) {
            for (CarModel c : carModelList) {
                if (c.getId() > highestCarId) highestCarId = c.getId();
            }
        }

        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        navBarWidth = 0.18 * screenWidth;

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

        registerBtn.setOnAction(e -> {
            RegisterNewClient display = new RegisterNewClient(carModelList, clientsList);
            display.start(primaryStage);
            cleanUp();
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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


        //////////////////////////////////////////////////////////////////////////////////////////////////////
        Label listOfCarsLabel = new Label("List of Cars");
        listOfCarsLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        listOfCarsLabel.setTextAlignment(TextAlignment.CENTER);
        List<CarModel> needToShow = new ArrayList<>();
        for(CarModel carModel:carModelList){
            if(carModel.getOwner() == selectedCar.getOwner()){
                needToShow.add(carModel);
            }
        }
        ScrollPane scrollPane1 = showCars(needToShow);
        scrollPane1.setPadding(new Insets(10));
        VBox box1 = new VBox(listOfCarsLabel,scrollPane1);
        box1.setAlignment(Pos.CENTER);

        Label billingHistory = new Label("Billing History");
        billingHistory.setTextAlignment(TextAlignment.CENTER);
        billingHistory.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");


//        System.out.println("\n\n\n#########################################################\n\n\n");
        // just need to take this profile bills
        List<Bills> billingHistoryList = MyJDBC.getBills();
        List<Bills>localBills = new ArrayList<>();

        assert billingHistoryList != null;
        for(Bills b: billingHistoryList){
            if(b.getPhoneNumber().equalsIgnoreCase(selectedCar.getOwner().getPhoneNo())){
                localBills.add(b);
            }
        }

        Collections.sort(localBills, new Comparator<Bills>() {
            @Override
            public int compare(Bills b2, Bills b1) {
                return b1.getDate().compareTo(b2.getDate());
            }
        });


        ScrollPane scrollPane2 = showBillingHistory(localBills);
        scrollPane2.setPadding(new Insets(10));
        VBox box2 = new VBox(billingHistory,scrollPane2);
        box2.setAlignment(Pos.CENTER);
        HBox box = new HBox(box1,box2);
        mainBody.getChildren().add(box);
        HBox mainPane = new HBox(navigationBar, mainBody);
        Scene scene = new Scene(mainPane, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register New Client");
        primaryStage.show();
    }

    private ScrollPane showBillingHistory(List<Bills> billingHistoryList) {

//        System.out.println("Showing Billing history");
        GridPane carPane = new GridPane();
        carPane.setPadding(new Insets(15));
        carPane.setHgap(15);
        carPane.setVgap(15);

        // Calculate number of columns based on product button size and available space
        double buttonWidth = 400; // Button width
        double buttonHeight = 180; // Button height
        double availableWidth = (screenWidth-navBarWidth-5)/2; // Available width excluding navigation bar
        int columns = 1; // Calculate number of columns

//        System.out.println("columns:"+columns);
        int totalbills = billingHistoryList.size();

//        System.out.println("total bills:"+totalbills);

        int rows = (int) Math.ceil((double) totalbills / columns); // Calculate number of rows
        int currentNumber= 0; // Track the current product number
        boolean loopBreak = false;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (currentNumber >= totalbills) {
                    loopBreak = true;
                    break; // Exit loop if all products are added
                }
//                System.out.println("==============billsssss");
                Bills bill = billingHistoryList.get(currentNumber);
                String text ="Date: "+ bill.getDate() +"\nProblems: \n";
                String problemString = bill.getProblemIds().trim();
                if(!problemString.isEmpty()){
//                    System.out.println("problems: "+problemString);
                    List<Integer> problemsIds = getProblems(problemString);
                    int ind=0;
                    for (Integer i:problemsIds){
                        if(problemAndSolutionMap.containsKey(i)){
                            String problem = problemAndSolutionMap.get(i);
                            ind++;
                            text = text+ind+". " +problem+"\n";
                        }
                    }
                }

                Button carButton = new Button(text);
                carButton.setStyle("-fx-background-color: lightgray;-fx-font-size: 16px;");
                carButton.setMinSize(buttonWidth, buttonHeight); // Set button size

                String hoverStyle = "-fx-background-color: darkgray;-fx-font-size: 24px;";
                String exitStyle = "-fx-background-color: lightgray;-fx-font-size: 16px;";

                // Set mouse enter and exit event handlers
//                carButton.setOnMouseEntered(e -> carButton.setStyle(hoverStyle));
//                carButton.setOnMouseExited(e -> carButton.setStyle(exitStyle));

                carButton.setOnMouseEntered(event -> {
                    carButton.setText("Extract Bill");
                    carButton.setStyle(hoverStyle);
                });

                String finalText = text;
                carButton.setOnMouseExited(event -> {
                    carButton.setText(finalText);
                    carButton.setStyle(exitStyle);
                });

                carButton.setOnAction(event -> {
                    MakeBill display = new MakeBill(carModelList,clientsList,selectedCar.getOwner().getPhoneNo(),selectedCar.getFrameNumber(),bill);
                    display.start(primaryStage);
                    cleanUp();
                });

                carPane.add(carButton, col, row);
                currentNumber++; // Move to the next product
            }
            if (loopBreak) break;
        }
        carPane.setAlignment(Pos.CENTER);


        ScrollPane scrollPane = new ScrollPane(carPane);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setPrefHeight(screenHeight - 150);
        scrollPane.setPrefWidth(screenWidth-navBarWidth-10);

        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private List<Integer> getProblems(String problemString) {

        String[] numbers = problemString.split(",");
        List<String> stringList = Arrays.asList(numbers);
        // Converting to a List of Integers
        return stringList.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private ScrollPane showCars(List<CarModel> localCarModelList) {
        // Create GridPane for products
        GridPane carPane = new GridPane();
        carPane.setPadding(new Insets(15));
        carPane.setHgap(15);
        carPane.setVgap(15);

        // Calculate number of columns based on product button size and available space
        double buttonWidth = 300; // Button width
        double buttonHeight = 180; // Button height
        double availableWidth = (screenWidth-navBarWidth-5)/2; // Available width excluding navigation bar
        int columns = (int) Math.floor(availableWidth / buttonWidth) - 1; // Calculate number of columns


        int totalCars = localCarModelList.size();

        int rows = (int) Math.ceil((double) totalCars / columns); // Calculate number of rows
        int currentNumber= 0; // Track the current product number
        boolean loopBreak = false;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (currentNumber >= totalCars) {
                    loopBreak = true;
                    break; // Exit loop if all products are added
                }

                String text = "Owner: "+localCarModelList.get(currentNumber).getOwner().getName() +"\nFrame Number: "+localCarModelList.get(currentNumber).getFrameNumber();
                text = text + "\nBrand: "+localCarModelList.get(currentNumber).getBrand();

                Button carButton = new Button(text);
                carButton.setStyle("-fx-background-color: lightgray;-fx-font-size: 16px;");
                carButton.setMinSize(buttonWidth, buttonHeight); // Set button size

                String hoverStyle = "-fx-background-color: darkgray;-fx-font-size: 24px;";
                String exitStyle = "-fx-background-color: lightgray;-fx-font-size: 16px;";

                // Set mouse enter and exit event handlers
//                carButton.setOnMouseEntered(e -> carButton.setStyle(hoverStyle));
//                carButton.setOnMouseExited(e -> carButton.setStyle(exitStyle));

                carButton.setOnMouseEntered(event -> {
                    carButton.setText("Make a Bill");
                    carButton.setStyle(hoverStyle);
                });

                String finalText = text;
                carButton.setOnMouseExited(event -> {
                    carButton.setText(finalText);
                    carButton.setStyle(exitStyle);
                });


                final String phoneNumber = localCarModelList.get(currentNumber).getOwner().getPhoneNo();
                final String frameNumber = localCarModelList.get(currentNumber).getFrameNumber();
                carButton.setOnAction(event -> {
                    MakeBill display = new MakeBill(carModelList,clientsList,phoneNumber,frameNumber);
                    display.start(primaryStage);
                    cleanUp();
                });

                carPane.add(carButton, col, row);
                currentNumber++; // Move to the next product
            }
            if (loopBreak) break;
        }
        carPane.setAlignment(Pos.CENTER);


        ScrollPane scrollPane = new ScrollPane(carPane);
        scrollPane.setPadding(new Insets(20));
        scrollPane.setPrefHeight(screenHeight - 150);
        scrollPane.setPrefWidth(screenWidth-navBarWidth-10);

        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    public void cleanUp(){
        carModelList=null;
        clientsList=null;
        primaryStage=null;
        problemAndSolutionMap=null;
    }
}

