package application.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import application.bll.validators.*;
import application.dao.ClientDAO;
import application.model.Client;


/**
 * Class used for the Client Business Layer.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    /**
     * ClientBLL constructor.
     * Instantiates the validator list and the ClientDAO Class.
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new ClientNameValidator());
        validators.add(new ClientAgeValidator());
        validators.add(new PhoneValidator());
        validators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }

    /**
     * Finds the Client from the database at the respective id.
     *
     * @param id The Client id.
     * @return The Client found at the id.
     * @throws NoSuchElementException Stating that the Client was not found.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
        return client;
    }

    /**
     * Finds the all the Clients from the database.
     *
     * @return A List of Clients.
     * @throws NoSuchElementException Stating that the Client table is empty.
     */
    public List<Client> findAllClients() {
        List<Client> clients = clientDAO.findAll();
        if (clients == null) {
            throw new NoSuchElementException("Client table is empty");
        }
        return clients;
    }

    /**
     * Inserts a Client in the Client table.
     *
     * @param client Client to be inserted.
     * @throws NullPointerException Stating that the Client could not be inserted.
     */
    public void insertClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }

        Client insertedClient = clientDAO.insert(client);
        if (insertedClient == null) {
            throw new NullPointerException("Client was not inserted");
        }
    }

    /**
     * Updates the Client in the Client table.
     *
     * @param client Client to be updated.
     * @throws NullPointerException Stating that the Client could not be updated.
     */
    public void updateClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        Client updatedClient = clientDAO.update(client);
        if (updatedClient == null) {
            throw new NullPointerException("Client was not updated");
        }
    }

    /**
     * Deletes the Client with the respective id.
     *
     * @param id The Client id.
     * @throws NullPointerException Stating that the Client could not be deleted.
     */
    public void deleteClientById(int id) {
        if (!clientDAO.deleteById(id)) {
            throw new NullPointerException("Client was not deleted");
        }
    }
}
