package foms.management.filters.workerfilters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import foms.workers.Worker;
/**
 * A filter for workers based on their age.
 */
public class AgeFilter implements WorkerFilters, Serializable {

    /**
     * Filters a list of workers based on their age.
     *
     * @param workerList The list of workers to filter.
     * @return The filtered list of workers.
     */
    @Override
    public ArrayList<Worker> filter(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList= new ArrayList<Worker>();
        System.out.println("Enter age to filter (Enter 0 to exit): ");
        while (true) {
            try {
                int choice = sc.nextInt();
                if (choice==0) {
                    System.out.println("Returning to previous page..");
                    return null;
                }
                else {
                    for(Worker worker: workerList) {
                        if (worker.getAge()==choice) {
                            filteredList.add(worker);
                        }
                    }
                    return filteredList;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Not a number. Enter age to filter (Enter 0 to exit): ");
                sc.next();
                continue;
            }
            catch (Exception e) {
                System.out.println("Something Went Wrong");
                return null;
            }
        }

    }

}
