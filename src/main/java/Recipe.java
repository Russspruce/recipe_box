import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Recipe {

  private int id;
  private String recipe_name;
  private String instructions;
  private int rating;

  public Recipe(String recipe_name, String instructions, int rating) {
    this.id = id;
    this.recipe_name = recipe_name;
    this.instructions = instructions;
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public String getRecipeName() {
    return recipe_name;
  }

  public String getInstructions() {
    return instructions;
  }

  public int getRating() {
    return rating;
  }

  public static List<Recipe> all() {
    String sql = "SELECT * FROM recipes;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Recipe.class);
    }
  }

  @Override
  public boolean equals(Object otherRecipe) {
    if (!(otherRecipe instanceof Recipe)) {
      return false;
    } else {
      Recipe newRecipe = (Recipe) otherRecipe;
      return this.getId() == newRecipe.getId() &&
            this.getRecipeName().equals(newRecipe.getRecipeName()) &&
            this.getInstructions().equals(newRecipe.getInstructions()) &&
            this.getRating() == newRecipe.getRating();
    }
  }

  public void save() {
  try(Connection con = DB.sql2o.open()) {
    String sql = "INSERT INTO recipes(recipe_name, instructions, rating) VALUES (:recipe_name, :instructions, :rating);";
    this.id = (int) con.createQuery(sql, true)
      .addParameter("recipe_name", this.recipe_name)
      .addParameter("instructions", this.instructions)
      .addParameter("rating", this.rating)
      .executeUpdate()
      .getKey();
    }
  }

  public static Recipe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM recipes WHERE id=:id;";
      Recipe recipe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Recipe.class);
      return recipe;
    }
  }

  public void update(String recipe_name, String instructions, int rating) {
    this.recipe_name = recipe_name;
    this.instructions = instructions;
    this.rating = rating;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE recipes SET recipe_name = :recipe_name, instructions = :instructions, rating = :rating WHERE id = :id";
      con.createQuery(sql)
        .addParameter("recipe_name", recipe_name)
        .addParameter("instructions", instructions)
        .addParameter("rating", rating)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM recipes WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM ingredients_recipes WHERE recipe_id = :recipe_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("recipe_id", this.getId())
          .executeUpdate();

      String joinDeleteRecipeTag = "DELETE FROM recipes_tags WHERE recipe_id = :recipe_id";
        con.createQuery(joinDeleteRecipeTag)
          .addParameter("recipe_id", this.getId())
          .executeUpdate();
    }
  }

  public void addTag(Tag tag) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO recipes_tags (recipe_id, tag_id) VALUES (:recipe_id, :tag_id)";
        con.createQuery(sql)
          .addParameter("recipe_id", this.id)
          .addParameter("tag_id", tag.getId())
          .executeUpdate();
    }
  }

  public void addIngredient(Ingredient ingredient) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO ingredients_recipes (recipe_id, ingredient_id) VALUES (:recipe_id, :ingredient_id)";
        con.createQuery(sql)
          .addParameter("recipe_id", this.id)
          .addParameter("ingredient_id", ingredient.getId())
          .executeUpdate();
    }
  }

  public List<Ingredient> getIngredients() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT ingredient_id FROM ingredients_recipes WHERE recipe_id = :recipe_id;";
      List<Integer> ingredient_ids = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Ingredient> ingredients = new ArrayList<Ingredient>();

      for (Integer ingredient_id : ingredient_ids) {
        String ingredientQuery = "SELECT * FROM ingredients WHERE id = :ingredient_id;";
        Ingredient ingredient = con.createQuery(ingredientQuery)
          .addParameter("ingredient_id", ingredient_id)
          .executeAndFetchFirst(Ingredient.class);
        ingredients.add(ingredient);
      }
      return ingredients;
    }
  }

  public List<Tag> getTags() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT tag_id FROM recipes_tags WHERE recipe_id = :recipe_id";
      List<Integer> tag_ids = con.createQuery(joinQuery)
        .addParameter("recipe_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Tag> tags = new ArrayList<Tag>();

      for (Integer tag_id : tag_ids) {
        String tagQuery = "SELECT * FROM tags WHERE id = :tag_id";
        Tag tag = con.createQuery(tagQuery)
          .addParameter("tag_id", tag_id)
          .executeAndFetchFirst(Tag.class);
        tags.add(tag);
      }
      return tags;

    }
  }
}
