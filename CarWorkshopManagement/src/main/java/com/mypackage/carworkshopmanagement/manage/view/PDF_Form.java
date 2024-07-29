package com.mypackage.carworkshopmanagement.manage.view;

import com.mypackage.carworkshopmanagement.manage.model.*;
import com.itextpdf.io.image.ImageData;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static com.mypackage.carworkshopmanagement.manage.view.Home.*;
import static com.mypackage.carworkshopmanagement.manage.view.Home.addHoverEffect;
import static javafx.scene.paint.Color.*;

public class PDF_Form extends Application {
    List<ProblemAndSolution> problemAndSolutionList;
    List<PartsAndMaterials> partsAndMaterialsList;
    List<Procedures>proceduresList;
    List<CarModel> carModelList;
    List<Client> clientsList;
    List<String>extraProblemList;
    String executor;
    CarModel car;
    String estimatedDuration;
    public PDF_Form(){

    }
    public PDF_Form(List<CarModel> carModelList, List<Client> clientsList, String executor, String estimatedDuration, CarModel car, List<ProblemAndSolution> problemAndSolutionList, List<Procedures> localPrecuduresList, List<PartsAndMaterials> localPartAndMaterialsList){
        this.carModelList =carModelList;
        this.clientsList =clientsList;
        this.executor = executor;
        this.car = car;
        this.problemAndSolutionList = problemAndSolutionList;
        this.estimatedDuration =estimatedDuration;
        this.partsAndMaterialsList = localPartAndMaterialsList;
        this.proceduresList = localPrecuduresList;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

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
        registerBtn.setOnAction(e -> {
            RegisterNewClient display = new RegisterNewClient(carModelList, clientsList);
            display.start(primaryStage);
            cleanUp();
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//        partsAndMaterialsList.add(new PartsAndMaterials(1,"asdff","qwer",2344));
        Background backgroundSilverColor = new Background(new BackgroundFill(Color.SILVER, null, null));
        Background background_f0f0f0 = new Background(new BackgroundFill(Color.valueOf("#f0f0f0"), null, null));
        Background backgroundGrayColor = new Background(new BackgroundFill(GRAY, null, null));
        Background backgroundWhiteColor = new Background(new BackgroundFill(WHITE, null, null));

        Font defaultFont = new Font(11);
        // Create BorderPane layout
        System.out.println("PDF");
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(backgroundGrayColor);


        int boxSizeY = (int) (screenHeight-50);
        int boxSizeX = (int) ((int) boxSizeY * (0.75));




        //---------------------------------------------------------------------------------
        Label companyNameLabel = new Label("DEVIZ\n            AUTOCLEVER SRL");
        companyNameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label noteId = new Label("CUI:30732474           J40/11264/2012");
        noteId.setFont(new Font(11));
        Label address = new Label("Adresa Pct Lucru: Strada Voineasa, Nr.16-20,\nSector 3, Bucuresti");
        noteId.setFont(new Font(11));
        Label others = new Label("IBAN: RO05 BTRL 0470 1202 W949 69XX\nBANCA: TRANSILVANIA BURURESTI");
        others.setFont(new Font(11));
        Label telephone = new Label("TEL: 0726198574 / 0730554211");
        telephone.setFont(new Font(11));
        VBox intro = new VBox(companyNameLabel,noteId,address,others,telephone);
        intro.setMaxWidth(boxSizeX/2);
        intro.setMaxHeight(170);
        intro.setMinWidth(boxSizeX/2);
        intro.setMinHeight(170);
        intro.setBackground(background_f0f0f0);
        intro.setPadding(new Insets(10));
        //---------------------------------------------------------------------------------



        //---------------------------------------------------------------------------------
        GridPane details = new GridPane();
        details.setPadding(new Insets(10));
        details.setHgap(20);

        Label brandLabel = new Label("Brand / Type of Car");
        Label brandDataLabel = new Label(car.getBrand());

        Label registrationNumberLabel = new Label("Registration Number");
        Label registrationNumberDataLabel = new Label(car.getOwner().getRegNo());
        Label ownerLabel = new Label("Owner");
        Label ownerDataLabel = new Label(car.getOwner().getName());
        Label frameNumberLabel = new Label("Frame Number");
        Label frameNumberDataLabel = new Label(car.getFrameNumber());
        Label milageLabel = new Label("Milage");
        Label milageDataLabel = new Label(""+car.getMilage());
        Label customerPhoneLabel = new Label("Customer Phone");
        Label customerPhoneDataLabel = new Label(car.getOwner().getPhoneNo());
        Label executorLabel = new Label("Executor");
        Label executorDataLabel = new Label(executor);
        Label currentDateLabel = new Label("Date");
        Label currentDateDataLabel = new Label(LocalDate.now().toString());


        details.add(brandLabel, 0,0);
        details.add(brandDataLabel, 1,0);
        details.add(registrationNumberLabel, 0,1);
        details.add(registrationNumberDataLabel, 1,1);
        details.add(ownerLabel, 0,2);
        details.add(ownerDataLabel, 1,2);
        details.add(frameNumberLabel, 0,3);
        details.add(frameNumberDataLabel, 1,3);
        details.add(milageLabel, 0,4);
        details.add(milageDataLabel, 1,4);
        details.add(customerPhoneLabel, 0,5);
        details.add(customerPhoneDataLabel, 1,5);
        details.add(executorLabel, 0,6);
        details.add(executorDataLabel, 1,6);
        details.add(currentDateLabel, 0,7);
        details.add(currentDateDataLabel, 1,7);

        VBox table1Container = new VBox(details);
        table1Container.setMaxWidth(boxSizeX/2);
        table1Container.setMaxHeight(170);
        table1Container.setMinWidth(boxSizeX/2);
        table1Container.setMinHeight(170);
        table1Container.setBackground(backgroundSilverColor);

        HBox introAndTable1Container = new HBox(intro,table1Container);
        introAndTable1Container.setMaxWidth(boxSizeX);
        introAndTable1Container.setMaxHeight(170);
        //------------------------------------------------------------------







        //------------------------------------------------------------------
        Label problemMessageLabel = new Label("Repairs requested by the customer:");
        problemMessageLabel.setPadding(new Insets(0,3,0,3));
        problemMessageLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        VBox repairsRequestedBox = new VBox();
        repairsRequestedBox.getChildren().add(problemMessageLabel);
        int ind=0;

        VBox requests = new VBox();
        requests.setMaxHeight(65);
        requests.setMinHeight(65);
        requests.setMaxWidth(screenWidth);
        requests.setMinWidth(screenWidth);
        requests.setPadding(new Insets(0,3,0,3));
        for(ProblemAndSolution data:problemAndSolutionList){
            ind++;
            String message = ""+ind+". Problem: "+data.getProblem()+"; possible solution: "+data.getPossibleSolution();
            Label messageLabel = new Label(message);
            messageLabel.setFont(new Font(10));
            requests.getChildren().add(messageLabel);
        }
        repairsRequestedBox.getChildren().add(requests);

        Label messageLabel = new Label("Estimated duration of repairs: "+estimatedDuration+".   Client acceptance/Signature __________");
        messageLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        messageLabel.setPadding(new Insets(0,3,0,3));
        repairsRequestedBox.getChildren().add(messageLabel);
        repairsRequestedBox.setMaxWidth(boxSizeX);
        repairsRequestedBox.setMaxHeight(100);
        repairsRequestedBox.setMinWidth(boxSizeX);
        repairsRequestedBox.setMinHeight(100);
        repairsRequestedBox.setBackground(backgroundWhiteColor);
        //------------------------------------------------------------------
















        //==========================================================
        TableView<Procedures> tableView2 = new TableView<>();
        tableView2.setStyle("-fx-font-size: 11px;");
        tableView2.setPadding(new Insets(2));
        // Define columns
        TableColumn<Procedures, String> productNameCol = new TableColumn<>("Name of Procedure");
        TableColumn<Procedures, String> observationsCol = new TableColumn<>("Observations");
        TableColumn<Procedures, Double> priceCol = new TableColumn<>("Price With Tax");
        // Set cell value factories
        productNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProcedure()));
        observationsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObservation()));
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPriceWithTax()).asObject());

