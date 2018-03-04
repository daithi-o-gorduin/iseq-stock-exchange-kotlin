package finalyearproject.iseqStockExchange.SharedPreferences;

import android.content.Context;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * Created by Dvaid on 15/01/2015.
 */
public class SharedPref {
    private SharedPreferences sharedPreferences;
    private Context mContext;

    public SharedPref(Context context) {
        this.mContext = context;
    }

    public  ArrayList<Integer> loadFavSavedPreferences(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String savedString = sharedPreferences.getString(key, "");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        int count = st.countTokens();
        ArrayList<Integer> preferences = new ArrayList<Integer>();
        for (int i = 0; i < count; i++) {
             preferences.add(Integer.parseInt(st.nextToken()));
        }
        return preferences;
    }


    public void saveFavPreferences(ArrayList<Integer> preferences, String key) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Editor editor = sharedPreferences.edit();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < preferences.size(); i++) {
            str.append(preferences.get(i)).append(",");
        }
        editor.putString(key, str.toString());
        editor.commit();
    }


    public  ArrayList<Double> loadChartValueFavSavedPreferences(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String savedString = sharedPreferences.getString(key, "");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        int count = st.countTokens();
        ArrayList<Double> preferences = new ArrayList<Double>();
        for (int i = 0; i < count; i++) {
             preferences.add(Double.parseDouble(st.nextToken()));
        }
        return preferences;
    }


    public void saveChartValuePreferences(ArrayList<Double> preferences, String key) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        Editor editor = sharedPreferences.edit();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < preferences.size(); i++) {
            str.append(preferences.get(i)).append(",");
        }
        editor.putString(key, str.toString());
        editor.commit();

    }




}