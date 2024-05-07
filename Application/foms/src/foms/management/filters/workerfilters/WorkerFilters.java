package foms.management.filters.workerfilters;

import foms.workers.Worker;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Defines a filter for workers.
 */
public interface WorkerFilters extends Serializable {

    /**
     * Filters a list of workers based on specific criteria.
     *
     * @param workerList The list of workers to filter.
     * @return The filtered list of workers.
     */
    public ArrayList<Worker> filter(ArrayList<Worker> workerList);

}