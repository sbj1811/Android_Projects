package com.example.android.bakingapp.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public interface RecipeColumns {


        @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";
        @DataType(DataType.Type.INTEGER) @NotNull String RECIPE_ID = "recipe_id";
        @DataType(DataType.Type.TEXT) @NotNull String RECIPE_NAME = "recipe_name";
        @DataType(DataType.Type.INTEGER) @NotNull String SERVING_SIZE = "serving_size";

}
