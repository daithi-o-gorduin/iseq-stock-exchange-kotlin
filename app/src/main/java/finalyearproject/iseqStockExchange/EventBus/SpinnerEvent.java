package finalyearproject.iseqStockExchange.EventBus;

/**
 * Created by Dvaid on 19/03/2015.
 */
public class SpinnerEvent {
    String mActiveQuoteText;
    int mPosition;

    public SpinnerEvent(String activeQuoteText, int position){
        this.mActiveQuoteText = activeQuoteText;
        this.mPosition = position;
    }

    public String getActiveQuoteText(){
        return mActiveQuoteText;
    }

    public int getPosition(){
        return mPosition;
    }

}
