package foms.workers;

import foms.management.branch.Branch;
import foms.order.Order;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The `StaffWorker` class represents a staff worker in the food order management system.
 * It extends the `Worker` class and implements the `Serializable` interface.
 */
public class StaffWorker extends Worker implements Serializable {
    /**
     * The branch at which the staff worker works.
     */
    private Branch branch;


    /**
     * Constructor for creating a staff worker.
     *
     * @param name    The full name of the staff worker.
     * @param age     The age of the staff worker.
     * @param gender  The gender of the staff worker.
     * @param loginID The login ID of the staff worker. Duplicate will be checked in previous function.
     * @param branch  The branch at which the staff worker works.
     */
    public StaffWorker(String name, int age, char gender, String loginID, Branch branch) {
        super(name, age, gender, loginID);
        this.branch = branch;
        setRole('S');
    }

    /**
     * Returns a stringified representation of the staff worker.
     *
     * @return Stringified details of the staff worker.
     */
    @Override
    public String toString() {
        return String.format("%-15s%-5d%-3c%-15s%-15s%-3c",
                getName(), getAge(), getGender(), getLoginID(), getBranch().getName(), getRole());
    }

    /**
     * Processes an order by scheduling it for processing at regular intervals.
     */
    public void processOrder(){
        Scanner sc = new Scanner(System.in);

        while (true) {

            try {
                getBranch().getOrderList().displayOrderList();
                System.out.println("Select the order to process (Enter 0 to exit): ");
                int choice = sc.nextInt();
                if (choice == 0) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                Order order = getBranch().getOrderList().findOrder(choice - 1);

                ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                getBranch().getOrderList().processOrder(order.getOrderID()); //process immediately
                    // Schedule the task to run once after 30 seconds delay
                scheduler.schedule(() -> {
                    getBranch().getOrderList().processOrder(order.getOrderID());
                }, 60, TimeUnit.SECONDS);
                return;

            }
            catch(InputMismatchException e)
            {
                System.out.println("Enter the order index (number) (Enter 0 to exit): ");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    /**
     * Displays all orders in the list, allowing the user to view individual order details.
     */
    public void displayOrders(){
        Scanner sc = new Scanner(System.in);
        getBranch().getOrderList().displayOrderList();
        System.out.println("Select the order to display its details (Enter 0 to exit): ");
        while (true){
            try{
                int choice = sc.nextInt();
                if (choice ==0){
                    System.out.println("Returning to previous page..");
                    return;
                }
                Order order = getBranch().getOrderList().findOrder(choice - 1);
                order.displayCart(); 
                return;
            }
            catch(InputMismatchException e)
            {
                  System.out.println("Enter the order index (number) (Enter 0 to exit): ");
            }
            catch (Exception e){
                System.out.println(e.getMessage()+" Enter the order index (number) (Enter 0 to exit): ");
            }
        }
    }




    /**
     * Gets the branch object of this worker.
     *
     * @return The branch object of this worker.
     */
    public Branch getBranch() {
        return branch;
    }

    /**
     * Changes the branch of this worker.
     *
     * @param branch The new branch of this worker.
     */
    public void setBranch(Branch branch){
        this.branch = branch;
    }
}

