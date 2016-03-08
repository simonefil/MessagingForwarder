package dk.stacktrace.messagingforwarder;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
    private static final String TAG = IncomingMessageReceiver.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating settings activity");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}