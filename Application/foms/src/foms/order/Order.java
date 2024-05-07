package foms.order;
import java.io.Serializable;
import java.util.*;

import foms.food.*;
import foms.management.filters.menufilters.DrinksFilter;
import foms.management.filters.menufilters.MenuFilters;
import foms.management.lists.Menu;

/**
 * Represents an order placed by customer.
 */
public class Order implements Serializable {
    /**
     * The orderID of an order.
     */
    private int orderID;
    /**
     * ArrayList of cart items.
     */
    private ArrayList<FoodItem> cart;
    /**
     * Sum of the amount of all the cart items.
     */
    private double totalAmount = 0.0;
    /**
     * Order status of the order.
     */
    private OrderStatus orderStatus = OrderStatus.PENDING;
    /**
     * dine in option of the order.
     */
    private boolean dineInOption = true;

    /**
     * Constructs a new Order with a randomly generated orderID and an empty cart.
     */
    public Order() {
        Random random = new Random();
        orderID = random.nextInt(8999) + 1000; //random orderID between 1000~9999
        cart = new ArrayList<FoodItem>();
    }

    /**
     * Constructs a new Order by copying another order.
     * @param order The order to copy.
     */
    public Order(Order order) {
        orderID = order.orderID;
        cart = new ArrayList<>();
        dineInOption = order.dineInOption;
        orderStatus = order.orderStatus;
    }

    /**
     * Deep copies the cart of the provided order.
     * @param order The order to copy the cart from.
     * @return The deep copied cart.
     */
    public ArrayList<FoodItem> deepCopyCart(Order order) {
        ArrayList<FoodItem> cartToCopy = cart;
        ArrayList<FoodItem> copiedCart = new ArrayList<>();
        FoodItem itemToAdd;
        for (FoodItem item : cartToCopy) {
            itemToAdd = switch (item.getClass().getSimpleName()) {

                case "SetMeal" -> new SetMeal(((SetMeal) item).getMainDish(), item.getPrice(), item.getDescription());

                case "MainDish" -> new MainDish(item.getName(), item.getPrice(), item.getDescription());

                case "Sides" -> new Sides(item.getName(), item.getPrice(), item.getDescription());

                case "Drinks" -> new Drinks(item.getName(), item.getPrice(), item.getDescription());

                default -> {
                    System.out.println("Error in order add filtered food!");
                    yield null; // or throw an exception or return a default value
                }
            };
        }

        return copiedCart;
    }



    /**
     * Adds a food item to the cart based on the provided index from the filtered menu.
     * Creates a new object and adds it to the cart, copying the object from the menu.
     * @param indexedFoodItem The index of the food item to retrieve from the filtered menu.
     * @param filterType The filter type used.
     * @param menu The menu object that contains the menu list.
     */
    public void addToCart(int indexedFoodItem, int filterType, Menu menu){

        //the filter used
        MenuFilters filter = Menu.findWhichFilter(filterType);

        FoodItem fromMenu;
        //the index of the food
        fromMenu = filter.findIndexedFoodItemInFilteredMenu(indexedFoodItem,menu);
        if (fromMenu == null) { //return cus cannot create food.
            return;
        }

        FoodItem addNewItemToCart = switch (fromMenu.getClass().getSimpleName()) {

            case "SetMeal" -> new SetMeal(((SetMeal) fromMenu).getMainDish(), fromMenu.getPrice(), fromMenu.getDescription());

            case "MainDish" -> new MainDish(fromMenu.getName(), fromMenu.getPrice(), fromMenu.getDescription());

            case "Sides" -> new Sides(fromMenu.getName(), fromMenu.getPrice(), fromMenu.getDescription());

            case "Drinks" -> new Drinks(fromMenu.getName(), fromMenu.getPrice(), fromMenu.getDescription());

            default -> {
                System.out.println("Error in order add filtered food!");
                yield null; // or throw an exception or return a default value
            }
        };
        if (addNewItemToCart == null) return;


        cart.add(addNewItemToCart);
        totalAmount += addNewItemToCart.getPrice();
        System.out.printf("added %s to cart.\n", addNewItemToCart.getName());


    }


    /**
     * Removes a food item from the cart.
     * @param indexOfFoodItemToDelete The index of the food item to delete from the cart.
     */
    public void removeIndexedFoodItem(int indexOfFoodItemToDelete){
        FoodItem cartItem = cart.get(indexOfFoodItemToDelete);
        cart.remove(cartItem);
        totalAmount -= cartItem.getPrice();
        System.out.println(cartItem.getName()+" is removed from the cart.");
    }

    /**
     * Edits a food item in the cart by calling its customiseFoodItem() method.
     * @param index The index of the selected food item in the cart.
     */
    public void editFoodItem(int index){

        getCart().get(index).customiseFoodItem();
    }


    /**
     * Displays and lists all items in the cart.
     */
    public void displayCart(){

        if(cart.isEmpty()){
            System.out.println("Cart is empty.");
            return;
        }
        else {

            System.out.println("Order ID: "+ getOrderID());

            for(int i=0; i<cart.size(); i++){
                System.out.print((i+1)+". "+ cart.get(i) + " - " + cart.get(i).getPrice() + "\n");
                if (!cart.get(i).getCustomRequest().equals(" "))
                    System.out.printf("\t\tCustomer Request: %s\n",cart.get(i).getCustomRequest());
            }
        }

    }

    /**
     * Get order ID of the order
     * @return orderID of the order
     */
    public int getOrderID(){
        return orderID;
    }

    /**
     * Get Cart Items placed in the cart
     * @return cart items
     */
    public ArrayList<FoodItem> getCart(){
        return cart;
    }

    /**
     * Get the total price of items in cart
     * @return price of all items in cart
     */
    public double getAmount(){
        return totalAmount;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    /**
     * Get the order status for this order
     * @return order status
     */
    public String getOrderStatusInString(){
        if (orderStatus.equals(OrderStatus.READYTOPICKUP)) {
            return "Ready to Pick Up";
        }

        if (orderStatus.equals(OrderStatus.PREPARING)) {
            return "Preparing";
        }

        if (orderStatus.equals(OrderStatus.PENDING)) {
            return "Pending";
        }
        return "Completed";
    }

    /**
     * Change the order status for this order
     * After paying - from PENDING to PREPARING
     * In the processing order - from PREPARING to READYTOPICKUP
     * After customer collected the food - from READYTOPICKUP to COMPLETED
     * If customer canceled order during payment - from PENDING to CANCELED
     * @param newOrderStatus this includes Order's new Order Status
     */
    public void setOrderStatus(OrderStatus newOrderStatus){
        this.orderStatus=newOrderStatus;
    }


    /**
     * Sets the dine-in option for this order.
     * @param dineInOption Customer's choice of dine-in option.
     */
    public void setDineInOption(boolean dineInOption) {this.dineInOption=dineInOption;}

    /**
     * Gets the size of the cart.
     * @return The size of the cart.
     */
    public int getCartSize () {
        return cart.size();
    }
}