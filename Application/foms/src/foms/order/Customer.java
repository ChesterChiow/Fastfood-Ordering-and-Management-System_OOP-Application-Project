package foms.order;

import foms.management.branch.Branch;
import foms.management.lists.BranchList;
import foms.order.payment.CheckOutProcess;
import foms.order.payment.Payment;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a customer in FOMS.
 */
public class Customer implements Serializable {
    /**
     * Customer's order.
     */
    private Order order;
    /**
     * Customer's branch.
     */
    private Branch branch;

    /**
     * payment method that the customer will use to pay for their food.
     */
    private Payment paymentMethod;

    /**
     * Customer's option to check out.
     */
    private CheckOutProcess checkOutProcess;
    
    /**
     * Customer's option to collect order.
     */
    private CollectOrder collectOrder;

    /**
     * Constructor for the Customer class.
     */
    public Customer() {
        order = new Order();
        this.branch = null;
        this.collectOrder = new CollectOrder(this);
        checkOutProcess = new CheckOutProcess(this);
    }

    /**
     * Constructor for the Customer class with a specified branch.
     * @param branch The branch associated with the customer.
     */
    public Customer(Branch branch) {
        order = new Order();
        this.branch = branch;
        this.collectOrder = new CollectOrder(this);
        checkOutProcess = new CheckOutProcess(this);
    }


    /**
     * Customer choosing a branch.
     * Customer can only choose branches that are open.
     * @return true if branch is successfully selected, false if branch is not successfully selected
     */
    public boolean selectBranch(){
        int branchChoice;
        Scanner scanner = new Scanner(System.in);
        while(true) {
            try{
                System.out.print("Select your branch (Enter 0 to exit): ");
                BranchList.displayBranchNames();
                branchChoice = scanner.nextInt();

                if (branchChoice == 0) {
                    System.out.println("Exiting... ");
                    return false;
                }

                Branch branch = BranchList.findBranch(branchChoice-1);

                if(!branch.getStatus()){
                    System.out.println("The branch is closed. Please select another branch.");
                } else {
                    setBranch(branch);
                    return true;
                }

            }catch(InputMismatchException e){
                System.out.println("Please enter a valid integer for branch selection.");
                scanner.next();
            }catch(Exception e){
                System.out.println(e.getMessage()+"Error occurred.");
            }
        }
    }

    /**
     * Add the food item into the cart.
     */
    public void addFoodItemToCart(){
        Scanner scanner = new Scanner(System.in);
        int filterType;
        int input;
        filterType = displayMenu();

        if (filterType == 0) { //exited display menu, so should exit this as well
            return;
        }

        while (true) {
            try {
                System.out.print("To view the menu again, press 0.\n" +
                        "To exit, press -1.\n" +
                        "To add food item, type in the number: ");
                input = scanner.nextInt();

                switch (input) {
                    case -1 -> {
                        System.out.println("Returning to main menu..");
                        return;
                    }

                    case 0 -> {
                        filterType = displayMenu();
                        if (filterType == 0) {
                            return;
                        }
                    }

                    default -> {

                        order.addToCart(input -1 ,filterType,getBranch().getMenu()); // valid index checking is done in fooditems

                    }
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a number!");
                scanner.next();
            }

        }

    }

    /**
     * Removing food item from the cart.
     */
    public void removeFoodItem(){
        Scanner scanner = new Scanner(System.in);
        int foodChoice;
        if (order.getCart().isEmpty()) {
            System.out.println("cart is empty!");
            return;
        }
        
        if (! order.getOrderStatus().equals(OrderStatus.PENDING)) {
            System.out.println("Order is no longer modifiable as it has been submitted.");
            return;
        }

        while (true) {
            try{

                if (order.getCart().isEmpty()) {
                    System.out.println("cart is empty!");
                    return;
                }
                System.out.print("Select the food item that you want to delete from your cart:\n " +
                        "(Enter 0 to exit)\n" +
                        "Enter your choice: ");

                order.displayCart();
                foodChoice = scanner.nextInt();


                if(foodChoice == 0){ // go back menu
                    System.out.println("Going back to main menu.");
                    return;
                }

                //valid indexing
                else if(foodChoice>=1 || foodChoice <= order.getCartSize()) {
                    order.removeIndexedFoodItem(foodChoice-1);
                    return;
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a valid input.");
                scanner.next();
            } catch (Exception e) {
                System.out.println(e.getMessage()+"Error occurred.");
            }
        }
    }

    /**
     * Display all items in the cart and ask users to select a cart item.
     * The index of the Food Item will be passed into the Order class (editFoodItem method).
     */
    public void editFoodItem(){
        Scanner scanner = new Scanner(System.in);
        //display order then ask person choose
        if (order.getCart().isEmpty()) {
            System.out.println("Empty cart!");
            return;
        }
        if (! order.getOrderStatus().equals(OrderStatus.PENDING)) {
            System.out.println("Order is no longer modifiable as it has been submitted.");
            return;
        }

        int choice;
        int maxCartIndex = order.getCartSize() -1;
        while (true) {
            order.displayCart();
            System.out.print("Enter the food item you wish to edit:" +
                    "\nPress 0 to exit.\n" +
                    "Enter your choice: ");
            try {
                choice = scanner.nextInt() -1;
            } catch (InputMismatchException e) {
                System.out.println("Enter a number!");
                scanner.next();
                continue;
            } catch (Exception e) {
                System.out.println("error in customer edit menu");
                return;
            }

            if (choice == -1) { //terminate
                System.out.println("exiting..");
                return;

            } else if (choice >= 0 && choice <= maxCartIndex) { //valid range
                order.editFoodItem(choice);

            } else {
                System.out.println("Enter a valid range!");
            }

        }


    }


// Getters and setters

    /**
     * Retrieves the branch associated with the customer.
     *
     * @return The branch associated with the customer.
     */
    public Branch getBranch() { return branch;}

    /**
     * Displays the menu for the customer's branch.
     *
     * @return The input on the latest chosen filter, if needed.
     */
    public int displayMenu () {
        return getBranch().getMenu().displayMenu();
    }

    /**
     * Retrieves the customer's current order.
     *
     * @return The customer's current order.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Retrieves the payment method used by the customer.
     *
     * @return The payment method used by the customer.
     */
    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method for the customer.
     *
     * @param paymentMethod The payment method to set.
     */
    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Sets the customer's order.
     *
     * @param order The order to set.
     */
    public void setOrder(Order order) {
        this.order = order;
    }
    
    /**
     * Sets the branch customer is in.
     * 
     * @param branch The branch to set
     */
    public void setBranch(Branch branch) {
    	this.branch = branch;
    }

    /**
     * Retrieves the checkout process associated with the customer.
     *
     * @return The checkout process associated with the customer.
     */
    public CheckOutProcess getCheckOutProcess() {
        return checkOutProcess;
    }

    /**
     * Retrieves the order collection process associated with the customer.
     *
     * @return The order collection process associated with the customer.
     */
    public CollectOrder getCollectOrder() {
        return collectOrder;
    }
}