package application.bll;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;

import application.bll.validators.EmailValidator;
import application.dao.ClientDAO;
import application.dao.OrderDAO;
import application.model.Client;
import application.model.Order;
import bll.validators.Validator;


public class ClientBLL {

	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());

		clientDAO = new ClientDAO();
	}

	public Client findClientById(int id) {
		Client client = clientDAO.findById(id);
		if (client == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return client;
	}

	public List<Client> findAllClients() {
		List<Client> clients = clientDAO.findAll();
		if (clients == null) {
			throw new NoSuchElementException("Client table is empty");
		}
		return clients;
	}

	public void insertClient(Client client) {
		Client insertedClient = clientDAO.insert(client);
		if (insertedClient == null) {
			throw new NullPointerException("Client was not inserted");
		}
	}

	public void deleteClientById(int id) {
		if(!clientDAO.deleteById(id)) {
			throw new NullPointerException("Client was not deleted");
		}
	}
}
