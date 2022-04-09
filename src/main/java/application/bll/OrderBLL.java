package application.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import application.bll.validators.EmailValidator;
import application.dao.OrderDAO;
import application.model.Order;
import application.model.Product;
import bll.validators.Validator;


public class OrderBLL {
    private List<EmailValidator> validators;
	private OrderDAO orderDAO;

	public OrderBLL() {
		validators = new ArrayList<EmailValidator>();
		validators.add(new EmailValidator());

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
		Order insertedOrder = orderDAO.insert(order);
		if (insertedOrder == null) {
			throw new NullPointerException("Order was not inserted");
		}
	}

	public void updateOrder(Order order) {
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
