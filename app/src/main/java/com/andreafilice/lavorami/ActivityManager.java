package com.andreafilice.lavorami;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityManager {
    public static void changeActivity(Context context, Class<?> destinationLayout){
        ///@PARAMETER
        /// Class<?> destinationLayout is a destination activity which this function change.

        //*CHANGE LAYOUT
        Intent layoutChange = new Intent(context, destinationLayout); //*CREATE THE INTENT WITH THE DESTINATION
        context.startActivity(layoutChange); //*CHANGE LAYOUT

        /// In this section, we check if the context is an Activity and then we apply the animation.
        if (context instanceof Activity)
            ((Activity) context).overridePendingTransition(1, 0);
    }
}
