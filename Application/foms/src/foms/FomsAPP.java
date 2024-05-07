package foms;

import foms.food.*;
import foms.management.branch.Branch;
import foms.management.branch.LoginSystemCtrl;
import foms.management.lists.AllWorkersList;
import foms.management.lists.BranchList;
import foms.order.Customer;
import foms.workers.AdminWorker;
import foms.workers.ManagerWorker;
import foms.workers.StaffWorker;
import foms.workers.Worker;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Food Ordering Management System (FOMS) main application
 */
public class FomsAPP implements Serializable {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
    	
    	
    	//Deserialisation
        AdminWorker admin = null;
        try {
            FileInputStream fileIn = new FileInputStream("AdminInfo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            admin = (AdminWorker) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("this didnt work");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        AllWorkersList.deserializeAllWorkerList();
        
    // Uncommment the two lines below if you would like to have the original test cases
    //AdminWorker admin = new AdminWorker("choonggi",20,'M',"Choonggi001");
    //createTestCases(admin);
    

     //Choonggi001 is adminID Choonggi is admin password

        while (true){
            Scanner scanner = new Scanner(System.in);
            try {
            //Start of Program
            System.out.println("**************************************************");
            System.out.println("Welcome to FastFood Operations Management System!");
            System.out.println("To start ordering, enter: 1");
            System.out.println("To login as a Staff Member, enter: 2.");
            System.out.println("To exit application, enter : 0");
            System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    allCustomerActions();
                    continue;
                }
                else if (choice == 2) {
                    Worker worker = LoginSystemCtrl.loginToSystemAsWorker();
                    if (worker == null) {continue;}
                    System.out.println("Login successful.");

                    //check if user is first login
                    if (worker.getLoginPassword().equals("default")) {
                        System.out.println("Enter your new password:");
                        scanner.nextLine();
                        String newPassword = scanner.nextLine();
                        worker.setLoginPassword(newPassword);
                    }
                    if (worker instanceof ManagerWorker){
                        allManagerActions((ManagerWorker)worker);
                        continue;
                    }
                    else if (worker instanceof StaffWorker){
                        allStaffActions((StaffWorker)worker);
                         continue;
                    }
                    else if (worker instanceof AdminWorker){
                        allAdminActions((AdminWorker)worker);
                        continue;
                    }
                }
                else if (choice ==0){
                	serialisationBeforeQuitting(admin);
                    System.out.println("Exiting app..");
                    return;
                }
                else{
                    System.out.println("Invalid choice. Enter again: ");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid choice. Enter again: ");
                scanner.next();
            }
        }




    }
    
    /**
     * Customer account. All functions that customer can do is displayed, awaiting customer's input.
     * @param admin
     */
    private static void allCustomerActions() {
        Customer customer = new Customer();

        String input;
        Scanner scanner = new Scanner(System.in);
        
        if(!customer.selectBranch())
        	return;

        while (true) {
            try {
                System.out.print("""
                        What do you want to do?
                        1. Place new Order
                        2. Check for Order Status
                        3. Collect your Order
                        4. Select a different Branch
                        0. Exit to Previous Screen
                        Enter Your Choice:\s""");


                input = scanner.next();

                switch (input) {
                    case "0" -> {
                        System.out.println("Signing out account..");
                        return;
                    }
                    case "1" -> {
                        placeNewOrder(customer);
                    }
                    case "2" -> customer.getCollectOrder().checkOrder();
                    case "3" -> customer.getCollectOrder().collectOrder();
                    case "4" -> customer.selectBranch();
                    default -> System.out.println("Invalid input. Please enter a valid option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number!");
            } catch (Exception e) {
               // System.out.println("Error occurred");
          
            }
        }
    }

    /**
     * Customer account. All functions that customer can do when ordering is displayed, awaiting customer's input.
     * @param customer
     */
    private static void placeNewOrder(Customer customer) {

        String input;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.print("""
                        Placing New Order
                        1. Add food item to cart
                        2. Edit food items in cart
                        3. Delete food item from cart
                        4. Check Cart
                        5. Check out
                        0. Exit to Previous Screen
                        Enter Your Choice:\s""");


                input = scanner.next();

                switch (input) {
                    case "0" -> {
                        System.out.println("Quitting place order...");
                        return;
                    }
                    case "1" -> {
                        System.out.println("Adding food item to cart...");
                        customer.addFoodItemToCart();
                    }
                    case "2" -> {
                        System.out.println("Editing food items in cart...");
                        customer.editFoodItem();
                    }

                    case "3" -> {
                        System.out.println("Removing food item from cart...");
                        customer.removeFoodItem();
                    }

                    case "4" -> {
                        System.out.println("Checking my Cart");
                        customer.getOrder().displayCart();
                    }
                    case "5" -> {
                        System.out.println("Checking out...");
                        customer.getCheckOutProcess().CheckOut();
                        return;
                    }
                    default -> System.out.println("Invalid input. Please enter a valid option.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number!"); }
//            } catch (Exception e) {
//                System.out.println("Error occurrexd");
//
//            }
        }
    }

    /**
     * Manager account. All functions that Manager worker can do is displayed, awaiting manager worker's input.
     * @param manager
     */
    private static void allManagerActions(ManagerWorker manager){
    	System.out.println("Welcome "+manager.getName());
        String input;
        Scanner scanner = new Scanner(System.in);
        while (true) {
        	System.out.println();
            System.out.println("1. Display orders");
            System.out.println("2. Process order");
            System.out.println("3. Display Menu");
            System.out.println("4. Add Item to Menu");
            System.out.println("5. Edit Item in Menu");
            System.out.println("6. Remove Item from Menu");
            System.out.println("7. Display branch workers");
            System.out.println();
            System.out.println("0. Exit");
            System.out.println("What would you like to do?");

            input = scanner.next();

            switch (input) {
                case "0" -> {
                    System.out.println("Signing out account..");
                    return;
                }

                case "1" -> manager.displayOrders();

                case "2" -> manager.processOrder();

                case "3" -> manager.getBranch().getMenu().displayMenu();

                case "4" -> manager.addFoodItemsToMenu();

                case "5" -> manager.updateFoodItemInformation();

                case "6" -> manager.removeFoodItemFromMenu();

                case "7" -> manager.displayBranchWorkers();
                
            }

        }

    }
    
    /**
     * Staff account. All functions that Staff worker can do is displayed, awaiting staff worker's input.
     * @param staff
     */
    private static void allStaffActions(StaffWorker staff){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome "+staff.getName());
        
        while (true){
            try{
            	System.out.println();
                System.out.println("1. Display orders");
                System.out.println("2. Process order");
                System.out.println();
                System.out.println("0. Exit");
                System.out.println("What would you like to do?");

                String choice = sc.nextLine();
                switch (choice) {
                    case "1" -> staff.displayOrders();
                    case "2" -> staff.processOrder();
                    case "0" -> {
                        System.out.println("Signing out account..");
                        return;
                    }
                    default -> System.out.println("Invalid. Please enter again:");
                }
            }
            catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }

    }

    /**
     * Admin account. All functions that admin worker can do is displayed, awaiting admin worker's input.
     * @param admin
     */
    private static void allAdminActions(AdminWorker admin){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome "+admin.getName());
        System.out.println();

        while (true) {
            try {
                System.out.println("------------------ Staff Account Settings------------------");
                System.out.println("1. Add staff account");
                System.out.println("2. Remove staff account");
                System.out.println("3. Edit staff account");
                System.out.println("4. Display staff list");
                System.out.println("5. Promote a staff");
                System.out.println("6. Transfer a staff");
                System.out.println();
                System.out.println("------------------ Branch Settings------------------");
                System.out.println("7. Add a branch");
                System.out.println("8. Remove a branch");
                System.out.println("9. Open a branch");
                System.out.println("10. Close a branch");
                System.out.println("11. Add payment method in a branch");
                System.out.println("12. Remove payment method in a branch");
                System.out.println();
                System.out.println("0. Exit");
                System.out.println("What would you like to do?");

                String choice = sc.nextLine();
                switch (choice) {
                    case "1" -> admin.getJobsOnWorkerList().addWorker();
                    case "2" -> admin.getJobsOnWorkerList().removeWorker();
                    case "3" -> admin.getJobsOnWorkerList().editWorker();
                    case "4" -> admin.getJobsOnWorkerList().displayWorkerList();
                    case "5" -> admin.getJobsOnWorkerList().promoteToManager();
                    case "6" -> admin.getJobsOnBranchList().transferStaff();
                    case "7" -> admin.getJobsOnBranchList().addBranch();
                    case "8" -> admin.getJobsOnBranchList().removeBranch();
                    case "9" -> admin.getJobsOnBranch().openBranch();
                    case "10" -> admin.getJobsOnBranch().closeBranch();
                    case "11" -> admin.getJobsOnPaymentList().addPayment();
                    case "12" -> admin.getJobsOnPaymentList().removeFromPaymentList();
                    case "0" -> {
                        System.out.println("Signing out account..");
                        return;
                    }
                    default -> System.out.println("Invalid. Please enter again:");
                }
            }
            catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
    }

    /**
     * test cases that was initially created to test for run case.
     * still stored in serialisation.
     * @param admin is the admin of the FOMS.
     */
    private static void createTestCases(AdminWorker admin) {
        BranchList dummy = new BranchList(); //quota is staff+manager
        Branch branch1 = new Branch("NTU", "NTU", 8);
        dummy.addCreatedBranch(branch1);
        branch1.setNumManager(2);
        branch1.setNumStaff(4);
        Branch branch2 = new Branch("JE", "JE", 5);
        dummy.addCreatedBranch(branch2);
        branch2.setNumManager(1);
        branch2.setNumStaff(4);
        Branch branch3 = new Branch("JP", "JP", 15);
        dummy.addCreatedBranch(branch3);
        branch3.setNumManager(2);
        branch3.setNumStaff(10);


        FoodItem item1 = new Sides("FRIES",3.20,"yummy and crispy fries");
        branch1.getMenu().addCreatedFoodItemToMenu(item1);
        branch2.getMenu().addCreatedFoodItemToMenu(item1);
        FoodItem item2 = new Sides("FRIES",3.50,"yummy and crispy fries");
        branch3.getMenu().addCreatedFoodItemToMenu(item2);

        FoodItem item3 = new MainDish("3PC TENDER", 15.30,"Yummy tenders");
        branch1.getMenu().addCreatedFoodItemToMenu(item3);
        FoodItem item4 = new MainDish("3PC TENDER", 19.30,"Yummy tenders");
        branch2.getMenu().addCreatedFoodItemToMenu(item4);
        branch3.getMenu().addCreatedFoodItemToMenu(item4);

        FoodItem item5 = new Drinks("Pepsi", 2.10,"coke is superior");
        branch1.getMenu().addCreatedFoodItemToMenu(item5);
        branch2.getMenu().addCreatedFoodItemToMenu(item5);
        branch3.getMenu().addCreatedFoodItemToMenu(item5);

        FoodItem item6 = new Sides("Chicken Mcnugget", 6.10,"i love chicken nuggets");
        branch1.getMenu().addCreatedFoodItemToMenu(item6);
        branch2.getMenu().addCreatedFoodItemToMenu(item6);
        branch3.getMenu().addCreatedFoodItemToMenu(item6);


        // Creating a new Drinks item
        FoodItem item7 = new Drinks("Coca-Cola", 2.00, "Coca-Cola is a classic choice");
        branch1.getMenu().addCreatedFoodItemToMenu(item7);
        branch2.getMenu().addCreatedFoodItemToMenu(item7);
        branch3.getMenu().addCreatedFoodItemToMenu(item7);


        // Creating a new MainCourse item
        FoodItem item8 = new SetMeal(item3, 21.30, "Creamy pasta with bacon and cheese");
        branch1.getMenu().addCreatedFoodItemToMenu(item8);
        branch2.getMenu().addCreatedFoodItemToMenu(item8);
        branch3.getMenu().addCreatedFoodItemToMenu(item8);


        // Creating Cajun Fish food item
        FoodItem item9 = new MainDish("Cajun Fish", 5.6, "Spicy Cajun-seasoned fish fillet");
        branch1.getMenu().addCreatedFoodItemToMenu(item9);
        branch2.getMenu().addCreatedFoodItemToMenu(new MainDish("Cajun Fish", 6.0, "Spicy Cajun-seasoned fish fillet"));
        branch3.getMenu().addCreatedFoodItemToMenu(item9);

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //create staff workers
        AllWorkersList allWorkersList = new AllWorkersList(admin);

        Worker worker1 = new StaffWorker("kumar Blackmore",42,'M',"KumarBlack",branch1);
        branch1.getWorkerList().addCreatedWorker(worker1);
        allWorkersList.addCreatedWorker(worker1);

        Worker worker2 = new StaffWorker("Emily White", 28, 'F', "EmilyW", branch1);
        branch1.getWorkerList().addCreatedWorker(worker2);
        allWorkersList.addCreatedWorker(worker2);

        Worker worker3 = new StaffWorker("James Johnson", 35, 'M', "JamesJ", branch1);
        branch1.getWorkerList().addCreatedWorker(worker3);
        allWorkersList.addCreatedWorker(worker3);

        Worker worker4 = new StaffWorker("Sophia Garcia", 23, 'F', "SophiaG", branch1);
        branch1.getWorkerList().addCreatedWorker(worker4);
        allWorkersList.addCreatedWorker(worker4);


        Worker worker5 = new StaffWorker("Daniel Smith", 30, 'M', "DanielS", branch2);
        branch2.getWorkerList().addCreatedWorker(worker5);
        allWorkersList.addCreatedWorker(worker5);

        Worker worker6 = new StaffWorker("Olivia Martinez", 25, 'F', "OliviaM", branch2);
        branch2.getWorkerList().addCreatedWorker(worker6);
        allWorkersList.addCreatedWorker(worker6);

        Worker worker7 = new StaffWorker("Michael Brown", 33, 'M', "MichaelB", branch2);
        branch2.getWorkerList().addCreatedWorker(worker7);
        allWorkersList.addCreatedWorker(worker7);

        Worker worker8 = new StaffWorker("Ava Johnson", 27, 'F', "AvaJ", branch2);
        branch2.getWorkerList().addCreatedWorker(worker8);
        allWorkersList.addCreatedWorker(worker8);

        // Creating worker 9 for branch3
        Worker worker9 = new StaffWorker("Ethan Davis", 31, 'M', "EthanD", branch3);
        branch3.getWorkerList().addCreatedWorker(worker9);
        allWorkersList.addCreatedWorker(worker9);


        Worker worker10 = new StaffWorker("Isabel Taylor", 26, 'F', "IsabellaT", branch3);
        branch3.getWorkerList().addCreatedWorker(worker10);
        allWorkersList.addCreatedWorker(worker10);

        Worker worker11 = new StaffWorker("William Ander", 29, 'M', "WilliamA", branch3);
        branch3.getWorkerList().addCreatedWorker(worker11);
        allWorkersList.addCreatedWorker(worker11);

        Worker worker12 = new StaffWorker("Sophie Wilson", 34, 'F', "SophieW", branch3);
        branch3.getWorkerList().addCreatedWorker(worker12);
        allWorkersList.addCreatedWorker(worker12);

        Worker worker13 = new StaffWorker("Alexander Mars", 32, 'M', "AlexanderM", branch3);
        branch3.getWorkerList().addCreatedWorker(worker13);
        allWorkersList.addCreatedWorker(worker13);

        Worker worker14 = new StaffWorker("Charlotte Brown", 28, 'F', "CharlotteB", branch3);
        branch3.getWorkerList().addCreatedWorker(worker14);
        allWorkersList.addCreatedWorker(worker14);

        Worker worker15 = new StaffWorker("Benjamin Garcia", 30, 'M', "BenjaminG", branch3);
        branch3.getWorkerList().addCreatedWorker(worker15);
        allWorkersList.addCreatedWorker(worker15);

        Worker worker16 = new StaffWorker("Amelia Jones", 27, 'F', "AmeliaJ", branch3);
        branch3.getWorkerList().addCreatedWorker(worker16);
        allWorkersList.addCreatedWorker(worker16);

        Worker worker17 = new StaffWorker("Matthew Miller", 33, 'M', "MatthewM", branch3);
        branch3.getWorkerList().addCreatedWorker(worker17);
        allWorkersList.addCreatedWorker(worker17);

        Worker worker18 = new StaffWorker("Ella Hernandez", 25, 'F', "EllaH", branch3);
        branch3.getWorkerList().addCreatedWorker(worker18);
        allWorkersList.addCreatedWorker(worker18);


        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////


        Worker manager1 = new ManagerWorker("hehe",31,'F',"heheChoonggi",branch1);
        branch1.getWorkerList().addCreatedWorker(manager1);
        allWorkersList.addCreatedWorker(manager1);

        Worker manager2 = new ManagerWorker("Haha", 35, 'M', "Haha123", branch1);
        branch1.getWorkerList().addCreatedWorker(manager2);
        allWorkersList.addCreatedWorker(manager2);

// Creating 1 manager for branch2
        Worker manager3 = new ManagerWorker("Soojin", 40, 'F', "Soojin123", branch2);
        branch2.getWorkerList().addCreatedWorker(manager3);
        allWorkersList.addCreatedWorker(manager3);

// Creating 2 managers for branch3
        Worker manager4 = new ManagerWorker("Doyeon", 38, 'F', "Doyeon2", branch3);
        branch3.getWorkerList().addCreatedWorker(manager4);
        allWorkersList.addCreatedWorker(manager4);

        Worker manager5 = new ManagerWorker("Jihoon", 36, 'M', "Jihoon4", branch3);
        branch3.getWorkerList().addCreatedWorker(manager5);
        allWorkersList.addCreatedWorker(manager5);

        System.out.println("All Workers has been added");
    }
    
    /**
     * Serialisation.
     * @param admin
     * @throws IOException
     */
    private static void serialisationBeforeQuitting(AdminWorker admin) throws IOException {
        try {
            FileOutputStream fileOut = new FileOutputStream("AdminInfo.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(admin);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("error");
        }
        AllWorkersList.serializeALlWorkerList();
    }
}