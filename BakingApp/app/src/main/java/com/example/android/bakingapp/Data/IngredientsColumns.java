package com.example.android.bakingapp.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

public interface IngredientsColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    @AutoIncrement
    String ID = "_id";

    @DataType(DataType.Type.INTEGER) @References(
            table = RecipeDb.RECIPE, column = RecipeColumns.RECIPE_ID) String RECIPE_LIST_ID = "recipeListId";

    @DataType(DataType.Type.REAL) String QUANTITY = "quantity";

    @DataType(DataType.Type.TEXT) String MEASUREMENT = "measurement";

    @DataType(DataType.Type.TEXT) String INGREDIENT = "ingredient";

}
