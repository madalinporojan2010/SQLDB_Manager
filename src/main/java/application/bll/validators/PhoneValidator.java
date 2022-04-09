package application.bll.validators;

import application.model.Client;

import java.util.regex.Pattern;

public class PhoneValidator implements Validator<Client> {
	private static final String PHONE_PATTERN = "^(\\d{3}[- ]?){2}\\d{4}$";
	public void validate(Client t) {
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		if (!pattern.matcher(t.getPhone()).matches()) {
			throw new IllegalArgumentException("Phone Number is not valid!");
		}
	}

}