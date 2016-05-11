import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Ingredient{
  private String name;
  private int id;

  public Ingredient(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Ingredient> all() {
    String sql = "SELECT * FROM ingredients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Ingredient.class);
    }
  }

  @Override
  public boolean equals(Object otherIngredient) {
    if (!(otherIngredient instanceof Ingredient)) {
      return false;
    }  else {
      Ingredient newIngredient = (Ingredient) otherIngredient;
      return this.getId() == newIngredient.getId() && this.getName().equals(newIngredient.getName());
    }
  }
}
