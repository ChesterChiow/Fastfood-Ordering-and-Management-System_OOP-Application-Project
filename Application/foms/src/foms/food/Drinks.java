package foms.food;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Represents a drink item in the menu, extending the FoodItem class.
 */
public class Drinks extends FoodItem implements Serializable {
    /**
     * The ice level of the drink, which can be customized.
     */
    private iceLevels iceLevel = iceLevels.DEFAULT_ICE;

    /**
     * Constructs a Drinks object with the specified name, price, and description.
     *
     * @param name        The name of the drink.
     * @param price       The price of the drink.
     * @param description The description of the drink.
     */
    public Drinks(String name, double price, String description) {
        super(name, price, description);
    }

    /**
     * Constructs a Drinks object by copying the details from another FoodItem object.
     *
     * @param drink The original menu item to copy from.
     */
    public Drinks(FoodItem drink) {
        super(drink.getName(),drink.getPrice(),drink.getDescription());
    }

    /**
     * Overrides the customiseFoodItem method to allow customization of ice levels for drinks.
     */
    @Override
    public void customiseFoodItem() {
        System.out.printf("Customising food item %s...\n", getName());

        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.println("Enter your choice: \n1. Change ice level" +
                    "\n2. Input Custom Request. (press 0 to quit): ");
            input = scanner.nextLine();
            switch (input) {
                case "0"-> {
                    System.out.println("Returning to main page..");
                    return;
                }
                case "1" -> {
                    updateIceLevel();
                    return;
                }
                case "2" -> {
                    super.customiseFoodItem();
                    return;
                }
            }
        }


    }

    /**
     * Allows updating the ice level of the drink.
     */
    public void updateIceLevel() {
        System.out.println("Enter your choice for ice level.");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. No Ice");
            System.out.println("2. Less Ice");
            System.out.println("3. Default Ice");
            System.out.print("4. More Ice. Press 0 to exit.");
            String input = scanner.next();
            switch (input) {
                case "0" -> System.out.println("Did not edit. Returning to main page..");
                case "1" -> {
                    System.out.println("Changed ice level to no ice.");
                    iceLevel = iceLevels.NO_ICE;
                    return;
                }

                case "2" -> {
                    System.out.println("Changed ice level to less ice.");
                    iceLevel = iceLevels.LESS_ICE;
                    return;
                }

                case "3" -> {
                    System.out.println("Changed ice level to default ice.");
                    iceLevel = iceLevels.DEFAULT_ICE;
                    return;
                }

                case "4" -> {
                    System.out.println("Changed ice level to more ice.");
                    iceLevel = iceLevels.MORE_ICE;
                    return;
                }

                default -> System.out.println("Enter a valid input!");
            }


        }
    }



}

