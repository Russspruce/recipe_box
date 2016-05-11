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

}
