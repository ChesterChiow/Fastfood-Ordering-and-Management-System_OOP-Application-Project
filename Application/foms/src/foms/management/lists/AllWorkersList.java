package foms.management.lists;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import foms.management.filters.workerfilters.*;
import foms.management.filters.workerfilters.WorkerFilters;
import foms.workers.AdminWorker;
import foms.workers.StaffWorker;
import foms.workers.Worker;

/**
 * The AllWorkersList class manages the list of all workers in the company.
 * It provides functionality to serialize/deserialize the worker list, add and remove workers, display the worker list,
 * filter the worker list based on various criteria, and find a specific worker.
 */
public class AllWorkersList implements Serializable {
    /**
     * The list of all workers in the company.
     */
    private static ArrayList<Worker> allWorkersList;
    
    private static final long serialVersionUID = 1L;


    /**
     * Serializes the list of all workers.
     *
     * @throws IOException If an I/O error occurs while writing the file.
     */
    public static void serializeALlWorkerList() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AllWorkerList.ser"))) {
            oos.writeObject(allWorkersList);
        }
    }

    public static void deserializeAllWorkerList()throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("AllWorkerList.ser"))) {
            allWorkersList= (ArrayList<Worker>) ois.readObject();
        }
        BranchList branchList = new BranchList();

        for (Worker worker : allWorkersList) {
            if (worker instanceof StaffWorker) {
                StaffWorker staffWorker = (StaffWorker) worker;

                if (!branchList.inBranchList(staffWorker.getBranch())) {
                    branchList.getBranchList().add(staffWorker.getBranch());
                }
            }
        }
    }
    /**
     * The admin worker in the company.
     */
    private AdminWorker admin;

    /**
     * Constructor for the AllWorkersList class.
     * Creates a worker list with an admin worker.
     *
     * @param admin The admin worker in the company.
     */
    public AllWorkersList(AdminWorker admin) {
        this.admin = admin;
        this.allWorkersList = new ArrayList<Worker>();
        allWorkersList.add(admin);
    }

    /**
     * Adds a worker to the list of workers.
     *
     * @param worker The Worker object to be added.
     */
    public void addCreatedWorker(Worker worker) {
        allWorkersList.add(worker);
    }

    /**
     * Removes a worker from the list of workers.
     *
     * @param worker The Worker object to be removed.
     */
    public void removeWorkerObject(Worker worker) {
        allWorkersList.remove(worker);
    }

    /**
     * Displays the list of workers in the company.
     * Provides an option to filter the list of workers based on user input.
     */
    public void displayWorkerListInSystem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("List of workers");
        for (Worker worker: allWorkersList) {
            System.out.println(worker.toString());
        }

        System.out.println("Do you want to filter the list of workers? Y/N");

        while(true) {
            String choice = sc.nextLine();
            if (choice.equals("Y")) {
                filterWorkerListInSystem(allWorkersList);
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
     * Filters the worker list based on various criteria such as role, age, gender, or branch.
     *
     * @param workerList The list of workers to be filtered.
     */
    public void filterWorkerListInSystem(ArrayList<Worker> workerList) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Worker> filteredList;

        System.out.println("Filters:\n1.Role\n2.Age\n3.Gender\n4.Branch");
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
                else if (choice.equals("4")) {
                    WorkerFilters branchFilter = new BranchFilter();
                    filteredList = branchFilter.filter(workerList);
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
                    System.out.println("Filters:\n1.Role\n2.Age\n3.Gender\n4.Branch");
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
                filterWorkerListInSystem(filteredList);
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
     * Finds a worker by index in the list of workers.
     *
     * @param index The index of the worker object in the list.
     * @return The worker object corresponding to the index.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public static Worker findWorker(int index) throws IndexOutOfBoundsException{
        if (index >= 0 && index < allWorkersList.size()) {
            return allWorkersList.get(index);
        }
        else {
            throw new IndexOutOfBoundsException("Worker not found!");
        }
    }

    /**
     * Checks if a worker with the given login ID exists in the system.
     *
     * @param loginID The login ID of the worker to check for duplicate.
     * @return The worker object if a duplicate exists, otherwise null.
     */
    public static Worker isWorkerInSystem(String loginID) {
        for(Worker worker : allWorkersList) {
            if (worker.getLoginID().equals(loginID)) // duplicate exist.
                return worker;
        }
        return null;
    }

    /**
     * Gets the list of all workers in the system.
     *
     * @return The list of workers.
     */
    public ArrayList<Worker> getWorkerList() {
        return allWorkersList;
    }
    
    public void setWorkerList(ArrayList<Worker> workerList) {
    	AllWorkersList.allWorkersList = workerList;
    }

}