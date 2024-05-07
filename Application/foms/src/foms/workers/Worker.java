package foms.workers;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * An abstract class that serves as the base class for all workers in the food order management system.
 * It provides common functionality and properties for all types of workers.
 */
public class Worker implements Serializable {
    /**
     * The first name and last name of this worker.
     */
    private String name;
    /**
     * The age of this worker.
     */
    private int age;
    /**
     * The gender of this worker.
     */
    private char gender;
    /**
     * The role of this worker;
     */
    private char role;
    /**
     * The login ID of this worker. It will not be changeable once set.
     */
    private final String loginID;
    /**
     * The login password of this worker. The default password is "default" and can be changed later.
     */
    private String loginPassword = "default";

    /**
     * Constructor for the Worker class.
     *
     * @param name    The first name and last name of the worker.
     * @param age     The age of the worker.
     * @param gender  The gender of the worker.
     * @param loginID The login ID of the worker. Duplicate will be checked in the previous function.
     */
    public Worker(String name, int age, char gender, String loginID) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.loginID = loginID;
    }


    /**
     * Displays the information of this worker.
     *
     * @return A string containing the name, login ID, age, and gender of this worker.
     */
    public String toString() {
        return String.format("%-20s %-15s %-5d %-3c", name, loginID, age, gender);
    }

    /**
     * Allows the worker to change their password.
     * Must enter previous password to make changes to current password.
     */
    public void changePassword() {

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print("Enter your current password: (press 0 to exit)");
            input = scanner.next();
            try {
                if (input.equals("0")) {
                    System.out.println("Returning...");
                    scanner.close();
                    return;
                }
                if (!loginPassword.equals(input)) { //Not equal then wrong password
                    System.out.println("Wrong Password!");
                } else {
                    System.out.print("Enter your new password: ");
                    this.loginPassword = scanner.next();
                    System.out.println("Successfully changed your password!");
                    return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid Password!");
            }
        }
    }



    /**
     * Gets the name of this worker.
     * @return The name of this worker.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the age of this worker.
     * @return The age of this worker.
     */
    public int getAge() {
        return age;
    }

    /**
     * Gets the gender of this worker.
     * @return The gender of this worker.
     */
    public char getGender() {
        return gender;
    }

    /**
     * Gets the role of this worker.
     * @return The role of this worker.
     */
    public char getRole() {
        return role;
    }

    /**
     * Gets the login ID of this worker.
     * @return The loginID of this worker.
     */
    public String getLoginID() {
        return loginID;
    }

    /**
     * Gets the login password of this worker.
     * @return The login password of this worker.
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * Changes the name of this worker.
     *
     * @param name The new name of this worker.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Changes the age of this worker.
     *
     * @param age The new age of this worker.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Changes the gender of this worker.
     *
     * @param gender The new gender of this worker.
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Changes the role of this worker.
     *
     * @param role The new role of this worker.
     */
    public void setRole(char role) {
        this.role = role;
    }

    /**
     * Changes the password of this worker.
     *
     * @param password The new password of this worker.
     */
    public void setLoginPassword(String password){this.loginPassword=password;}
}