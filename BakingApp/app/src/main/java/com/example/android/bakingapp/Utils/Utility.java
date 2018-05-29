package com.example.android.bakingapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.example.android.bakingapp.Data.IngredientsColumns;
import com.example.android.bakingapp.Data.RecipeColumns;
import com.example.android.bakingapp.Data.RecipeDb;
import com.example.android.bakingapp.Models.Ingredient;
import com.example.android.bakingapp.Models.Recipe;

import java.util.List;
import java.util.Locale;

public class Utility {

    private static final String INGREDIENT = "ingredient";

    public static ContentValues[] makeIngredientsList(List<Ingredient> list, Recipe recipe) {
        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Ingredient ingredient = list.get(i);
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put(IngredientsColumns.RECIPE_LIST_ID, recipe.getId());
            ingredientValues.put(IngredientsColumns.INGREDIENT, ingredient.getIngredient());
            ingredientValues.put(IngredientsColumns.MEASUREMENT, ingredient.getMeasure());
            ingredientValues.put(IngredientsColumns.QUANTITY, ingredient.getQuantity());

            result[i] = ingredientValues;
        }

        return result;
    }

    public static boolean checkRecipeExist(Context context, int id){
        SQLiteOpenHelper dbs = com.example.android.bakingapp.Data.generated.RecipeDb.getInstance(context);
        SQLiteDatabase db = dbs.getReadableDatabase();
        Long exist = DatabaseUtils.queryNumEntries(db, RecipeDb.RECIPE, RecipeColumns.RECIPE_ID+ " = " +id,null);
        db.close();

        return exist > 0;
    }

    public static String formatQuantity(double q){
        String quantity = "";
        if (q == (long)q){
            quantity = String.format(Locale.US,"%d",(long)q);
        } else
            quantity = String.format(Locale.US,"%s",q);
        return quantity;
    }

    public static String getIngredientName(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(INGREDIENT, "");
    }

    public static void setIngredientName(Context context, String recipe){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(INGREDIENT,recipe);
        editor.apply();
    }


}
