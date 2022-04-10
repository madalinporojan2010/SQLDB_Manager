package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;

/**
 * An PhoneValidator class that implements Validator interface.
 * It is used to validate the Client phone.
 */
public class PhoneValidator implements Validator<Client> {
    private static final String PHONE_PATTERN = "^(\\d{3}[- ]?){2}\\d{4}$";

    /**
     * This method finds if the phone matches the PHONE_PATTERN string regex.
     *
     * @param t The Client to validate.
     * @throws IllegalArgumentException Stating that the phone number is invalid.
     */
    public void validate(Client t) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        if (!pattern.matcher(t.getPhone()).matches()) {
            throw new IllegalArgumentException("Phone Number is not valid!");
        }
    }

}