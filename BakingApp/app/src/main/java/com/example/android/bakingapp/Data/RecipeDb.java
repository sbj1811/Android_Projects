package com.example.android.bakingapp.Data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = RecipeDb.VERSION)
public final class RecipeDb {

    public RecipeDb(){
    }

    public static final int VERSION = 4;

    @Table(RecipeColumns.class) public static final String RECIPE = "list";

    @Table(IngredientsColumns.class) public static final String INGREDIENTS = "ingredients";

}
