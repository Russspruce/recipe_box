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
  public void equals_returnsTrueifNamesAreSame() {
    Recipe firstRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    Recipe secondRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    assertTrue(firstRecipe.equals(secondRecipe));
  }

  @Test
  public void save_returnsTrueifNamessAreSame() {
    Recipe firstRecipe = new Recipe("Pie", "Bake stuff in a pan", 5);
    firstRecipe.save();
    assertEquals(Recipe.all().get(0), firstRecipe);
  }

}
