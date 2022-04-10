package application.bll.validators;

import application.model.Product;

/**
 * An StockValidator class that implements Validator interface.
 * It is used to validate the Product stock.
 */
public class StockValidator implements Validator<Product> {

    /**
     * This method finds if the stock is lower or equal than 0.
     *
     * @param t The Product to validate.
     * @throws IllegalArgumentException Stating that the stock is invalid.
     */
    public void validate(Product t) {

        if (t.getStock() <= 0) {
            throw new IllegalArgumentException("The Stock limit is not respected!");
        }

    }
}
