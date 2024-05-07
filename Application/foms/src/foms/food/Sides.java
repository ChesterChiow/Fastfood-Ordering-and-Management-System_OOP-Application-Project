package foms.food;

import java.io.Serializable;

/**
 * The `Sides` class represents a food item that is categorized as a side dish.
 * It extends the `FoodItem` class and implements the `Serializable` interface.
 */
public class Sides extends FoodItem implements Serializable {

    /**
     * Constructor
     * Creates a new `Sides` object with the given name, price, and description.
     * Automatically adds the side dish to the menu.
     *
     * @param name        The name of this side dish.
     * @param price       The price of this side dish.
     * @param description The description of this side dish.
     */
    public Sides(String name, double price, String description) {
        super(name, price, description);
    }

    /**
     * Constructor
     * Creates a new `Sides` object using the information from another `FoodItem` object.
     * This constructor is useful for converting a `FoodItem` into a `Sides` object.
     *
     * @param menuItem The `FoodItem` object to be converted into a `Sides` object.
     */
    public Sides(FoodItem menuItem) {
        super(menuItem.getName(), menuItem.getPrice(), menuItem.getDescription());
    }
}
