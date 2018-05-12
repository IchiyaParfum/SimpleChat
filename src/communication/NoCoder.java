package communication;

public class NoCoder implements Coder{

	@Override
	public Object code(Object o, String key) {
		return o;
	}

	@Override
	public Object decode(Object o, String key) {
		return o;
	}
	
}
