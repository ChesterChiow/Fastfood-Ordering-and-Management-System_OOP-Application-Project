package foms.order;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

import static foms.order.OrderStatus.COMPLETED;

/**
 * Represents the process of collecting an order by the customer.
 */
public class CollectOrder implements Serializable {

    /**
     * The customer associated with the collection process.
     */
    Customer customer;

    /**
     * Constructs a CollectOrder object with the specified customer.
     *
     * @param customer The customer associated with the collection process.
     */
    public CollectOrder (Customer customer) {
        this.customer = customer;
    }


    /**
     * Checks the status of a customer's order based on the order ID.
     * Prompts the customer to enter their order ID and displays the status of the order.
     */
    public void checkOrder() {
        System.out.println("Checking your order: ");

        Scanner scanner = new Scanner(System.in);
        int id;
        while (true) {
            try {
                System.out.print("Enter your orderID (enter 0 to exit): ");

                id = scanner.nextInt();
                 if (id == 0) {
                     System.out.println("returning..");
                     return;
                 }

                for (Order order :customer.getBranch().getOrderList().getOrderList()) {
                    if (id == order.getOrderID()) { //correct order id
                        System.out.printf("Order found! The status is: %s\n", order.getOrderStatusInString());
                        return;
                    }
                }
                System.out.println("Order ID not found!");
            } catch (InputMismatchException e) {
                System.out.println("Enter a number!");
                scanner.next();
            }
        }


    }
    /**
     * Allows the customer to collect their order.
     * Once the order is collected, the status is changed to COMPLETED,
     * and the order is removed from the order list.
     */
    public void collectOrder(){
        Scanner scanner = new Scanner(System.in);
        int input;
        System.out.print("Enter your orderID to collect (Enter 0 to exit): ");
        while (true) {
            try {
                input = scanner.nextInt();
                if (input == 0) {
                    System.out.println("returning..");
                    return;
                }
                for (Order order: customer.getBranch().getOrderList().getOrderList()) {
                    if (order.getOrderID() == input) { //correct order ID

                        if (order.getOrderStatus().equals(OrderStatus.READYTOPICKUP)) {
                            System.out.println("Order is ready for collection.");
                            System.out.println("You've picked your order up!");
                            order.setOrderStatus(COMPLETED);
                            customer.getBranch().getOrderList().getOrderList().remove(order);
                            return;
                        } else {
                            System.out.println("Your order is not ready for pick up.");
                            return;
                        }
                    }
                    else {
                    	System.out.println("Order not found!");
                    	return;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a number!");
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }
    }
}
