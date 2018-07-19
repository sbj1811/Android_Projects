package com.example.android.bakingapp.UI.Widget;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Data.IngredientsColumns;
import com.example.android.bakingapp.Data.RecipeContentProvider;
import com.example.android.bakingapp.Data.RecipeDb;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.UI.RecipeIngredient.IngredientActivity;
import com.example.android.bakingapp.Utils.Utility;
import com.google.android.exoplayer2.C;

/**
 * Created by sjani on 5/25/2018.
 */

public class RecipeWidgetService extends IntentService {

    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    private static final String TAG = RecipeWidgetService.class.getSimpleName();
    public static final String UPDATE_WIDGET = "update_widget";
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";

    private static final String[] INGREDIENTS_COLUMNS = {
            IngredientsColumns.ID,
            IngredientsColumns.INGREDIENT,
            IngredientsColumns.MEASUREMENT,
            IngredientsColumns.QUANTITY,
            RecipeDb.INGREDIENTS + "." + IngredientsColumns.RECIPE_LIST_ID
    };

    private static final int INDEX_INGREDIENT_ID = 0;
    private static final int INDEX_INGREDIENT_INGREDIENT = 1;
    private static final int INDEX_INGREDIENT_MEASUREMENT = 2;
    private static final int INDEX_INGREDIENT_QUANTITY = 3;
    private static final int INDEX_INGREDIENT_RECIPE_ID = 4;

    private Cursor data;
    String recipeName = "";

    public static void startActionUpdateWidgets(Context context) {
        Intent intent = new Intent(context, RecipeWidgetService.class);
        intent.setAction(UPDATE_WIDGET);
       // context.startService(intent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bitmap largeIcon = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Added to Favorite")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(largeIcon);

        Notification notification = notificationBuilder.build();
        notificationManager.notify(111,notification);
        startForeground(1,notification);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent == null) return;
        if (intent.getAction().equals(UPDATE_WIDGET)) {
            Context context = getApplicationContext();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
            data = getContentResolver().query(RecipeContentProvider.RecipeIngredients.CONTENT_URI,
                    INGREDIENTS_COLUMNS,
                    null,
                    null,
                    null);


            StringBuilder sb = new StringBuilder();
            if(data != null) {
                int position = 1;
                sb.append("Ingredients:");
                recipeName = Utility.getIngredientName(context);
                while (data.moveToNext()){
                    String name = data.getString(INDEX_INGREDIENT_INGREDIENT);
                    String measure = data.getString(INDEX_INGREDIENT_MEASUREMENT);
                    double q = data.getDouble(INDEX_INGREDIENT_QUANTITY);
                    Log.e("RecipeWidgetService", "getViewAt: NAME: "+name+" QUANTITY: "+q+" "+measure);
                    String quantity = Utility.formatQuantity(q);
                    sb.append("\n");
                    sb.append(position+") "+name+" ( "+quantity+" "+measure.toLowerCase()+" )");
                    position++;
                }
                RecipeWidgetProvider.updateAppWidgets(context,appWidgetManager,appWidgetIds,recipeName,sb.toString());
            } else {
                Log.e(TAG, "onHandleIntent: Data NULL");
                return;
            }

        }

    }
}
