package utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Generic<E> {

	private Class<E> clazz;
	
	public Generic() {
		clazz = getType();
	}

	public Generic(Class<E> clazz) {
		this.clazz = clazz;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Class<E> getClazz() throws Exception {
    	Class<E> result = null;
    	ParameterizedType superclass = null;
    	
        try {
        	superclass = (ParameterizedType) getClass().getGenericSuperclass();
	        result = (Class<E>) superclass.getActualTypeArguments()[0];
        } catch (ClassCastException e) {
	        Class obtainedClass = getClass();
	        Type genericSuperclass = null;
	        for(;;) {
	            genericSuperclass = obtainedClass.getGenericSuperclass();
	            if(genericSuperclass instanceof ParameterizedType) {
	                break;
	            }
	            obtainedClass = obtainedClass.getSuperclass();
	        }
	        superclass = (ParameterizedType) genericSuperclass;
	        result = ((Class) ((Class) superclass.getActualTypeArguments()[0]));
        }

        return result;
    }
    
    public Class<E> getType() {
    	if (clazz == null) {
	    	try {
	    		clazz = getClazz();
			} catch (Exception e) {
				System.out.println("Error to get class = " + e.getMessage());
			}
    	}
    	return clazz;
    }
    
	public E[] getArray(int size) {
	    @SuppressWarnings("unchecked")
	    E[] arr = (E[]) Array.newInstance(clazz, size);
	    return arr;
	}

	public static <E> E[] getArray(Class<E> cls, int size) {
	    @SuppressWarnings("unchecked")
	    E[] arr = (E[]) Array.newInstance(cls, size);
	    return arr;
	}	
	
	public E newInstance() throws InstantiationException, IllegalAccessException {
	    try {
	        return clazz.getConstructor((Class[])null).newInstance(null);
	    } catch (Exception e) {
	        return null;
	    }
	}

	// type(C, A1,...,An) => C<A1,...,An>
	public static ParameterizedType type(final Class<?> raw, final Type... args)
	{
	    return new ParameterizedType()
	    {
	        public Type getRawType(){ return raw; }

	        public Type[] getActualTypeArguments(){ return args; }

	        public Type getOwnerType(){ return null; }
	    };
	}
	
	public void printMethods(Class<E> classRef) {
		Method[] methods = classRef.getMethods();

		for (Method method : methods) {
			System.out.println("method = " + method.getName());
		}
	}

	public void printFields(Class<E> classRef) {
		Field[] fields = classRef.getFields();

		for (Field field : fields) {
			System.out.println("field = " + field.getName());
		}
	}

	public void printEnumConstants(Class<E> classRef) {
		E[] fields = classRef.getEnumConstants();

		for (E field : fields) {
			System.out.println("const = " + field.toString());
		}
	}

	public void printAnnotations(Class<E> classRef) {
		Annotation[] annotations = classRef.getAnnotations();

		for (Annotation annotation : annotations) {
			System.out.println("annotation = "
					+ annotation.annotationType().getName());
		}
	}

	public void printInterfaces(Class<E> classRef) {
		Class<?>[] interfaces = classRef.getClass().getInterfaces();

		for (Class<?> intf : interfaces) {
			System.out.println("interface = " + intf.getName());
		}
	}

	public void printGenerics(Class<E> classRef) {
		System.out.println("Class name = " + classRef.getName());
		System.out.println("Component Type = " + classRef.getComponentType());
		System.out.println("Type Parameters = " + classRef.getSigners());
		System.out.println("Annotations = " + classRef.getAnnotations());

	}

	public void printClassName(Class<E> classRef) {
		System.out.println("Class Name = " + classRef.getName());
		System.out.println("Canonical Name = " + classRef.getCanonicalName());
		System.out.println("Simple Name = " + classRef.getSimpleName());
		System.out.println("Generic Superclass = "
				+ classRef.getGenericSuperclass());
		System.out.println("Generic Interfaces = "
				+ classRef.getGenericInterfaces().length);
		System.out.println("Annotations = " + classRef.getAnnotations().length);
	}
	
	public void printGenericMethods(Class<E> classRef, String methodName, Class<?> param) throws NoSuchMethodException, SecurityException {
		Method method = classRef.getMethod(methodName, param);
		Type returnType = method.getGenericReturnType();

		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			for (Type typeArgument : typeArguments) {
				@SuppressWarnings("unchecked")
				Class<E> typeArgClass = (Class<E>) typeArgument;
				System.out.println("typeArgClass = " + typeArgClass);
			}
		}
	}
	
	public void printGenericField(Class<E> classRef, String fieldName) throws NoSuchFieldException, SecurityException {
		Field field = classRef.getField(fieldName);
		Type genericFieldType = field.getGenericType();

		if (genericFieldType instanceof ParameterizedType) {
		    ParameterizedType aType = (ParameterizedType) genericFieldType;
		    Type[] fieldArgTypes = aType.getActualTypeArguments();
		    for(Type fieldArgType : fieldArgTypes){
		        @SuppressWarnings("unchecked")
				Class<E> fieldArgClass = (Class<E>) fieldArgType;
		        System.out.println("fieldArgClass = " + fieldArgClass);
		    }
		}
	}
	
}
