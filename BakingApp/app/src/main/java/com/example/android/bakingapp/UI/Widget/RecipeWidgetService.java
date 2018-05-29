package com.example.android.bakingapp.UI.Widget;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.Data.IngredientsColumns;
import com.example.android.bakingapp.Data.RecipeContentProvider;
import com.example.android.bakingapp.Data.RecipeDb;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.Utils.Utility;
import com.google.android.exoplayer2.C;

/**
 * Created by sjani on 5/25/2018.
 */

public class RecipeWidgetService extends RemoteViewsService {

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

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory(){

            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if(data != null){
                    data.close();
                }
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(RecipeContentProvider.RecipeIngredients.CONTENT_URI,
                        INGREDIENTS_COLUMNS,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null){
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

                if(position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_list_item);
                String name = data.getString(INDEX_INGREDIENT_INGREDIENT);
                String measure = data.getString(INDEX_INGREDIENT_MEASUREMENT);
                double q = data.getDouble(INDEX_INGREDIENT_QUANTITY);
                Log.e("RecipeWidgetService", "getViewAt: NAME: "+name+" QUANTITY: "+q+" "+measure);
                String quantity = Utility.formatQuantity(q);
                String sb = position+") "+name+" ( "+quantity+" "+measure+" )";
                remoteViews.setTextViewText(R.id.widget_ingredient_name,sb);
                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(),R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int i) {
                if (data.moveToPosition(i))
                    return data.getLong(INDEX_INGREDIENT_ID);
                return i;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RecipeWidgetProvider.setWidgetText(this, Utility.getIngredientName(this));
    }
}
