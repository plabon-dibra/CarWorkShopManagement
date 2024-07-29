package com.mypackage.carworkshopmanagement.manage.view;

import com.mypackage.carworkshopmanagement.manage.database.MyJDBC;
import com.mypackage.carworkshopmanagement.manage.model.AutoCompleteTextField;
import com.mypackage.carworkshopmanagement.manage.model.CarModel;
import com.mypackage.carworkshopmanagement.manage.model.Client;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Home extends Application {
    boolean needCheck = true;
    List<CarModel> carModelList;
    List<Client> clientsList;
    private ScrollPane scrollPane = new ScrollPane();
    Stage primaryStage;

    double screenHeight, screenWidth, navBarWidth;



    public Home(){

    }

    public Home(List<CarModel> carModelList, List<Client> clientsList) {
        this.clientsList = clientsList;
        this.carModelList = carModelList;
        needCheck = false;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if(needCheck){
            clientsList = MyJDBC.getClientList();
            carModelList = MyJDBC.getCarList(clientsList);
        }

//        System.out.println("Client's: "+clientsList.size());
//        System.out.println("Car's: "+carModelList.size());


        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        navBarWidth = 0.18 * screenWidth;
////////////////////////////////////////////////////////////////////////////////////////
        // Left: Navigation Bar
        GridPane navigationBar = new GridPane();
        navigationBar.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        navigationBar.setMinWidth(navBarWidth);
        navigationBar.setMaxWidth(navBarWidth);
        navigationBar.setMinHeight(screenHeight);
        navigationBar.setMaxHeight(screenHeight);
        navigationBar.setAlignment(Pos.CENTER);
        // Create buttons with Unicode icons
        Button homeBtn = createNavigationButton("\u2302", "Home", navBarWidth);
        Button profileBtn = createNavigationButton("\u263A", "Client Profiles", navBarWidth);
        Button makeABillBtn = createNavigationButton("\u270D", "Make a Bill", navBarWidth);
        Button registerBtn = createNavigationButton("\u2713", "Register New Client", navBarWidth);  // Unicode check mark as icon
        // Set initial background to dark gray
        setInitialBackground(homeBtn);
        setInitialBackground(profileBtn);
        setInitialBackground(makeABillBtn);
        setInitialBackground(registerBtn);
        // Add buttons to the grid
        navigationBar.add(homeBtn, 0, 0);
//        navigationBar.add(profileBtn, 0, 1);
        navigationBar.add(registerBtn, 0, 3);
        navigationBar.add(makeABillBtn, 0, 4);
        // Set button width to be equal and align center
        homeBtn.setMaxWidth(Double.MAX_VALUE);
        profileBtn.setMaxWidth(Double.MAX_VALUE);
        makeABillBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        // Change button color on hover
        addHoverEffect(homeBtn);
        addHoverEffect(profileBtn);
        addHoverEffect(makeABillBtn);
        addHoverEffect(registerBtn);
////////////////////////////////////////////////////////////////////////////////////////
        registerBtn.setOnAction(e -> {
            RegisterNewClient display = new RegisterNewClient(carModelList,clientsList);
            display.start(primaryStage);
            cleanUp();
        });


        makeABillBtn.setOnAction(e -> {
            MakeBill display = new MakeBill(carModelList,clientsList);
            display.start(primaryStage);
            cleanUp();
        });

//        profileBtn.setOnAction(e -> {
//            ClientProfile display = new ClientProfile(carModelList,clientsList);
//            display.start(primaryStage);
//        });

///////////////////////////////////////////////////////////////////////////////
        // Top: Welcome Label
        Label welcomeLabel = new Label("DEVIZ   AUTOCLEAVER   SRL");
        welcomeLabel.setFont(Font.font(30));
        welcomeLabel.setTextAlignment(TextAlignment.CENTER);

        Background backgroundSilverColor = new Background(new BackgroundFill(Color.SILVER, null, null));
        Background background_f0f0f0 = new Background(new BackgroundFill(Color.valueOf("#f0f0f0"), null, null));


        VBox welcome = new VBox(welcomeLabel);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPrefHeight(50);
        welcome.setPrefWidth(screenWidth-navBarWidth);
        welcome.setBackground(backgroundSilverColor);

        VBox mainBody = new VBox(welcome);
        mainBody.setPrefHeight(screenHeight);
        mainBody.setPrefWidth(screenWidth-navBarWidth);
        mainBody.setBackground(background_f0f0f0);
/////////////////////////////////////////////////////////////////////////////////////////

        // Search Bar
        Button searchButton = searchButton();

        AutoCompleteTextField searchField = searchField();
        Set<String> frameNumbersToShow = new HashSet<>();
        for (CarModel c : carModelList) {
            frameNumbersToShow.add(c.getFrameNumber());
        }
        ObservableList<String> frameNumbers = FXCollections.observableArrayList(frameNumbersToShow);
        searchField.setEntries(frameNumbers);

        HBox searchBar = getSearchBar(searchField, searchButton);
//        searchBar.setPrefSize(500,50);
//        searchBar.setBackground(Background.fill(Color.RED));

        mainBody.getChildren().add(searchBar);

        scrollPane = showCars(carModelList);

//        searchField.setOnAction();
        searchField.setOnKeyTyped(event -> {
            String searchText = searchField.getText();
            System.out.println("User typed: " + searchText);
            // Add your search logic here

            List<CarModel> localCarModelList = new ArrayList<>();
            for(CarModel car:carModelList){
                if( searchText.isEmpty()|| car.getFrameNumber().toLowerCase().contains(searchText.toLowerCase()) ||car.getOwner().getName().toLowerCase().contains(searchText.toLowerCase())){
                    localCarModelList.add(car);
                }
            }
            ScrollPane newScrollPane = showCars(localCarModelList);
            mainBody.getChildren().remove(scrollPane);
            scrollPane = newScrollPane;
            mainBody.getChildren().add(scrollPane);
        });

/////////////////////////////////////////////////////////////////////////////////////////



//----------------------------------------------------------------------------------

//        scrollPane.setBackground(Background.fill(Color.YELLOW));

        mainBody.getChildren().add(scrollPane);
//----------------------------------------------------------------------------------

        HBox mainPane = new HBox(navigationBar,mainBody);
        // Create scene
        Scene scene = new Scene(mainPane, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
        primaryStage.show();
    }

    private ScrollPane showCars(List<CarModel> localCarModelList) {
        // Create GridPane for products
        GridPane carPane = new GridPane();
        carPane.setPadding(new Insets(15));
        carPane.setHgap(15);
        carPane.setVgap(15);

        // Calculate number of columns based on product button size and available space
        double buttonWidth = 200; // Button width
        double buttonHeight = 200; // Button height
        double availableWidth = screenWidth - navBarWidth; // Available width excluding navigation bar
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
                carButton.setStyle("-fx-background-color: lightgray;");
                carButton.setMinSize(buttonWidth, buttonHeight); // Set button size

                String hoverStyle = "-fx-background-color: darkgray;";
                String exitStyle = "-fx-background-color: lightgray;";

                // Set mouse enter and exit event handlers
                carButton.setOnMouseEntered(e -> carButton.setStyle(hoverStyle));
                carButton.setOnMouseExited(e -> carButton.setStyle(exitStyle));


                int finalCurrentNumber = currentNumber;
                carButton.setOnAction(event -> {
                    ClientProfile display = new ClientProfile(carModelList,clientsList,localCarModelList.get(finalCurrentNumber));
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

    public static Button createNavigationButton(String icon, String text, double width) {
        Button button = new Button();
        HBox content = new HBox(10); // spacing between icon and text
        content.setAlignment(Pos.CENTER_LEFT);

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(20));

        Label textLabel = new Label(text);
        textLabel.setFont(Font.font(20));

        content.getChildren().addAll(iconLabel, textLabel);
        content.setPadding(new Insets(0, 0, 0, 10)); // Add padding to align properly
        button.setGraphic(content);
        button.setMinWidth(width);
        button.setMinHeight(50);
        button.setAlignment(Pos.CENTER_LEFT); // Ensure button content aligns to the left

        return button;
    }

    public static void setInitialBackground(Button button) {
        button.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public static void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: lightgray;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: darkgray;"));
    }

    Button searchButton(){
        // Create Button for search action
        Button searchButton = new Button("🔍");
        searchButton.setStyle(
                "-fx-background-radius: 15px; " +
                        "-fx-padding: 15px; " +
                        "-fx-font-size: 18px; " +    // Increased font size
                        "-fx-background-color: #362f40; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand;"
        );

        searchButton.setOnMouseEntered(e -> searchButton.setStyle(
                "-fx-background-radius: 15px; " +
                        "-fx-padding: 15px; " +
                        "-fx-font-size: 18px; " +    // Increased font size
                        "-fx-background-color: #32c76b; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand;"
        ));

        searchButton.setOnMouseExited(e -> searchButton.setStyle(
                "-fx-background-radius: 15px; " +
                        "-fx-padding: 15px; " +
                        "-fx-font-size: 18px; " +    // Increased font size
                        "-fx-background-color: #362f40; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand;"
        ));

        return searchButton;
    }
    AutoCompleteTextField searchField(){
        AutoCompleteTextField searchField = new AutoCompleteTextField();
        searchField.setPromptText("Search...");
        searchField.setStyle(
                "-fx-background-radius: 15px; " +
                        "-fx-border-radius: 15px; " +
                        "-fx-padding: 15px; " +
                        "-fx-pref-width: 400px; " +  // Increased width
                        "-fx-font-size: 18px; " +    // Increased font size
                        "-fx-background-color: white; " +
                        "-fx-border-color: #cccccc;"
        );
        return searchField;
    }
    HBox getSearchBar(AutoCompleteTextField searchField, Button searchButton){
        HBox searchBar = new HBox(searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER);
        searchBar.setSpacing(10);
        searchBar.setPadding(new Insets(10));
        searchBar.setStyle(
                "-fx-background-color: #f0f0f0; " +
                        "-fx-border-radius: 15px; " +
                        "-fx-padding: 10px;"
        );
        return searchBar;
    }
    private void cleanUp(){
        carModelList=null;
        clientsList=null;
        scrollPane=null;
        primaryStage=null;
    }

}
