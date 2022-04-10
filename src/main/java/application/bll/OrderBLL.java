package application.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import application.bll.validators.AmountValidator;
import application.bll.validators.Validator;
import application.dao.OrderDAO;
import application.model.Order;

/**
 * Class used for the Order Business Layer.
 */
public class OrderBLL {
    private List<Validator<Order>> validators;
    private OrderDAO orderDAO;

    /**
     * OrderBLL constructor.
     * Instantiates the validator list and the OrderDAO Class.
     */
    public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();
        validators.add(new AmountValidator());

        orderDAO = new OrderDAO();
    }

    /**
     * Finds the Order from the database at the respective id.
     *
     * @param id The Order id.
     * @return The Order found at the id.
     * @throws NoSuchElementException Stating that the Order was not found.
     */
    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    /**
     * Finds the all the Orders from the database.
     *
     * @return A List of Orders.
     * @throws NoSuchElementException Stating that the Order table is empty.
     */
    public List<Order> findAllOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("Order order is empty");
        }
        return orders;
    }


    /**
     * Inserts an Order in the Order table.
     *
     * @param order Order to be inserted.
     * @throws NullPointerException Stating that the Order could not be inserted.
     */
    public void insertOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }
        Order insertedOrder = orderDAO.insert(order);
        if (insertedOrder == null) {
            throw new NullPointerException("Order was not inserted");
        }
    }

    /**
     * Updates the Order in the Order table.
     * @param order Order to be updated.
     * @throws NullPointerException Stating that the Order could not be updated.
     */
    public void updateOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }

        Order updatedOrder = orderDAO.update(order);
        if (updatedOrder == null) {
            throw new NullPointerException("Client was not updated");
        }
    }

    /**
     * Deletes the Order with the respective id.
     * @param id The Order id.
     * @throws NullPointerException Stating that the Order could not be deleted.
     */
    public void deleteOrderById(int id) {
        if (!orderDAO.deleteById(id)) {
            throw new NullPointerException("Order was not deleted");
        }
    }
}
