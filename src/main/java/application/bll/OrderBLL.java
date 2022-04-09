package application.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import application.bll.validators.AmountValidator;
import application.bll.validators.Validator;
import application.dao.OrderDAO;
import application.model.Order;


public class OrderBLL {
    private List<Validator<Order>> validators;
	private OrderDAO orderDAO;

	public OrderBLL() {
        validators = new ArrayList<Validator<Order>>();
        validators.add(new AmountValidator());

		orderDAO = new OrderDAO();
	}

	public Order findOrderById(int id) {
		Order order = orderDAO.findById(id);
		if (order == null) {
			throw new NoSuchElementException("The order with id =" + id + " was not found!");
		}
		return order;
	}

	public List<Order> findAllOrders() {
		List<Order> orders = orderDAO.findAll();
		if (orders == null) {
			throw new NoSuchElementException("Order order is empty");
		}
		return orders;
	}


	public void insertOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }
		Order insertedOrder = orderDAO.insert(order);
		if (insertedOrder == null) {
			throw new NullPointerException("Order was not inserted");
		}
	}

	public void updateOrder(Order order) {
        for (Validator<Order> validator : validators) {
            validator.validate(order);
        }

		Order updatedOrder = orderDAO.update(order);
		if (updatedOrder == null) {
			throw new NullPointerException("Client was not updated");
		}
	}

	public void deleteOrderById(int id) {
		if(!orderDAO.deleteById(id)) {
			throw new NullPointerException("Order was not deleted");
		}
	}
}
