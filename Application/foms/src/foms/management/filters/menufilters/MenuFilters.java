package foms.management.filters.menufilters;

import foms.food.FoodItem;
import foms.management.lists.Menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The MenuFilters interface represents filters for menu items.
 */
public interface MenuFilters extends Serializable {
    /**
     * Displays the filtered menu based on the implementation.
     * @param menu The list of food items representing the menu.
     */
    public void displayFilteredMenu(ArrayList<FoodItem> menu);

    /**
     * Finds the food item in the filtered menu based on the index.
     *
     * @param index   The index of the food item in the filtered menu.
     * @param menuObj The Menu object representing the menu selected by the customer.
     * @return The FoodItem object at the specified index in the filtered menu, or null if the index is invalid.
     */
    public FoodItem findIndexedFoodItemInFilteredMenu(int index, Menu menuObj);
}
