package com.mypackage.carworkshopmanagement.manage.database;

import com.mypackage.carworkshopmanagement.manage.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/car_workshop";
    private static final String USER = "root";
    private static final String PASSWORD = "2S#&$25";

    public static int insertCar(CarModel car){
        String selectSql = "SELECT COUNT(*) FROM car WHERE frameNumber = ?";
        String insertSql = "INSERT INTO car (brand, idOwner, frameNumber, milage) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Check if the username already exists
            selectStatement.setString(1, car.getFrameNumber());
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("Car frame number already exist!");
                return -1; // Exit method without inserting
            }
            // Insert the user data
            insertStatement.setString(1, car.getBrand());
            insertStatement.setInt(2, car.getOwner().getId());
            insertStatement.setString(3, car.getFrameNumber());
            insertStatement.setDouble(4, car.getMilage());



            System.out.println(">>>>>>>>>>>>>>MyJdbc<<<<<<<<<<<<<");
            System.out.println("Car Id       : "+ car.getId());
            System.out.println("Brand        : "+ car.getBrand());
            System.out.println("Frame Number : "+ car.getFrameNumber());
            System.out.println("Milage       : "+ car.getMilage());
            System.out.println("Owner Id     : "+ car.getOwner());
            System.out.println("+++++++++++++++++++++++++++++++++++");



            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return 1;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int insertClient(Client client){
        String selectSql = "SELECT COUNT(*) FROM client WHERE phoneNo = ?";
        String insertSql = "INSERT INTO client (name, regNo, phoneNo) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Check if the username already exists
            selectStatement.setString(1, client.getPhoneNo());
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("Client already exists!");
                return -1; // Exit method without inserting
            }
            // Insert the user data
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getRegNo());
            insertStatement.setString(3, client.getPhoneNo());

            System.out.println(">>>>>>>>>>>>>>MyJdbc<<<<<<<<<<<<<");
            System.out.println("Name        : "+ client.getName());
            System.out.println("Phone Number: "+ client.getPhoneNo());
            System.out.println("Reg No      : "+ client.getRegNo());
            System.out.println("+++++++++++++++++++++++++++++++++++");

            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return rowsAffected;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int insertProblemAndSolution(ProblemAndSolution problemAndSolution) {
        String selectSql = "SELECT COUNT(*) FROM problem_and_solution WHERE problem = ?";
        String insertSql = "INSERT INTO problem_and_solution (problem, solution) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Check if the username already exists
            selectStatement.setString(1, problemAndSolution.getProblem());
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("Client already exists!");
                return -1; // Exit method without inserting
            }
            // Insert the user data
            insertStatement.setString(1, problemAndSolution.getProblem());
            insertStatement.setString(2, problemAndSolution.getPossibleSolution());

            System.out.println(">>>>>>>>>>>>>>MyJdbc<<<<<<<<<<<<<");
            System.out.println("Problem : "+ problemAndSolution.getProblem());
            System.out.println("Solution: "+ problemAndSolution.getPossibleSolution());
            System.out.println("+++++++++++++++++++++++++++++++++++");

            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return rowsAffected;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int insertProcedures(Procedures localPrecudures) {

        System.out.println(">>>>>>>>>>>>>>MyJdbc insertProcedure<<<<<<<<<<<<<");
        System.out.println("Procedure  : "+ localPrecudures.getProcedure());
        System.out.println("Observation: "+ localPrecudures.getObservation());
        System.out.println("Price      : "+ localPrecudures.getPriceWithTax());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");

        System.out.println("executing..");
        String selectSql = "SELECT COUNT(*) FROM procedures WHERE procedureName = ?";
        String insertSql = "INSERT INTO procedures (procedureName, observation, priceWithTax) VALUES (?, ?, ?)";

        System.out.println("select: "+selectSql);
        System.out.println("insert: "+insertSql);

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Check if the username already exists
            selectStatement.setString(1, localPrecudures.getProcedure());
            System.out.println("select executing...");
            ResultSet resultSet = selectStatement.executeQuery();
            System.out.println("select executed.");
            resultSet.next();
            System.out.println("next...");
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("procedure already exists!");
                return -1; // Exit method without inserting
            }
            // Insert the user data
            insertStatement.setString(1, localPrecudures.getProcedure());
            insertStatement.setString(2, localPrecudures.getObservation());
            insertStatement.setDouble(3, localPrecudures.getPriceWithTax());



            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return rowsAffected;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int insertMaterials(PartsAndMaterials localPartAndMaterials) {
        String selectSql = "SELECT COUNT(*) FROM parts_and_materials WHERE partCode = ?";
        String insertSql = "INSERT INTO parts_and_materials (partsAndMaterial, partCode, priceWithTax) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Check if the username already exists
            selectStatement.setString(1, localPartAndMaterials.getPartCode());
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            if (count > 0) {
                System.out.println("Part already exists!");
                return -1; // Exit method without inserting
            }
            // Insert the user data
            insertStatement.setString(1, localPartAndMaterials.getPartsAndMaterial());
            insertStatement.setString(2, localPartAndMaterials.getPartCode());
            insertStatement.setDouble(3, localPartAndMaterials.getPriceWithTax());

            System.out.println(">>>>>>>>>>>>>>MyJdbc<<<<<<<<<<<<<");
            System.out.println("Part  : "+ localPartAndMaterials.getPartsAndMaterial());
            System.out.println("Part Code: "+ localPartAndMaterials.getPartCode());
            System.out.println("Price      : "+ localPartAndMaterials.getPriceWithTax());
            System.out.println("+++++++++++++++++++++++++++++++++++");

            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return rowsAffected;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int insertBill(Bills bill) {

        String insertSql = "INSERT INTO bill (frameNumber, procedureIds, problemIds, parts, executor, estimatedDuration, date, phoneNumber) VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            // Insert the user data
            insertStatement.setString(1, bill.getFrameNumber());
            insertStatement.setString(2, bill.getProcedureIds());
            insertStatement.setString(3, bill.getProblemIds());
            insertStatement.setString(4, bill.getPartsAndMaterialsIdsWithQuantity());
            insertStatement.setString(5, bill.getExecutor());
            insertStatement.setString(6, bill.getEstimatedDuration());
            insertStatement.setDate(7, Date.valueOf(LocalDate.now()));
            insertStatement.setString(8,bill.getPhoneNumber());

            System.out.println(">>>>>>>>>>>>>>MyJdbc<<<<<<<<<<<<<");
//            System.out.println("Part  : "+ localPartAndMaterials.getPartsAndMaterial());
//            System.out.println("Part Code: "+ localPartAndMaterials.getPartCode());
//            System.out.println("Price      : "+ localPartAndMaterials.getPriceWithTax());
            System.out.println("+++++++++++++++++++++++++++++++++++");

            int rowsAffected = insertStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
            return rowsAffected;
        }
        catch (SQLIntegrityConstraintViolationException err){
            err.printStackTrace();
            return -1;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }


//    public static void updateOrderStatusInDatabase(int orderId, String newStatus) {
//        // JDBC URL, username, and password of MySQL server
//
//        // SQL query to update order status
//        String sql = "UPDATE orders_table SET status = ? WHERE idorder = ?";
//
//        try (Connection conn = DriverManager.getConnection(URL2, USER, PASSWORD);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            // Set parameters for the PreparedStatement
//            stmt.setString(1, newStatus);
//            stmt.setInt(2, orderId);
//
//            // Execute the update
//            int rowsAffected = stmt.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Order status updated successfully.");
//            } else {
//                System.out.println("Failed to update order status.");
//            }
//        } catch (SQLException e) {
//            System.out.println("Error updating order status: " + e.getMessage());
//        }
//    }

//    public static boolean deleteOrderFromDatabase(int id) {
//
//
//        String query = "DELETE FROM orders_table WHERE idorder = ?";
//
//        try (Connection conn = DriverManager.getConnection(URL2, USER, PASSWORD);
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//            pstmt.setInt(1, id);
//            int rowsAffected = pstmt.executeUpdate();
//
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//
//            e.printStackTrace();
//            return false;
//        }
//    }





//    public static boolean isValidUser2(String username, String password) {
//        String sql = "SELECT COUNT(*) FROM manager WHERE username = ? AND password = ?";
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, password);
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            int count = resultSet.getInt(1);
//            return count > 0; // If count > 0, user is valid
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            return false; // Return false in case of any exception or SQL error
//        }
//    }

    public static List<CarModel> getCarList(List<Client> clientsList) {
        Map<Integer, Client> clientMap = new HashMap<>();
        // Populate the map
        for (Client client : clientsList) {
            clientMap.put(client.getId(), client);
        }

        List<CarModel> carModelList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.car";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int idCar = resultSet.getInt("idCar");
                String brand = resultSet.getString("brand");
                String frameNumber = resultSet.getString("frameNumber");
                double milage = resultSet.getDouble("milage");
                int idOwner = resultSet.getInt("idOwner");

                if(clientMap.containsKey(idOwner)){
                    Client client = clientMap.get(idOwner);
                    carModelList.add(new CarModel(idCar, frameNumber, brand, milage, client));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return carModelList;
        }

        return carModelList;
    }

    public static List<Client> getClientList() {
        List<Client> clientList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.client";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("idClient");
                String regNo = resultSet.getString("regNo");
                String name = resultSet.getString("name");
                String phoneNo = resultSet.getString("phoneNo");

                clientList.add(new Client(id, name, regNo, phoneNo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return clientList;
        }
        return clientList;
    }

    public static Client getClient(String phoneNumber) {
        String selectSql = "SELECT * FROM client WHERE phoneNo = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStatement = connection.prepareStatement(selectSql)) {

            selectStatement.setString(1, phoneNumber);
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();

            int id = resultSet.getInt("idClient");
            String regNo = resultSet.getString("regNo");
            String name = resultSet.getString("name");
            String phoneNo = resultSet.getString("phoneNo");

            return new Client(id, name, regNo, phoneNo);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }




    public static List<ProblemAndSolution> getProblemAndSolutionList() {
        List<ProblemAndSolution> problemAndSolutionList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.problem_and_solution";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("idProblem");
                String problem = resultSet.getString("problem");
                String solution = resultSet.getString("solution");
                problemAndSolutionList.add(new ProblemAndSolution(id, problem, solution));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return problemAndSolutionList;

    }

    public static List<Procedures> getProcedureList() {
        List<Procedures> proceduresList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.procedures";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("idProcedures");
                String procedure = resultSet.getString("procedureName");
                String observation = resultSet.getString("observation");
                double priceWithTax = resultSet.getDouble("priceWithTax");
                proceduresList.add(new Procedures(id, procedure, observation, priceWithTax));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return proceduresList;
        }
        return proceduresList;
    }

    public static List<PartsAndMaterials> getPartsAndMaterialsList() {
        List<PartsAndMaterials> partsAndMaterialsList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.parts_and_materials";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("idPartsAndMaterial");
                String partsAndMaterial = resultSet.getString("partsAndMaterial");
                String partCode = resultSet.getString("partCode");
                double priceWithTax = resultSet.getDouble("priceWithTax");
                partsAndMaterialsList.add(new PartsAndMaterials(id, partsAndMaterial, partCode, priceWithTax));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return partsAndMaterialsList;
        }
        return partsAndMaterialsList;
    }


    public static List<Bills> getBills() {
        List<Bills> billsList = new ArrayList<>();

        String sql = "SELECT * FROM car_workshop.bill";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt("idBill");
                String executor = resultSet.getString("executor");
                String frameNumber = resultSet.getString("frameNumber");
                String procedureIds = resultSet.getString("procedureIds");
                String problemIds = resultSet.getString("problemIds");
                String parts = resultSet.getString("parts");
                String estimatedDuration = resultSet.getString("estimatedDuration");
                String phoneNumber = resultSet.getString("phoneNumber");
                Date date = resultSet.getDate("date");

                billsList.add(new Bills(id,phoneNumber, frameNumber,procedureIds,problemIds,parts,executor,estimatedDuration,date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return billsList;
        }
        return billsList;
    }



    public static Bills getBill(String frameNumber) {
        String sql = "SELECT * FROM car_workshop.bill where frameNumber = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, frameNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("idBill");
            String executor = resultSet.getString("executor");
            String procedureIds = resultSet.getString("procedureIds");
            String problemIds = resultSet.getString("problemIds");
            String parts = resultSet.getString("parts");
            String phoneNumber = resultSet.getString("phoneNumber");
            String estimatedDuration = resultSet.getString("estimatedDuration");
            Date date = resultSet.getDate("date");

            return new Bills(id,phoneNumber, frameNumber,procedureIds,problemIds,parts,executor,estimatedDuration,date);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }





    public static CarModel getCar(String frame, List<Client> clientsList) {
        Map<Integer, Client> clientMap = new HashMap<>();
        // Populate the map
        for (Client client : clientsList) {
            clientMap.put(client.getId(), client);
        }
        CarModel carModel = null;

        String sql = "SELECT * FROM car_workshop.car WHERE frameNumber = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, frame);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            int idCar = resultSet.getInt("idCar");
            String brand = resultSet.getString("brand");
            String frameNumber = resultSet.getString("frameNumber");
            double milage = resultSet.getDouble("milage");
            int idOwner = resultSet.getInt("idOwner");

            if(clientMap.containsKey(idOwner)){
                Client client = clientMap.get(idOwner);
                return new CarModel(idCar,frameNumber,brand,milage,client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return carModel;
        }

        return carModel;
    }


    public static ProblemAndSolution getProblemAndSolution(String problem) {
        String sql = "SELECT * FROM car_workshop.problem_and_solution where problem = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,problem);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("idProblem");
            String solution = resultSet.getString("solution");
            return new ProblemAndSolution(id, problem, solution);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Procedures getProcedure(String procedure) {
        String sql = "SELECT * FROM car_workshop.procedures where procedureName = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,procedure);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("idProcedures");
            String observation = resultSet.getString("observation");
            double price = resultSet.getDouble("priceWithTax");
            return new Procedures(id, procedure, observation, price);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static PartsAndMaterials getMaterials(String partCode) {
        String sql = "SELECT * FROM car_workshop.parts_and_materials where partCode = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,partCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("idPartsAndMaterial");
            String partAndMaterials = resultSet.getString("partsAndMaterial");
            double price = resultSet.getDouble("priceWithTax");
            return new PartsAndMaterials(id, partAndMaterials,partCode, price);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



}
