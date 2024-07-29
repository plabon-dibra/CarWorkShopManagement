package com.mypackage.carworkshopmanagement.manage.view;

import com.mypackage.carworkshopmanagement.manage.database.MyJDBC;
import com.mypackage.carworkshopmanagement.manage.model.*;
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

import java.util.*;
import java.util.stream.Collectors;
import static com.mypackage.carworkshopmanagement.manage.view.Home.*;

public class MakeBill extends Application {
    List<CarModel> carModelList;
    List<Client> clientsList;

    String globalPhoneNumber, globalFrameNumber;

    private List<HBox> problemSolutionBoxList = new ArrayList<>(); // To hold car input sections
    private List<HBox> procedureAndobservationList = new ArrayList<>();
    private List<HBox> materialsList = new ArrayList<>();

    private List<Procedures> proceduresList;
    private List<PartsAndMaterials> partsAndMaterialsList;
    private List<ProblemAndSolution> problemAndSolutionList;

    Bills bill;

    Set<String> procedureToShow = new HashSet<>();
    Set<String> problemToShow = new HashSet<>();
    Set<String> partCodeToShow = new HashSet<>();

    Map<String, String> partMap = new HashMap<>();
    Map<String, String>problemMap = new HashMap<>();

    int procedureCount=0, problemCount=0, partCount=0;

    public MakeBill(List<CarModel> carModelList, List<Client> clientsList) {
        this.clientsList = clientsList;
        this.carModelList = carModelList;
        globalPhoneNumber = "";
        globalFrameNumber = "";
        this.bill = null;
    }

    public MakeBill(List<CarModel> carModelList, List<Client> clientsList, String phoneNumber, String frameNumber) {
        this.clientsList = clientsList;
        this.carModelList = carModelList;
        this.globalPhoneNumber = phoneNumber;
        this.globalFrameNumber = frameNumber;
        this.bill = null;
    }

    public MakeBill(List<CarModel> carModelList, List<Client> clientsList, String phoneNumber, String frameNumber, Bills bill) {
        this.clientsList = clientsList;
        this.carModelList = carModelList;
        this.globalPhoneNumber = phoneNumber;
        this.globalFrameNumber = frameNumber;
        this.bill = bill;
    }

    @Override
    public void start(Stage primaryStage) {
//        for(int i=0;i<500;i++) System.out.println(generateUniqueRegistrationNumber());
        proceduresList = MyJDBC.getProcedureList();
        problemAndSolutionList = MyJDBC.getProblemAndSolutionList();
        partsAndMaterialsList = MyJDBC.getPartsAndMaterialsList();


        for (Procedures c : proceduresList) {
            procedureToShow.add(c.getProcedure());
        }

        assert partsAndMaterialsList != null;
        for (PartsAndMaterials c : partsAndMaterialsList) {
            partMap.put(c.getPartCode(),c.getPartsAndMaterial());
            partCodeToShow.add(c.getPartCode());
        }

        for (ProblemAndSolution c : problemAndSolutionList) {
            problemMap.put(c.getProblem(),c.getPossibleSolution());
            problemToShow.add(c.getProblem());
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
            Home display = new Home(carModelList, clientsList);
            display.start(primaryStage);
            cleanUp();
        });

        registerBtn.setOnAction(e -> {
            RegisterNewClient display = new RegisterNewClient(carModelList, clientsList);
            display.start(primaryStage);
            cleanUp();
        });
/////////////////////////////////////////////////////////////////////////////////////////////////

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

        Label headlineLabel = new Label("Make a Bill");
        headlineLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        headlineLabel.setAlignment(Pos.CENTER);


        Label frameNumberLabel = new Label("Car Frame Number:");
        frameNumberLabel.setStyle("-fx-font-size: 18px;");
        grid.setConstraints(frameNumberLabel, 0, 0);
        AutoCompleteTextField frameNumberInput = new AutoCompleteTextField();
        frameNumberInput.setPromptText("Enter Frame number");
        frameNumberInput.setStyle("-fx-font-size: 16px; ");
        frameNumberInput.setText(globalFrameNumber);
        VBox frameVbox = new VBox(frameNumberInput);
        frameVbox.setPadding(new Insets(5));
        frameVbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        grid.setConstraints(frameVbox, 1, 0);


