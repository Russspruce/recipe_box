import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class TagTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Tag_instantiatesCorrectly() {
    Tag testTag = new Tag ("Breakfast");
    assertTrue(testTag instanceof Tag);
  }

  @Test
  public void getCategory_returnsCategory_String() {
    Tag testTag = new Tag ("Breakfast");
    assertEquals("Breakfast", testTag.getCategory());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Tag.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueifCategoriesAreSame() {
    Tag firstTag = new Tag("Breakfast");
    Tag secondTag = new Tag("Breakfast");
    assertTrue(firstTag.equals(secondTag));
  }

  @Test
  public void save_returnsTrueifCategoriesAreSame() {
    Tag firstTag = new Tag("Breakfast");
    firstTag.save();
    assertEquals(Tag.all().get(0), firstTag);
  }

}
