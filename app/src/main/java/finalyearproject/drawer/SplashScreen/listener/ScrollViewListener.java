package finalyearproject.drawer.SplashScreen.listener;


import finalyearproject.drawer.SplashScreen.views.ObservableScrollView;

public interface ScrollViewListener {

    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldX, int oldY);
    
    void onEndScroll(ObservableScrollView scrollView);

}
