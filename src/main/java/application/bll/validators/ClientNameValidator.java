package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;

public class ClientNameValidator implements Validator<Client> {
	private static final String NAME_PATTERN = "^[a-zA-Z]+$";
	public void validate(Client t) {
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		if (!pattern.matcher(t.getName()).matches()) {
			throw new IllegalArgumentException("Name is not valid!");
		}
	}

}
