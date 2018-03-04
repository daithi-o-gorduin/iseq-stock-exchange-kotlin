package finalyearproject.drawer.Subject;


import finalyearproject.drawer.Observer.Observer;

/**
 * Created by Dvaid on 24/11/2014.
 */

public interface Subject {
    public void attach(Observer observer);

    public void detach(Observer observer);

    public void notifyObservers();
}
