package com.example.android.bakingapp.UI.Widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeIngredient.IngredientActivity;
import com.example.android.bakingapp.UI.RecipeList.MainActivity;
import com.example.android.bakingapp.Utils.Utility;

import io.reactivex.annotations.NonNull;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String TAG = RecipeWidgetProvider.class.getSimpleName();

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,String recipeName, String ingredients){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,recipeName,ingredients);
        }
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String recipeName, String ingredients) {
        CharSequence widgetText = context.getString(R.string.app_name);


        Log.d(TAG, "updateAppWidget: RECIPE: "+recipeName+"\n"+ingredients);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if(recipeName.equals("")) {
            views.setTextViewText(R.id.widget_title, widgetText);
            views.setTextViewText(R.id.widget_ingredient, "No Ingredients to display");
        } else {
            views.setTextViewText(R.id.widget_title, recipeName);
            views.setTextViewText(R.id.widget_ingredient, ingredients);
        }
        views.setOnClickPendingIntent(R.id.widget_appBar,pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        RecipeWidgetService.startActionUpdateWidgets(context);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        RecipeWidgetService.startActionUpdateWidgets(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.e(TAG, "onAppWidgetOptionsChanged: HERE");
        context.startService(new Intent(context,RecipeWidgetService.class));
    }


}

