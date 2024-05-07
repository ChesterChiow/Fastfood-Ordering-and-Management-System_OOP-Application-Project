package foms.management.lists;

import foms.management.branch.Branch;
import foms.order.payment.*;
import foms.workers.AdminWorker;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Admin will use this class to add and remove payment
 */
public class OperationsOnPaymentList implements Serializable {
    /**
     * The admin who has access to this method
     */
    private AdminWorker admin;

    /**
     * Constructor with the given admin
     * @param admin is the admin worker performing the operations on branch list.
     */
    public OperationsOnPaymentList(AdminWorker admin) {
        this.admin = admin;
    }

    /**
     * add payment method to the payment list
     */
    public void addPayment() {
        Scanner scanner = new Scanner(System.in);
        Branch branch;
        BranchList.displayBranchNames();
        System.out.println("Enter the branch that you want to add payment method (Enter 0 to exit): ");
        while(true) {
            try {
                int choice = scanner.nextInt()-1;
                if (choice==-1) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                branch = BranchList.findBranch(choice);
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid branch index.");
                scanner.next();
            }
            catch (Exception e) {
                System.out.println(e.getMessage()+" Enter a valid branch index.");
            }
        }

        System.out.println("Payment Methods:");
        branch.getPaymentList().displayAllPayments();
        System.out.printf("Enter the payment method you want to add to %s (Enter 0 to exit):\n", branch.getName());
        while (true) {
            try {
                int choice2 = scanner.nextInt();
                if (choice2==0) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                else if (choice2 == 1) {
                    Payment payment = new NETS();
                    if (!branch.getPaymentList().checkPayment(payment)) {
                        branch.getPaymentList().addCreatedPayment(payment);
                        System.out.printf("NETS is added to list of payment methods in %s.\n", branch.getName());
                        return;
                    }
                    else {
                        System.out.printf("NETS is already a payment method in %s.\n",branch.getName());
                        System.out.println("Returning to previous page..");
                        return;
                    }
                }
                else if (choice2==3) {
                    Payment payment = new PayWave();
                    if (!branch.getPaymentList().checkPayment(payment)) {
                        branch.getPaymentList().addCreatedPayment(payment);
                        System.out.printf("PayWave is added to list of payment methods in %s.\n", branch.getName());
                        return;
                    }
                    else {
                        System.out.printf("PayWave is already a payment method in %s.\n",branch.getName());
                        System.out.println("Returning to previous page..");
                        return;
                    }
                }
                else if (choice2==2) {
                    Payment payment = new Paypal();
                    if (!branch.getPaymentList().checkPayment(payment)) {
                        branch.getPaymentList().addCreatedPayment(payment);
                        System.out.printf("Paypal is added to list of payment methods in %s.\n", branch.getName());
                        return;
                    }
                    else {
                        System.out.printf("Paypal is already a payment method in %s.\n",branch.getName());
                        System.out.println("Returning to previous page..");
                        return;
                    }
                }
                else if (choice2==4) {
                    Payment payment = new ShopeePay();
                    if (!branch.getPaymentList().checkPayment(payment)) {
                        branch.getPaymentList().addCreatedPayment(payment);
                        System.out.printf("ShopeePay is added to list of payment methods in %s.\n", branch.getName());
                        return;
                    }
                    else {
                        System.out.printf("ShopeePay is already a payment method in %s.\n",branch.getName());
                        System.out.println("Returning to previous page..");
                        return;
                    }
                }
                else {
                    System.out.println("Enter a valid payment method (number).");
                    continue;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid payment method (number).");
                scanner.next();
                continue;
            }
        }
    }

    /**
     * remove payment method from the payment list
     */
    public void removeFromPaymentList() {
        Scanner scanner = new Scanner(System.in);
        Branch branch;
        BranchList.displayBranchNames();
        System.out.println("Enter the branch that you want to remove payment method (Enter 0 to exit): ");
        while(true) {
            try {
                int choice = scanner.nextInt()-1;
                if (choice==-1) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                branch = BranchList.findBranch(choice);
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid branch index.");
                scanner.next();
            }
            catch (Exception e) {
                System.out.println(e.getMessage()+" Enter a valid branch index.");
            }
        }

        System.out.println("Payment methods available in the branch:");
        branch.getPaymentList().displayAvailablePayments();
        System.out.printf("Enter the payment method you want to remove from %s (Enter 0 to exit):\n", branch.getName());
        while (true) {
            try {
                int choice2 = scanner.nextInt()-1;
                if (choice2==-1) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                branch.getPaymentList().removeIndexedPayment(choice2);
                System.out.println("payment removed.");
                return;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid payment method (number).");
                scanner.next();
                continue;
            }
            catch (Exception e) {
                System.out.println("Enter a valid payment method (number).");
                scanner.next();
                continue;
            }
        }

    }

}