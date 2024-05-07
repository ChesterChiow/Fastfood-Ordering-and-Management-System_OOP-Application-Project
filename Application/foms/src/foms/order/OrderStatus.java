package foms.order;

import java.io.Serializable;

/**
 * Represents the status of an order.
 */
public enum OrderStatus implements Serializable {
    PENDING,    //Order is pending
    PREPARING,  //Order is being prepared
    READYTOPICKUP,  //Order is ready for pickup
    COMPLETED,  //Order is completed
    CANCELLED;  //Order is cancelled

    }