//        productNameCol.setSortable(false);
//        observationsCol.setSortable(false);
//        priceCol.setSortable(false);
        tableView2.setSelectionModel(null);


        double table2BoxSize = (boxSizeX-96)/2;
        productNameCol.setMaxWidth(table2BoxSize);
        productNameCol.setMinWidth(table2BoxSize);
        observationsCol.setMaxWidth(table2BoxSize);
        observationsCol.setMinWidth(table2BoxSize);
        priceCol.setMaxWidth(90);
        priceCol.setMinWidth(90);
        tableView2.getColumns().addAll(productNameCol,observationsCol,priceCol);
        VBox table2Container = new VBox(tableView2);
        table2Container.setMaxWidth(boxSizeX);
        table2Container.setMaxHeight(170);
        table2Container.setMinWidth(boxSizeX);
        table2Container.setMinHeight(170);
        //---------------------------------------------------------------------------------
        double totalPriceInTable2 = 0.00;
        for(Procedures p:proceduresList){
            totalPriceInTable2+=p.getPriceWithTax();
        }
        Label totalLabel  = new Label("Total Price for workmanship: ");
        totalLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        totalLabel.setPrefSize(boxSizeX-92, 20);
        totalLabel.setPadding(new Insets(0,0,10,0));
        totalLabel.setAlignment(Pos.BASELINE_RIGHT);
        Label totalPriceInTable2Label = new Label("" + totalPriceInTable2);
        totalPriceInTable2Label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        HBox totalPriceBox = new HBox(totalLabel,totalPriceInTable2Label);
        totalPriceBox.setPrefSize(boxSizeX,20);
        tableView2.getItems().addAll(proceduresList);
        tableView2.setSelectionModel(null);
        //============================================














        //---------------------------------------------------------------------------------
        TableView<PartsAndMaterials> tableView3 = new TableView<>();
        tableView3.setStyle("-fx-font-size: 11px;");
        tableView3.setPadding(new Insets(2));
