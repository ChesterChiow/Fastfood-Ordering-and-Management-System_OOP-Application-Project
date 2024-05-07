package foms.food;

import java.io.Serializable;

/**
 * The `SetMeal` class represents a combination of food items offered as a single meal.
 * It extends the `FoodItem` class and implements the `Serializable` interface.
 */
public class SetMeal extends FoodItem implements Serializable {
    /**
     * This is mainDish in SetMeal
     */
    private FoodItem mainDish;
    /**
     * This is the sideDish in SetMeal
     */
    private FoodItem sideDish;
    /**
     * This is the drink in SetMeal
     */
    private FoodItem drink;
    /**
     * Constructor for creating a set meal.
     * Creates a set meal with a main dish, allowing the customer to choose the side dish and drink later.
     *
     * @param mainDish    The main dish of the set meal.
     * @param price       The price of the set meal.
     * @param description The description of the set meal.
     */
    public SetMeal(FoodItem mainDish, double price, String description) {
        super(String.format(mainDish.getName() + " set meal"), price, description);
        this.mainDish = mainDish;
    }

    /**
     * Retrieves the main dish of the set meal.
     *
     * @return The main dish of the set meal.
     */
    public FoodItem getMainDish() {
        return mainDish;
    }

    /**
     * Sets the main dish of the set meal.
     *
     * @param mainDish The main dish to be set for the set meal.
     */
    public void setMainDish(FoodItem mainDish) {
        this.mainDish = mainDish;
    }

    /**
     * Sets the side dish of the set meal.
     *
     * @param sideDish The side dish to be set for the set meal.
     */
    public void setSideDish(FoodItem sideDish) {
        this.sideDish = sideDish;
    }

    /**
     * Sets the drink of the set meal.
     *
     * @param drink The drink to be set for the set meal.
     */
    public void setDrink(FoodItem drink) {
        this.drink = drink;
    }
}

