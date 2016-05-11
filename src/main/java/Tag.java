import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Tag{
  private String category;
  private int id;

  public Tag(String category) {
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
}
