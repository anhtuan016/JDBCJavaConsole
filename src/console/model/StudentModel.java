/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.model;

import console.entity.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ADMIN-PC
 */
public class StudentModel {

    static Connection con = null;
    private static final String USER = "root";
    private static final String PASS = "1234";
    private static final String URL = "jdbc:mysql://localhost:3306/liststudent?useSSL=false";

    //Get the student list by return a ResultSet.
    public ResultSet getList() throws SQLException {
        ResultSet rs = null;
        Statement stm = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Database Connected!");
                stm = con.createStatement();
            } catch (SQLException e) {
                System.out.println(e);
                System.err.println("Cannot connect!");
            }
        } else {
            try {
                stm = con.createStatement();
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Error");
            }
        }
        String query = "SELECT * FROM list_student";

        //Statement brings query to database and then return a ResultSet stored in rs variable.
        rs = stm.executeQuery(query);
        return rs;

    }

    public void insert(Student student) throws SQLException {
        Statement stm = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Database Connected!");
                stm = con.createStatement();

            } catch (SQLException e) {
                System.out.println(e);
                System.err.println("Cannot Connect!");
            }
        } else {
            stm = con.createStatement();
        }
        String str = "INSERT INTO list_student VALUES (" + student.getId() + "," + "'" + student.getName() + "'" + "," + "'" + student.getEmail() + "'" + ")";
        
        stm.executeUpdate(str);

    }

    public void edit(long i, Student student) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("=================");
                System.out.println("Database Connected");
                System.out.println("===================");
                stm = con.createStatement();
            } catch (SQLException e) {
                System.err.println("Cannot Connected!!");
                System.out.println(e);
            }
        } else {
            stm = con.createStatement();
        }
        String querySearch = "SELECT * FROM list_student WHERE id =" + i;
        rs = stm.executeQuery(querySearch);
        if (rs.isBeforeFirst()) {
            System.out.println("====================");
            System.out.println("Program found the student-Update process is about implemented");
            String queryUpdate = "UPDATE list_student SET id =" + student.getId() + "," + "name ="
                    + "'" + student.getName() + "'" + "," + "email =" + "'" + student.getEmail() + "'" + "WHERE id =" + i;
            stm.executeUpdate(queryUpdate);
            System.out.println("================");
            System.out.println("DONE!!!!");
            System.out.println("=====================");

        } else {
            System.out.println("============================");
            System.err.println("The student is not exist!!!");
            System.out.println("========================");
        }
    }

    public void delete(long i) throws SQLException {
        Statement stm = null;
        ResultSet rs = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Database connected");
                stm = con.createStatement();
            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Cannot Connected");
            }
        } else {
            stm = con.createStatement();
        }
        String querySearch = "SELECT * FROM list_student WHERE id =" + i;
        rs = stm.executeQuery(querySearch);
        if (rs.isBeforeFirst()) {
            System.out.println("Program found the student \nDeleting process is about implemented");
            String queryDelete = "DELETE FROM list_student WHERE id=" + i;
            stm.executeUpdate(queryDelete);
            System.out.println("=================");
            System.out.println("DONE!!!!");
            System.out.println("=================");
        } else {
            System.err.println("The student is not exist!!");
        }
    }

    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connection is closed");
            } catch (SQLException e) {
                System.out.println(e);
                System.err.println("Error");
            }
        }
    }

}
