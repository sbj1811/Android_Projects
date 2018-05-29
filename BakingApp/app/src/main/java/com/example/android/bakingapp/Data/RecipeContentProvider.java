package com.example.android.bakingapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.NotifyBulkInsert;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = RecipeContentProvider.AUTHORITY, database = RecipeDb.class)

public class RecipeContentProvider {

    public RecipeContentProvider() {
    }

    public static final String AUTHORITY = "com.example.android.bakingapp.Data.RecipeContentProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String RECIPELIST = "list";
        String INGREDIENTS = "ingredients";
        String FROM_LIST = "fromList";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = RecipeDb.RECIPE) public static class RecipeList {

        @ContentUri(
                path = Path.RECIPELIST,
                type = "vnd.android.cursor.dir/list",
                defaultSort = RecipeColumns.RECIPE_ID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.RECIPELIST);

        @InexactContentUri(
                path = Path.RECIPELIST + "/#",
                name = "RECIPE_LIST_ID",
                type ="vnd.android.cursor.item/list",
                whereColumn = RecipeColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return  buildUri(Path.RECIPELIST, String.valueOf(id));
        }

        @InexactContentUri(
                path = Path.RECIPELIST + "/*",
                name = "RECIPE_NAME",
                type = "vnd.android.cursor.item/list",
                whereColumn = RecipeColumns.RECIPE_NAME,
                pathSegment = 1)
        public static Uri withRecipeName(String name){
            return buildUri(Path.RECIPELIST, name);
        }
    }

    @TableEndpoint(table = RecipeDb.INGREDIENTS) public static class RecipeIngredients {

        @ContentUri(
                path = Path.INGREDIENTS,
                type = "vnd.android.cursor.dir/ingredients")
        public static final Uri CONTENT_URI = buildUri(Path.INGREDIENTS);

        @InexactContentUri(
                name = "INGREDIENTS_FROM_LIST",
                path = Path.INGREDIENTS + "/" + Path.FROM_LIST + "/#",
                type = "vnd.android.cursor.dir/list",
                whereColumn = IngredientsColumns.RECIPE_LIST_ID,
                pathSegment = 2)
        public static Uri fromList(int recipeId) {
            return buildUri(Path.INGREDIENTS, Path.FROM_LIST, String.valueOf(recipeId));
        }

        @NotifyInsert(paths = Path.INGREDIENTS)
        public static Uri[] onInsert(ContentValues contentValues) {
            final int recipeId = contentValues.getAsInteger(IngredientsColumns.RECIPE_LIST_ID);
            return new Uri[]{
                    RecipeList.withId(recipeId), fromList(recipeId)
            };
        }

        @NotifyBulkInsert(paths = Path.INGREDIENTS)
        public static Uri[] onBulkInsert(Context context, Uri uri, ContentValues[] values, long[] ids) {
            return new Uri[]{
                    uri,
            };
        }
    }

}
