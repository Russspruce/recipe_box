import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class RecipeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Recipe_instantiatesCorrectly() {
    Recipe testRecipe = new Recipe ("Pie", "Bake stuff in a pan", 5);
    assertTrue(testRecipe instanceof Recipe);
  }

  @Test
  public void getRecipeName_returnsRecipeName_String() {
    Recipe testRecipe = new Recipe ("Pie", "Bake stuff in a pan", 5);
    assertEquals("Pie", testRecipe.getRecipeName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Recipe.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueifRecipeNamesAreSame() {
    Recipe firstRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    Recipe secondRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_returnsTrueifRecipeNamessAreSame() {
    Recipe firstRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    firstRecipe.save();
    assertEquals(Recipe.all().get(0), firstRecipe);
  }

  @Test
  public void save_assignsIdToObject_1() {
    Recipe firstRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    firstRecipe.save();
    Recipe savedRecipe = Recipe.all().get(0);
    assertEquals(savedRecipe.getId(), firstRecipe.getId());
  }

  @Test
  public void find_findsRecipeInDatabase_true() {
    Recipe testRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    testRecipe.save();
    Recipe savedRecipe = Recipe.find(testRecipe.getId());
    assertTrue(testRecipe.equals(savedRecipe));
  }

  @Test
  public void update_updatesRecipe_String() {
    Recipe testRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    testRecipe.save();
    testRecipe.update("Cake", "Mix in a bowl and bake in an oven.", 4);
    assertEquals("Cake", Recipe.find(testRecipe.getId()).getRecipeName());
  }

  @Test
  public void delete_deletesRecipeFromDatabase_true() {
    Recipe testRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    testRecipe.save();
    int testRecipeId = testRecipe.getId();
    testRecipe.delete();
    assertEquals(null, Recipe.find(testRecipeId));

  }

  @Test
  public void addTag_addsTagToRecipe_true() {
    Tag myTag = new Tag("Breakfast");
    myTag.save();
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    myRecipe.addTag(myTag);
    Tag savedTag = myRecipe.getTags().get(0);
    assertTrue(myTag.equals(savedTag));

  }

  @Test
  public void addIngredient_addsIngredientToRecipe_true() {
    Ingredient myIngredient = new Ingredient("Grape Jelly");
    myIngredient.save();
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    myRecipe.addIngredient(myIngredient);
    Ingredient savedIngredient = myRecipe.getIngredients().get(0);
    assertTrue(myIngredient.equals(savedIngredient));

  }

  @Test
  public void getTags_returnsAllTags_List() {
    Recipe testRecipe = new Recipe("Pie", "Make pie", 1);
    testRecipe.save();
    Tag testTag = new Tag("Dessert");
    testTag.addRecipe(testRecipe);
    List savedTag = testRecipe.getTags();
    assertEquals(1, savedTag.size());
  }

  @Test
  public void getIngredient_returnsAllIngredients_List() {
    Recipe testRecipe = new Recipe("Pie", "Make pie", 1);
    testRecipe.save();
    Ingredient testIngredient = new Ingredient("Peanut");
    testIngredient.addRecipe(testRecipe);
    List savedIngredient = testRecipe.getIngredients();
    assertEquals(1, savedIngredient.size());
  }

  @Test
  public void delete_deletesAllRecipesAndIngredientsAssociations_nada() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Honey");
    myIngredient.save();
    myRecipe.addIngredient(myIngredient);
    myRecipe.delete();
    assertEquals(0, Recipe.all().size());
    assertEquals(0, myIngredient.getRecipes().size());
  }

  @Test
  public void delete_deletesAllRecipesAndTagsAssociations_nada() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Tag myTag = new Tag("Lunch");
    myTag.save();
    myRecipe.addTag(myTag);
    myRecipe.delete();
    assertEquals(0, Recipe.all().size());
    assertEquals(0, myTag.getRecipes().size());
  }
}
