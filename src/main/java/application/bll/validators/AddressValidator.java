package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;

public class AddressValidator implements Validator<Client> {
	private static final String PHONE_PATTERN = "^[a-zA-Z]+$";
	public void validate(Client t) {
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		if (!pattern.matcher(t.getAddress()).matches()) {
			throw new IllegalArgumentException("Address is not valid!");
		}
	}

}