package foms.management.lists;

import foms.order.payment.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a list of payment methods available for a branch.
 */
public class PaymentList implements Serializable {

    /**
     * List of payment methods of a branch.
     */
    private ArrayList<Payment> paymentList;

    /**
     * Constructor for PaymentList
     */
    public PaymentList() {
        this.paymentList = new ArrayList<>();
        paymentList.add(new NETS());
        paymentList.add(new Paypal());
        paymentList.add(new PayWave());
        paymentList.add(new ShopeePay());
    }

    /**
     * Add a new payment method to the payment list.
     *
     * @param newPaymentMethod The new payment method to be added.
     */
    public void addCreatedPayment(Payment newPaymentMethod) {
        paymentList.add(newPaymentMethod);
    }

    /**
     * Remove a payment method from the payment list at the specified index.
     *
     * @param index The index of the payment method to be removed.
     * @throws IndexOutOfBoundsException If the index is out of bounds.
     */
    public void removeIndexedPayment(int index) throws IndexOutOfBoundsException {
        paymentList.remove(index);
        if (index>=paymentList.size())
            throw new IndexOutOfBoundsException();
    }

    /**
     * Retrieves the payment method at the specified index from the payment list.
     *
     * @param index The index of the payment method to retrieve.
     * @return The payment method at the specified index.
     */
    public Payment getPayment(int index) {
        return paymentList.get(index); //TODO Exception
    }

    /**
     * Retrieves a list of all available payment methods.
     *
     * @return A list of all available payment methods.
     */
    public List<Payment> getAvailablePayments() {
        return paymentList.stream().filter(Payment::getPaymentStatus).collect(Collectors.toList());
    }

    /**
     * Display all the payments in the payment list.
     */
    public void displayAllPayments(){
        System.out.println("List of payments:");
        System.out.println("1. NETS\n2. Paypal\n3. PayWave\n4. ShopeePay");
    }

    /**
     * Display all the payments available in the branch.
     */
    public void displayAvailablePayments() {
        int i=1;
        for (Payment payment : paymentList) {
            System.out.println(i+". "+payment.getName());
            i++;
        }

    }

    /**
     * Check if payment is in payment list.
     * @return true if payment is in payment list, false if payment is not in the payment list.
     */
    public boolean checkPayment(Payment payment) {
        for (Payment payment1 : paymentList)
            if (payment1.getName().equals(payment.getName()))
                return true;
        return false;
    }
}