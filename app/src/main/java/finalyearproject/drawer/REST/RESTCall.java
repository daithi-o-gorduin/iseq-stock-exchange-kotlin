package finalyearproject.drawer.REST;

import android.content.Context;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutionException;

import finalyearproject.drawer.POJO.ResultWrapper;

/**
 * Created by Dvaid on 19/01/2015.
 */
public interface RESTCall {
    public ResultWrapper doTask(Context context);
}
