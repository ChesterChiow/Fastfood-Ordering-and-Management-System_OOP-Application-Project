package foms.food;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a food item in the menu, providing methods to customize and manage its properties.
 */
public abstract class FoodItem implements Serializable {
    /**
     * Name of the FoodItem.
     */
    private String name;
    /**
     * Price of the FoodItem.
     */
    private double price;
    /**
     * Availability of the FoodItem.
     */
    private boolean availability = true;
    /**
     * Description of the FoodItem.
     */
    private String description;
    /**
     * String of Customer's request.
     */
    private String customRequest = " ";

    /**
     * Constructs a FoodItem object with the specified name, price, and description.
     *
     * @param name        The name of the food item.
     * @param price       The price of the food item.
     * @param description A brief description of the food item.
     */
    public FoodItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }



    /**
     * Allows customization of the food item with special requests.
     */
    public void customiseFoodItem() {
        System.out.printf("Customising food item %s...\n", getName());
        System.out.println("Enter your custom request (press 0 to quit): ");

        Scanner scanner = new Scanner(System.in);
        String customRequest;

        try {

            customRequest = scanner.nextLine();

            if (customRequest.equals("0")) {
                System.out.println("Didnt not input custom request. \nReturning to previous page.");
                return;
            }

            setCustomRequest(customRequest);
            System.out.printf("Custom Request has been set to: %s\n", customRequest);
            return;
        } catch (Exception e) {
            System.out.println("something went wrong");
        }
    }


    // Getters and Setters omitted for brevity

    /**
     * Gets the name of the food item.
     *
     * @return A string representing the name of the food item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the food item.
     *
     * @return The price of the food item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the availability status of the food item.
     *
     * @return True if the food item is available, false otherwise.
     */
    public boolean getAvailability() {
        return availability;
    }

    /**
     * Gets the description of the food item.
     *
     * @return A string representing the description of the food item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the name of the food item.
     *
     * @param name The name to set for the food item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the price of the food item based on user input.
     */
    public void updatePrice() {
        System.out.print("Enter the new price: ");
        Scanner scanner = new Scanner(System.in);
        double newPrice;
        while (true) {
            try {
                newPrice = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("enter a float!");
                scanner.next();
            }
        }
        this.price = newPrice;
    }

    /**
     * Sets the availability of the food item based on user input.
     */
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    /**
     * Sets the description of the food item based on user input.
     * @param description This is the description of the food.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the description of the food item based on user input.
     */
    public void updateDescription() {
        System.out.print("Enter the new Description: ");
        Scanner scanner = new Scanner(System.in);
        String newDescription;
        while (true) {
            try {
                newDescription = scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("enter a float!");
                scanner.next();
            }
        }
        this.description = newDescription;

        System.out.printf("Updated the description to: %s\n",description);
    }

    /**
     * Overrides the toString method to provide a string representation of the food item.
     *
     * @return A string representation of the food item.
     */
    @Override
    public String toString() {
        return name + ": " + description + " | Price: $" + price + (availability ? " (Available)" : " (Unavailable)");
    }



    /**
     * Sets the availability of the food item based on user input.
     */
    public void setAvailability() {
        Scanner scanner = new Scanner(System.in);

        String choice;
        while (true) {
            System.out.print("Choose 1 to set as Available\n 2 to set as Unavailable.\nChoose 0 to exit.\n" +
                    "Enter your choice: ");
            choice = scanner.next();
            switch (choice) {
                case "0"-> {System.out.println("going back to previous page..");
                            return;}
                case "1" -> {System.out.println("Availability has been set to true.");
                             availability = true;}
                case "2" -> {System.out.println("Availability has been set to false.");
                    availability = false;}
                }
            }
        }
    /**
     * Sets the custom request for the food item.
     *
     * @param customRequest The custom request to set.
     */
    public void setCustomRequest(String customRequest) {
        this.customRequest = customRequest;
    }

    public String  getCustomRequest() {
        return customRequest;
    }
}