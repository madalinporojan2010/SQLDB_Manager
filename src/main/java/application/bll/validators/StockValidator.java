package application.bll.validators;

import application.model.Product;

public class StockValidator implements Validator<Product> {

    public void validate(Product t) {

        if (t.getStock() <= 0) {
            throw new IllegalArgumentException("The Stock limit is not respected!");
        }

    }
}
