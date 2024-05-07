package foms.management.branch;

import foms.management.lists.BranchList;
import foms.workers.AdminWorker;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Admin uses this class to change the branch status of a branch
 */
public class OperationsOnBranchStatus implements Serializable {
    /**
     * Operations on branch status is performed by admin worker.
     */
    private AdminWorker admin;

    /**
     * Constructor for OperationsOnBranchStatus class
     * @param admin is the admin worker performing the change of branch status.
     */
    public OperationsOnBranchStatus(AdminWorker admin){
        this.admin = admin;
    }

    /**
     * Change branch's status to open.
     */
    public void openBranch(){
        Scanner sc = new Scanner(System.in);
        BranchList.displayBranchNames();
        System.out.println("Enter the branch index to open (Enter 0 to exit): ");
        while (true) {
            try{
                int choice = sc.nextInt();
                if (choice ==0) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                Branch branch = BranchList.findBranch(choice-1);
                if (!branch.getStatus())
                    branch.setStatus(true);
                System.out.println(branch.getName()+" branch is opened.");
                return;
            }
            catch(InputMismatchException e){
                System.out.println("Branch not found! Enter a valid branch index (Enter 0 to exit):");
                sc.next();
            }
            catch(Exception e) {
                System.out.println(e.getMessage()+ " Enter a valid branch index (Enter 0 to exit):");
            }
        }
    }

    /**
     * Change branch's status to close.
     */
    public void closeBranch(){
        Scanner sc = new Scanner(System.in);
        BranchList.displayBranchNames();
        System.out.println("Enter the branch index to close (Enter 0 to exit): ");
        while(true) {
            try{
                int choice = sc.nextInt();
                if (choice ==0) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                Branch branch = BranchList.findBranch(choice-1);
                if (branch.getStatus())
                    branch.setStatus(false);
                System.out.println(branch.getName()+" branch is closed.");
                return;
            }
            catch(InputMismatchException e){
                System.out.println("Branch not found! Enter a valid branch index (Enter 0 to exit):");
                sc.next();
            }
            catch(Exception e) {
                System.out.println(e.getMessage()+ " Enter a valid branch index (Enter 0 to exit):");
            }
        }
    }

}