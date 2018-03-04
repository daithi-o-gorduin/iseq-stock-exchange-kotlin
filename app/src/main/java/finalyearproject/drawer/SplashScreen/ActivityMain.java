package finalyearproject.drawer.SplashScreen;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import finalyearproject.drawer.R;


public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);

        if(savedInstanceState == null) {
            FragmentMain fragmentMain = FragmentMain.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.frame, fragmentMain).commit();
        }
    }

}
