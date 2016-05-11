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

}
