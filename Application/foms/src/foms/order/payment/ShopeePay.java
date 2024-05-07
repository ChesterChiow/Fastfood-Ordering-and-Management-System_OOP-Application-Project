package foms.order.payment;

import java.io.Serializable;

/**
 * Represents a ShopeePay payment method.
 * Implements the Payment interface.
 */
public class ShopeePay implements Serializable, Payment {

    /** The name of the payment method. */
    final private String name = "ShopeePay";

    /** The payment status (true if payment is successful, false otherwise). */
    private boolean paymentStatus = true;

    /**
     * Get the name of the payment method.
     *
     * @return The name of the payment method.
     */
    @Override
    public String getName() {
        return name;
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
        this.paymentStatus = status;
    }
}