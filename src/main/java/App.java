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

    post("/recipes/info/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String recipe_name = request.queryParams("recipe_name");
      String instructions = request.queryParams("instructions");
      int rating = Integer.parseInt(request.queryParams("rating"));
      Recipe newRecipe = new Recipe(recipe_name, instructions, rating);


      String category1 = request.queryParams("category1");
      String category2 = request.queryParams("category2");
      ArrayList<String> categories = new ArrayList<String>();
      categories.add(category1);
      categories.add(category2);
      for(String category : categories) {
        Tag newCategory = new Tag(category);
        newCategory.save();
      }

      newRecipe.save();

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
