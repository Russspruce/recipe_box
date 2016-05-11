import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/recipe_box_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteRecipesQuery = "DELETE FROM recipes *;";
      String deleteIngredientsRecipesQuery = "DELETE FROM ingredients_recipes *;";
      String deleteIngredientsQuery = "DELETE FROM ingredients *;";
      String deleteRecipesTagsQuery = "DELETE FROM recipes_tags *;";
      String deleteTagsQuery = "DELETE FROM tags *;";
      con.createQuery(deleteRecipesQuery).executeUpdate();
      con.createQuery(deleteIngredientsRecipesQuery).executeUpdate();
      con.createQuery(deleteIngredientsQuery).executeUpdate();
      con.createQuery(deleteRecipesTagsQuery).executeUpdate();
      con.createQuery(deleteTagsQuery).executeUpdate();

    }
  }

}
