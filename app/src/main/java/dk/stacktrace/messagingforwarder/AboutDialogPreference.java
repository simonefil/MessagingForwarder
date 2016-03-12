package dk.stacktrace.messagingforwarder;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class AboutDialogPreference extends DialogPreference {
    public AboutDialogPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setNegativeButtonText(null);
    }
}
