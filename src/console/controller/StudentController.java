/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console.controller;

import console.model.StudentModel;
import java.util.Scanner;
import console.entity.Student;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentController {

    //1.getList
    //2.add
    //3.edit.
    //4.delete.
    StudentModel studentModel = new StudentModel();

    public void getList() throws SQLException {
        ResultSet rs = null;
        rs = studentModel.getList();
        while (rs.next()) {
            System.out.println("ID: " + rs.getLong("id") + "\nName: " + rs.getString("name") + "\nEmail: " + rs.getString("email"));
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
        System.out.println("Name:  " + name + " email: " + email);
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

        System.out.print("Enter new name you want to change: ");
        String name = sc.nextLine();
        System.out.print("\nEnter new Email you want to change: ");
        String email = sc.nextLine();
        Student studentEdited = new Student();
        studentEdited.setName(name);
        studentEdited.setEmail(email);
        studentEdited.setId(System.currentTimeMillis());
        studentModel.edit(id, studentEdited);
    }

    public void deleteStudent() throws SQLException {
        long id = 0;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter ID of student that you want to delete: ");
        try {
            id = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("You did not enter a valid number!!!");
            return;
        }
        studentModel.delete(id);

    }

    public void Exit() {
        studentModel.closeConnection();
    }
}
