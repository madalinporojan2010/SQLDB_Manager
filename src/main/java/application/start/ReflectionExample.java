package application.start;

import java.lang.reflect.Field;

public class ReflectionExample {

	public static void retrieveProperties(Object object) {

		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(object);
				System.out.println(field.getName() + " = " + value);

			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}
	}
}
