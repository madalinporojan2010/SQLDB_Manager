package application.start;

import application.bll.ClientBLL;
import application.bll.OrderBLL;
import application.bll.ProductBLL;
import application.model.Client;
import application.model.Order;
import application.model.Product;
import com.pspdfkit.api.PSPDFKit;
import com.pspdfkit.api.PSPDFKitInitializeException;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException{

        ClientBLL clientBLL = new ClientBLL();
        ProductBLL productBLL = new ProductBLL();
        OrderBLL orderBLL = new OrderBLL();

        Client client1 = null;
        Order order1 = null;
        Product product1 = null;

        List<Client> clients = null;
        List<Product> products = null;
        List<Order> orders = null;

        try {
            client1 = clientBLL.findClientById(0);
            ReflectionExample.retrieveProperties(client1);
            System.out.println();

            product1 = productBLL.findProductById(0);
            ReflectionExample.retrieveProperties(product1);
            System.out.println();

            order1 = orderBLL.findOrderById(0);
            ReflectionExample.retrieveProperties(order1);

            System.out.println("\n");

            clients = clientBLL.findAllClients();
            for(Client client: clients) {
                ReflectionExample.retrieveProperties(client);
                System.out.println();
            }

            products = productBLL.findAllProducts();
            for(Product product: products) {
                ReflectionExample.retrieveProperties(product);
                System.out.println();
            }

            orders = orderBLL.findAllOrders();
            for(Order order: orders) {
                ReflectionExample.retrieveProperties(order);
                System.out.println();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }

        // obtain field-value pairs for object through reflection

    }

}
