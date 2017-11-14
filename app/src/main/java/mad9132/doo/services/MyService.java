package mad9132.doo.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import java.io.IOException;

import mad9132.doo.model.BuildingPOJO;
import mad9132.doo.utils.HttpHelper;
import mad9132.doo.utils.RequestPackage;

/**
 * Class MyService.
 *
 * Fetch the data at URI.
 * Return an array of Building[] as a broadcast message.
 *
 * @author David Gasner
 */
public class MyService extends IntentService {

    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    public static final String MY_SERVICE_EXCEPTION = "myServiceException";
    // TODO # 2 - Intents calling this IntentService send a REQUEST_PACKAGE
    public static final String REQUEST_PACKAGE = "requestPackage";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO #4 - get requestPackage from intent
        RequestPackage requestPackage = (RequestPackage) intent.getParcelableExtra(REQUEST_PACKAGE);

        String response;
        try {
            // TODO #6 - pass the requestPackage to downloadUrl( )
            response = HttpHelper.downloadUrl(requestPackage);
        } catch (IOException e) {
            e.printStackTrace();
            Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
            messageIntent.putExtra(MY_SERVICE_EXCEPTION, e.getMessage());
            LocalBroadcastManager manager =
                    LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
            return;
        }

        Gson gson = new Gson();
        BuildingPOJO[] buildingsArray = gson.fromJson(response, BuildingPOJO[].class);

        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, buildingsArray);
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }
}
