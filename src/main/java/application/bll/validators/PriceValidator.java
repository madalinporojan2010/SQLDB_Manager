package application.bll.validators;

import application.model.Product;

public class PriceValidator implements Validator<Product> {

    public void validate(Product t) {

        if (t.getPrice() <= 0) {
            throw new IllegalArgumentException("The Price limit is not respected!");
        }

    }
}
