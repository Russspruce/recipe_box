public class Recipe {

  private int id;
  private String recipe_name;
  private String instructions;
  private int rating;

  public Recipe(String recipe_name, String instructions) {
    this.recipe_name = recipe_name;
    this.instructions = instructions;
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
}
