package application.bll.validators;

import application.model.Order;

public class AmountValidator implements Validator<Order> {

    public void validate(Order t) {

        if (t.getAmmount() <= 0) {
            throw new IllegalArgumentException("The amount limit is not respected!");
        }

    }
}
