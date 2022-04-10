package application.bll.validators;

import application.model.Order;

/**
 * An AmountValidator class that implements Validator interface.
 * It is used to validate the Order amount.
 */
public class AmountValidator implements Validator<Order> {

    /**
     * This method finds if the amount is lower or equal than 0.
     *
     * @param t The Order to validate.
     * @throws IllegalArgumentException Stating that the amount is invalid.
     */
    public void validate(Order t) {

        if (t.getAmount() <= 0) {
            throw new IllegalArgumentException("The amount limit is not respected!");
        }

    }
}
