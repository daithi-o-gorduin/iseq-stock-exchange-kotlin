package finalyearproject.drawer.EventBus;

import java.util.ArrayList;

/**
 * Created by Dvaid on 16/02/2015.
 */
public class FavouritesEvent {


    ArrayList<Integer> favourites = new ArrayList<Integer>();
    public FavouritesEvent(ArrayList<Integer> favourites){
        this.favourites = favourites;
    }


    public ArrayList<Integer> getFavourites(){
        return favourites;
    }
}
