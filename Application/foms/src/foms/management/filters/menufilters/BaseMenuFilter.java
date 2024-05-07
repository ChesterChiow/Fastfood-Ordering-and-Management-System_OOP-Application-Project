package foms.management.filters.menufilters;

import foms.food.FoodItem;
import foms.management.lists.Menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The BaseMenuFilter class represents a filter for displaying the entire menu.
 */
public class BaseMenuFilter implements MenuFilters, Serializable {

    /**
     * Displays the filtered menu containing all items.
     *
     * @param menu The list of food items in the menu.
     */
    @Override
    public void displayFilteredMenu(ArrayList<FoodItem> menu) {
        int counter = 1;
        String s;
        System.out.println("All Items");
        for (FoodItem foodItem: menu) {
            if (foodItem.getAvailability()) {
                s = "Available";
            } else {
                s = "Unavailable";
            }
            System.out.printf("%d. %s | %s | Price: %.2f (%s)\n",counter,foodItem.getName()
                    ,foodItem.getDescription(),foodItem.getPrice(),s);
                counter++;
        }
    }

    /**
     * Finds the food item in the filtered menu based on the index.
     *
     * @param index   The index of the food item in the filtered menu.
     * @param menuObj The Menu object representing the menu selected by the customer.
     * @return The FoodItem object at the specified index in the filtered menu, or null if the index is invalid.
     */
    @Override
    public FoodItem findIndexedFoodItemInFilteredMenu(int index, Menu menuObj) {

        if (index < 0 || index >= menuObj.getMenuSize()) {
            System.out.println("Enter a valid index!");
            return null;
        } else {
            return menuObj.getMenuArraylist().get(index);
        }

    }
}

