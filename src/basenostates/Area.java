package basenostates;
import org.json.JSONObject;

import java.util.ArrayList;

// Area es una clase abstracta que representa un área,
// es decir como un edificio.  Forma parte del patrón de
// diseño Composite, que utiliza la classe Space y Partition.
public abstract class Area {
  protected final String name;

  public Area(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  // Obtiene los espacios que forman parte del área.
  public abstract ArrayList<Area> getSpaces();

  // metodo abstracto para aceptar un visitante
  public abstract void accept(Visitor visitor);

  public abstract JSONObject toJson(int depth);

}
