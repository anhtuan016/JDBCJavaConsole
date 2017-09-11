/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.controller;

import FileHandle.StudentFileHandle;
import console.model.StudentModel;
import java.util.Scanner;
import console.entity.Student;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentController {

    //1.getList
    //2.add
    //3.edit.
    //4.delete.
    StudentModel studentModel = new StudentModel();

    public void showList() throws SQLException {
        ArrayList<Student> list;
        list = studentModel.getList();
        for (Student student : list) {
            System.out.println("id: " + student.getId());
            System.out.println("name: " + student.getName());
            System.out.println("email: " + student.getEmail());
        }

    }

    //Nhận dữ liệu từ người dùng
    //Validate dữ liệu, tiến hành lưu thông tin.
    public void addStudent() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter student information");
        System.out.println("Please enter name: ");
        String name = sc.nextLine();
        System.out.println("Please enter email: ");
        String email = sc.nextLine();
        System.out.println("Name:  " + name + " \nemail: " + email);
        //Validate here
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setId(System.currentTimeMillis());
        //Save data to database
        studentModel.insert(student);

    }

    public void editStudent() throws SQLException {
        long id = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter ID of Student you want to edit");
        try {
            id = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {

            System.err.println("You did not enter a valid number!");
            return;
        }
        Student student = studentModel.getStudentById(id);
        if (student != null) {
            System.out.println("Student Found!!!");
            System.out.println("======================");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.print("Do you really want to edit this student(Y/N)?: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "Y":
                    System.out.print("Enter new Name: ");
                    String name = sc.nextLine();
                    System.out.print("\nEnter new Email: ");
                    String email = sc.nextLine();
                    student.setId(System.currentTimeMillis());
                    student.setName(name);
                    student.setEmail(email);
                    studentModel.edit(id, student);
                    break;
                case "N":
                    System.out.println("Cancelled");
                    break;
                default:
                    System.out.println("===============");
                    System.out.println("Wrong Key Pressed");
            }
        } else {
            System.out.println("====================");
            System.out.println("Student not found!!!");
        }

    }

    public void deleteStudent() throws SQLException {
        long id = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of student that you want to delete: ");
        try {
            id = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("You did not enter a valid number!!!");

        }
        //Use ID to get the Student from Database.
        Student student = studentModel.getStudentById(id);
        if (student != null) {
            System.out.println("Student Found: ");
            System.out.println("============================");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
            System.out.println("Email: " + student.getEmail());
            System.out.print("Do you really want to delete this student(Y/N)?: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "Y":
                    studentModel.delete(id);
                    break;
                case "N":
                    System.out.println("Cancelled");
                    break;
                default:
                    System.out.println("Wrong key (Y/N must be UPPERCASE)!!!");
            }
        } else {
            System.err.println("Student not found!!!");

        }

    }

 

    public void Exit() {
        studentModel.closeConnection();
    }
}
