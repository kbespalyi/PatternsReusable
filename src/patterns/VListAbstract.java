package patterns;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.function.IntFunction;

import utils.*;

public class VListAbstract {

	public static interface IList<E> {
		public static int DEFAULT_LIST_CAPACITY = 16;

		public int count();

		public E get(int index);

		public int add(E item);

		public void garbige();

		public void remove(int index);

		public void removeAll();

		public E[] copy(int newLength);

		public E[] copy();
	}

	public static abstract class Iterator<E> {

		protected Iterator() {

		}

		public abstract void first();

		public abstract void next();

		public abstract void previous();

		public abstract void last();

		public abstract boolean isDone();

		public abstract E currentItem();
	}

	public static abstract class VList<E> extends Iterator<E> implements IList<E> {

		private E[] _list;
		private Class<E> _class;
		private int _current = 0;
		private int _size = 0;

		public VList() {
			_class = getType();
			//_list = new AbstractList<E>(getType()) {};
			_list = newArray(IList.DEFAULT_LIST_CAPACITY);
		}

		public VList(int size) {
			_class = getType();
			//_list = new AbstractList<E>(getType(), size) {};
			_size = size;
			int capacity = (size / DEFAULT_LIST_CAPACITY + 1) * DEFAULT_LIST_CAPACITY;			
			_list = newArray(capacity);
		}

		public VList(E[] aslist) {
			_list = aslist;
		}

		@SuppressWarnings({ "unchecked", "unused" })
		private E[] newArray(String className, int size) {
			E[] newArray = null;
			
			if (className == null) return newArray;
			
			try {
				newArray = (E[]) Array.newInstance(Class.forName(className), size);
			} catch (NegativeArraySizeException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return newArray;
		}

		@SuppressWarnings("unchecked")
		private E[] newArray(int size) {
			return (E[]) Array.newInstance(_class, size);
		}

		@Override
		public void first() {
			_current = 0;
		}

		@Override
		public void next() {
			_current++;
		}

		@Override
		public void previous() {
			_current--;
		}

		@Override
		public void last() {
			_current = count() - 1;
		}

		@Override
		public E currentItem() throws IndexOutOfBoundsException {
			if (isDone()) {
				throw new IndexOutOfBoundsException();
			}
			return get(_current);
		}

		@Override
		public boolean isDone() {
			return _current >= count() || _current < 0;
		}

		@Override
		public int count() {
			return _size;
		}

		@Override
		public E get(int index) {
			final E item = (E) (index < _list.length ? _list[index] : null);
			return item;
		}

		@Override
		public int add(E item) {
			int diff = _list.length - (_size + 1);
			if (diff <= 0) {
				int capacity = (_size / DEFAULT_LIST_CAPACITY + 2) * DEFAULT_LIST_CAPACITY;
				_list = this.copy(capacity);
			}
			_list[_size] = item;
			return ++_size;
		}

		@Override
		public void remove(int index) {
			if (index < _size && index >= 0) {
				_list[index] = null;
				_size--;
				if (_size > 0 && _list.length / _size >= DEFAULT_LIST_CAPACITY ) {
					_list = Arrays.stream(_list).filter(item -> item != null).toArray(new IntFunction<E[]>() {
						@Override
						public E[] apply(int value) {
							return newArray(value);
						}});

					if (_list.length < _size) {
						int capacity = (_size / DEFAULT_LIST_CAPACITY + 2) * DEFAULT_LIST_CAPACITY;
						_list = this.copy(capacity);
					}
					
				}
			}
			if (_size < 0) {
				removeAll();
			}
		}

		@Override
		public void removeAll() {
			_size = 0;
			_list = null;
			java.lang.Runtime.getRuntime().gc();			
			_list = newArray(IList.DEFAULT_LIST_CAPACITY);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void garbige() {
			if (_size > 0) {
				E[] __list = (E[]) Arrays.stream(_list).filter(item -> item != null).toArray();
				_list = null;
				java.lang.Runtime.getRuntime().gc();			
				_list = __list;
			}

			int capacity = (_size / DEFAULT_LIST_CAPACITY + 2) * DEFAULT_LIST_CAPACITY;
			_list = this.copy(capacity);
		}

		@Override
		public E[] copy(int newLength) {
			return Arrays.copyOf(_list, newLength);
		}

		@Override
		public E[] copy() {
			return Arrays.copyOf(_list, _size);
		}

		public static <E> void printList(VList<E> _list) {
			int index = 0;
			if (_list.count() > 0) {
				for (_list.first(); !_list.isDone(); _list.next()) {
					E item = _list.currentItem();
					if (item != null) {
						System.out.println(String.format("[%d]: %s", index, item.toString()));
					} else {
						System.out.println(String.format("[%d]: %s", index, "NULL"));
					}
					index++;
				}
			}
		}

		public void printList() {
			if (count() > 0) {

				System.out.println("List of " + count() + " items:");

				CollectionUtils.mapWithIndex(Arrays.stream(copy()), (index, item) -> {
					System.out.println(String.format("[%i]: %s", index, item.toString()));
					return item;
				}).close();

				/*
				 * int index = 0; Arrays .stream(copy())
				 * .collect(Collectors.toList()) // Create a map of the index
				 * next to the object .forEach((index, item) -> { if (item !=
				 * null) { System.out.println(String.format("[%d]: %s", index,
				 * item.toString())); } else { System.out.println(String.format(
				 * "[%d]: %s", index, "NULL")); } });
				 */
			}
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
	    
		private Class<E> getType() {
	    	if (_class == null) {
		    	try {
		    		_class = getClazz();
				} catch (Exception e) {
					System.out.println("Error in getting the class = " + e.getMessage());
				}
	    	}
	    	return _class;
	    }
		
	}
}
