package foms.management.filters.workerfilters;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import foms.management.branch.Branch;
import foms.management.lists.BranchList;
import foms.workers.StaffWorker;
import foms.workers.Worker;

/**
 * A filter for workers based on the branches they work in.
 */
public class BranchFilter implements WorkerFilters{

    /**
     * Filters a list of workers based on the branches they work in.
     *
     * @param workerList The list of workers to filter.
     * @return The filtered list of workers.
     */
    @Override
    public ArrayList<Worker> filter(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList= new ArrayList<Worker>();
        BranchList.displayBranchNames();
        System.out.println("Enter branch index to filter (Enter 0 to exit): ");
        while (true) {
            try {
                int choice = sc.nextInt();
                if (choice==0) {
                    System.out.println("Returning to previous page..");
                    return null;
                }
                else {
                    Branch branch = BranchList.findBranch(choice-1);
                    for (Worker worker : workerList)
                        if (worker instanceof StaffWorker)
                            if(((StaffWorker)worker).getBranch().getName().equals(branch.getName()))
                                filteredList.add(worker);
                    return filteredList;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Branch not found! Enter a valid branch index (Enter 0 to exit):");
                sc.next();
                continue;
            }
            catch(Exception e) {
                System.out.println(e.getMessage()+ " Enter a valid branch index (Enter 0 to exit):");
                continue;
            }
        }
    }



}
