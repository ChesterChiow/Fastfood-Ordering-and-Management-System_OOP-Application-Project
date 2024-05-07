package foms.management.filters.workerfilters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import foms.workers.Worker;

/**
 * A filter for workers based on their gender.
 */
public class GenderFilter implements WorkerFilters, Serializable {

    /**
     * Filters a list of workers based on their gender.
     *
     * @param workerList The list of workers to filter.
     * @return The filtered list of workers.
     */
    @Override
    public ArrayList<Worker> filter(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList= new ArrayList<Worker>();
        System.out.println("1. Male\n2. Female\nSelect the gender to filter (Enter 0 to exit):");
        while (true) {
            try {
                String choice = sc.nextLine();
                if (choice.equals("1")) {
                    for(Worker worker : workerList) {
                        if (worker.getGender() =='M') {
                            filteredList.add(worker);
                        }
                    }
                    return filteredList;
                }
                else if (choice.equals("2")){
                    for(Worker worker : workerList) {
                        if (worker.getGender() =='F') {
                            filteredList.add(worker);
                        }
                    }
                    return filteredList;
                }
                else if (choice.equals("0")) {
                    System.out.println("Returning to previous page..");
                    return null;
                }
                else {
                    System.out.println("1. Male\n2. Female\nEnter a valid filter (number) (Enter 0 to exit): ");
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
