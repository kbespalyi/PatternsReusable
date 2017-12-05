package patterns;

import java.util.function.Function;
import java.util.stream.Stream;

public class Pipeline {

	public Pipeline() {
		
	}
	
	@SafeVarargs
	static public <T> Function<T, T> apply(Function<? super T, ? extends T>... funcs) {
		return (T t) -> {
			for (Function<? super T, ? extends T> func : funcs) {
				t = func.apply(t);
			}
			return t;
		};
	}

	public static interface FuncStream<T> extends Stream<T> {
		
	}
}