        Set<String> frameNumbersToShow = new HashSet<>();
        for (CarModel c : carModelList) {
            frameNumbersToShow.add(c.getFrameNumber());
        }
        ObservableList<String> frameNumbers = FXCollections.observableArrayList(frameNumbersToShow);
        frameNumberInput.setEntries(frameNumbers);


        Label problemAndSolutionLabel = new Label("Problems and Solutions:");
        problemAndSolutionLabel.setStyle("-fx-font-size: 18px;");
        GridPane.setConstraints(problemAndSolutionLabel, 0, 3);
        VBox problemSolutionVBox = new VBox(5); // VBox to hold problem and solution input fields
        problemSolutionVBox.setPadding(new Insets(5));
        problemSolutionVBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");
        GridPane.setConstraints(problemSolutionVBox, 1, 3);
        Button addProblemAndSolutionButton = new Button("+");
        addProblemAndSolutionButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        addProblemAndSolutionButton.setOnAction(e -> addProblemAndSolution(problemSolutionVBox));

        addProblemAndSolutionButton.setOnAction(event -> {
            if(problemCount<4){
                addProblemAndSolution(problemSolutionVBox);
                problemCount++;
                messageLabel.setText("Problem and Solution Added Successfully!");
                messageLabel.setTextFill(Color.GREEN);
            }
            else{
                messageLabel.setText("You have reached the maximum number of problems!");
                messageLabel.setTextFill(Color.RED);
            }
        });

        GridPane.setConstraints(addProblemAndSolutionButton, 2, 3);

        Label procedureLabel = new Label("Procedures:");
        procedureLabel.setStyle("-fx-font-size: 18px;");
        grid.setConstraints(procedureLabel, 0, 2);
        VBox procedureVBox = new VBox(5); // VBox to hold car input fields
        procedureVBox.setPadding(new Insets(5));
        procedureVBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");
        grid.setConstraints(procedureVBox, 1, 2);
        Button addprocedureButton = new Button("+");
        addprocedureButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        addprocedureButton.setOnAction(event -> {
            if(procedureCount<6){
                addProcedureObservation(procedureVBox);
                procedureCount++;
                messageLabel.setText("Procedure Added Successfully!");
                messageLabel.setTextFill(Color.GREEN);
            }
            else{
                messageLabel.setText("You have reached the maximum number of procedure!");
                messageLabel.setTextFill(Color.RED);
            }
        });
        grid.setConstraints(addprocedureButton, 2, 2);


        Label materialsLabel = new Label("Part and Materials:");
        materialsLabel.setStyle("-fx-font-size: 18px;");
        GridPane.setConstraints(materialsLabel, 0, 4);
        VBox materialsVBox = new VBox(5); // VBox to hold car input fields
        materialsVBox.setPadding(new Insets(5));
        materialsVBox.setStyle("-fx-border-color: gray; -fx-border-width: 1; -fx-padding: 10;");
        GridPane.setConstraints(materialsVBox, 1, 4);
        Button addMaterialsButton = new Button("+");
        addMaterialsButton.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        addMaterialsButton.setOnAction(event -> {
            if(partCount<8){
                addMaterials(materialsVBox);
                partCount++;
                messageLabel.setText("Part and Materials Added Successfully!");
                messageLabel.setTextFill(Color.GREEN);
            }
            else{
                messageLabel.setText("You have reached the maximum number of materials!");
                messageLabel.setTextFill(Color.RED);
            }
        });
        GridPane.setConstraints(addMaterialsButton, 2, 4);


