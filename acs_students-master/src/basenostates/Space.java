package basenostates;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;
import basenostates.Door;

// Space forma parte del patrón de diseño Composite.
// Es una extensión de Area que representa
// un espacio como una sala o un pasillo que tiene puertas.
public class Space extends Area {
  private final ArrayList<Door> doors;

  public Space(String name) {
    super(name);
    this.doors = new ArrayList<>();
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }

  // addDoor añade una puerta al espacio
  public void addDoor(Door door) {
    doors.add(door);
  }

  //getSpaces devuelve el espacio como una lista
  @Override
  public ArrayList<Area> getSpaces() {
    ArrayList<Area> spaces = new ArrayList<>();
    spaces.add(this);
    return spaces;
  }

  /**
  // getDoorsGivingAccess devuelve las puertas que
  // dan acceso al espacio
  //@Override
  public ArrayList<Door> getDoorsGivingAccess() {
    return doors;
  }
  **/

  /**
  //findAreaById busca un área por su ID dentro del espacio
  //@Override
  public Area findAreaById(String id) {
    if (this.name.equals(id)) {
      return this;
    }
    return null;
  } **/

  @Override
  public void accept(Visitor visitor) {
    visitor.visitSpace(this); // Pasamos al visitante el objeto Space
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", name);
    JSONArray jsonDoors = new JSONArray();
    for (Door d : doors) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }
}
