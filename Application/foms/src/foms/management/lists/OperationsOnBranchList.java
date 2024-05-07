package foms.management.lists;

import foms.management.branch.Branch;
import foms.workers.*;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * This class represents operations that can be performed on the list of branches by an admin worker.
 */
public class OperationsOnBranchList implements Serializable {
    /** The admin worker performing operations on the branch list. */
    private AdminWorker admin;

    /**
     * Constructs an OperationsOnBranchList object with the specified admin worker.
     * @param admin the admin worker performing the operations on the branch list
     */
    public OperationsOnBranchList(AdminWorker admin){
        this.admin = admin;
    }

    /**
     * Add a new Branch to the list.
     * 
     */
    public void addBranch() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String branchName;
            try {
                System.out.print("Enter the new branch name (Enter 0 to exit): ");
                branchName = scanner.nextLine();
                if (branchName.equals("0")) {
                    System.out.println("Returning to previous page..");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Something went wrong");
                return;
            }


            if (admin.getBranchList().isBranchInBranchList(branchName)!= null) { //if not null, means there is a duplicate
                System.out.println("This branch name already exists in system.");
                continue;
            }

            String location;
            int quota;

            while (true) {
                try {
                    System.out.print("Enter the location of the branch (Enter 0 to exit): ");
                    location = scanner.nextLine();
                    if (location.equals("0")) {
                        System.out.println("Returning to previous page..");
                        return;
                    }
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                    return;
                }

                System.out.print("Enter the branch quota (Enter 0 to exit): ");
                while (true) {
                    try {
                        quota = scanner.nextInt();
                        if (quota == 0) {
                            System.out.println("Returning to previous page..");
                            return;
                        }
                    }
                    catch (InputMismatchException e){
                        System.out.println("Enter the branch quota (number):");
                        scanner.next();
                        continue;
                    }
                    Branch branch = new Branch(branchName, location, quota);
                    admin.getBranchList().addCreatedBranch(branch);
                    System.out.printf("Transfer a staff worker to %s branch.\n", branch.getName());
                    transferStaff();
                    if (branch.getTotalNumStaff()==0) {
                    	System.out.println(branch.getName()+ " branch not added because there are no workers.");
                    	admin.getBranchList().getBranchList().remove(branch);
                    	BranchList.displayBranchNames();
                    	return;
                    }
                    	System.out.println(branch.getName()+ " branch added.");
                    	BranchList.displayBranchNames();
                    return;
                    }
                }
            }
        }



    /**
     * Remove a branch from the list.
     * if the branch has workers, it cannot be removed.
     */
    public void removeBranch() {
        Scanner scanner = new Scanner(System.in);
        BranchList.displayBranchNames();
        System.out.println("Enter the branch index to remove (Enter 0 to exit): ");
        while(true) {
            try {
                int choice = scanner.nextInt()-1;
                if (choice==-1) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                Branch branch = BranchList.findBranch(choice);
                if (branch.getTotalNumStaff()!=0) {
                	System.out.println("Branch has workers. Branch cannot be removed, remove the branch workers first.");
                	return;
                }
                admin.getBranchList().removeBranchObject(branch);
                return;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid branch index.");
                scanner.next();
            }
            catch (Exception e) {
                System.out.println(e.getMessage()+" Enter a valid branch index.");
            }
        }
    }

    /**
     * Transfers a staff worker from one branch to another branch.
     */
    public void transferStaff() {
        Scanner scanner = new Scanner(System.in);
        int i=0;
        System.out.println("List of workers:");
        for(Worker worker : admin.getAllWorkersList().getWorkerList()) {
            if (worker instanceof AdminWorker)
                continue;
            System.out.printf("%d: %s\n", i+1, worker.toString());
            i++;
        }

        System.out.println("Enter the staff worker to transfer branch (Enter 0 to exit): ");
        int choice;
        StaffWorker worker;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice==0) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                worker = (StaffWorker) admin.getAllWorkersList().findWorker(choice);
                break;
            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid worker index.");
                scanner.next();
                continue;
            }
            catch (Exception e) {
                System.out.println(e.getMessage()+" Enter a valid worker index.");
                continue;
            }
        }


        BranchList.displayBranchNames();
        System.out.printf("Currently, %s works at %s.\n", worker.getName(),worker.getBranch().getName());
        System.out.println("Enter the branch index to transfer to (Enter 0 to exit): ");

        while(true) {
            try {
                int choice2 = scanner.nextInt()-1;
                if (choice2==-1) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                Branch branch = BranchList.findBranch(choice2);
                if (branch.getName().equals(worker.getBranch().getName())) {
                    System.out.printf("%s already works at %s.\n",worker.getName(),branch.getName());
                    System.out.println("Returning to previous page..");
                    return;
                }
                if (branch.checkQuota()==false) {
                    System.out.println("Cannot be transferred because branch is full.");
                    System.out.println("Returning to previous page..");
                    return;
                }
                if (worker instanceof ManagerWorker) {
                    if(branch.checkCanAddManager()==false) {
                        System.out.println("Manager cannot be assigned because quota ratio is full.");
                        System.out.printf("Number of staff (excluding Manager): \t%d\n", branch.getNumStaff());
                        System.out.printf("Number of manager: \t\t\t%d\n", branch.getNumManager());
                        System.out.println("Returning to previous page..");
                        return;
                    }
                    admin.getAllWorkersList().removeWorkerObject(worker);
                    worker.getBranch().getWorkerList().removeWorkerObject(worker);
                    worker.getBranch().setNumManager(worker.getBranch().getNumManager()-1);

                    worker.setBranch(branch);

                    branch.getWorkerList().addCreatedWorker(worker);
                    branch.setNumManager(branch.getNumManager()+1);
                    admin.getAllWorkersList().addCreatedWorker(worker);
                    System.out.printf("%s transferred to %s.\n",worker.getName(),branch.getName());
                    if (branch.checkRatio()==false){
                        System.out.printf("%s Branch Manpower:\n", branch.getName());
                        System.out.printf("Number of staff (excluding Manager): \t%d\n", branch.getNumStaff());
                        System.out.printf("Number of manager: \t\t\t%d\n", branch.getNumManager());
                        System.out.println("Note that quota ratio is not met.");
                        return;
                    }
                    else {
                        System.out.printf("%s Branch Manpower:\n", branch.getName());
                        System.out.printf("Number of staff (excluding Manager): \t%d\n", branch.getNumStaff());
                        System.out.printf("Number of manager: \t\t\t%d\n", branch.getNumManager());
                        System.out.println("Quota ratio is met.");
                        return;
                    }

                }
                if (worker instanceof StaffWorker) {
                    admin.getAllWorkersList().removeWorkerObject(worker);
                    worker.getBranch().getWorkerList().removeWorkerObject(worker);
                    worker.getBranch().setNumStaff(worker.getBranch().getNumStaff()-1);

                    worker.setBranch(branch);

                    branch.getWorkerList().addCreatedWorker(worker);
                    branch.setNumStaff(branch.getNumStaff()+1);
                    admin.getAllWorkersList().addCreatedWorker(worker);
                    System.out.printf("%s transferred to %s.\n",worker.getName(),branch.getName());
                    if (branch.checkRatio()==false){
                        System.out.printf("%s Branch Manpower:\n", branch.getName());
                        System.out.printf("Number of staff (excluding Manager): \t%d\n", branch.getNumStaff());
                        System.out.printf("Number of manager: \t\t\t%d\n", branch.getNumManager());
                        System.out.println("Note that quota ratio is not met.");
                        return;
                    }
                    else {
                        System.out.printf("%s Branch Manpower:\n", branch.getName());
                        System.out.printf("Number of staff (excluding Manager): \t%d\n", branch.getNumStaff());
                        System.out.printf("Number of manager: \t\t\t%d\n", branch.getNumManager());
                        System.out.println("Quota ratio is met.");
                        return;
                    }

                }

            }
            catch (InputMismatchException e) {
                System.out.println("Enter a valid branch index.");
                scanner.next();
                continue;
            }
            catch (Exception e) {
                System.out.println(e.getMessage()+" Enter a valid branch index.");
                continue;
            }
        }
    }


}