//        tableView3.setPadding(new Insets(10));
        // Define columns
        TableColumn<PartsAndMaterials, String> partsAndMaterialsCol = new TableColumn<>("Parts and Materials");
        TableColumn<PartsAndMaterials, String> partCodeCol = new TableColumn<>("Part Code");
        TableColumn<PartsAndMaterials, Integer> quantityCol = new TableColumn<>("Quantity");
        TableColumn<PartsAndMaterials, Double> price3Col = new TableColumn<>("Price with Tax");
        // Set cell value factories
        partsAndMaterialsCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartsAndMaterial()));
        partCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPartCode()));
        quantityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        price3Col.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPriceWithTax()).asObject());

        double colSize = (boxSizeX-(boxSizeX/3))/3;
        double partsize = (boxSizeX-90)/2-4;
        partsAndMaterialsCol.setMaxWidth(partsize);
        partsAndMaterialsCol.setMinWidth(partsize);
        partsize = (boxSizeX-partsize-90)/2-3;
        partCodeCol.setMaxWidth(partsize);
        partCodeCol.setMinWidth(partsize);
        quantityCol.setMaxWidth(partsize);
        quantityCol.setMinWidth(partsize);
        price3Col.setMaxWidth(90);
        price3Col.setMinWidth(90);
        tableView3.getColumns().addAll(partsAndMaterialsCol,partCodeCol,quantityCol,price3Col);

        VBox table3Container = new VBox(tableView3);
        table3Container.setMaxWidth(boxSizeX);
        table3Container.setMaxHeight(220);
        table3Container.setMinWidth(boxSizeX);
        table3Container.setMinHeight(220);
        table3Container.setMaxWidth(boxSizeX);
        table2Container.setMinWidth(boxSizeX);
        tableView3.getItems().setAll(partsAndMaterialsList);
        tableView3.setSelectionModel(null);
        //---------------------------------------------------------------------------------

        //============================================
        double totalPriceInTable3 = 0.00;
        for(PartsAndMaterials p:partsAndMaterialsList){
            totalPriceInTable3+=p.getPriceWithTax();
        }

        Label totalLabel3  = new Label("Total Price for parts: ");
        totalLabel3.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        totalLabel3.setPrefSize(boxSizeX-92, 20);
        totalLabel3.setPadding(new Insets(0,0,10,0));
        totalLabel3.setAlignment(Pos.BASELINE_RIGHT);

        Label totalPriceInTable3Label = new Label(""+totalPriceInTable3);
        totalPriceInTable3Label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        HBox totalPriceBox3 = new HBox(totalLabel3,totalPriceInTable3Label);
        totalPriceBox3.setPrefSize(boxSizeX,20);
        //============================================

