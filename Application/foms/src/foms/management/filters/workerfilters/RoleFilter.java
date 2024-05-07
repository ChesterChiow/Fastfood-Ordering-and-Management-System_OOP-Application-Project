package foms.management.filters.workerfilters;

import foms.workers.Worker;

import java.io.Serializable;

import java.util.ArrayList;

import java.util.Scanner;

/**
 * A filter for workers based on their roles.
 */
public class RoleFilter implements WorkerFilters, Serializable {

    /**
     * Filters a list of workers based on their roles.
     *
     * @param workerList The list of workers to filter.
     * @return The filtered list of workers.
     */
    @Override
    public ArrayList<Worker> filter(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList= new ArrayList<Worker>();
        System.out.println("1. Admin\n2. Manager\n3. Staff (excluding Manager)\nSelect the role to filter (Enter 0 to exit):");
        while (true) {
            try {
                String choice = sc.nextLine();
                if (choice.equals("1")) {
                    for(Worker worker : workerList)
                        if (worker.getRole() =='A') {
                            filteredList.add(worker);
                        }
                    return filteredList;
                }
                else if (choice.equals("2")){
                    for(Worker worker : workerList)
                        if (worker.getRole() =='M') {
                            filteredList.add(worker);
                        }
                    return filteredList;
                }
                else if (choice.equals("3")) {
                    for(Worker worker : workerList)
                        if (worker.getRole() =='S') {
                            filteredList.add(worker);
                        }
                    return filteredList;
                }
                else if (choice.equals("0")) {
                    System.out.println("Returning to previous page..");
                    return null;
                }
                else {
                    System.out.println("1. Admin\n2. Manager\n3. Staff (excluding Manager)\nEnter a valid filter (number) (Enter 0 to exit): ");
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println("Something Went Wrong");
                return null;
            }
        }
    }

}