        if (bill != null) {
            String proceduresString = bill.getProcedureIds().trim();
            if(!proceduresString.isEmpty()){
                List<Integer> procedures = getIds(proceduresString);
                Map<Integer, Procedures> checkPro = new HashMap<>();
                for (Procedures p : proceduresList) {
                    checkPro.put(p.getId(), p);
                }
                for (Integer i : procedures) {
                    if (checkPro.containsKey(i)) {
                        addProcedureObservation(procedureVBox, checkPro.get(i));
                        procedureCount++;
                    }
                }
            }
            else{
                addProcedureObservation(procedureVBox);
                procedureCount++;
            }


            String problemsString = bill.getProblemIds().trim();
            if(!problemsString.isEmpty()){
                List<Integer> problems = getIds(problemsString);
                Map<Integer, ProblemAndSolution> checkProblem = new HashMap<>();
                for (ProblemAndSolution p : problemAndSolutionList) {
                    checkProblem.put(p.getId(), p);
                }
                for (Integer i : problems) {
                    if (checkProblem.containsKey(i)) {
                        addProblemAndSolution(problemSolutionVBox, checkProblem.get(i));
                        problemCount++;
                    }
                }
            }
            else{
                addProblemAndSolution(problemSolutionVBox);
                problemCount++;
            }



            String materialsString = bill.getPartsAndMaterialsIdsWithQuantity().trim();
            if(!materialsString.isEmpty()){
                List<String> idsAndQuantity = getIdsAndQuantity(materialsString);
                Map<Integer, PartsAndMaterials> checkpart = new HashMap<>();
                for (PartsAndMaterials p : partsAndMaterialsList) {
                    checkpart.put(p.getId(), p);
                }
                System.out.println(idsAndQuantity.size());
                for (String i : idsAndQuantity) {
                    System.out.println("Ids and Quantity: " + i);
                    List<Integer> tokens = getIds(i);
                    if (tokens.size() == 2) {
                        if (checkpart.containsKey(tokens.get(0))) {
                            addMaterials(materialsVBox, checkpart.get(tokens.get(0)), tokens.get(1));
                            partCount++;
                        }
                    }
                }
            }
            else{
                addMaterials(materialsVBox);
                partCount++;
            }
        } else {
            addProcedureObservation(procedureVBox);
            addProblemAndSolution(problemSolutionVBox);
            addMaterials(materialsVBox);
            partCount++;
            problemCount++;
            procedureCount++;
        }


        Label executorLabel = new Label("Executor:");
        executorLabel.setStyle("-fx-font-size: 18px;");
        GridPane.setConstraints(executorLabel, 0, 5);
        VBox executorVBox = new VBox(5); // VBox to hold car input fields

        TextField executorNameInput = new TextField();
        executorNameInput.setStyle("-fx-font-size: 16px; ");
        executorNameInput.setPrefWidth(310);
        executorNameInput.setPromptText("Executor Name");
        executorNameInput.setId("executorNameInput");
        TextField estimatedDurationInput = new TextField();
        estimatedDurationInput.setPromptText("Estimated Duration");
        estimatedDurationInput.setId("estimatedDurationInput");
        estimatedDurationInput.setStyle("-fx-font-size: 16px; ");
        estimatedDurationInput.setPrefWidth(310);
        if (bill != null) {
            executorNameInput.setText(bill.getExecutor());
            estimatedDurationInput.setText(bill.getEstimatedDuration());
        }

        HBox executorHBox = new HBox(10, executorNameInput, estimatedDurationInput);
        executorHBox.setPadding(new Insets(5));
        executorHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        executorVBox.getChildren().add(executorHBox);
        GridPane.setConstraints(executorVBox, 1, 5);

        Button registerButton = new Button("Submit");
        registerButton.setAlignment(Pos.CENTER);
        registerButton.setStyle("-fx-font-size: 20px; ");
        registerButton.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
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
//            String phoneNumber = phoneNumberInput.getText().trim();
            String frameNumber = frameNumberInput.getText().trim();
            String executorName = executorNameInput.getText();
            String estimatedDuration = estimatedDurationInput.getText();

            if ( frameNumber.isEmpty() || executorName.isEmpty() || estimatedDuration.isEmpty()) {
                messageLabel.setText("Fill the Blanks!");
                messageLabel.setTextFill(Color.RED);
                return;
            }

//////////////////////////////////// Checking Availability //////////////////////////////////
            CarModel selectedCarModel = null;
            for (CarModel carModel : carModelList) {
                if (carModel.getFrameNumber().equalsIgnoreCase(frameNumber)) {
                    selectedCarModel = carModel;
                    globalFrameNumber = selectedCarModel.getFrameNumber();
                    globalPhoneNumber = selectedCarModel.getOwner().getPhoneNo();
                    break;
                }
            }
//
//            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
////            System.out.println("Phone Number        : " + phoneNumber);
//            System.out.println("Frame Number        : " + frameNumber);
//            System.out.println("Executor Name       : " + executorName);
//            System.out.println("Estimated Duration  : " + estimatedDuration);
//            System.out.println("////////////////////////////////////////////////////");

