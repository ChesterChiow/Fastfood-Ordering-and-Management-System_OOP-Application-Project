package foms.workers;

import java.io.ObjectOutputStream;
import java.io.Serializable;

import foms.management.lists.OperationsOnBranchList;
import foms.management.lists.OperationsOnPaymentList;
import foms.management.branch.OperationsOnBranchStatus;
import foms.management.lists.OperationsOnWorkerList;
import foms.management.lists.AllWorkersList;
import foms.management.lists.BranchList;

/**
 * Represents an admin worker in the company.
 */
public class AdminWorker extends Worker implements Serializable {

    /**
     * The list of branches.
     */
    private BranchList branchList;

    /**
     * The list of all workers in the company.
     */
    private AllWorkersList allWorkersList;

    /**
     * Admin worker perform operations on branch list.
     */
    private  OperationsOnBranchList jobsOnBranchList;

    /**
     * Admin worker perform operations on worker list of one of the branch.
     */
    private  OperationsOnWorkerList jobsOnWorkerList;

    /**
     * Admin worker perform operations on a branch.
     */
    private  OperationsOnBranchStatus jobsOnBranch;

    /**
     * Admin worker perform operations on payment list of one of the branch.
     */
    private  OperationsOnPaymentList jobsOnPaymentList;

    /**
     * Constructor for AdminWorker class.
     * Create an admin worker.
     * @param name    full name of the worker.
     * @param age     age of the worker.
     * @param gender  gender of the worker.
     * @param loginID loginID of the worker.
     */
    public AdminWorker(String name, int age, char gender, String loginID) {
        super(name, age, gender, loginID);
        setRole('A');
        branchList = new BranchList();
        allWorkersList = new AllWorkersList(this);
        jobsOnBranchList = new OperationsOnBranchList(this);
        jobsOnWorkerList = new OperationsOnWorkerList(this);
        jobsOnBranch = new OperationsOnBranchStatus(this);
        jobsOnPaymentList = new OperationsOnPaymentList(this);
    }

    /**
     * @return Stringified details of admin
     */
    @Override
    public String toString() {
        return String.format("%-15s%-5d%-3c%-15s%-3c",
                getName(), getAge(), getGender(), getLoginID(), getRole());
    }

    /**
     * Gets the list of branches.
     * @return list of branches.
     */
    public BranchList getBranchList() {
        return branchList;
    }

    /**
     * Gets the list of workers in the company.
     * @return list of workers in the company.
     */
    public AllWorkersList getAllWorkersList() {
        return allWorkersList;
    }

    /**
     * Use the operations on branch list.
     * @return operations on branch list.
     */
    public OperationsOnBranchList getJobsOnBranchList() {
        return jobsOnBranchList;
    }

    /**
     * Use the operations on worker list.
     * @return operations on worker list.
     */
    public OperationsOnWorkerList getJobsOnWorkerList(){
        return jobsOnWorkerList;
    }

    /**
     * Use the operations on branch.
     * @return change branch status.
     */
    public OperationsOnBranchStatus getJobsOnBranch(){
        return jobsOnBranch;
    }

    /**
     * Use the operations on payment list.
     * @return change payment status of a branch.
     */
    public OperationsOnPaymentList getJobsOnPaymentList(){
        return jobsOnPaymentList;
    }
    
    /**
     * Changes the list of all workers in the company.
     *
     * @param workerList The list of all workers in the company.
     */
    public void setAllWorkersList(AllWorkersList workerList) {
    	this.allWorkersList = workerList;
    }
    
    /**
     * Changes the list of branches
     *
     * @param branchList The list of branches
     */
    public void setBranchList(BranchList branchList) {
    	this.branchList = branchList;
    }
}
