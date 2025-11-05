package basenostates;

import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

// Partition forma parte del patrón de diseño Composite.
// Es una extensión de Area que representa una
// partición de un espacio más grande, o sea un edificio
// contiene diferentes areas=particiones
// (basement,ground floor, floor 1, exterior, stairs)
public class Partition extends Area {
  private final ArrayList<Area> areas;

  public Partition(String name) {
    super(name);
    areas = new ArrayList<>();
  }

  // addArea añade un área a la partición.
  public void addArea(Area area) {
    areas.add(area);
  }

  //getSpaces devuelve todos los espacios contenidos en la partición
  @Override
  public ArrayList<Area> getSpaces() {
    ArrayList<Area> spaces = new ArrayList<>();
    for (Area area : areas) {
      spaces.add(area);
      spaces.addAll(area.getSpaces());
    }
    return spaces;
  }

  public ArrayList<Area> getAreas() {
    return areas;
  }

  /**
  // getDoorsGivingAccess devuelve todas las puertas que
  // dan acceso a las particiones.
  //@Override
  public ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> doors = new ArrayList<>();
    for (Area area : areas) {
      doors.addAll(area.getDoorsGivingAccess());
    }
    return doors;
  }
  **/

  /**
  //findAreaById busca un área por su ID dentro de la partición
  //@Override
  public Area findAreaById(String id) {
    if (this.name.equals(id)) {
      return this;
    }
    for (Area area : areas) {
      Area found = area.findAreaById(id);
      if (found != null) {
        return found;
      }
    }
    return null;
  }
  **/

  @Override
  public void accept(Visitor visitor) {
    visitor.visitPartition(this); // Pasamos al visitante el objeto Space
  }

  public JSONObject toJson(int depth) {
    // for depth=1 only the root and children,
    // for recusive = all levels use Integer.MAX_VALUE
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", name);
    JSONArray jsonAreas = new JSONArray();
    if (depth > 0) {
      for (Area a : areas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }

}