//        "extra problems found during the repairing process ……".

        Label extraProblemMessageLabel = new Label("Extra problems found during the repairing process ……\n1._______________________________________________________________________________________\n2._______________________________________________________________________________________");
        extraProblemMessageLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        VBox extraProblemBox = new VBox();
        extraProblemBox.getChildren().add(extraProblemMessageLabel);

//        ind=0;
//        for(String data:extraProblemList){
//            ind++;
//            String message = ""+ind+". "+data;
//            messageLabel = new Label(message);
//            messageLabel.setFont(new Font(10));
//            extraProblemBox.getChildren().add(messageLabel);
//        }



        double total =  totalPriceInTable2+totalPriceInTable3;
        Label finalTotalLabel  = new Label("Total Price:  "+total);
        finalTotalLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");
        finalTotalLabel.setPadding(new Insets(0,3,0,3));
        finalTotalLabel.setPrefSize(screenWidth/3, 30);
        finalTotalLabel.setAlignment(Pos.BASELINE_RIGHT);
        extraProblemBox.setPadding(new Insets(0,3,0,3));

        VBox footer = new VBox(extraProblemBox,finalTotalLabel);
        footer.setMaxWidth(boxSizeX);
        footer.setMaxHeight(200);
        footer.setMinWidth(boxSizeX);
        footer.setMinHeight(200);
        footer.setBackground(backgroundWhiteColor);

        //============================================



        VBox mainBox = new VBox(introAndTable1Container,repairsRequestedBox,table2Container,totalPriceBox,table3Container,totalPriceBox3,footer);
        mainBox.setBackground(backgroundWhiteColor);
        mainBox.setMaxHeight(boxSizeY);
        mainBox.setMaxWidth(boxSizeX);




        borderPane.setCenter(mainBox);
        borderPane.setLeft(navigationBar);


        Button printButton = new Button(" Save as PDF ");
        printButton.setAlignment(Pos.CENTER);
        printButton.setStyle("-fx-font-size: 20px; ");
        printButton.setBackground(new Background(new BackgroundFill(Color.SILVER,new CornerRadii(10), Insets.EMPTY)));
        printButton.setOnMouseEntered(e -> {
            printButton.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, new CornerRadii(10), Insets.EMPTY)));
            printButton.setStyle("-fx-font-size: 20px;");
        });
        printButton.setOnMouseExited(e -> {
            printButton.setBackground(new Background(new BackgroundFill(Color.SILVER, new CornerRadii(10), Insets.EMPTY)));
            printButton.setStyle("-fx-font-size: 20px;");
        });
        printButton.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(printButton);
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(screenHeight);
        vBox.setAlignment(Pos.CENTER);
        borderPane.setRight(vBox);

        // Create scene
        double menuWidth = screenWidth- 300-navBarWidth;

        Scene scene = new Scene(borderPane, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("To PDF");
        primaryStage.show();



        double x = (screenWidth - (navBarWidth+300+boxSizeX))/2;
        int cropX = (int)(x+navBarWidth);
        printButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            // Set initial directory to the user's desktop
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), "Desktop"));
            // Set extension filter
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                WritableImage writableImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
                scene.snapshot(writableImage);

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

                // Crop the image
                BufferedImage croppedImage = bufferedImage.getSubimage(cropX, 0, boxSizeX, (int)screenHeight);

                // Save the cropped image
                try {
                    ImageIO.write(croppedImage, "png", new File("cropped_image.png"));
//                    System.out.println("Cropped image saved successfully.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(croppedImage, "png", byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();
                    createPdf(file, imageBytes);
//                    System.out.println("PDF created successfully at: " + file);
                } catch (IOException eex) {
                    eex.printStackTrace();
                }
            }
        });
    }

    public static void createPdf(File dest, byte[] imageBytes) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        ImageData data = ImageDataFactory.create(imageBytes);
        Image pdfImg = new Image(data);
        doc.add(pdfImg);
        doc.close();
    }

    private void cleanUp(){
        problemAndSolutionList=null;
        partsAndMaterialsList=null;
        proceduresList=null;
        carModelList=null;
        clientsList=null;
        extraProblemList=null;
    }
}
