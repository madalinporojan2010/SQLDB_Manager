package application.bll.validators;

import application.model.Product;

import java.util.regex.Pattern;

public class ProductNameValidator implements Validator<Product> {
	private static final String NAME_PATTERN = "^[a-zA-Z]+$";
	public void validate(Product t) {
		Pattern pattern = Pattern.compile(NAME_PATTERN);
		if (!pattern.matcher(t.getName()).matches()) {
			throw new IllegalArgumentException("Name is not valid!");
		}
	}

}
