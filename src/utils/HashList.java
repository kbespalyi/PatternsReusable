package utils;

import java.util.HashMap;

public final class HashList<V> extends HashMap<Integer, V> {
	private static final long serialVersionUID = 010110010101011011L;

    public HashList() {
    	super();
    }
    
	public V findByHash(int hashCode) {
		return super.get(hashCode);
	}
	
	public V addItem(V item) {
		super.put(item.hashCode(), item);
		return item;
	}
}
