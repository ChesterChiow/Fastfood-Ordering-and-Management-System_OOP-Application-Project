package foms.management.lists;

import java.io.Serializable;
import java.util.ArrayList;

import foms.food.FoodItem;
import foms.order.Order;
import foms.order.OrderStatus;

/**
 * OrderList will collate all the Orders in the branch.
 */
public class OrderList implements Serializable {

    /**
     * This orderList.
     */
    private ArrayList<Order> orderList;

    /**
     * Constructor for OrderList class.
     * Initializes an empty list of orders.
     */
    public OrderList() {
        this.orderList = new ArrayList<>();
    }


    /**
     * Method to add an order to the order list.
     *
     * @param order Order with OrderStatus PREPARING will be added to this OrderList.
     */
    public void addOrderToOrderList(Order order) {
            orderList.add(order);
    }



    /**
     * Method to display every Order in the OrderList.
     * Uses a for loop to iterate through the list and displays each order's ID and status.
     */
    public void displayOrderList() {
        
        System.out.println("Orders:" );
        int i=1;
        for (Order order : orderList) {
            System.out.println(i + ". OrderID: "+order.getOrderID()+ ", status: "+order.getOrderStatus());
            i++;
        }
    }

    /**
     * Finds an order by index in the order list.
     *
     * @param index Index of the order in the order list.
     * @return The order object corresponding to the index.
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public Order findOrder(int index) throws IndexOutOfBoundsException{
        if(index>=0 && index<orderList.size()){
            return orderList.get(index);
        }else{
            throw new IndexOutOfBoundsException("Order not found!");
        }
    }

    /**
     * Process an order based on its ID.
     * If the order status is PREPARING, it changes it to READYTOPICKUP.
     * If the order status is READYTOPICKUP, it changes it to CANCELLED.
     *
     * @param orderID The ID of the order to process.
     */
    public void processOrder(int orderID) {

        for (Order order : orderList) {
            if (orderID == order.getOrderID()) {
                if (order.getOrderStatus() == OrderStatus.PREPARING) {
                    order.setOrderStatus(OrderStatus.READYTOPICKUP);
                    System.out.println("The order is ready to pick up now.");
                } else if (order.getOrderStatus() == OrderStatus.READYTOPICKUP) {
                    order.setOrderStatus(OrderStatus.CANCELLED);
                    System.out.println("The order has been cancelled due to over time.");
                    orderList.remove(order);
                }
            }
        }
    }



    // Getter and Setter methods

    /**
     * Get the list of orders.
     * @return The list of orders.
     */
    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    /**
     * Set the list of orders.
     * @param orderList The list of orders to set.
     */
    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }
}
