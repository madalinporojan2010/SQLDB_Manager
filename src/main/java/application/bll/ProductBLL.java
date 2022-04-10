package application.bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import application.bll.validators.PriceValidator;
import application.bll.validators.ProductNameValidator;
import application.bll.validators.StockValidator;
import application.bll.validators.Validator;
import application.dao.ProductDAO;
import application.model.Product;

/**
 * Class used for the Product Business Layer.
 */
public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    /**
     * ProductBLL constructor.
     * Instantiates the validator list and the ProductDAO Class.
     */
    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new ProductNameValidator());
        validators.add(new StockValidator());
        validators.add(new PriceValidator());

        productDAO = new ProductDAO();
    }

    /**
     * Finds the Product from the database at the respective id.
     *
     * @param id The Product id.
     * @return The Product found at the id.
     * @throws NoSuchElementException Stating that the Product was not found.
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * Finds the all the Products from the database.
     *
     * @return A List of Products.
     * @throws NoSuchElementException Stating that the Product table is empty.
     */
    public List<Product> findAllProducts() {
        List<Product> products = productDAO.findAll();
        if (products == null) {
            throw new NoSuchElementException("Product product is empty");
        }
        return products;
    }

    /**
     * Inserts a Product in the Product table.
     *
     * @param product Product to be inserted.
     * @throws NullPointerException Stating that the Product could not be inserted.
     */
    public void insertProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }

        Product insertedProduct = productDAO.insert(product);
        if (insertedProduct == null) {
            throw new NullPointerException("Product was not inserted");
        }
    }

    /**
     * Updates the Product in the Product table.
     *
     * @param product Product to be updated.
     * @throws NullPointerException Stating that the Product could not be updated.
     */
    public void updateProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }

        Product updatedProduct = productDAO.update(product);
        if (updatedProduct == null) {
            throw new NullPointerException("Client was not updated");
        }
    }

    /**
     * Deletes the Product with the respective id.
     *
     * @param id The Product id.
     * @throws NullPointerException Stating that the Product could not be deleted.
     */
    public void deleteProductById(int id) {
        if (!productDAO.deleteById(id)) {
            throw new NullPointerException("Product was not deleted");
        }
    }
}
