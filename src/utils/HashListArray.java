package utils;

import java.util.ArrayList;

public final class HashListArray<E> extends ArrayList<E> {
	private static final long serialVersionUID = 010110010101011012L;

    public HashListArray() {
    	super();
    }
	
	public int findByHash(int hashCode) {
		int result = -1;
		int idx = 0;
		int size = this.size();
		for (; idx < size; idx++) {
			E item = super.get(idx);
			if (item.hashCode() == hashCode) {
				result = super.indexOf(item);
				break;
			}
		}
		
		/* (2)
		for (E item: this) {
			if (item.hashCode() == hashCode) {
				result = super.indexOf(item);
				break;
			}
		}*/
		
		return result;
	}
	
	public E push(E e) {
		super.add(e);
		return super.get(super.size() - 1);
	}
}
