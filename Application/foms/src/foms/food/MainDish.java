package foms.food;

import java.io.Serializable;

/**
 * The `MainDish` class represents a main dish item on the menu.
 * It extends the `FoodItem` class and implements the `Serializable` interface.
 */
public class MainDish extends FoodItem implements Serializable {

    /**
     * Constructor for creating a main dish.
     *
     * @param name        The name of the main dish.
     * @param price       The price of the main dish.
     * @param description The description of the main dish.
     */
    public MainDish(String name, double price, String description) {
        super(name, price, description);
    }

    /**
     * Constructor for creating a main dish using the information from another `FoodItem` object.
     *
     * @param menuItem The `FoodItem` object to be converted into a `MainDish` object.
     */
    public MainDish(FoodItem menuItem) {
        super(menuItem.getName(), menuItem.getPrice(), menuItem.getDescription());
    }


}