            if(selectedCarModel == null){
                messageLabel.setText("Car not found!");
                messageLabel.setTextFill(Color.RED);
                return;
            }
            else {
                messageLabel.setText("Car Successfully found!");
                messageLabel.setTextFill(Color.GREEN);
                System.out.println("_/ Car found!");
            }
////////////////////////////////////////////////////////////////////////////////
            List<Procedures> localPrecuduresList = new ArrayList<>();
            for (HBox procedureHBox : procedureAndobservationList) {
                TextField procedureInput = (TextField) procedureHBox.lookup("#procudureInput");
                TextField observationInput = (TextField) procedureHBox.lookup("#observationInput");
                TextField priceWithTaxInput = (TextField) procedureHBox.lookup("#priceInput");

                String procedure = procedureInput.getText().trim();
                String observation = observationInput.getText().trim();
                String priceWithTaxString = priceWithTaxInput.getText().trim();

                if (procedure.isEmpty() || observation.isEmpty() || priceWithTaxString.isEmpty()) {
                    messageLabel.setText("Please fill out all problem and solution fields.");
                    messageLabel.setTextFill(Color.RED);
                    return; // Exit method if any problem or solution field is empty
                }
                double priceWithTax;

                try {
                    priceWithTax = Double.parseDouble(priceWithTaxString);
//                    System.out.println("The double value is: " + priceWithTax);

                } catch (NumberFormatException er) {
//                    System.out.println("Invalid string for conversion to double.");
                    messageLabel.setText("Price with Tax should be double value!");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }
                // need to store in database
                localPrecuduresList.add(new Procedures(procedure, observation, priceWithTax));
            }

            List<ProblemAndSolution> localProblemAndSolutionList = new ArrayList<>();
            for (HBox problemSolutionHBox : problemSolutionBoxList) {
                TextField problemInput = (TextField) problemSolutionHBox.lookup("#problemInput");
                TextField solutionInput = (TextField) problemSolutionHBox.lookup("#solutionInput");

                String problem = problemInput.getText().trim();
                String solution = solutionInput.getText().trim();

                if (problem.isEmpty() || solution.isEmpty()) {
                    messageLabel.setText("Please fill out all problem and solution fields.");
                    messageLabel.setTextFill(Color.RED);
                    return; // Exit method if any problem or solution field is empty
                }

                // need to store in database
                localProblemAndSolutionList.add(new ProblemAndSolution(problem, solution));
            }

            List<PartsAndMaterials> localPartAndMaterialsList = new ArrayList<>();
            for (HBox procedureHBox : materialsList) {
                TextField materialsInput = (TextField) procedureHBox.lookup("#materialsInput");
                TextField partCodeInput = (TextField) procedureHBox.lookup("#partCodeInput");
                TextField priceWithTaxInput = (TextField) procedureHBox.lookup("#priceInput");
                TextField quantityInput = (TextField) procedureHBox.lookup("#quantityInput");

                String materials = materialsInput.getText().trim();
                String partCode = partCodeInput.getText().trim();
                String priceWithTaxString = priceWithTaxInput.getText().trim();
                String quantityString = quantityInput.getText().trim();


                if (materials.isEmpty() || partCode.isEmpty() || priceWithTaxString.isEmpty() || quantityString.isEmpty()) {
                    messageLabel.setText("Please fill out all problem and solution fields.");
                    messageLabel.setTextFill(Color.RED);
                    return; // Exit method if any problem or solution field is empty
                }
                double priceWithTax;
                try {
                    priceWithTax = Double.parseDouble(priceWithTaxString);
//                    System.out.println("The double value is: " + priceWithTax);

                } catch (NumberFormatException er) {
//                    System.out.println("Invalid string for conversion to double.");
                    messageLabel.setText("Price with Tax should be double value!");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }

                int quantity;
                try {
                    quantity = Integer.parseInt(quantityString);
//                    System.out.println("The double value is: " + priceWithTax);

                } catch (NumberFormatException er) {
//                    System.out.println("Invalid string for conversion to Integer.");
                    messageLabel.setText("Quantity should be an integer value!");
                    messageLabel.setTextFill(Color.RED);
                    return;
                }
                // need to store in database
                localPartAndMaterialsList.add(new PartsAndMaterials(materials, partCode, priceWithTax, quantity));
            }
//            localPartAndMaterialsList.get(0).set

