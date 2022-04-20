package com.pramod.init;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.pramod.internal.Person;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Person person = createObjectsRecursively(Person.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T createObjectsRecursively(Class<T> clazz)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<?> constructor = getFirstConstructor(clazz);

		List<Object> constructorArguments = new ArrayList<>();

		for (Class<?> argumentType : constructor.getParameterTypes()) {
			Object argumentValue = createObjectsRecursively(argumentType);
			constructorArguments.add(argumentValue);
		}

		constructor.setAccessible(true);

		return (T) constructor.newInstance(constructorArguments.toArray());
	}

	public static Constructor<?> getFirstConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		if (constructors.length == 0) {
			throw new IllegalStateException(
					String.format("No constructors have been found for class : %s", clazz.getSimpleName()));
		}

		return constructors[0];
	}

}
