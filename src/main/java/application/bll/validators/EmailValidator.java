package application.bll.validators;

import java.util.regex.Pattern;

import application.model.Client;

/**
 * An EmailValidator class that implements Validator interface.
 * It is used to validate the Client email.
 */
public class EmailValidator implements Validator<Client> {
    private static final String EMAIL_PATTERN = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    /**
     * This method finds if the email matches the EMAIL_PATTERN string regex.
     *
     * @param t The Client to validate.
     * @throws IllegalArgumentException Stating that the email is invalid.
     */
    public void validate(Client t) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(t.getEmail()).matches()) {
            throw new IllegalArgumentException("Email is not a valid email!");
        }
    }

}