            for (int i = 0; i < localProblemAndSolutionList.size(); i++) {
                ProblemAndSolution problemAndSolution = localProblemAndSolutionList.get(i);
                int status = MyJDBC.insertProblemAndSolution(problemAndSolution);
//                if (status == 1) {
//                    System.out.println("problem solved");
//                }
                problemAndSolution = MyJDBC.getProblemAndSolution(problemAndSolution.getProblem());
                if (problemAndSolution != null) {
                    localProblemAndSolutionList.get(i).setId(problemAndSolution.getId());
                }
            }

            for (int i = 0; i < localPrecuduresList.size(); i++) {
                Procedures localPrecudures = localPrecuduresList.get(i);
                int status = MyJDBC.insertProcedures(localPrecudures);
//                if (status == 1) {
//                    System.out.println("problem solved");
//                }
                localPrecudures = MyJDBC.getProcedure(localPrecudures.getProcedure());
                if (localPrecudures != null) {
                    localPrecuduresList.get(i).setId(localPrecudures.getId());
                }
            }

            for (int i = 0; i < localPartAndMaterialsList.size(); i++) {
                PartsAndMaterials localPartAndMaterials = localPartAndMaterialsList.get(i);
                int status = MyJDBC.insertMaterials(localPartAndMaterials);
//                if (status == 1) {
//                    System.out.println("problem solved");
//                }
                localPartAndMaterials = MyJDBC.getMaterials(localPartAndMaterials.getPartCode());
                if (localPartAndMaterials != null) {
                    localPartAndMaterialsList.get(i).setId(localPartAndMaterials.getId());
                }
            }


//            System.out.println("===============>Congratulations! All cases passed!<==============");

//            executorName
//            estimatedDuration
            String procedureIds = "";
            String problemIds = "";
            String partIdsAndQuantity = "";

//            System.out.println("========== Resolving Procedure Ids ============");
            for (Procedures p : localPrecuduresList) {
//                System.out.println("procedure id: " + p.getId());
                procedureIds = procedureIds + p.getId() + ",";
            }
//            System.out.println("-----------------------------------------------");

//            System.out.println("========== Resolving Problem Ids ============");
            for (ProblemAndSolution p : localProblemAndSolutionList) {
//                System.out.println("procedure id: " + p.getId());
                problemIds = problemIds + p.getId() + ",";
            }
//            System.out.println("-----------------------------------------------");

//            System.out.println("========== Resolving Parts and Materials Ids and Quantity ============");
            for (PartsAndMaterials p : localPartAndMaterialsList) {
//                System.out.println("Part id: " + p.getId() + ", Quantity: " + p.getQuantity());
                partIdsAndQuantity = partIdsAndQuantity + p.getId() + "," + p.getQuantity() + ";";
            }
//            System.out.println("-----------------------------------------------");

            Bills bill = new Bills(globalPhoneNumber, globalFrameNumber, procedureIds, problemIds, partIdsAndQuantity, executorName, estimatedDuration);
//            System.out.println(">>>>>>>>>>>>>>>Bill Created!<<<<<<<<<<<<<<<<<<");
//            System.out.println("Executor          : " + executorName);
//            System.out.println("Estimated Duration: " + estimatedDuration);
//            System.out.println("Frame Number      : " + globalFrameNumber);
//            System.out.println("problemIds        : " + problemIds);
//            System.out.println("procedureIds      : " + procedureIds);
//            System.out.println("partIdsAndQuantity: " + partIdsAndQuantity);
//            System.out.println("==============================================");

            int status = MyJDBC.insertBill(bill);
//            System.out.println("Status: " + status);

