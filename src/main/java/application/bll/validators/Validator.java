package application.bll.validators;

public interface Validator<T> {
    public void validate(T t);
}
