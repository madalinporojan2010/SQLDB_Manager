package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;


/**
 * An AddressValidator class that implements Validator interface.
 * It is used to validate the Client address.
 */
public class AddressValidator implements Validator<Client> {

    private static final String PHONE_PATTERN = "^[a-zA-Z]+$";

    /**
     * This method finds if the address matches the PHONE_PATTERN string regex.
     *
     * @param t The Client to validate.
     * @throws IllegalArgumentException Stating that the address is invalid.
     */
    public void validate(Client t) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(t.getAddress()).matches()) {
            throw new IllegalArgumentException("Address is not valid!");
        }
    }

}