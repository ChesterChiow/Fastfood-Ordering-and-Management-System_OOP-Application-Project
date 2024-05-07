package foms.management.lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import foms.management.filters.workerfilters.*;
import foms.management.filters.workerfilters.WorkerFilters;
import foms.workers.AdminWorker;
import foms.workers.Worker;

/**
 * The WorkerList class manages the list of all workers in a branch.
 */
public class WorkerList implements Serializable {


    /**
     * list of workers in a branch.
     */
    private ArrayList<Worker> workerList;

    /**
     * Constructor for WorkerList class.
     * Create a worker list.
     */
    public WorkerList() {
        this.workerList = new ArrayList<Worker>();
    }

    /**
     * Add a worker into list of workers.
     * @param worker is the Worker object.
     */
    public void addCreatedWorker(Worker worker) {
        workerList.add(worker);
    }

    /**
     * Remove worker from list of workers.
     * @param worker is the Worker object.
     */
    public void removeWorkerObject(Worker worker) {
        workerList.remove(worker);
    }

    /**
     * Display worker list
     */
    public void displayWorkerListInBranch() {
        Scanner sc = new Scanner(System.in);
        System.out.println("List of workers");
        for (Worker worker: workerList) {
            System.out.println(worker.toString());
        }

        System.out.println("Do you want to filter the list of workers? Y/N");

        while(true) {
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                filterWorkerListInBranch(workerList);
                return;
            }
            else if (choice.equals("N")) {
                System.out.println("Returning to previous page..");
                return;
            }
            else {
                System.out.println("Do you want to filter the list of workers? Y/N");
                continue;
            }
        }
    }

    /**
     * Filter worker list
     * @param workerList list of workers
     */
    public void filterWorkerListInBranch(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList;

        System.out.println("Filters:\n1.Role\n2.Age\n3.Gender");
        System.out.println("Enter filter (Enter 0 to exit): ");
        while(true) {
            try {
                String choice = sc.nextLine();
                if (choice.equals("1")) {
                    WorkerFilters roleFilter = new RoleFilter();
                    filteredList = roleFilter.filter(workerList);
                    if (filteredList == null)
                        return;
                    for (Worker worker: filteredList) {
                        System.out.println(worker.toString());
                    }
                    break;
                }
                else if (choice.equals("2")) {
                    WorkerFilters ageFilter = new AgeFilter();
                    filteredList = ageFilter.filter(workerList);
                    if (filteredList == null)
                        return;
                    for (Worker worker: filteredList) {
                        System.out.println(worker.toString());
                    }
                    break;
                }
                else if (choice.equals("3")) {
                    WorkerFilters genderFilter = new GenderFilter();
                    filteredList = genderFilter.filter(workerList);
                    if (filteredList == null)
                        return;
                    for (Worker worker: filteredList) {
                        System.out.println(worker.toString());
                    }
                    break;
                }
                else if (choice.equals("0")) {
                    System.out.println("Returning to previous page..");
                    return;
                }
                else {
                    System.out.println("Filters:\n1.Role\n2.Age\n3.Gender");
                    System.out.println("Invalid filter. Enter filter (number) (Enter 0 to exit): ");
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println("Something Went Wrong");
                return;
            }
        }

        System.out.println("Do you want to add another filter? Y/N");
        while(true) {
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                filterWorkerListInBranch(filteredList);
                return;
            }
            else if (choice.equals("N")) {
                System.out.println("Returning to previous page..");
                return;
            }
            else {
                System.out.println("Do you want to add another filter? Y/N");
                continue;
            }
        }

    }

    /**
     * Gets the list of workers in a branch.
     * @return list of workers in a branch.
     */
    public ArrayList<Worker> getWorkerList() {
        return workerList;
    }
}
