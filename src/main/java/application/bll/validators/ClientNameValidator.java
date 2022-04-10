package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;

/**
 * An ClientNameValidator class that implements Validator interface.
 * It is used to validate the Client name.
 */
public class ClientNameValidator implements Validator<Client> {
	private static final String NAME_PATTERN = "^[a-zA-Z]+$";

	/**
	 * This method finds if the name matches the NAME_PATTERN string regex.
	 *
	 * @param t The Client to validate.
	 * @throws IllegalArgumentException Stating that the name is invalid.
	 */
	public void validate(Client t) {
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		if (!pattern.matcher(t.getName()).matches()) {
			throw new IllegalArgumentException("Name is not valid!");
		}
	}

}
