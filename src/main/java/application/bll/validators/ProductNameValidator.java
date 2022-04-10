package application.bll.validators;

import application.model.Product;

import java.util.regex.Pattern;

/**
 * An ProductNameValidator class that implements Validator interface.
 * It is used to validate the Product name.
 */
public class ProductNameValidator implements Validator<Product> {
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";

    /**
     * This method finds if the name matches the NAME_PATTERN string regex.
     *
     * @param t The Product to validate.
     * @throws IllegalArgumentException Stating that the name is invalid.
     */
    public void validate(Product t) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        if (!pattern.matcher(t.getName()).matches()) {
            throw new IllegalArgumentException("Name is not valid!");
        }
    }

}
