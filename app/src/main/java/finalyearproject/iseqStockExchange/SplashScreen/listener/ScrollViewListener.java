package finalyearproject.iseqStockExchange.SplashScreen.listener;


import finalyearproject.iseqStockExchange.SplashScreen.views.ObservableScrollView;

public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX, int oldY);
    
    void onEndScroll(ObservableScrollView scrollView);

}
