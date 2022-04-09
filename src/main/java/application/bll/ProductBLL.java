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


public class ProductBLL {
    private List<Validator<Product>> validators;
	private ProductDAO productDAO;

	public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new ProductNameValidator());
        validators.add(new StockValidator());
        validators.add(new PriceValidator());

		productDAO = new ProductDAO();
	}

	public Product findProductById(int id) {
		Product product = productDAO.findById(id);
		if (product == null) {
			throw new NoSuchElementException("The product with id =" + id + " was not found!");
		}
		return product;
	}

	public List<Product> findAllProducts() {
		List<Product> products = productDAO.findAll();
		if (products == null) {
			throw new NoSuchElementException("Product product is empty");
		}
		return products;
	}

	public void insertProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }

		Product insertedProduct = productDAO.insert(product);
		if (insertedProduct == null) {
			throw new NullPointerException("Product was not inserted");
		}
	}

	public void updateProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }

		Product updatedProduct = productDAO.update(product);
		if (updatedProduct == null) {
			throw new NullPointerException("Client was not updated");
		}
	}

	public void deleteProductById(int id) {
		if(!productDAO.deleteById(id)) {
			throw new NullPointerException("Product was not deleted");
		}
	}
}
