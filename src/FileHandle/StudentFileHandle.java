/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileHandle;

import console.entity.Student;
import console.model.StudentModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ADMIN-PC
 */
public class StudentFileHandle {

    public void exportToFile() {
        StudentModel studentModel = new StudentModel();
        ArrayList<Student> list = null;
        System.out.println("==================================================");
        System.out.println("The Student List will be saved in StudentList.txt");
        System.out.println("==================================================");
        try {
            list = studentModel.getList();
        } catch (SQLException e) {
            System.out.println(e);
        }

//Use Try-with-resource instead of using finally block.
        try (BufferedWriter br = new BufferedWriter(new FileWriter("StudentList.txt"));) {

            br.write("==================================================");
            br.newLine();
            br.write("Student List");
            for (Student student : list) {
                br.newLine();
                br.write("==================================================");
                br.newLine();
                br.write("ID: " + student.getId());
                br.newLine();
                br.write("Name: " + student.getName());
                br.newLine();
                br.write("Email: " + student.getEmail());
                br.newLine();
                br.write("==================================================");
            }

        } catch (IOException e) {
            System.out.println(e);
        }
//        finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//
//        }

    }

    public void importFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("StudentList.txt"))) {
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File is not found!!!");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
