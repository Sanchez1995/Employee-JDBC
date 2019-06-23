import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee {

    private int empId;
    private String title;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String gender;
    private String email;
    private String dateOfBirth;
    private String dateOfJoining;
    private int salary;

    /*public Employee(long empId, String title, String firstName, String middleInitial, String lastName, String gender, String email, String dateOfBirth, String dateOfJoining, int salary) {

        this.empId = empId;
        this.title = title;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;

    }*/

    private Connection myConn = null;
    private PreparedStatement myStmt = null;

    private String dbURL = "jdbc:mysql:// localhost:3306/csvemployee?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
    private String user = "student";
    private String pass = "student";

    /*private String dbURL = "jdbc:mysql:// localhost:3306/sakila?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=GMT";
    private String user = "student";
    private String pass = "student";

    public Connection myConn = null;*/

    public void connect() throws SQLException {

        //1. Get a Connection to the database
        myConn = DriverManager.getConnection(dbURL, user, pass);
        //System.out.println("Database connection was successful!");
    }

    public void createDatabase() throws SQLException {
        connect();
        //2. Create a statement

        myStmt = myConn.prepareStatement("drop database if exists csvemployee");

        //3. Drop an existing database
        System.out.println("Dropping an existing database");

        int rowAffected = myStmt.executeUpdate();
        System.out.println("Database dropped successfully ...");

        //4. Create a new database
        System.out.println("Creating a new database");
        String createDatabase = "create database if not exists csvemployee";
        myStmt.executeUpdate(createDatabase);
        System.out.println("Database created successfully ...");
    }

    public void createTable() throws SQLException {
        connect();
        myStmt = myConn.prepareStatement("create table if not exists employee" +
                " (" +
                "empId int not null," +
                "title varchar(255) not null," +
                "firstName varchar(255) not null," +
                "middleInitial varchar(255) not null," +
                "lastName varchar(255) not null," +
                "gender varchar(255) not null," +
                "email varchar(255) not null," +
                "dateOfBirth varchar(255) not null," +
                "dateOfJoining varchar(255) not null," +
                "salary int not null," +
                "primary key(empId)" +
                ") engine=innodb");

        //3. Create a table
        System.out.println("Creating a new table");
        int rowAffected = myStmt.executeUpdate();
        System.out.println("Table created successfully ...");
    }

    public void breakdownCSVData() throws Exception {

        try {
            connect();
            Scanner input = new Scanner(new File("resources\\TestData.csv"));
            input.nextLine();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                //System.out.println(input);

                try (Scanner data = new Scanner(line)) {
                    data.useDelimiter(",");
                    //System.out.println(line);
                    // System.out.println(data);
                    if (data.hasNextInt()) {
                        empId = data.nextInt();
                    }
                    if (data.hasNext()) {

                        title = data.next();
                    }
                    if (data.hasNext()) {

                        firstName = data.next();
                    }
                    if (data.hasNext()) {

                        middleInitial = data.next();
                    }
                    if (data.hasNext()) {

                        lastName = data.next();
                    }
                    if (data.hasNext()) {

                        gender = data.next();
                    }
                    if (data.hasNext()) {
                        email = data.next();
                    }
                    if (data.hasNext()) {
                        dateOfBirth = data.next();
                    }
                    if (data.hasNext()) {
                        dateOfJoining = data.next();
                    }
                    if (data.hasNextInt()) {
                        salary = data.nextInt();
                    }
                    //System.out.println(empId + " " + title + " " + firstName + " " + middleInitial + " " + lastName + " " + gender + " " + email + " " + dateOfBirth + " " + dateOfJoining + " " + salary);
                    }
                System.out.println(empId + " " + title + " " + firstName + " " + middleInitial + " " + lastName + " " + gender + " " + email + " " + dateOfBirth + " " + dateOfJoining + " " + salary);
               insertData(empId, title, firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);

            } //insertData(empId, title, firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }
            //System.out.println(empId);
            //insertData(empId, title, firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary);

        }
    }


    public void insertData(int empId, String title, String firstName, String middleInitial, String
            lastName, String gender, String email, String dateOfBirth, String dateOfJoining, int salary) throws SQLException {
        try { //1. Connect
            connect();
            //2. Create a statement
            myStmt = myConn.prepareStatement("insert into employee" + "(empId, title,  firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary)" + "values " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            //3. Set the parameters
            myStmt.setInt(1, empId);
            myStmt.setString(2, title);
            myStmt.setString(3, firstName);
            myStmt.setString(4, middleInitial);
            myStmt.setString(5, lastName);
            myStmt.setString(6, gender);
            myStmt.setString(7, email);
            myStmt.setString(8, dateOfBirth);
            myStmt.setString(9, dateOfJoining);
            myStmt.setInt(10, salary);

            //5. Execute SQL query
            int rowAffected = myStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }

        }
    }
}





