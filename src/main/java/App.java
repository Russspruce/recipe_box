import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/add", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("tags", Tag.all());
      model.put("template", "templates/recipe-add.vtl");

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/recipes", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String recipe_name = request.queryParams("recipe_name");
      String instructions = request.queryParams("instructions");
      int rating = Integer.parseInt(request.queryParams("rating"));
      Recipe newRecipe = new Recipe(recipe_name, instructions, rating);
      newRecipe.save();

      String ingredientOne = request.queryParams("ingredientOne");
      String ingredientTwo = request.queryParams("ingredientTwo");
      String ingredientThree = request.queryParams("ingredientThree");
      Ingredient newIngredient1 = new Ingredient(ingredientOne);
      newIngredient1.save();
      newRecipe.addIngredient(newIngredient1);
      Ingredient newIngredient2 = new Ingredient(ingredientTwo);
      newIngredient2.save();
      newRecipe.addIngredient(newIngredient2);
      Ingredient newIngredient3 = new Ingredient(ingredientThree);
      newIngredient3.save();
      newRecipe.addIngredient(newIngredient3);
      Tag tag = Tag.find(Integer.parseInt(request.queryParams("category")));
      newRecipe.addTag(tag);
      model.put("recipes", Recipe.all());
      model.put("template", "templates/recipes.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/recipes/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("id")));
      model.put("recipe", recipe);
      model.put("ingredients", recipe.getIngredients());
      model.put("categories", recipe.getTags());
      model.put("template", "templates/recipe-info.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("recipes/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Recipe recipe = Recipe.find(Integer.parseInt(request.params("id")));
      recipe.delete();
      response.redirect("/recipes");
      return null;
    });

  }
}
