package communication;

public interface Coder {
	public Object code(Object o, String key);
	public Object decode(Object o, String key);
}
