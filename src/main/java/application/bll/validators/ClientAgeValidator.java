package application.bll.validators;

import application.model.Client;

/**
 * An ClientAgeValidator class that implements Validator interface.
 * It is used to validate the Client age.
 */
public class ClientAgeValidator implements Validator<Client> {
    private static final int MIN_AGE = 16;
    private static final int MAX_AGE = 80;

    /**
     * This method finds if the age is between [MIN_AGE = 16, MAX_AGE = 80].
     *
     * @param t The Client to validate.
     * @throws IllegalArgumentException Stating that the age is invalid.
     */
    public void validate(Client t) {

        if (t.getAge() < MIN_AGE || t.getAge() > MAX_AGE) {
            throw new IllegalArgumentException("The Student Age limit is not respected!");
        }

    }

}
