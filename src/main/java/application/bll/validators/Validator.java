package application.bll.validators;

/**
 * Generic class used for validation purposes.
 *
 * @param <T> The Class to Validate.
 */
public interface Validator<T> {
    /**
     * @param t Parameter to validate.
     */
    void validate(T t);
}
