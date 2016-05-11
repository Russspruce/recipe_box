import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Tag{
  private String category;
  private int id;

  public Tag(String category) {
    this.id = id;
    this.category = category;
  }

  public String getCategory() {
    return category;
  }

  public int getId() {
    return id;
  }

  public static List<Tag> all() {
    String sql = "SELECT * FROM tags";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Tag.class);
    }
  }

  @Override
  public boolean equals(Object otherTag) {
    if (!(otherTag instanceof Tag)) {
      return false;
    }  else {
      Tag newTag = (Tag) otherTag;
      return this.getId() == newTag.getId() && this.getCategory().equals(newTag.getCategory());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tags (category) VALUES (:category);";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("category", this.category)
        .executeUpdate()
        .getKey();
    }
  }

  public static Tag find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tags WHERE id = :id;";
      Tag tag = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Tag.class);
      return tag;
    }
  }

  public void update(String category) {
    this.category = category;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tags SET category = :category WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("category", category)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
  try(Connection con = DB.sql2o.open()) {
    String deleteQuery = "DELETE FROM tags WHERE id = :id;";
      con.createQuery(deleteQuery)
        .addParameter("id", this.getId())
        .executeUpdate();

    String joinDeleteQuery = "DELETE FROM recipes_tags WHERE tag_id = :tag_id;";
      con.createQuery(joinDeleteQuery)
        .addParameter("tag_id", this.getId())
        .executeUpdate();
    }
  }

  public void addRecipe(Recipe recipe) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id);";
      con.createQuery(sql)
        .addParameter("recipe_id", recipe.getId())
        .addParameter("tag_id", this.id)
        .executeUpdate();
    }
  }

  public List<Recipe> getRecipes() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT recipe_id FROM recipes_tags WHERE tag_id = :tag_id";
      List<Integer> recipe_ids = con.createQuery(joinQuery)
        .addParameter("tag_id", this.id)
        .executeAndFetch(Integer.class);

      List<Recipe> recipes = new ArrayList<Recipe>();

      for(Integer recipe_id : recipe_ids) {
        String recipeQuery = "SELECT * FROM recipes where id = :recipe_id";
        Recipe recipe = con.createQuery(recipeQuery)
          .addParameter("recipe_id", recipe_id)
          .executeAndFetchFirst(Recipe.class);
        recipes.add(recipe);
      }
      return recipes;
    }
  }
}
