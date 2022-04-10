package application.bll.validators;

import application.model.Product;

/**
 * An PriceValidator class that implements Validator interface.
 * It is used to validate the Product price.
 */
public class PriceValidator implements Validator<Product> {

    /**
     * This method finds if the price is lower or equal than 0.
     *
     * @param t The Product to validate.
     * @throws IllegalArgumentException Stating that the price is invalid.
     */
    public void validate(Product t) {

        if (t.getPrice() <= 0) {
            throw new IllegalArgumentException("The Price limit is not respected!");
        }

    }
}
