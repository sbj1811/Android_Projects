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

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e(TAG, "updateAppWidget: HERE");
        CharSequence widgetText = context.getString(R.string.app_name);

        String recipe = Utility.getIngredientName(context);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        Log.d("RecipeWidgetProvider", "updateAppWidget: RECIPE: "+recipe);
        if(recipe.equals("")) {
            views.setTextViewText(R.id.widget_title, widgetText);
        } else {
            views.setTextViewText(R.id.widget_title, recipe);
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_appBar,pendingIntent);

        setRemoteAdapter(context, views);

        boolean enableDetails = context.getResources().getBoolean(R.bool.widget_details);
        Intent clickIntent;
        if(enableDetails){
            clickIntent = new Intent(context, IngredientActivity.class);
        } else {
            clickIntent = new Intent(context, MainActivity.class);
        }
        PendingIntent clickPendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            clickPendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntent);
        views.setEmptyView(R.id.widget_list,R.id.widget_empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e(TAG, "onUpdate: HERE: "+appWidgetIds.length);
        for (int appWidgetId : appWidgetIds) {
            Log.e(TAG, "onUpdate: "+appWidgetId);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
   //     Log.e(TAG, "onReceive: HERE: ACTION");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appwidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        Log.e(TAG, "onReceive: LENGTH appwidgetIds: "+appwidgetIds.length);
        appWidgetManager.notifyAppWidgetViewDataChanged(appwidgetIds,R.id.widget_list);
        onUpdate(context,appWidgetManager,appwidgetIds);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.e(TAG, "onAppWidgetOptionsChanged: HERE");
        context.startService(new Intent(context,RecipeWidgetService.class));
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views){
        Log.e(TAG, "setRemoteAdapter: HERE");
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, RecipeWidgetService.class));
    }

    public static void setWidgetText(Context context, String name){
        Log.e(TAG, "setWidgetText: HERE");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        views.setTextViewText(R.id.widget_title,name);

    }

}