            try {
                PDF_Form display = new PDF_Form(carModelList, clientsList, executorName, estimatedDuration, selectedCarModel, localProblemAndSolutionList, localPrecuduresList, localPartAndMaterialsList);
                display.start(primaryStage);
                cleanUp();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            messageLabel.setText("Client registered successfully!");
            messageLabel.setTextFill(Color.GREEN);
        });


        Button clearButton = new Button("Clear");
        clearButton.setAlignment(Pos.CENTER);
        clearButton.setStyle("-fx-font-size: 20px; ");
        clearButton.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
        clearButton.setOnMouseEntered(e -> {
            clearButton.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
            clearButton.setStyle("-fx-font-size: 20px;");
        });

        clearButton.setOnMouseExited(e -> {
            clearButton.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
            clearButton.setStyle("-fx-font-size: 20px;");
        });

        clearButton.setOnAction(e -> {
            frameNumberInput.clear();
            procedureVBox.getChildren().clear(); // Clear all car input fields
//            addCarInputFields(carVBox); // Add a new set of car input fields
            problemSolutionVBox.getChildren().clear(); // Clear all problem and solution input fields
            materialsVBox.getChildren().clear();
            addMaterials(materialsVBox);
            addProcedureObservation(procedureVBox);
            addProblemAndSolution(problemSolutionVBox); // Add a new set of problem and solution input fields
            messageLabel.setText(""); // Clear any message

            executorNameInput.setText("");
            estimatedDurationInput.setText("");
        });


        HBox buttonBox = new HBox(10, registerButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER);
        GridPane.setConstraints(buttonBox, 0, 6);
        GridPane.setColumnSpan(buttonBox, 3);

        // Add all UI elements to the grid
        grid.getChildren().addAll(
                frameNumberLabel, frameVbox,
                procedureLabel, procedureVBox, addprocedureButton,
                materialsLabel, materialsVBox, addMaterialsButton,
                problemAndSolutionLabel, problemSolutionVBox, addProblemAndSolutionButton,
                executorLabel, executorVBox,
                buttonBox
        );
        grid.setAlignment(Pos.CENTER);

        // Main layout
        VBox layout = new VBox(10, headlineLabel, grid, messageLabel);
        layout.setPrefSize(screenWidth - navBarWidth, screenHeight - 70);
        layout.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(screenHeight - 50);
        scrollPane.setStyle("-fx-background-color:transparent;");

        mainBody.getChildren().addAll(scrollPane);
        HBox root = new HBox(navigationBar, mainBody);
        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Make a Bill");
        primaryStage.show();
    }

    private List<Integer> getIds(String problemString) {

        String[] numbers = problemString.split(",");
        List<String> stringList = Arrays.asList(numbers);

        // Converting to a List of Integers
        List<Integer> integerList = stringList.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return integerList;
    }

    private List<String> getIdsAndQuantity(String materialsString) {
        String[] numbers = materialsString.split(";");
        List<String> stringList = Arrays.asList(numbers);
        return stringList;
    }

    private void addMaterials(VBox materialsVBox, PartsAndMaterials partsAndMaterials, Integer integer) {
        TextField materialsInput = new TextField();
        materialsInput.setPromptText("Part and Materials");
        materialsInput.setId("materialsInput");
        materialsInput.setStyle("-fx-font-size: 16px; ");
        materialsInput.setPrefWidth(300);
        materialsInput.setText(partsAndMaterials.getPartsAndMaterial());

        TextField partCodeInput = new TextField();
        partCodeInput.setPromptText("Part Code");
        partCodeInput.setId("partCodeInput");
        partCodeInput.setStyle("-fx-font-size: 16px; ");
        partCodeInput.setPrefWidth(100);
        partCodeInput.setText(partsAndMaterials.getPartCode());

        TextField quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");
        quantityInput.setId("quantityInput");
        quantityInput.setStyle("-fx-font-size: 16px; ");
        quantityInput.setPrefWidth(80);
        quantityInput.setText("" + integer);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Price with Tax");
        priceInput.setId("priceInput");
        priceInput.setStyle("-fx-font-size: 16px; ");
        priceInput.setPrefWidth(120);
        priceInput.setText("" + partsAndMaterials.getPriceWithTax());


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

        HBox materialsHBox = new HBox(10, partCodeInput, materialsInput, quantityInput, priceInput, delete);
        materialsHBox.setPadding(new Insets(5));
        materialsHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        materialsVBox.getChildren().add(materialsHBox);
        materialsList.add(materialsHBox);
        delete.setOnAction(e -> {
            materialsVBox.getChildren().remove(materialsHBox);
            materialsList.remove(materialsHBox);
            partCount--;
        });
    }

    private void addProblemAndSolution(VBox problemSolutionVBox, ProblemAndSolution problemAndSolution) {
        TextField problemInput = new TextField();
        problemInput.setPromptText("Problem");
        problemInput.setId("problemInput");
        problemInput.setStyle("-fx-font-size: 16px; ");
        problemInput.setPrefWidth(310);
        problemInput.setText(problemAndSolution.getProblem());

        TextField solutionInput = new TextField();
        solutionInput.setPromptText("Solution");
        solutionInput.setId("solutionInput");
        solutionInput.setStyle("-fx-font-size: 16px; ");
        solutionInput.setPrefWidth(310);
        solutionInput.setText(problemAndSolution.getPossibleSolution());

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

        HBox problemSolutionHBox = new HBox(10, problemInput, solutionInput,delete);
        problemSolutionHBox.setPadding(new Insets(5));
        problemSolutionHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        problemSolutionVBox.getChildren().add(problemSolutionHBox);
        problemSolutionBoxList.add(problemSolutionHBox);

        delete.setOnAction(e -> {
            problemSolutionVBox.getChildren().remove(problemSolutionHBox);
            problemSolutionBoxList.remove(problemSolutionHBox);
            problemCount--;
        });
    }

    private void addProcedureObservation(VBox procedureVBox, Procedures procedure) {
        AutoCompleteTextField procedureInput = new AutoCompleteTextField();
        procedureInput.setStyle("-fx-font-size: 16px; ");
        procedureInput.setPrefWidth(240);
        procedureInput.setPromptText("Procedure");
        procedureInput.setId("procudureInput");
        procedureInput.setText(procedure.getProcedure());


        TextField observationInput = new TextField();
        observationInput.setPromptText("Observation");
        observationInput.setId("observationInput");
        observationInput.setStyle("-fx-font-size: 16px; ");
        observationInput.setPrefWidth(240);
        observationInput.setText(procedure.getObservation());

        TextField priceInput = new TextField();
        priceInput.setPromptText("Price with Tax");
        priceInput.setId("priceInput");
        priceInput.setStyle("-fx-font-size: 16px; ");
        priceInput.setPrefWidth(130);
        priceInput.setText("" + procedure.getPriceWithTax());



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

        HBox procudureHBox = new HBox(10, procedureInput, observationInput, priceInput, delete);
        procudureHBox.setPadding(new Insets(5));
        procudureHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        procedureVBox.getChildren().add(procudureHBox);
        procedureAndobservationList.add(procudureHBox);

        delete.setOnAction(e -> {
            procedureVBox.getChildren().remove(procudureHBox);
            procedureAndobservationList.remove(procudureHBox);
            procedureCount--;
        });

    }

    private void addMaterials(VBox materialsVBox) {
        AutoCompleteTextField materialsInput = new AutoCompleteTextField();
        materialsInput.setPromptText("Part and Materials");
        materialsInput.setId("materialsInput");
        materialsInput.setStyle("-fx-font-size: 16px; ");
        materialsInput.setPrefWidth(300);
//        for (String s: materialsToShow){
//            System.out.println("mmmmmmmmmmmmmmmm=>>>>>>>>"+s);
//        }




        AutoCompleteTextField partCodeInput = new AutoCompleteTextField();
        partCodeInput.setPromptText("Part Code");
        partCodeInput.setId("partCodeInput");
        partCodeInput.setStyle("-fx-font-size: 16px; ");
        partCodeInput.setPrefWidth(100);
        ObservableList<String> pro = FXCollections.observableArrayList(partCodeToShow);
        partCodeInput.setEntries(pro);

        partCodeInput.setOnTextChanged(text -> {
//            System.out.println("Current text: " + text);
            if(partMap.containsKey(text)){
                materialsInput.setText(partMap.get(text));
            }
        });



        TextField quantityInput = new TextField();
        quantityInput.setPromptText("Quantity");
        quantityInput.setId("quantityInput");
        quantityInput.setStyle("-fx-font-size: 16px; ");
        quantityInput.setPrefWidth(80);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Price with Tax");
        priceInput.setId("priceInput");
        priceInput.setStyle("-fx-font-size: 16px; ");
        priceInput.setPrefWidth(120);

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

        HBox materialsHBox = new HBox(10, partCodeInput, materialsInput, quantityInput, priceInput,delete);
        materialsHBox.setPadding(new Insets(5));
        materialsHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        materialsVBox.getChildren().add(materialsHBox);
        materialsList.add(materialsHBox);
        delete.setOnAction(e -> {
            materialsVBox.getChildren().remove(materialsHBox);
            materialsList.remove(materialsHBox);
            partCount--;
        });
    }

    private void addProblemAndSolution(VBox problemSolutionVBox) {
        AutoCompleteTextField problemInput = new AutoCompleteTextField();
        problemInput.setPromptText("Problem");
        problemInput.setId("problemInput");
        problemInput.setStyle("-fx-font-size: 16px; ");
        problemInput.setPrefWidth(310);



        ObservableList<String> pro = FXCollections.observableArrayList(problemToShow);
        problemInput.setEntries(pro);

        TextField solutionInput = new TextField();
        solutionInput.setPromptText("Solution");
        solutionInput.setId("solutionInput");
        solutionInput.setStyle("-fx-font-size: 16px; ");
        solutionInput.setPrefWidth(310);


        problemInput.setOnTextChanged(text -> {
//            System.out.println("Current text: " + text);
            if(problemMap.containsKey(text)){
                solutionInput.setText(problemMap.get(text));
            }
        });

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


        HBox problemSolutionHBox = new HBox(10, problemInput, solutionInput,delete);
        problemSolutionHBox.setPadding(new Insets(5));
        problemSolutionHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        problemSolutionVBox.getChildren().add(problemSolutionHBox);
        problemSolutionBoxList.add(problemSolutionHBox);

        delete.setOnAction(e -> {
            problemSolutionVBox.getChildren().remove(problemSolutionHBox);
            problemSolutionBoxList.remove(problemSolutionHBox);
            problemCount--;
        });
    }

    private void addProcedureObservation(VBox procedureVBox) {
        AutoCompleteTextField procedureInput = new AutoCompleteTextField();
        procedureInput.setStyle("-fx-font-size: 16px; ");
        procedureInput.setPrefWidth(240);
        procedureInput.setPromptText("Procedure");
        procedureInput.setId("procudureInput");

//        for (String s:procedureToShow){
//            System.out.println("=>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>procedure:"+s);
//        }
        ObservableList<String> pro = FXCollections.observableArrayList(procedureToShow);
        procedureInput.setEntries(pro);


        TextField observationInput = new TextField();
        observationInput.setPromptText("Observation");
        observationInput.setId("observationInput");
        observationInput.setStyle("-fx-font-size: 16px; ");
        observationInput.setPrefWidth(240);

        TextField priceInput = new TextField();
        priceInput.setPromptText("Price with Tax");
        priceInput.setId("priceInput");
        priceInput.setStyle("-fx-font-size: 16px; ");
        priceInput.setPrefWidth(130);


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



        HBox procudureHBox = new HBox(10, procedureInput, observationInput, priceInput,delete);
        procudureHBox.setPadding(new Insets(5));
        procudureHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), Insets.EMPTY)));
        procedureVBox.getChildren().add(procudureHBox);
        procedureAndobservationList.add(procudureHBox);


        delete.setOnAction(e -> {
            procedureVBox.getChildren().remove(procudureHBox);
            procedureAndobservationList.remove(procudureHBox);
            procedureCount--;
        });
    }

    private void cleanUp(){
        problemToShow=null;
        partCodeToShow=null;
        procedureToShow=null;

        carModelList=null;
        clientsList=null;
        partMap=null;
        problemMap=null;

        proceduresList=null;
        partsAndMaterialsList=null;
        problemAndSolutionList=null;
    }
}