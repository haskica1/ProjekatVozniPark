package ba.unsa.etf.rpr.projekat;


import org.exolab.castor.mapping.xml.Sql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class MotorFleetDAO {

    private static MotorFleetDAO instance;
    private Connection conn= null;

    private PreparedStatement getLevel, getFirstName, getLastName, getAllUsers, getUsersByParametars, updateUser, addUser, getNextUserId, deleteUser, getAllVehicles, getVehiclesByParametars, getAllServiceOfVehicle, getAllServiceOfVehicleByParametars, addServiceForVehicle, getNextServiceManagementId, getNextIdService, addService, deleteServiceForVehicle, updateServiceForVehicle;
    private PreparedStatement searchService, getVehiclesByParametars2, getNextIDVehicle, addVehicle, updateVehicle, deleteVehicle, getAllStorages, getUserByID, getStoragesByParametar, addStorage, getNextIdStorage, updateStorage, deleteStorage, getAllPartsOfStorage, addPartToStorage, addPart, getNextPartId, updatePart, deletePart, deletePartFromStorage, getAllEquipmentOfVehicle;
    private PreparedStatement getNextIdEquipment, addEquipmentForVehicle, addEquipment, updateEquipment, deleteEquipmentFromWehicle, getEquipmentByParametar, deleteVehicleFromVehicleEquipment, getAllEquipment, getAllParts, searchEquipment, getPartsOfStorageByParametars, getPartsByParametars, getUserByUsernameAndPassword, deleteVehicleFromService, isThereVehicleWithLicencePlate;
    private PreparedStatement isThereUserWithUsernameAndPassword, deleteService, deleteStorageFromStorageParts, isThereEquipment, getEquipmentIdByName, isTherePart, getPartIdByName, updatePartOfStorage, updateEquipmentOfVehicle, deleteEquipment, isTherePartInOtherStorage, isThereEquipmentInOtherVehicle, isThereVehicleInMotorFleet, addVehicleToMotorFleet, deletevehicleFromMotorFleet;
    private PreparedStatement getAllMotorFleet, getManagerOfMotorFleet, getVehiclesFromMothorFleet, getVehiclesById, getEmployeesFromMotorFleet, getStoragesFromMotorFleet, getStorageById, getServiceManagementFromMotorFleet, isThereUserInMotorFleet, getAllEquipmentFromMotorFleet, getEquipmentById, getPartsFromMotorFleet, getPartById, addUserInMotorFleet, deleteUserFromMotorFleet;
    private PreparedStatement isThereStorageInMotoFleet, addStorageInMotorFleet, deleteStorageFromMotorFleet, isEquipmentInMotorFleet, addEquipmentInMotorFleet, isThereVehicleWithEquipmentInMotorFleet, deleteEquipmentFromMotorFleet, deleteEquipmentFromAllVehicles, isThereEquipmentInOtherMotorFleet, isPartInMotorFleet, addPartInMotorFleet, deletePartFromAllStorage, isThereStorageWithPartInMotorFleet;
    private PreparedStatement deletePartFromMotorFleet, isTherePartInOtherMotorFleet, getMotorFleetsByName, getNextIdMotorFleet, addMotorFleet, getMotorFleetById, updateNameOfMotorFleet, getAllEquipmentInOtherMotorFleets, getPartsFromOtherMotorFleet, getServiceFromMotorFleet, deleteServiceFromMotorFleet, deleteMotorFleet, addServiceInMotorFleet, getServiceById, getAllUsersByType;
    private PreparedStatement getAllUsersFromUserEmployee;

    public static MotorFleetDAO getInstance() {
        if (instance == null) instance = new MotorFleetDAO();
        return instance;
    }

    public Connection getConnection() {
        return conn;
    }

    private MotorFleetDAO() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:projekat_baza.db");

            getLevel = conn.prepareStatement("select USER_EMPLOYEE.is_administrator from USER_EMPLOYEE where USER_EMPLOYEE.username = ? and USER_EMPLOYEE.password = ?");
            getFirstName = conn.prepareStatement("select USER_EMPLOYEE.first_name from USER_EMPLOYEE where USER_EMPLOYEE.username = ? and USER_EMPLOYEE.password = ?");
            getLastName = conn.prepareStatement("select USER_EMPLOYEE.last_name from USER_EMPLOYEE where USER_EMPLOYEE.username = ? and USER_EMPLOYEE.password = ?");
            getAllUsers = conn.prepareStatement("select * from USER_EMPLOYEE");
            getUsersByParametars = conn.prepareStatement("select * from USER_EMPLOYEE where first_name = ? or last_name = ? or telephone_number = ? or address = ?");
            updateUser = conn.prepareStatement("update USER_EMPLOYEE set first_name = ?, last_name = ?, telephone_number = ?, address = ?, username = ?, password = ?, is_administrator = ? where user_id = ?");
            addUser = conn.prepareStatement("insert into USER_EMPLOYEE values(?,?,?,?,?,?,?,?)");
            getNextUserId = conn.prepareStatement("select MAX(user_id) + 1 from USER_EMPLOYEE");
            deleteUser = conn.prepareStatement("delete from USER_EMPLOYEE where user_id = ?");
            getUserByID = conn.prepareStatement("select * from USER_EMPLOYEE where user_id = ?");
            getUserByUsernameAndPassword = conn.prepareStatement("select * from USER_EMPLOYEE where USER_EMPLOYEE.username = ? and USER_EMPLOYEE.password = ?");
            isThereUserWithUsernameAndPassword = conn.prepareStatement("select * from USER_EMPLOYEE where USER_EMPLOYEE.username = ? and USER_EMPLOYEE.password = ?");
            getAllUsersByType = conn.prepareStatement("select * from USER_EMPLOYEE where is_administrator = ?");
            getAllUsersFromUserEmployee = conn.prepareStatement("select user_id from MOTOR_FLEET_USER where motor_fleet_id = ?");

            getAllVehicles = conn.prepareStatement("select * from VEHICLE");
            getVehiclesByParametars = conn.prepareStatement("select * from VEHICLE where name = ? or licence_plate = ?");
            getVehiclesByParametars2 = conn.prepareStatement("select * from VEHICLE where name = ? or licence_plate = ? or color = ?");
            getNextIDVehicle = conn.prepareStatement("select MAX(vehicle_id) + 1 from VEHICLE");
            addVehicle = conn.prepareStatement("insert into VEHICLE values(?,?,?,?,?)");
            updateVehicle = conn.prepareStatement("update VEHICLE set name = ?, type = ?, licence_plate = ?, color = ? where vehicle_id = ?");
            deleteVehicle = conn.prepareStatement("delete from VEHICLE where vehicle_id = ?");
            deleteVehicleFromVehicleEquipment = conn.prepareStatement("delete from VEHICLE_EQUIPMENT where vehicle_id = ?");
            deleteVehicleFromService = conn.prepareStatement("delete from SERVICE_MANAGEMENT where vehicle_id = ?");
            isThereVehicleWithLicencePlate = conn.prepareStatement("select * from VEHICLE where VEHICLE.licence_plate = ?");
            getVehiclesById = conn.prepareStatement("select * from VEHICLE where vehicle_id = ?");


            getAllServiceOfVehicle = conn.prepareStatement("select SERVICE.service_id, SERVICE.name, SERVICE.date, SERVICE.service_persone, SERVICE.info  from SERVICE_MANAGEMENT, SERVICE WHERE SERVICE_MANAGEMENT.service_id = SERVICE.service_id and SERVICE_MANAGEMENT.vehicle_id = ?");
            getAllServiceOfVehicleByParametars = conn.prepareStatement("select SERVICE.service_id, SERVICE.name, SERVICE.date, SERVICE.service_persone, SERVICE.info from SERVICE_MANAGEMENT, SERVICE WHERE SERVICE_MANAGEMENT.service_id = SERVICE.service_id and SERVICE_MANAGEMENT.vehicle_id = ? and (SERVICE.name = ? or SERVICE.date = ? or SERVICE.service_persone = ?)");
            addServiceForVehicle = conn.prepareStatement("insert into SERVICE_MANAGEMENT values (?,?,?)");
            getNextServiceManagementId = conn.prepareStatement("select MAX(service_management_id) + 1 from SERVICE_MANAGEMENT");
            getNextIdService = conn.prepareStatement("select MAX(service_id)+1 from SERVICE");
            addService = conn.prepareStatement("insert into SERVICE values (?,?,?,?,?)");
            deleteServiceForVehicle = conn.prepareStatement("delete from SERVICE_MANAGEMENT where vehicle_id = ? and service_id = ?");
            updateServiceForVehicle = conn.prepareStatement("update SERVICE set name = ?, date = ?, service_persone = ?, info = ? where service_id = ?");
            searchService = conn.prepareStatement("select * from SERVICE where name = ?");
            deleteService = conn.prepareStatement("delete from SERVICE where service_id = ?");
            getServiceById = conn.prepareStatement("select * from SERVICE where service_id = ?");

            getAllStorages = conn.prepareStatement("select * from STORAGE");
            getStoragesByParametar = conn.prepareStatement("select * from STORAGE where name = ?");
            addStorage = conn.prepareStatement("insert into STORAGE values(?,?,?)");
            getNextIdStorage = conn.prepareStatement("select MAX(storage_id) + 1 from STORAGE");
            updateStorage = conn.prepareStatement("update STORAGE set name = ?, manager_id = ? where storage_id = ?");
            deleteStorage = conn.prepareStatement("delete from STORAGE where storage_id = ?");
            getAllPartsOfStorage = conn.prepareStatement("select * from PART, STORAGE_PARTS WHERE PART.part_id = STORAGE_PARTS.part_id and STORAGE_PARTS.storage_id = ?");
            addPartToStorage = conn.prepareStatement("insert into STORAGE_PARTS values(?,?)");
            addPart = conn.prepareStatement("insert into PART values (?,?,?)");
            getNextPartId = conn.prepareStatement("select MAX(part_id) + 1 from PART");
            updatePart = conn.prepareStatement("update PART set name = ?, is_useful = ? where part_id = ?");
            deletePart = conn.prepareStatement("delete from PART where part_id = ?");
            deletePartFromStorage = conn.prepareStatement("delete from STORAGE_PARTS where part_id = ? and storage_id = ?");
            getAllParts = conn.prepareStatement("select * from PART");
            getPartsOfStorageByParametars = conn.prepareStatement("select PART.part_id, PART.name, PART.is_useful from PART, STORAGE_PARTS where PART.part_id = STORAGE_PARTS.part_id and STORAGE_PARTS.storage_id = ? and (PART.name = ? or PART.part_id = ?)");
            getPartsByParametars = conn.prepareStatement("select * from PART where PART.part_id = ? or PART.name = ?");
            deleteStorageFromStorageParts = conn.prepareStatement("delete from STORAGE_PARTS where storage_id = ?");
            isTherePart = conn.prepareStatement("select * from PART where PART.name = ?");
            getPartIdByName = conn.prepareStatement("select * from PART where PART.name = ?");
            updatePartOfStorage = conn.prepareStatement("update STORAGE_PARTS set storage_id = ?, part_id = ? where part_id = ? and storage_id = ?");
            isTherePartInOtherStorage = conn.prepareStatement("select * from STORAGE_PARTS where STORAGE_PARTS.storage_id != ? and STORAGE_PARTS.part_id = ?");
            getStorageById = conn.prepareStatement("select * from STORAGE where storage_id = ?");
            getPartById = conn.prepareStatement("select * from PART where part_id = ?");
            deletePartFromAllStorage = conn.prepareStatement("delete from STORAGE_PARTS where part_id = ?");

            getAllEquipmentOfVehicle = conn.prepareStatement("select EQUIPMENT.equipment_id, EQUIPMENT.name, EQUIPMENT.info from EQUIPMENT, VEHICLE_EQUIPMENT WHERE EQUIPMENT.equipment_id = VEHICLE_EQUIPMENT.equipment_id and VEHICLE_EQUIPMENT.vehicle_id = ?");
            getNextIdEquipment = conn.prepareStatement("select MAX(equipment_id) + 1 from EQUIPMENT");
            addEquipmentForVehicle = conn.prepareStatement("insert into VEHICLE_EQUIPMENT values(?,?)");
            addEquipment = conn.prepareStatement("insert into EQUIPMENT values (?,?,?)");
            updateEquipment = conn.prepareStatement("update EQUIPMENT set name = ?, info = ? where equipment_id = ?");
            deleteEquipmentFromWehicle = conn.prepareStatement("delete from VEHICLE_EQUIPMENT where equipment_id = ? and vehicle_id = ?");
            deleteEquipment = conn.prepareStatement("delete from EQUIPMENT where equipment_id = ?");
            getEquipmentByParametar = conn.prepareStatement("select EQUIPMENT.equipment_id, EQUIPMENT.name, EQUIPMENT.info from EQUIPMENT, VEHICLE_EQUIPMENT WHERE EQUIPMENT.equipment_id = VEHICLE_EQUIPMENT.equipment_id and VEHICLE_EQUIPMENT.vehicle_id = ? and EQUIPMENT.name = ?");
            getAllEquipment = conn.prepareStatement("select * from EQUIPMENT");
            searchEquipment = conn.prepareStatement("select EQUIPMENT.equipment_id, EQUIPMENT.name, EQUIPMENT.info from EQUIPMENT WHERE EQUIPMENT.name = ?");
            isThereEquipment = conn.prepareStatement("select * from EQUIPMENT where EQUIPMENT.name = ?");
            getEquipmentIdByName = conn.prepareStatement("select EQUIPMENT.equipment_id from EQUIPMENT where EQUIPMENT.name = ?");
            updateEquipmentOfVehicle = conn.prepareStatement("update VEHICLE_EQUIPMENT set vehicle_id = ?, equipment_id = ? where vehicle_id = ? and equipment_id = ?");
            isThereEquipmentInOtherVehicle = conn.prepareStatement("select * from VEHICLE_EQUIPMENT where VEHICLE_EQUIPMENT.vehicle_id != ? and VEHICLE_EQUIPMENT.equipment_id = ?");
            getEquipmentById = conn.prepareStatement("select * from EQUIPMENT where equipment_id = ?");
            deleteEquipmentFromAllVehicles = conn.prepareStatement("delete from VEHICLE_EQUIPMENT where equipment_id = ?");

            getAllMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET");
            getManagerOfMotorFleet = conn.prepareStatement("select manager_id from MOTOR_FLEET where motor_fleet_id = ?");
            getVehiclesFromMothorFleet = conn.prepareStatement("select vehicle_id from MOTOR_FLEET_VEHICLE where motor_fleet_id = ?");
            getEmployeesFromMotorFleet = conn.prepareStatement("select user_id from MOTOR_FLEET_USER where motor_fleet_id = ?");
            getStoragesFromMotorFleet = conn.prepareStatement("select storage_id from MOTOR_FLEET_STORAGE where motor_fleet_id = ?");
            getServiceManagementFromMotorFleet = conn.prepareStatement("select service_id from MOTOR_FLEET_SERVICE where motor_fleet_id = ?");
            isThereUserInMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_USER where motor_fleet_id = ? and user_id = ?");
            getAllEquipmentFromMotorFleet = conn.prepareStatement("select equipment_id from MOTOR_FLEET_EQUIPMENT where motor_fleet_id = ?");
            getPartsFromMotorFleet = conn.prepareStatement("select part_id from MOTOR_FLEET_PART where motor_fleet_id = ?");
            isThereVehicleInMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_VEHICLE where motor_fleet_id = ? and vehicle_id = ?");
            addVehicleToMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_VEHICLE values (?,?)");
            deletevehicleFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_VEHICLE where motor_fleet_id = ? and vehicle_id = ?");
            addUserInMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_USER values (?,?)");
            deleteUserFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_USER where motor_fleet_id = ? and user_id = ?");
            isThereStorageInMotoFleet = conn.prepareStatement("select * from MOTOR_FLEET_STORAGE where motor_fleet_id = ? and storage_id = ?");
            addStorageInMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_STORAGE values (?,?)");
            deleteStorageFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_STORAGE where motor_fleet_id = ? and storage_id = ?");
            isEquipmentInMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_EQUIPMENT where motor_fleet_id = ? and equipment_id = ?");
            addEquipmentInMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_EQUIPMENT values (?,?)");
            isThereVehicleWithEquipmentInMotorFleet = conn.prepareStatement("select VEHICLE.vehicle_id from VEHICLE, MOTOR_FLEET_VEHICLE, VEHICLE_EQUIPMENT where VEHICLE.vehicle_id = MOTOR_FLEET_VEHICLE.vehicle_id and VEHICLE.vehicle_id = VEHICLE_EQUIPMENT.vehicle_id and MOTOR_FLEET_VEHICLE.motor_fleet_id = ? and  VEHICLE_EQUIPMENT.equipment_id = ?");
            deleteEquipmentFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_EQUIPMENT where motor_fleet_id = ? and equipment_id = ?");
            isThereEquipmentInOtherMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_EQUIPMENT where motor_fleet_id != ? and equipment_id = ?");
            isPartInMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_PART where motor_fleet_id = ? and part_id = ?");
            addPartInMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_PART values (?,?)");
            isThereStorageWithPartInMotorFleet = conn.prepareStatement("select STORAGE.storage_id from STORAGE, MOTOR_FLEET_STORAGE, STORAGE_PARTS where STORAGE.storage_id = MOTOR_FLEET_STORAGE.storage_id and STORAGE.storage_id = STORAGE_PARTS.storage_id and MOTOR_FLEET_STORAGE.motor_fleet_id = ? and STORAGE_PARTS.part_id = ?");
            deletePartFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_PART where motor_fleet_id = ? and part_id = ?");
            isTherePartInOtherMotorFleet = conn.prepareStatement("select * from MOTOR_FLEET_PART where motor_fleet_id != ? and part_id = ?");
            getMotorFleetsByName = conn.prepareStatement("select * from MOTOR_FLEET where name = ?");
            getNextIdMotorFleet = conn.prepareStatement("select MAX(motor_fleet_id) + 1 from MOTOR_FLEET");
            addMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET values(?,?,?)");
            getMotorFleetById = conn.prepareStatement("select * from MOTOR_FLEET where motor_fleet_id = ?");
            updateNameOfMotorFleet = conn.prepareStatement("update MOTOR_FLEET set name = ? where motor_fleet_id = ?");
            getAllEquipmentInOtherMotorFleets = conn.prepareStatement("select equipment_id from MOTOR_FLEET_EQUIPMENT where motor_fleet_id != ?");
            getPartsFromOtherMotorFleet = conn.prepareStatement("select part_id from MOTOR_FLEET_PART where motor_fleet_id != ?");
            getServiceFromMotorFleet = conn.prepareStatement("select service_id from MOTOR_FLEET_SERVICE where motor_fleet_id = ?");
            deleteServiceFromMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET_SERVICE where motor_fleet_id = ? and service_id = ?");
            deleteMotorFleet = conn.prepareStatement("delete from MOTOR_FLEET where motor_fleet_id = ?");
            addServiceInMotorFleet = conn.prepareStatement("insert into MOTOR_FLEET_SERVICE values (?,?)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeInstance() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        instance = null;
    }


    private void regenerisiBazu() {
        Scanner ulaz = null;
        try {
            ulaz = new Scanner(new FileInputStream("projekat_baza.db.sql"));
            String sqlUpit = "";
            while (ulaz.hasNext()) {
                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            ulaz.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getLevelOfUser(String username, String password){
        try{
            getLevel.setString(1,username);
            getLevel.setString(2,password);
            ResultSet resultSet = getLevel.executeQuery();
            if(!resultSet.next())   return 0;
            return resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public String getFirstName(String username, String password) {
        try{
            getFirstName.setString(1,username);
            getFirstName.setString(2,password);
            ResultSet resultSet = getFirstName.executeQuery();
            if(!resultSet.next())   return null;
            return resultSet.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public String getLastName(String username, String password) {
        try{
            getLastName.setString(1,username);
            getLastName.setString(2,password);
            ResultSet resultSet = getLastName.executeQuery();
            if(!resultSet.next())   return null;
            return resultSet.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try{
            ResultSet resultSet = getAllUsers.executeQuery();
            while(resultSet.next()){
                User user = new User(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),null);
                int level = resultSet.getInt(8);
                switch(level){
                    case 1: user.setLevelOfResponsibility(LevelOfResponsibility.ADMINISTRATOR);break;
                    case 2: user.setLevelOfResponsibility(LevelOfResponsibility.VEHICLE);break;
                    case 3: user.setLevelOfResponsibility(LevelOfResponsibility.STORAGE);break;
                    case 4: user.setLevelOfResponsibility(LevelOfResponsibility.SERVICE);break;
                    case 5: user.setLevelOfResponsibility(LevelOfResponsibility.EMPLOYEE);break;
                    case 6: user.setLevelOfResponsibility(LevelOfResponsibility.WORKER); break;
                    default: user.setLevelOfResponsibility(LevelOfResponsibility.DIRECTOR);
                }
                user.setUserId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(6));
                user.setPassword(resultSet.getString(7));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getUsersByParametars(String firstName, String lastName, String telephoneNumber, String address, MotorFleet motorFleet) {
        ArrayList<User> users = new ArrayList<>();
        try{
            getUsersByParametars.setString(1, firstName);
            getUsersByParametars.setString(2, lastName);
            getUsersByParametars.setString(3, telephoneNumber);
            getUsersByParametars.setString(4, address);
            ResultSet resultSet = getUsersByParametars.executeQuery();
            while(resultSet.next()){
                User user = new User(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),null);
                int level = resultSet.getInt(8);
                switch(level){
                    case 1: user.setLevelOfResponsibility(LevelOfResponsibility.ADMINISTRATOR);break;
                    case 2: user.setLevelOfResponsibility(LevelOfResponsibility.VEHICLE);break;
                    case 3: user.setLevelOfResponsibility(LevelOfResponsibility.STORAGE);break;
                    case 4: user.setLevelOfResponsibility(LevelOfResponsibility.SERVICE);break;
                    case 5: user.setLevelOfResponsibility(LevelOfResponsibility.EMPLOYEE);break;
                    case 6: user.setLevelOfResponsibility(LevelOfResponsibility.WORKER);break;
                    default: user.setLevelOfResponsibility(LevelOfResponsibility.DIRECTOR);
                }
                user.setUserId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(6));
                user.setPassword(resultSet.getString(7));
                if(isThereUserInMotorFleet(motorFleet.getMotorFleetId(),user.getUsername(),user.getPassword()))
                    users.add(user);
            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateUser(User user){
        try{
            updateUser.setString(1,user.getFirstName());
            updateUser.setString(2,user.getLastName());
            updateUser.setString(3,user.getTelephoneNumber());
            updateUser.setString(4,user.getAddress());
            updateUser.setString(5,user.getUsername());
            updateUser.setString(6,user.getPassword());
            switch (user.getLevelOfResponsibility()){
                case ADMINISTRATOR: updateUser.setInt(7,1); break;
                case EMPLOYEE: updateUser.setInt(7,5); break;
                case VEHICLE: updateUser.setInt(7,2); break;
                case SERVICE: updateUser.setInt(7,4); break;
                case STORAGE: updateUser.setInt(7,3); break;
                case WORKER: updateUser.setInt(7,6); break;
                case DIRECTOR: updateUser.setInt(7,7); break;
                default: updateUser.setInt(7,0);
            }

            updateUser.setInt(8,user.getUserId());
            updateUser.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void addUser(User user, MotorFleet motorFleet){
        try{
            ResultSet resultSet = getNextUserId.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);

            user.setUserId(id);

            addUser.setInt(1,id);
            addUser.setString(2,user.getFirstName());
            addUser.setString(3, user.getLastName());
            addUser.setString(4, user.getTelephoneNumber());
            addUser.setString(5, user.getAddress());
            addUser.setString(6, user.getUsername());
            addUser.setString(7, user.getPassword());
            addUser.setInt(8, user.getLevelOfResponsibility().getLevelCode());
            addUser.executeUpdate();

            addUserInMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            addUserInMotorFleet.setInt(2,user.getUserId());
            addUserInMotorFleet.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(User user, MotorFleet motorFleet){
        try{
            //delete user from motor_fleet_user
            deleteUserFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            deleteUserFromMotorFleet.setInt(2, user.getUserId());
            deleteUserFromMotorFleet.executeUpdate();

            //delete user
            deleteUser.setInt(1,user.getUserId());
            deleteUser.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Vehicle> getAllVehicles(){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try {
            ResultSet resultSet = getAllVehicles.executeQuery();
            while(resultSet.next()){
                Vehicle vehicle = null;
                switch (resultSet.getInt(3)){
                    case 1: vehicle = new MotorVehicle(); vehicle.setVehicleType("MOTOR VEHICLE"); break;
                    case 2: vehicle = new HeavyVehicle(); vehicle.setVehicleType("HEAVY VEHICLE"); break;
                    case 3: vehicle = new TrailerVehicle(); vehicle.setVehicleType("TRAILER VEHICLE"); break;
                    default:    break;
                }
                vehicle.setVehicleId(resultSet.getInt(1));
                vehicle.setVehicleName(resultSet.getString(2));
                vehicle.setLicencePlate(resultSet.getString(4));
                vehicle.setColor(resultSet.getString(5));
                vehicles.add(vehicle);
            }
            return vehicles;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Vehicle> getVehiclesByParametars(String vehicleName, String vehicleLicencePlate, MotorFleet motorFleet){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try {
            getVehiclesByParametars.setString(1,vehicleName);
            getVehiclesByParametars.setString(2,vehicleLicencePlate);
            ResultSet resultSet = getVehiclesByParametars.executeQuery();
            while(resultSet.next()){
                Vehicle vehicle = null;
                switch (resultSet.getInt(3)){
                    case 1: vehicle = new MotorVehicle(); vehicle.setVehicleType("MOTOR VEHICLE"); break;
                    case 2: vehicle = new HeavyVehicle(); vehicle.setVehicleType("HEAVY VEHICLE"); break;
                    case 3: vehicle = new TrailerVehicle(); vehicle.setVehicleType("TRAILER VEHICLE"); break;
                    default:    break;
                }
                vehicle.setVehicleId(resultSet.getInt(1));
                vehicle.setVehicleName(resultSet.getString(2));
                vehicle.setLicencePlate(resultSet.getString(4));
                vehicle.setColor(resultSet.getString(5));
                if(isThereVehicleInMotorFleet(motorFleet.getMotorFleetId(), vehicle.getVehicleId()))
                    vehicles.add(vehicle);
            }
            return vehicles;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Service> getAllServiceOfVehicle(Vehicle vehicle){
        ArrayList<Service> services = new ArrayList<>();
        try {
            getAllServiceOfVehicle.setInt(1,vehicle.getVehicleId());
            ResultSet resultSet = getAllServiceOfVehicle.executeQuery();
            while(resultSet.next()){
                Service service = new Service(resultSet.getString(2), null,resultSet.getString(4),resultSet.getString(5));
                service.setServiceId(resultSet.getInt(1));
                LocalDate localDate = LocalDate.parse(resultSet.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                service.setDateOfService(localDate);
                services.add(service);
            }
            return services;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Service> getAllServiceOfVehicleByParametars(Vehicle vehicle, String serviceName, String serviceDate, String servicePerson){
        ArrayList<Service> services = new ArrayList<>();
        try {
            getAllServiceOfVehicleByParametars.setInt(1,vehicle.getVehicleId());
            getAllServiceOfVehicleByParametars.setString(2, serviceName);
            getAllServiceOfVehicleByParametars.setString(3, serviceDate);
            getAllServiceOfVehicleByParametars.setString(4, servicePerson);
            ResultSet resultSet = getAllServiceOfVehicleByParametars.executeQuery();
            while(resultSet.next()){
                Service service = new Service(resultSet.getString(2), null,resultSet.getString(4),resultSet.getString(5));
                service.setServiceId(resultSet.getInt(1));
                service.setDateOfService(LocalDate.parse(resultSet.getString(3),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                services.add(service);
            }
            return services;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void addServiceForVehicle(Vehicle vehicle, Service service){
        try{

            Boolean haveService = false;
            searchService.setString(1,service.getNameOfService());
            ResultSet searchResultSet = searchService.executeQuery();
            if(searchResultSet.next())  haveService = true;

            ResultSet resultSet = getNextServiceManagementId.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);

            //adding in SERVICE_MANAGEMENT

            addServiceForVehicle.setInt(1, id);
            addServiceForVehicle.setInt(2,vehicle.getVehicleId());
            addServiceForVehicle.setInt(3, service.getServiceId());
            addServiceForVehicle.executeUpdate();




            //adding in SERVICE
            if(!haveService) {
                addService.setInt(1, service.getServiceId());
                addService.setString(2, service.getNameOfService());
                addService.setString(3, service.getDateOfService().toString());
                addService.setString(4, service.getServicePerson());
                addService.setString(5, service.getInformation());
                addService.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addServiceForVehicle(Vehicle vehicle, Service service, MotorFleet motorFleet){
        try{

            Boolean haveService = false;
            searchService.setString(1,service.getNameOfService());
            ResultSet searchResultSet = searchService.executeQuery();
            if(searchResultSet.next())  haveService = true;

            ResultSet resultSet = getNextServiceManagementId.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);

            //adding in SERVICE_MANAGEMENT

            addServiceForVehicle.setInt(1, id);
            addServiceForVehicle.setInt(2,vehicle.getVehicleId());
            addServiceForVehicle.setInt(3, service.getServiceId());
            addServiceForVehicle.executeUpdate();




            //adding in SERVICE
            if(!haveService) {
                addService.setInt(1, service.getServiceId());
                addService.setString(2, service.getNameOfService());
                addService.setString(3, service.getDateOfService().toString());
                addService.setString(4, service.getServicePerson());
                addService.setString(5, service.getInformation());
                addService.executeUpdate();
            }

            //adding servic in motor_fleet_service
            addServiceInMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            addServiceInMotorFleet.setInt(2, service.getServiceId());
            addServiceInMotorFleet.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getNextIdService(){
        try{
            ResultSet resultSet = getNextIdService.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public void deleteServiceForVehicle(Vehicle vehicle, Service service){
        try{
            deleteServiceForVehicle.setInt(1, vehicle.getVehicleId());
            deleteServiceForVehicle.setInt(2, service.getServiceId());
            deleteServiceForVehicle.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteServiceForVehicle(Vehicle vehicle, Service service, MotorFleet motorFleet){
        try{
            deleteServiceForVehicle.setInt(1, vehicle.getVehicleId());
            deleteServiceForVehicle.setInt(2, service.getServiceId());
            deleteServiceForVehicle.executeUpdate();

            deleteServiceFromMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            deleteServiceFromMotorFleet.setInt(2, service.getServiceId());
            deleteServiceFromMotorFleet.executeUpdate();

            deleteService.setInt(1,service.getServiceId());
            deleteService.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateServiceForVehicle(Service service){
        try{
            updateServiceForVehicle.setString(1,service.getNameOfService());
            updateServiceForVehicle.setString(2,service.getServiceDateAsString());
            updateServiceForVehicle.setString(3,service.getServicePerson());
            updateServiceForVehicle.setString(4,service.getInformation());
            updateServiceForVehicle.setInt(5,service.getServiceId());
            updateServiceForVehicle.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int searchService(Service service){
        try{
            searchService.setString(1,service.getNameOfService());
            ResultSet resultSet = searchService.executeQuery();
            if(resultSet.next())    return resultSet.getInt(1);
            return getNextIdService.executeQuery().getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Vehicle> getVehiclesByParametars2(String vehicleName, String vehicleLicencePlate, String vehicleColor, int motorFleetId){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try {
            getVehiclesByParametars2.setString(1,vehicleName);
            getVehiclesByParametars2.setString(2,vehicleLicencePlate);
            getVehiclesByParametars2.setString(3,vehicleColor);
            ResultSet resultSet = getVehiclesByParametars2.executeQuery();
            while(resultSet.next()){
                Vehicle vehicle = null;
                switch (resultSet.getInt(3)){
                    case 1: vehicle = new MotorVehicle(); vehicle.setVehicleType("MOTOR VEHICLE"); break;
                    case 2: vehicle = new HeavyVehicle(); vehicle.setVehicleType("HEAVY VEHICLE"); break;
                    case 3: vehicle = new TrailerVehicle(); vehicle.setVehicleType("TRAILER VEHICLE"); break;
                    default:    break;
                }
                vehicle.setVehicleId(resultSet.getInt(1));
                vehicle.setVehicleName(resultSet.getString(2));
                vehicle.setLicencePlate(resultSet.getString(4));
                vehicle.setColor(resultSet.getString(5));
                if(isThereVehicleInMotorFleet(motorFleetId,vehicle.getVehicleId()))
                    vehicles.add(vehicle);
            }
            return vehicles;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getNextIDVehicle(){
        try{
            ResultSet resultSet = getNextIDVehicle.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public void addVehicle(Vehicle vehicle, MotorFleet motorFleet){
        try{

            addVehicle.setInt(1, vehicle.getVehicleId());
            addVehicle.setString(2, vehicle.getVehicleName());
            switch (vehicle.getVehicleType()){
                case "MOTOR VEHICLE": addVehicle.setInt(3, 1); break;
                case "HEAVY VEHICLE": addVehicle.setInt(3, 2); break;
                default: addVehicle.setInt(3,3);
            }
            addVehicle.setString(4, vehicle.getLicencePlate());
            addVehicle.setString(5,vehicle.getColor());
            addVehicle.executeUpdate();

            addVehicleToMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            addVehicleToMotorFleet.setInt(2,vehicle.getVehicleId());
            addVehicleToMotorFleet.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateVehicle(Vehicle vehicle){
        try{
            updateVehicle.setString(1,vehicle.getVehicleName());
            switch (vehicle.getVehicleType()){
                case "MOTOR VEHICLE": updateVehicle.setInt(2, 1); break;
                case "HEAVY VEHICLE": updateVehicle.setInt(2, 2); break;
                default: updateVehicle.setInt(2,3);
            }
            updateVehicle.setString(3,vehicle.getLicencePlate());
            updateVehicle.setString(4,vehicle.getColor());
            updateVehicle.setInt(5, vehicle.getVehicleId());
            updateVehicle.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteVehicle(Vehicle vehicle, MotorFleet motorFleet){
        try{

            //lista opreme
            ArrayList<Equipment> equipments = getAllEquipmentOfVehicle(vehicle, motorFleet);
            for(int i = 0; i < equipments.size(); i++){
                if(!isThereEquipmentInOtherVehicle(vehicle.getVehicleId(),equipments.get(i).getEquipmentId()))  deleteEquipment(equipments.get(i));
            }

            //brisanje svih servisa koji su bili kod vozila

            getAllServiceOfVehicle.setInt(1, vehicle.getVehicleId());
            ResultSet resultSet = getAllServiceOfVehicle.executeQuery();
            while(resultSet.next()){
                deleteService.setInt(1,resultSet.getInt(1));
                deleteService.executeUpdate();
            }

            //brisanje iz servisnog managementa
            deleteVehicleFromService.setInt(1,vehicle.getVehicleId());
            deleteVehicleFromService.executeUpdate();

            //brisanje iz tabele vozila-oprema
            deleteVehicleFromVehicleEquipment.setInt(1,vehicle.getVehicleId());
            deleteVehicleFromVehicleEquipment.executeUpdate();

            //brisanje iz motorFleet tabele
            deletevehicleFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            deletevehicleFromMotorFleet.setInt(2,vehicle.getVehicleId());
            deletevehicleFromMotorFleet.executeUpdate();

            //brisanje iz tabele vozila
            deleteVehicle.setInt(1,vehicle.getVehicleId());
            deleteVehicle.executeUpdate();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public Boolean isThereEquipmentInOtherVehicle(int vehicle_id, int equipment_id){
        try {
            isThereEquipmentInOtherVehicle.setInt(1, vehicle_id);
            isThereEquipmentInOtherVehicle.setInt(2, equipment_id);
            ResultSet resultSet = isThereEquipmentInOtherVehicle.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }


    public void deleteEquipment(Equipment equipment){
        try{
            deleteEquipment.setInt(1,equipment.getEquipmentId());
            deleteEquipment.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Storage> getAllStorages(){
        ArrayList<Storage> storages = new ArrayList<>();
        try{
            ResultSet resultSet = getAllStorages.executeQuery();
            while(resultSet.next()){
                Storage storage = new Storage();
                storage.setStorageId(resultSet.getInt(1));
                storage.setNameOfStorage(resultSet.getString(2));
                int id = resultSet.getInt(3);
                storage.setManager(getUserByID(id));
                storages.add(storage);
            }
            return storages;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByID(int userId){
        try {
            getUserByID.setInt(1,userId);
            ResultSet resultSet = getUserByID.executeQuery();
            if(resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), null);
                user.setUserId(userId);
                user.setUsername(resultSet.getString(6));
                user.setPassword(resultSet.getString(7));
                int level = resultSet.getInt(8);
                switch (level) {
                    case 1:
                        user.setLevelOfResponsibility(LevelOfResponsibility.ADMINISTRATOR);
                        break;
                    case 2:
                        user.setLevelOfResponsibility(LevelOfResponsibility.VEHICLE);
                        break;
                    case 3:
                        user.setLevelOfResponsibility(LevelOfResponsibility.STORAGE);
                        break;
                    case 4:
                        user.setLevelOfResponsibility(LevelOfResponsibility.SERVICE);
                        break;
                    case 5:
                        user.setLevelOfResponsibility(LevelOfResponsibility.EMPLOYEE);
                        break;
                    case 6:
                        user.setLevelOfResponsibility(LevelOfResponsibility.WORKER);
                        break;
                    default:
                        user.setLevelOfResponsibility(LevelOfResponsibility.DIRECTOR);
                }
                return user;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Storage> getStoragesByParametar(String storageName, MotorFleet motorFleet){
        ArrayList<Storage> storages = new ArrayList<>();
        try{
            getStoragesByParametar.setString(1,storageName);
            ResultSet resultSet = getStoragesByParametar.executeQuery();
            while(resultSet.next()){
                Storage storage = new Storage();
                storage.setStorageId(resultSet.getInt(1));
                storage.setNameOfStorage(resultSet.getString(2));
                int id = resultSet.getInt(3);
                storage.setManager(getUserByID(id));
                if(isThereStorageInMotoFleet(motorFleet.getMotorFleetId(), storage.getStorageId()))
                    storages.add(storage);
            }
            return storages;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void addStorage(Storage storage, MotorFleet motorFleet){
        try{
            ResultSet resultSet = getNextIdStorage.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);

            storage.setStorageId(id);

            addStorage.setInt(1, id);
            addStorage.setString(2, storage.getNameOfStorage());
            addStorage.setInt(3, storage.getManager().getUserId());
            addStorage.executeUpdate();

            addStorageInMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            addStorageInMotorFleet.setInt(2,storage.getStorageId());
            addStorageInMotorFleet.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateStorage(Storage storage){
        try{
            updateStorage.setString(1, storage.getNameOfStorage());
            updateStorage.setInt(2, storage.getManager().getUserId());
            updateStorage.setInt(3, storage.getStorageId());
            updateStorage.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteStorage(int storageId, MotorFleet motorFleet){
        try{
            //lista svih partova
            ArrayList<Part> parts = getAllPartsOfStorage(storageId, motorFleet);
            for(int i = 0; i < parts.size(); i++){
                if(!isTherePartInOtherStorage(storageId,parts.get(i).getPartId())) {
                    deletePart(parts.get(i));
                    //brisanje parta iz motor_fleet_part
                    deletePartFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
                    deletePartFromMotorFleet.setInt(2,motorFleet.getMotorFleetId());
                    deletePartFromMotorFleet.executeUpdate();
                }
            }

            //brisanje iz motor_fleet_storage
            deleteStorageFromMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            deleteStorageFromMotorFleet.setInt(2, storageId);
            deleteStorageFromMotorFleet.executeUpdate();

            //brisanje skladista iz skladista dijelovi
            deleteStorageFromStorageParts.setInt(1,storageId);
            deleteStorageFromStorageParts.executeUpdate();

            //delete storage from table storage
            deleteStorage.setInt(1,storageId);
            deleteStorage.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Boolean isTherePartInOtherStorage(int storageId, int partId){
        try {
            isTherePartInOtherStorage.setInt(1, storageId);
            isTherePartInOtherStorage.setInt(2, partId);
            ResultSet resultSet = isTherePartInOtherStorage.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public ArrayList<Part> getAllPartsOfStorage(int storage_id, MotorFleet motorFleet){
        ArrayList<Part> parts = new ArrayList<>();
        try {
            getAllPartsOfStorage.setInt(1,storage_id);
            ResultSet resultSet = getAllPartsOfStorage.executeQuery();
            while(resultSet.next()){
                Part part = new Part(resultSet.getInt(1),resultSet.getString(2), null);
                if(resultSet.getInt(3) == 1) part.setUsefulPart(true);
                else    part.setUsefulPart(false);
                if(isPartInMotorFleet(motorFleet.getMotorFleetId(),part.getPartId()))
                    parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void addPartToStorage(Storage storage, Part part, MotorFleet motorFleet){
        try {
            //dodavanje u storage_parts
            if(storage != null) {
                addPartToStorage.setInt(1, storage.getStorageId());
                addPartToStorage.setInt(2, part.getPartId());
                addPartToStorage.executeUpdate();
            }

            if(!isTherePart(part, motorFleet)) {
                //dodavnje novog part-a
                addPart.setInt(1, part.getPartId());
                addPart.setString(2, part.getNameOfPart());
                if (part.isUsefulPart()) addPart.setInt(3, 1);
                else addPart.setInt(3, 0);
                addPart.executeUpdate();
            }

            if(!isPartInMotorFleet(motorFleet.getMotorFleetId(),part.getPartId())){
                //dodati u motor part
                addPartInMotorFleet.setInt(1,motorFleet.getMotorFleetId());
                addPartInMotorFleet.setInt(2,part.getPartId());
                addPartInMotorFleet.executeUpdate();
            }




        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addPart(Part part){
        try {
            addPart.setInt(1, part.getPartId());
            addPart.setString(2, part.getNameOfPart());
            if (part.isUsefulPart()) addPart.setInt(3, 1);
            else addPart.setInt(3, 0);
            addPart.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getNextIDPart(){
        try {
            ResultSet resultSetId = getNextPartId.executeQuery();
            if(resultSetId.next() && resultSetId.getInt(1) != 0)
                return resultSetId.getInt(1);
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void updatePart(Part part){
        try {
                updatePart.setString(1, part.getNameOfPart());
                if (part.isUsefulPart()) updatePart.setInt(2, 1);
                else updatePart.setInt(2, 0);
                updatePart.setInt(3, part.getPartId());
                updatePart.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePart(Part part){
        try{
            deletePartFromStorage.setInt(1,part.getPartId());
            deletePartFromStorage.executeUpdate();
            deletePart.setInt(1,part.getPartId());
            deletePart.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePart(Part part, Storage storage, MotorFleet motorFleet){
        try{
            if(storage != null){
                deletePartFromStorage.setInt(1,part.getPartId());
                deletePartFromStorage.setInt(2,storage.getStorageId());
                deletePartFromStorage.executeUpdate();
            }else {
                deletePartFromAllStorage.setInt(1, part.getPartId());
                deletePartFromAllStorage.executeUpdate();
            }

            if(!isThereStorageWithPartInMotorFleet(motorFleet.getMotorFleetId(), part.getPartId())){
                deletePartFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
                deletePartFromMotorFleet.setInt(2,part.getPartId());
                deletePartFromMotorFleet.executeUpdate();
            }

            if(storage != null) {
                if(!isTherePartInOtherStorage(storage.getStorageId(), part.getPartId())){
                    deletePart.setInt(1,part.getPartId());
                    deletePart.executeUpdate();
                }
            }else{
                if(!isTherePartInOtherMotorFleet(motorFleet.getMotorFleetId(), part.getPartId())){
                    deletePart.setInt(1,part.getPartId());
                    deletePart.executeUpdate();
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Equipment> getAllEquipmentOfVehicle(Vehicle vehicle, MotorFleet motorFleet){
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            getAllEquipmentOfVehicle.setInt(1,vehicle.getVehicleId());
            ResultSet resultSet = getAllEquipmentOfVehicle.executeQuery();
            while(resultSet.next()){
                Equipment equipment = new Equipment(resultSet.getString(2),resultSet.getString(3));
                equipment.setEquipmentId(resultSet.getInt(1));
                if(isThereVehicleInMotorFleet(motorFleet.getMotorFleetId(), vehicle.getVehicleId()))
                    equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getNextIdEquipment(){
        try {
            ResultSet resultSetId = getNextIdEquipment.executeQuery();
            if(resultSetId.next() && resultSetId.getInt(1) != 0)
                return resultSetId.getInt(1);
            return 1;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void addEquipmentForVehicle(Vehicle vehicle, Equipment equipment, MotorFleet motorFleet){
        try {
            if(!isThereEquipment(equipment, motorFleet)){
                //dodavanje novog equipment-a
                addEquipment.setInt(1, equipment.getEquipmentId());
                addEquipment.setString(2, equipment.getNameOfEquipment());
                addEquipment.setString(3, equipment.getInformation());
                addEquipment.executeUpdate();

                //dodavanje equipmenta u motor fleet equipemnt
                addEquipmentInMotorFleet.setInt(1, motorFleet.getMotorFleetId());
                addEquipmentInMotorFleet.setInt(2, equipment.getEquipmentId());
                addEquipmentInMotorFleet.executeUpdate();
            }


            //dodavnje equipment-a u vehicle
            if(vehicle != null) {
                addEquipmentForVehicle.setInt(1, vehicle.getVehicleId());
                addEquipmentForVehicle.setInt(2, equipment.getEquipmentId());
                addEquipmentForVehicle.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateEquipment(Equipment equipment){
        try {
            updateEquipment.setString(1,equipment.getNameOfEquipment());
            updateEquipment.setString(2,equipment.getInformation());
            updateEquipment.setInt(3,equipment.getEquipmentId());
            updateEquipment.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /*public void deleteEquipmentFromWehicle(Equipment equipment){
        try{
            deleteEquipmentFromWehicle.setInt(1,equipment.getEquipmentId());
            deleteEquipmentFromWehicle.executeUpdate();

            deleteEquipment.setInt(1,equipment.getEquipmentId());
            deleteEquipment.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }*/

    public ArrayList<Equipment> getEquipmentByParametar(String equipmentName, Vehicle vehicle) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            getEquipmentByParametar.setInt(1,vehicle.getVehicleId());
            getEquipmentByParametar.setString(2, equipmentName);
            ResultSet resultSet = getEquipmentByParametar.executeQuery();
            while(resultSet.next()){
                Equipment equipment = new Equipment(resultSet.getString(2),resultSet.getString(3));
                equipment.setEquipmentId(resultSet.getInt(1));
                equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Equipment> getAllEquipment(){
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            ResultSet resultSet = getAllEquipment.executeQuery();
            while(resultSet.next()){
                Equipment equipment = new Equipment(resultSet.getString(2),resultSet.getString(3));
                equipment.setEquipmentId(resultSet.getInt(1));
                equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Part> getAllParts(){
        ArrayList<Part> parts = new ArrayList<>();
        try {
            ResultSet resultSet = getAllParts.executeQuery();
            while(resultSet.next()){
                Part part = new Part(resultSet.getInt(1),resultSet.getString(2), null);
                if(resultSet.getInt(3) == 1) part.setUsefulPart(true);
                else    part.setUsefulPart(false);
                parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Equipment> searchEquipment(String equipmentName) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            searchEquipment.setString(1,equipmentName);
            ResultSet resultSet = searchEquipment.executeQuery();
            while(resultSet.next()){
                Equipment equipment = new Equipment(resultSet.getString(2),resultSet.getString(3));
                equipment.setEquipmentId(resultSet.getInt(1));
                equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Part> getPartsOfStorageByParametars(Storage storage, Integer partId, String partName){
        ArrayList<Part> parts = new ArrayList<>();
        try{
            getPartsOfStorageByParametars.setInt(1,storage.getStorageId());
            getPartsOfStorageByParametars.setString(2,partName);
            getPartsOfStorageByParametars.setInt(3,partId);
            ResultSet resultSet = getPartsOfStorageByParametars.executeQuery();
            while(resultSet.next()){
                Part part = new Part();
                part.setPartId(resultSet.getInt(1));
                part.setNameOfPart(resultSet.getString(2));
                if(resultSet.getInt(3) == 1)    part.setUsefulPart(true);
                else part.setUsefulPart(false);

                parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Part> getPartsByParametars(Integer partId, String partName){
        ArrayList<Part> parts = new ArrayList<>();
        try{
            getPartsByParametars.setInt(1,partId);
            getPartsByParametars.setString(2,partName);
            ResultSet resultSet = getPartsByParametars.executeQuery();
            while(resultSet.next()){
                Part part = new Part();
                part.setPartId(resultSet.getInt(1));
                part.setNameOfPart(resultSet.getString(2));
                if(resultSet.getInt(3) == 1)    part.setUsefulPart(true);
                else part.setUsefulPart(false);

                parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public User getUserByUsernameAndPassword(String username, String password){
        try {
            if(isThereUserWithUsernameAndPassword(username,password)){
                getUserByUsernameAndPassword.setString(1, username);
                getUserByUsernameAndPassword.setString(2, password);
                ResultSet resultSet = getUserByUsernameAndPassword.executeQuery();
                if(resultSet.next()){
                    User user = getUserByID(resultSet.getInt(1));
                    return user;
                }
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isThereVehicleWithLicencePlate(String licencePlate){
        try {
            isThereVehicleWithLicencePlate.setString(1, licencePlate);
            ResultSet resultSet = isThereVehicleWithLicencePlate.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereUserWithUsernameAndPassword(String username, String password) {
        try {
            isThereUserWithUsernameAndPassword.setString(1, username);
            isThereUserWithUsernameAndPassword.setString(2, password);
            ResultSet resultSet = isThereUserWithUsernameAndPassword.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereStorageWithSameName(String storageName) {
        try {
            getStoragesByParametar.setString(1, storageName);
            ResultSet resultSet = getStoragesByParametar.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereEquipment(Equipment equipment, MotorFleet motorFleet){
        try {
            isThereEquipment.setString(1, equipment.getNameOfEquipment());
            ResultSet resultSet = isThereEquipment.executeQuery();
            if(resultSet.next() && isEquipmentInMotorFleet(motorFleet.getMotorFleetId(), equipment.getEquipmentId()))    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Integer getEquipmentIdByName(String nameOdEquipment){
        try {
            getEquipmentIdByName.setString(1, nameOdEquipment);
            ResultSet resultSet = getEquipmentIdByName.executeQuery();
            if(resultSet.next())    return resultSet.getInt(1);
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public Boolean isTherePart(Part part, MotorFleet motorFleet){
        try {
            isTherePart.setString(1, part.getNameOfPart());
            ResultSet resultSet = isTherePart.executeQuery();
            if(resultSet.next() && isPartInMotorFleet(motorFleet.getMotorFleetId(),part.getPartId()))    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Integer getPartIdByName(String nameOfPart){
        try {
            getPartIdByName.setString(1, nameOfPart);
            ResultSet resultSet = getPartIdByName.executeQuery();
            if(resultSet.next())    return resultSet.getInt(1);
            return 0;
        }catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public void updatePartOfStorage(int storageId, int oldPartId, int newPartId){
        try {
            updatePartOfStorage.setInt(1,storageId);
            updatePartOfStorage.setInt(2,newPartId);
            updatePartOfStorage.setInt(3,oldPartId);
            updatePartOfStorage.setInt(4,storageId);
            updatePartOfStorage.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateEquipmentOfVehicle(int vehicleId, int oldEquipmentId, int newEquipmentId){
        try {
            updateEquipmentOfVehicle.setInt(1,vehicleId);
            updateEquipmentOfVehicle.setInt(2,newEquipmentId);
            updateEquipmentOfVehicle.setInt(3,vehicleId);
            updateEquipmentOfVehicle.setInt(4,oldEquipmentId);
            updateEquipmentOfVehicle.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addEquipment(Equipment equipment){
        try {
            addEquipment.setInt(1, equipment.getEquipmentId());
            addEquipment.setString(2, equipment.getNameOfEquipment());
            addEquipment.setString(3, equipment.getInformation());
            addEquipment.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<MotorFleet> getAllMotorFleet(){
        ArrayList<MotorFleet> motorFleets = new ArrayList<>();
        try{
            ResultSet resultSet = getAllMotorFleet.executeQuery();
            while(resultSet.next()){
                MotorFleet motorFleet = new MotorFleet(resultSet.getString(2),null,null,null,null,null);
                motorFleet.setMotorFleetId(resultSet.getInt(1));

                //dodavanje manager
                motorFleet.setManager(getManagerOfMotorFleet(resultSet.getInt(1)));

                //dodavanje vozila
                motorFleet.setVehicleList(getVehiclesFromMothorFleet(resultSet.getInt(1)));

                //dodavanje zaposlenika
                motorFleet.setEmployeeList(getEmployeesFromMotorFleet(resultSet.getInt(1)));

                //dodavanje skladista
                motorFleet.setStorageList(getStoragesFromMotorFleet(resultSet.getInt(1)));

                //dodavanje servisa
                motorFleet.setServiceList(getServiceManagementFromMotorFleet(resultSet.getInt(1)));

                motorFleets.add(motorFleet);
            }
            return motorFleets;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public User getManagerOfMotorFleet(int motorFleetId){
        try{
            getManagerOfMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getManagerOfMotorFleet.executeQuery();
            if(resultSet.next())    return getUserByID(resultSet.getInt(1));
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Vehicle> getVehiclesFromMothorFleet(int motorFleetId){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        try{
            getVehiclesFromMothorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getVehiclesFromMothorFleet.executeQuery();
            while(resultSet.next()){
                Vehicle vehicle = getVehiclesById(resultSet.getInt(1));
                vehicles.add(vehicle);
            }
            return vehicles;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Vehicle getVehiclesById(int vehicleId) {
        try{
            getVehiclesById.setInt(1, vehicleId);
            ResultSet resultSet = getVehiclesById.executeQuery();
            if(resultSet.next()){
                Vehicle vehicle;
                switch (resultSet.getInt(3)){
                    case 1: vehicle = new MotorVehicle(); vehicle.setVehicleType("MOTOR VEHICLE"); break;
                    case 2: vehicle = new HeavyVehicle(); vehicle.setVehicleType("HEAVY VEHICLE"); break;
                    default: vehicle = new TrailerVehicle(); vehicle.setVehicleType("TRAILER VEHICLE");

                }
                vehicle.setVehicleId(resultSet.getInt(1));
                vehicle.setVehicleName(resultSet.getString(2));
                vehicle.setLicencePlate(resultSet.getString(4));
                vehicle.setColor(resultSet.getString(5));
                //vehicle.setEquipmentList(getAllEquipmentOfVehicle(vehicle));
                return vehicle;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getEmployeesFromMotorFleet(int motorFleetId){
        ArrayList<User> users = new ArrayList<>();
        try{
            getEmployeesFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getEmployeesFromMotorFleet.executeQuery();
            while(resultSet.next()){
                User user = getUserByID(resultSet.getInt(1));
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Storage> getStoragesFromMotorFleet(int motorFleetId){
        ArrayList<Storage> storages = new ArrayList<>();
        try{
            getStoragesFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getStoragesFromMotorFleet.executeQuery();
            while(resultSet.next()){
                Storage storage = getStorageById(resultSet.getInt(1));
                storages.add(storage);
            }
            return storages;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Storage getStorageById(int storageId) {
        try{
            getStorageById.setInt(1, storageId);
            ResultSet resultSet = getStorageById.executeQuery();
            if(resultSet.next()){
                Storage storage = new Storage();
                storage.setStorageId(storageId);
                storage.setNameOfStorage(resultSet.getString(2));
                storage.setManager(getUserByID(resultSet.getInt(3)));
                //storage.setParts(getAllPartsOfStorage(storageId, ));
                return storage;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<ServiceManagement> getServiceManagementFromMotorFleet(int motorFleetId){
        ArrayList<ServiceManagement> serviceManagements = new ArrayList<>();
        try{
            getServiceManagementFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getServiceManagementFromMotorFleet.executeQuery();
            while(resultSet.next()){

                ArrayList<Vehicle> vehicles = getVehiclesFromMothorFleet(motorFleetId);
                for(Vehicle vehicle : vehicles){
                    ServiceManagement serviceManagement = new ServiceManagement();
                    serviceManagement.setVehicle(vehicle);
                    serviceManagement.setService(getAllServiceOfVehicle(vehicle));
                    serviceManagements.add(serviceManagement);
                }
            }
            return serviceManagements;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isThereUserInMotorFleet(int motorFleetId, String username, String password){
        try {
            User user = getUserByUsernameAndPassword(username, password);
            isThereUserInMotorFleet.setInt(1,motorFleetId);
            isThereUserInMotorFleet.setInt(2,user.getUserId());
            ResultSet resultSet = isThereUserInMotorFleet.executeQuery();
            if(resultSet.next())    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public ArrayList<Vehicle> getAllVehicles(MotorFleet motorFleet){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        vehicles = getVehiclesFromMothorFleet(motorFleet.getMotorFleetId());
        return vehicles;

    }

    public ArrayList<User> getAllUsers(MotorFleet motorFleet) {
        ArrayList<User> users = new ArrayList<>();
        users = getEmployeesFromMotorFleet(motorFleet.getMotorFleetId());
        return users;
    }

    public ArrayList<Storage> getAllStorages(MotorFleet motorFleet){
        ArrayList<Storage> storages = new ArrayList<>();
        storages = getStoragesFromMotorFleet(motorFleet.getMotorFleetId());
        return storages;
    }

    public ArrayList<Equipment> getAllEquipment(MotorFleet motorFleet){
        ArrayList<Equipment> equipments = new ArrayList<>();
        equipments = getAllEquipmentFromMotorFleet(motorFleet.getMotorFleetId());
        return equipments;
    }

    public ArrayList<Equipment> getAllEquipmentFromMotorFleet(int motorFleetId) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            getAllEquipmentFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getAllEquipmentFromMotorFleet.executeQuery();
            while(resultSet.next()){
                Equipment equipment = getEquipmentById(resultSet.getInt(1));
                equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Equipment getEquipmentById(int equipmentId){
        try {
            getEquipmentById.setInt(1, equipmentId);
            ResultSet resultSet = getEquipmentById.executeQuery();
            if(resultSet.next()){
                Equipment equipment = new Equipment(resultSet.getString(2),resultSet.getString(3));
                equipment.setEquipmentId(equipmentId);
                return equipment;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Part> getAllParts(MotorFleet motorFleet){
        ArrayList<Part> parts = new ArrayList<>();
        parts = getPartsFromMotorFleet(motorFleet.getMotorFleetId());
        return parts;
    }

    public ArrayList<Part> getPartsFromMotorFleet(int motorFleetId) {
        ArrayList<Part> parts = new ArrayList<>();
        try{
            getPartsFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getPartsFromMotorFleet.executeQuery();
            while(resultSet.next()){
                Part part = getPartById(resultSet.getInt(1));
                parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Part getPartById(int partId){
        try {
            getPartById.setInt(1, partId);
            ResultSet resultSet = getPartById.executeQuery();
            if(resultSet.next()){
                Part part = new Part(resultSet.getInt(1), resultSet.getString(2),null);
                if(resultSet.getInt(3) == 1)    part.setUsefulPart(true);
                else  part.setUsefulPart(false);
                return part;
            }
            return null;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean isThereVehicleInMotorFleet(int motorFleetId, int vehicleId){
        try {
            isThereVehicleInMotorFleet.setInt(1,motorFleetId);
            isThereVehicleInMotorFleet.setInt(2,vehicleId);
            ResultSet resultSet = isThereVehicleInMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereStorageInMotoFleet(int motorFleetId, int storageId){
        try {
            isThereStorageInMotoFleet.setInt(1,motorFleetId);
            isThereStorageInMotoFleet.setInt(2,storageId);
            ResultSet resultSet = isThereStorageInMotoFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isEquipmentInMotorFleet(int motorFleetId, int equipment_id){
        try {
            isEquipmentInMotorFleet.setInt(1,motorFleetId);
            isEquipmentInMotorFleet.setInt(2,equipment_id);
            ResultSet resultSet = isEquipmentInMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public void deleteEquipmentFromWehicle(Vehicle vehicle, Equipment equipment, MotorFleet motorFleet){
        try{
            if(vehicle != null) {
                deleteEquipmentFromWehicle.setInt(1, equipment.getEquipmentId());
                deleteEquipmentFromWehicle.setInt(2, vehicle.getVehicleId());
                deleteEquipmentFromWehicle.executeUpdate();
            }else {
                deleteEquipmentFromAllVehicles.setInt(1,equipment.getEquipmentId());
                deleteEquipmentFromAllVehicles.executeUpdate();
            }

            if(!isThereVehicleWithEquipmentInMotorFleet(motorFleet.getMotorFleetId(), equipment.getEquipmentId())){
                deleteEquipmentFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
                deleteEquipmentFromMotorFleet.setInt(2,equipment.getEquipmentId());
                deleteEquipmentFromMotorFleet.executeUpdate();
            }
            if(vehicle != null){
                if(!isThereEquipmentInOtherVehicle(vehicle.getVehicleId(), equipment.getEquipmentId())){

                    deleteEquipment.setInt(1, equipment.getEquipmentId());
                    deleteEquipment.executeUpdate();
                }
            }else {
                if(!isThereEquipmentInOtherMotorFleet(motorFleet.getMotorFleetId(), equipment.getEquipmentId())) {
                    deleteEquipment.setInt(1, equipment.getEquipmentId());
                    deleteEquipment.executeUpdate();
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Boolean isThereVehicleWithEquipmentInMotorFleet(int motorFleetId, int equipment_id){
        try {
            isThereVehicleWithEquipmentInMotorFleet.setInt(1,motorFleetId);
            isThereVehicleWithEquipmentInMotorFleet.setInt(2,equipment_id);
            ResultSet resultSet = isThereVehicleWithEquipmentInMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereEquipmentInOtherMotorFleet(int motorFleetId, int equipment_id){
        try {
            isThereEquipmentInOtherMotorFleet.setInt(1,motorFleetId);
            isThereEquipmentInOtherMotorFleet.setInt(2,equipment_id);
            ResultSet resultSet = isThereEquipmentInOtherMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isPartInMotorFleet(int motorFleetId, int partId){
        try {
            isPartInMotorFleet.setInt(1,motorFleetId);
            isPartInMotorFleet.setInt(2,partId);
            ResultSet resultSet = isPartInMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isThereStorageWithPartInMotorFleet(int motorFleetId, int partId){
        try {
            isThereStorageWithPartInMotorFleet.setInt(1,motorFleetId);
            isThereStorageWithPartInMotorFleet.setInt(2,partId);
            ResultSet resultSet = isThereStorageWithPartInMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public Boolean isTherePartInOtherMotorFleet(int motorFleetId, int partId){
        try {
            isTherePartInOtherMotorFleet.setInt(1,motorFleetId);
            isTherePartInOtherMotorFleet.setInt(2,partId);
            ResultSet resultSet = isTherePartInOtherMotorFleet.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) != 0)    return true;
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return true;
        }
    }

    public ArrayList<MotorFleet> getMotorFleetsByName(String nameOfMotorFleet){
        ArrayList<MotorFleet> motorFleets = new ArrayList<>();

        ArrayList<MotorFleet> allMotorFleets = getAllMotorFleet();
        for(MotorFleet motorFleet : allMotorFleets){
            if(motorFleet.getNameOfMotorFleet().equals(nameOfMotorFleet))   motorFleets.add(motorFleet);
        }
        return motorFleets;
    }

    public int getNextIdMotorFleet(){
        try{
            ResultSet resultSet = getNextIdMotorFleet.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public void addMotorFleet(String nameOfMotorFleet, int managerId){
        try{
            addMotorFleet.setInt(1,getNextIdMotorFleet());
            addMotorFleet.setString(2,nameOfMotorFleet);
            addMotorFleet.setInt(3, managerId);
            addMotorFleet.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public MotorFleet getMotorFleetById(int motorFleetId){
        try {
            getMotorFleetById.setInt(1,motorFleetId);
            ResultSet resultSet = getMotorFleetById.executeQuery();
            if(resultSet.next())    return getMotorFleetsByName(resultSet.getString(2)).get(0);
            return null;
        }catch(SQLException e){
            e.getStackTrace();
            return null;
        }
    }

    public int getNextUserId(){
        try{
            ResultSet resultSet = getNextUserId.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);
            return id;
        }catch (SQLException e){
            e.printStackTrace();
            return 1;
        }
    }

    public void addUser(User user, int motorFleetId){
        try{
            ResultSet resultSet = getNextUserId.executeQuery();
            int id = 1;
            if(resultSet.next() && resultSet.getInt(1) != 0)    id = resultSet.getInt(1);

            user.setUserId(id);

            addUser.setInt(1,id);
            addUser.setString(2,user.getFirstName());
            addUser.setString(3, user.getLastName());
            addUser.setString(4, user.getTelephoneNumber());
            addUser.setString(5, user.getAddress());
            addUser.setString(6, user.getUsername());
            addUser.setString(7, user.getPassword());
            addUser.setInt(8, user.getLevelOfResponsibility().getLevelCode());
            addUser.executeUpdate();

            addUserInMotorFleet.setInt(1,motorFleetId);
            addUserInMotorFleet.setInt(2,user.getUserId());
            addUserInMotorFleet.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateNameOfMotorFleet(MotorFleet motorFleet){
        try {
            updateNameOfMotorFleet.setString(1, motorFleet.getNameOfMotorFleet());
            updateNameOfMotorFleet.setInt(2,motorFleet.getMotorFleetId());
            updateNameOfMotorFleet.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void deleteMotorFleet(MotorFleet motorFleet){
        try {
        //oprema koja se treba obrisati
        ArrayList<Equipment> equipments = getAllEquipmentFromMotorFleet(motorFleet.getMotorFleetId());
        ArrayList<Equipment> otherEquipments = getAllEquipmentInOtherMotorFleets(motorFleet.getMotorFleetId());
        for(int i = 0; i < equipments.size(); i++){
            for(int j = 0; j < otherEquipments.size();i++){
                if(equipments.get(i).getEquipmentId() == otherEquipments.get(j).getEquipmentId()){
                    equipments.remove(i);
                    break;
                }
            }
        }

        //vozila koja se trebaju obrisati
        ArrayList<Vehicle> vehicles = getVehiclesFromMothorFleet(motorFleet.getMotorFleetId());

        //dijelovi koji se trebaju obrisati
        ArrayList<Part> parts = getPartsFromMotorFleet(motorFleet.getMotorFleetId());
        ArrayList<Part> otherParts = getPartsFromOtherMotorFleet(motorFleet.getMotorFleetId());
        for(int i = 0; i < parts.size(); i++){
            for(int j = 0; j < otherParts.size();i++){
                if(parts.get(i).getPartId() == otherParts.get(j).getPartId()){
                    parts.remove(i);
                    break;
                }
            }
        }

        //skladista koja treba izbrisati
        ArrayList<Storage> storages = getStoragesFromMotorFleet(motorFleet.getMotorFleetId());

        //zaposleni koje treba izbrisati
        ArrayList<User> users = getEmployeesFromMotorFleet(motorFleet.getMotorFleetId());

        //servise koje treba izbrisati
        ArrayList<Service> services = getServiceFromMotorFleet(motorFleet.getMotorFleetId());

        //brisanje vozila i svega onog sto je vezano za vozilo
        for(Vehicle vehicle : vehicles){
            deleteVehicleFromVehicleEquipment.setInt(1, vehicle.getVehicleId());
            deleteVehicleFromVehicleEquipment.executeUpdate();
            deleteVehicleFromService.setInt(1, vehicle.getVehicleId());
            deleteVehicleFromService.executeUpdate();
            deletevehicleFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            deletevehicleFromMotorFleet.setInt(2,vehicle.getVehicleId());
            deletevehicleFromMotorFleet.executeUpdate();
            deleteVehicle.setInt(1, vehicle.getVehicleId());
            deleteVehicle.executeUpdate();
        }

        //brisanje opreme
        for(Equipment equipment : equipments){
            deleteEquipmentFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            deleteEquipmentFromMotorFleet.setInt(2, equipment.getEquipmentId());
            deleteEquipmentFromMotorFleet.executeUpdate();
            deleteEquipment.setInt(1, equipment.getEquipmentId());
            deleteEquipment.executeUpdate();
        }

        //brisanje servise
        for (Service service : services){
            deleteServiceFromMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            deleteServiceFromMotorFleet.setInt(2, service.getServiceId());
            deleteServiceFromMotorFleet.executeUpdate();
            deleteService.setInt(1, service.getServiceId());
            deleteService.executeUpdate();
        }

        //brisanje skladista
        for(Storage storage : storages){
            deleteStorageFromStorageParts.setInt(1,storage.getStorageId());
            deleteStorageFromStorageParts.executeUpdate();
            deleteStorageFromMotorFleet.setInt(1,motorFleet.getMotorFleetId());
            deleteStorageFromMotorFleet.setInt(2, storage.getStorageId());
            deleteStorageFromMotorFleet.executeUpdate();
            deleteStorage.setInt(1, storage.getStorageId());
            deleteStorage.executeUpdate();
        }

        //brisanje dijelova
        for(Part part : parts){
            deletePartFromMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            deletePartFromMotorFleet.setInt(2, part.getPartId());
            deletePartFromMotorFleet.executeUpdate();
            deletePart.setInt(1, part.getPartId());
            deletePart.executeUpdate();
        }

        //brisanje user
        for (User user : users){
            deleteUserFromMotorFleet.setInt(1, motorFleet.getMotorFleetId());
            deleteUserFromMotorFleet.setInt(2, user.getUserId());
            deleteUserFromMotorFleet.executeUpdate();
        }
        deleteMotorFleet.setInt(1, motorFleet.getMotorFleetId());
        deleteMotorFleet.executeUpdate();
            for (User user : users){
                deleteUser.setInt(1,user.getUserId());
                deleteUser.executeUpdate();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Equipment> getAllEquipmentInOtherMotorFleets(int motorFleetId) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try{
            getAllEquipmentInOtherMotorFleets.setInt(1, motorFleetId);
            ResultSet resultSet = getAllEquipmentInOtherMotorFleets.executeQuery();
            while(resultSet.next()){
                Equipment equipment = getEquipmentById(resultSet.getInt(1));
                equipments.add(equipment);
            }
            return equipments;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Part> getPartsFromOtherMotorFleet(int motorFleetId) {
        ArrayList<Part> parts = new ArrayList<>();
        try{
            getPartsFromOtherMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getPartsFromOtherMotorFleet.executeQuery();
            while(resultSet.next()){
                Part part = getPartById(resultSet.getInt(1));
                parts.add(part);
            }
            return parts;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Service> getServiceFromMotorFleet(int motorFleetId){
        ArrayList<Service> services = new ArrayList<>();
        try{
            getServiceFromMotorFleet.setInt(1, motorFleetId);
            ResultSet resultSet = getServiceFromMotorFleet.executeQuery();
            while(resultSet.next()){
                getServiceById.setInt(1,resultSet.getInt(1));
                ResultSet resultSetService = getServiceById.executeQuery();
            Service service = new Service(resultSetService.getString(2),LocalDate.parse(resultSetService.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd")),resultSetService.getString(4),resultSetService.getString(5));
            service.setServiceId(resultSetService.getInt(1));
            services.add(service);
            }
            return services;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAllUsersByType(LevelOfResponsibility levelOfResponsibility, MotorFleet motorFleet){
    
        try{
            ArrayList<User> users = new ArrayList<>();
            switch (levelOfResponsibility){
                case WORKER: getAllUsersByType.setInt(1,6); break;
                case ADMINISTRATOR: getAllUsersByType.setInt(1,1); break;
                case VEHICLE: getAllUsersByType.setInt(1,2); break;
                case SERVICE: getAllUsersByType.setInt(1,4); break;
                case STORAGE: getAllUsersByType.setInt(1,3); break;
                case EMPLOYEE: getAllUsersByType.setInt(1,5); break;
            }

            ArrayList<User> users2 = getEmployeesFromMotorFleet(motorFleet.getMotorFleetId());
            ArrayList<User> returnUsers = new ArrayList<>();
            for(User user : users2){
                if(user.getLevelOfResponsibility() == levelOfResponsibility)
                    returnUsers.add(user);
            }
            return returnUsers;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
