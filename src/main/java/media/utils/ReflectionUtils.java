package media.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtils {

	private static final Set<Class> simpleTypes = new HashSet<Class>() {{
			this.add(boolean.class);
			this.add(byte.class);
			this.add(char.class);
			this.add(short.class);
			this.add(int.class);
			this.add(long.class);
			this.add(float.class);
			this.add(double.class);
			this.add(void.class);
			this.add(Boolean.class);
			this.add(Byte.class);
			this.add(Character.class);
			this.add(Short.class);
			this.add(Integer.class);
			this.add(Long.class);
			this.add(Float.class);
			this.add(Double.class);
			this.add(Void.class);
			this.add(String.class);
	}};
	
	public static boolean isEnum(Field field) {
		return field.getType().isEnum();
	}
	
	public static boolean isTypeSimple(Field field) {
		return simpleTypes.contains(field.getType());
	}
	
	public static boolean isString(Field field) {
		return String.class.equals(field.getDeclaringClass());
	}
	
	public static boolean isCollection(Field field) {
		return Collection.class.isAssignableFrom(field.getType());
	}

}
