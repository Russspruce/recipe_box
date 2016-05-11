import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class IngredientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Ingredient_instantiatesCorrectly() {
    Ingredient testIngredient = new Ingredient ("Basil");
    assertTrue(testIngredient instanceof Ingredient);
  }

  @Test
  public void getName_returnsName_String() {
    Ingredient testIngredient = new Ingredient ("Basil");
    assertEquals("Basil", testIngredient.getName());
  }
  @Test
  public void all_emptyAtFirst() {
    assertEquals(Ingredient.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueifNamesAreSame() {
    Ingredient firstIngredient = new Ingredient("Basil");
    Ingredient secondIngredient = new Ingredient("Basil");
    assertTrue(firstIngredient.equals(secondIngredient));
  }

  @Test
  public void save_returnsTrueifNamesAreSame() {
    Ingredient firstIngredient = new Ingredient("Basil");
    firstIngredient.save();
    assertEquals(Ingredient.all().get(0), firstIngredient);
  }
  @Test
  public void save_assignsIdToObject_1() {
    Ingredient firstIngredient = new Ingredient("Apple");
    firstIngredient.save();
    Ingredient savedIngredient = Ingredient.all().get(0);
    assertEquals(savedIngredient.getId(), firstIngredient.getId());
  }

  @Test
  public void find_findsIngredientInDatabase_true() {
    Ingredient testIngredient = new Ingredient("Apple");
    testIngredient.save();
    Ingredient savedIngredient = Ingredient.find(testIngredient.getId());
    assertTrue(testIngredient.equals(savedIngredient));
  }

  @Test
  public void update_updatesName_String() {
    Ingredient testIngredient = new Ingredient("Apple");
    testIngredient.save();
    testIngredient.update("Lunch");
    assertEquals("Lunch", Ingredient.find(testIngredient.getId()).getName());
  }

  @Test
  public void delete_deletesIngredientFromDatabase_true() {
    Ingredient testIngredient = new Ingredient("Apple");
    testIngredient.save();
    int testIngredientId = testIngredient.getId();
    testIngredient.delete();
    assertEquals(null, Ingredient.find(testIngredientId));

  }

  @Test
  public void addRecipe_addsRecipeToIngredient_true() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Apple");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    Recipe savedRecipe = myIngredient.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));

  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Ingredient testIngredient = new Ingredient("Apple");
    testIngredient.save();
    Recipe testRecipe = new Recipe("Pie", "Make pie", 1);
    testIngredient.addRecipe(testRecipe);
    List savedRecipe = testIngredient.getRecipes();
    assertEquals(1, savedRecipe.size());
  }

  @Test
  public void delete_deletesAllIngredientsAndRecipesAssociations_nada() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Ingredient myIngredient = new Ingredient("Grape");
    myIngredient.save();
    myIngredient.addRecipe(myRecipe);
    myIngredient.delete();
    assertEquals(0, Ingredient.all().size());
    assertEquals(0, myRecipe.getIngredients().size());
  }
}
