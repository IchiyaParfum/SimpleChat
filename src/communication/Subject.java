package communication;

import java.util.List;

public interface Subject {
	public void addObserver(Observer observer);
	public boolean removeObserver(Observer observer);
	public List<Observer> getObservers();
}
