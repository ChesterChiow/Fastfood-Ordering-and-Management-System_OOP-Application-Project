package foms.order.payment;

import foms.order.Customer;
import foms.order.OrderStatus;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class facilitates the checkout process for customers.
 * It handles payment processing and payment failure.
 */
public class CheckOutProcess implements Serializable {
    /**
     * The Customer who is ready to check out their order.
     */
    final private Customer customer;

    /**
     * Constructor for CheckOutProcess class.
     *
     * @param customer The Customer who is ready to check out their order.
     */
    public CheckOutProcess(Customer customer) {
        this.customer = customer;
    }

    /**
     * Checks out the order that the customer has put into the cart.
     * If the checkout is successful, the order is emptied out so that the customer can order again.
     */
    public void CheckOut() {
        System.out.println("Checking out Process");
        System.out.printf("Your total bill is: %.2f\n", customer.getOrder().getAmount());
        System.out.println("Proceed to checkout?\n" +
                "0. Continue Shopping\n" +
                "1. Checkout");

        Scanner scanner = new Scanner(System.in);

        String checkOutOrNot = "I love oop";

        while (!checkOutOrNot.equals("1")) { //if not 1 then continue prompting
            checkOutOrNot = scanner.next();

            if (checkOutOrNot.equals("0")) { //don't check out so return
                System.out.println("Returning to order screen!");
                return;
            } else if (!checkOutOrNot.equals("1")){ //wrong input
                System.out.println("Enter a valid input!");
            }
        }

        System.out.println("Please select your dine-in option: (Press 0 to exit) \n" +
                "1. Dine in\n" +
                "2. Take away");

        int dineInOption;

        while (true) { //if not 1 or 2 then continue prompting
            dineInOption = scanner.nextInt();


            if(dineInOption==0){
                break;
            }
            else if (dineInOption == 1) { 
                customer.getOrder().setDineInOption(true);
                break;
            } else if (dineInOption == 2) { 
                customer.getOrder().setDineInOption(false);
                break;
            }else {
                System.out.println("Enter a valid input.");
                continue;
            }

        }

        System.out.println("Checking out now.");

        int maxIndex = 1;
        for (Payment payment : customer.getBranch().getPaymentList().getAvailablePayments()) {
            System.out.printf("%d. %s\n", maxIndex, payment.getName());
            maxIndex++;
        }

        int choice;
        while (true) { //true loop to catch input mismatch error
            System.out.println("Please select a payment method (press 0 to exit): ");
            try {
                choice = scanner.nextInt();

                if (choice == 0) {
                    System.out.println("cancelled check out. returning..");
                    return;
                }

                if (choice > 0 && choice < maxIndex) { //valid indexing
                    customer.setPaymentMethod(customer.getBranch().getPaymentList().getPayment(choice-1));
                    break;
                } else {
                    System.out.println("Enter a valid range!");
                }

            } catch (InputMismatchException e) {
                System.out.println("enter a number!");
            } catch (Exception e) {
                System.out.println("something went wrong");
            }
        }

        System.out.printf("Paying using %s", customer.getPaymentMethod().getName());

        System.out.println("Payment successful!");

        printReceipt();

        customer.getOrder().setOrderStatus(OrderStatus.PREPARING);
        customer.getBranch().getOrderList().addOrderToOrderList(customer.getOrder());
        System.out.printf("Your order ID is: %d\n",customer.getOrder().getOrderID());
        //customer.setOrder(new Order());
    }

    /**
     * Prints the receipt with order details including orderID, items, amount due, amount paid, and payment method.
     * This will be printed once the customer checks out their order.
     */
    private void printReceipt() {
        customer.getOrder().displayCart();
        System.out.printf("Amount Due: %.2f\n", customer.getOrder().getAmount());
        System.out.printf("Amount Paid: %.2f\n", customer.getOrder().getAmount());
        System.out.println("Paid using: " + customer.getPaymentMethod().getName());
    }
}