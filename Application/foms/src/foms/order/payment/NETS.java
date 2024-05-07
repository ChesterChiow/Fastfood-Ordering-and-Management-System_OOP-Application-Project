package foms.order.payment;

import java.io.Serializable;

/**
 * Represents a NETS payment method.
 * Implements the Payment interface.
 */
public class NETS implements Serializable, Payment {

    /** The name of the payment method. */
    final private String name = "NETS";

    /** The payment status (true if payment is successful, false otherwise). */
    private boolean paymentStatus = true;

    /**
     * Get the name of the payment method.
     *
     * @return The name of the payment method.
     */
    @Override
    public String getName() {
        return "NETS";
    }

    /**
     * Get the payment status.
     *
     * @return The payment status (true if payment is successful, false otherwise).
     */
    @Override
    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Set the payment status.
     *
     * @param status The payment status to set (true if payment is successful, false otherwise).
     */
    @Override
    public void setPaymentStatus(boolean status) {
        paymentStatus = status;
    }
}