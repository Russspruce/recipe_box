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

  @Test
  public void save_assignsIdToObject_1() {
    Tag firstTag = new Tag("Breakfast");
    firstTag.save();
    Tag savedTag = Tag.all().get(0);
    assertEquals(savedTag.getId(), firstTag.getId());
  }

  @Test
  public void find_findsTagInDatabase_true() {
    Tag testTag = new Tag("Breakfast");
    testTag.save();
    Tag savedTag = Tag.find(testTag.getId());
    assertTrue(testTag.equals(savedTag));
  }

  @Test
  public void update_updatesCategory_String() {
    Tag testTag = new Tag("Breakfast");
    testTag.save();
    testTag.update("Lunch");
    assertEquals("Lunch", Tag.find(testTag.getId()).getCategory());
  }

  @Test
  public void delete_deletesTagFromDatabase_true() {
    Tag testTag = new Tag("Breakfast");
    testTag.save();
    int testTagId = testTag.getId();
    testTag.delete();
    assertEquals(null, Tag.find(testTagId));

  }

  @Test
  public void addRecipe_addsRecipeToTag_true() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Tag myTag = new Tag("Breakfast");
    myTag.save();
    myTag.addRecipe(myRecipe);
    Recipe savedRecipe = myTag.getRecipes().get(0);
    assertTrue(myRecipe.equals(savedRecipe));

  }

  @Test
  public void getRecipes_returnsAllRecipes_List() {
    Tag testTag = new Tag("Breakfast");
    testTag.save();
    Recipe testRecipe = new Recipe("Pie", "Make pie", 1);
    testTag.addRecipe(testRecipe);
    List savedRecipe = testTag.getRecipes();
    assertEquals(1, savedRecipe.size());
  }

  @Test
  public void delete_deletesAllTagsAndRecipesAssociations() {
    Recipe myRecipe = new Recipe("Pie", "Make pie", 1);
    myRecipe.save();
    Tag myTag = new Tag("Lunch");
    myTag.save();
    myTag.addRecipe(myRecipe);
    myTag.delete();
    assertEquals(0, Tag.all().size());
    assertEquals(0, myRecipe.getTags().size());
  }
}
