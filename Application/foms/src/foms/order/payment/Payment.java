package foms.order.payment;

import java.io.Serializable;

/**
 * The Payment interface represents a payment method.
 * It defines methods to get the name of the payment method, check the payment status, and set the payment status.
 */
public interface Payment extends Serializable {

    /**
     * Get the name of the payment method.
     *
     * @return The name of the payment method.
     */
    String getName();

    /**
     * Get the payment status.
     *
     * @return The payment status (true if payment is successful, false otherwise).
     */
    boolean getPaymentStatus();

    /**
     * Set the payment status.
     *
     * @param status The payment status to set (true if payment is successful, false otherwise).
     */
    void setPaymentStatus(boolean status);
}