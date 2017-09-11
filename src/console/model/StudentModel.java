/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.model;

import console.controller.MenuConsole;
import console.entity.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ADMIN-PC
 */
public class StudentModel {

    static Connection con = null;
    private static final String USER = "root";
    private static final String PASS = "1234";
    private static final String URL = "jdbc:mysql://localhost:3306/liststudent?useSSL=false&useUnicode=yes&characterEncoding =UTF-8";

    //Get the student list by return a ResultSet/ Use PreparedStatement.
    public ArrayList getList() throws SQLException {
        ResultSet rs = null;

        PreparedStatement stm = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("=================");
                System.out.println("Database Connected");
                System.out.println("===================");

            } catch (SQLException e) {
                System.out.println(e);
                System.err.println("Cannot connect!");
            }
        }
        String prQuery = "SELECT * FROM list_student";

        stm = con.prepareStatement(prQuery);

        rs = stm.executeQuery();

        //Statement brings query to database and then return a ResultSet stored in rs variable.
        ArrayList<Student> listStudent = new ArrayList<>();
        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            listStudent.add(student);
        }
        return listStudent;
    }
// Use PrepareStatement with parameter to Insert record to table from object input in Java.

    public void insert(Student student) throws SQLException {
        PreparedStatement stm = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("=================");
                System.out.println("Database Connected");
                System.out.println("===================");

            } catch (SQLException e) {
                System.out.println(e);
                System.err.println("Cannot Connect!");
            }
        }
        String prQuery = "INSERT INTO list_student (id,name,email) VALUES (?,?,?)";
        stm = con.prepareStatement(prQuery);
        stm.setLong(1, student.getId());
        stm.setString(2, student.getName());
        stm.setString(3, student.getEmail());
        int i = stm.executeUpdate();
        System.out.println(i + "record affected");
    }

    public Student getStudentById(long id) {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("=================");
                System.out.println("Database Connected");
                System.out.println("===================");
            } catch (Exception e) {
                System.out.println(e);

            }
        }
        ResultSet rs;
        PreparedStatement stm;
        String prQuery = "SELECT * FROM list_student WHERE id = ?";
        Student student = null;
        try {
            stm = con.prepareStatement(prQuery);
            stm.setLong(1, id);
            rs = stm.executeQuery();
            if (rs.isBeforeFirst()) {
                student = new Student();
                rs.next();
                student.setId(rs.getLong("id"));
                student.setName(rs.getString("name"));
                student.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return student;
    }

    public void edit(long id, Student student) throws SQLException {
        PreparedStatement stm = null;

        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("=================");
                System.out.println("Database Connected");
                System.out.println("===================");
            } catch (SQLException e) {
                System.err.println("Cannot Connected!!");
                System.out.println(e);

            }
        }
        try {
            stm = con.prepareStatement("UPDATE list_student SET id = ?, name = ?, email = ? WHERE id = ?");
            stm.setLong(1, System.currentTimeMillis());
            stm.setString(2, student.getName());
            stm.setString(3, student.getEmail());
            stm.setLong(4, id);
            int b = stm.executeUpdate();
            System.out.println("====================");
            System.out.println(b+" row affected");
            System.out.println("=====================");
            System.out.println("DONE!!!");
        } catch (SQLException e) {
            System.out.println("SQL exception");
            System.out.println(e);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void delete(long id) throws SQLException {
        PreparedStatement stm = null;
        ResultSet rs = null;
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Database connected");

            } catch (SQLException e) {
                System.out.println(e);
                System.out.println("Cannot Connected");
                return;

            }
        }
        String preQuery = "SELECT * FROM list_student WHERE id = ?";
        stm = con.prepareStatement(preQuery);
        stm.setLong(1, id);
        String querySearch = "SELECT * FROM list_student WHERE id =" + id;
        rs = stm.executeQuery(querySearch);
        if (rs.isBeforeFirst()) {
            System.out.println("Program found the student \nDeleting process is about implemented");
            String queryDelete = "DELETE FROM list_student WHERE id=" + id;
            int x = stm.executeUpdate(queryDelete);
            System.out.println("=====================");
            System.out.println("Row affected: " + x);
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
