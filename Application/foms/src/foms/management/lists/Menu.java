package foms.management.lists;

import foms.food.FoodItem;
import foms.management.filters.menufilters.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Menu class represents the menu of a branch.
 */
public class Menu implements Serializable {

    /**
     * This branch's menu.
     */
    private ArrayList<FoodItem> menu;

    /**
     * Constructor for Menu class.
     */
    public Menu(){
        menu = new ArrayList<>();
    }

    /**
     * Checks if a food item with the given name exists in the menu.
     *
     * @param foodName the name of the food item to check for duplicates
     * @return the FoodItem if a duplicate exists, null otherwise
     */
    public FoodItem isFoodItemInMenu(String foodName) {

        for (FoodItem foodItem: menu) {
            if (foodItem.getName().equals(foodName)) { //duplicate exists
                return foodItem;
            }
        }
        return null;
    }

    /**
     * Adds a created food item to the menu.
     *
     * @param foodItem the FoodItem object to add to the menu
     */
    public void addCreatedFoodItemToMenu(FoodItem foodItem) {

        System.out.printf("Please confirm that you want to add %s to menu.\n",foodItem.getName());
        System.out.println("Enter 1 to add (Enter 0 to exit): ");


        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String choice = scanner.next();
                if (choice.equals("1")) {
                    menu.add(foodItem);
                    System.out.printf("Added %s to menu\n", foodItem.getName());
                    return;
                } else if (choice.equals("0")) {
                    System.out.println("Did not add item to menu.\nReturning to previous page..");
                    return;
                } else {
                    System.out.println("Enter 1 to add (Enter 0 to exit): ");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
    }

    /**
     * Removes a food item from the menu based on its index.
     *
     * @param index the index of the food item to remove from the menu
     */
    public void removeIndexedFoodItemFromMenu(int index) {
        FoodItem foodItem;
        try {
            foodItem = menu.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexed out of range.");
            return;
        }

        System.out.printf("Please confirm that you want to remove %s from menu.\n",foodItem.getName());
        System.out.println("Enter 1 to remove (Enter 0 to exit): ");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String choice = scanner.next();
                if (choice.equals("1")) {
                    menu.remove(foodItem);
                    System.out.printf("Removed %s from menu.\n", foodItem.getName());
                    return;
                } else if (choice.equals("0")) {
                    System.out.println("Did not remove item to menu.\nReturning to previous page..");
                    return;
                } else {
                    System.out.println("Enter 1 to remove (Enter 0 to exit): ");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
    }

    /**
     * Asks the person to choose the menu filter type and prints accordingly.
     *
     * @return the input on the latest chosen filter, if needed
     */
    public int displayMenu() {

        int input;

        Scanner scanner = new Scanner(System.in);

        MenuFilters filter = null;
        while (true) {
            try {
                System.out.print("Filter the Menu:\n1.Display All Items\n2.Display Set Meals\n3.Display Main Dish" +
                        "\n4.Display Sides\n5.Display Drinks (Press 0 to quit):");
                input = scanner.nextInt();


                switch (input) {
                    case 0 -> {
                        System.out.println("returning to previous page..");
                        return 0;
                    }
                    case 1 -> filter = new BaseMenuFilter();
                    case 2 -> filter = new SetMealFilter();
                    case 3 -> filter = new MainDishFilter();
                    case 4 -> filter = new SidesFilter();
                    case 5 -> filter = new DrinksFilter();
                    default -> {
                        System.out.println("Wrong Option!");
                        continue;
                    }
                }

                    filter.displayFilteredMenu(menu);
                    return input;


            } catch (InputMismatchException e) {
                System.out.println("Enter a number!");
            }
        }


    }

    /**
     * Displays the menu based on the specified filter type.
     *
     * @param filterType the type of filter to be applied to the menu
     */
    public void displayMenu(String filterType) {

        MenuFilters filter;

        switch (filterType) {
            case "Sides" -> filter = new SidesFilter();
            case "Drinks" -> filter = new DrinksFilter();
            default -> {
                System.out.println("did something wrong");
                return;
            }
        }
        filter.displayFilteredMenu(menu);
    }


    /**
     * Updates food information of the indexed food item in menu.
     * @param index is the index of the food in the menu.
     */
    public void updateIndexedFoodItemFromMenu(int index) {

        FoodItem foodItem;
        try {
            foodItem = menu.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Indexed out of range.");
            return;
        }

        System.out.printf("Please confirm that you want to update %s in the menu.\n",foodItem.getName());
        System.out.println("Enter 1 to update, 2 to exit.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {

                if (scanner.next().equals("1")) {
                    System.out.printf("updating %s from menu...\n", foodItem.getName());
                    while (true) {
                        System.out.println("Choose ur action: \n1. Update Price\n2. Update Description\n3. Quit");

                        switch (scanner.next()) {

                            case "1"-> foodItem.updatePrice();
                            case "2" -> foodItem.updateDescription();
                            case "3" -> {
                                return;
                            }
                            default -> System.out.println("Enter a valid option!");
                        }
                        return;
                    }



                } else if (scanner.next().equals("2")) {
                    System.out.println("Did not update item to menu.");
                    return;
                } else {
                    System.out.println("Enter 1 or 2!");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong.");
                return;
            }
        }

    }
    /**
     * Get the Menu size.
     * @return the size of the menu.
     */
    public int getMenuSize() {
        return menu.size();
    }

    /**
     * Get the Menu.
     * @return the Menu.
     */
    public ArrayList<FoodItem> getMenuArraylist() {
        return menu;
    }

    /**
     * Find the filter of the menu.
     * @param filterType is the type of filter used for filtering the menu.
     * @return filtered Menu.
     */
    public static MenuFilters findWhichFilter(int filterType) {
        switch (filterType) {
            case 1 -> {
                return new BaseMenuFilter();
            }
            case 2 -> {
                return new SetMealFilter();
            }

            case 3 -> {
                return new MainDishFilter();
            }

            case 4 -> {
                return new SidesFilter();
            }
            case 5 -> {
                return new DrinksFilter();
            }
            default -> {
                System.out.println("Error!");
                return new BaseMenuFilter();
            }
        }
    }
    
    /**
     * Displays menu with no filter
     */
    public void displayMenuNoFilter() {
        MenuFilters filter = new BaseMenuFilter();
        filter.displayFilteredMenu(menu);
    }
}
