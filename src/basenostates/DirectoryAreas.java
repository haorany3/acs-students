package basenostates;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// DirectoryAreas gestiona las áreas,
// almacenando la jerarquía de áreas y puertas.
// Permite crear áreas y encontrar áreas o puertas
// mediante su ID.
public class DirectoryAreas {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryAreas.class.getName());
  private static Area rootArea;
  private static ArrayList<Door> allDoors = new ArrayList<>();
  // makeAreas crea una estructura en forma de jerarquia
  // (edificio=building, partition, espacios=spaces)
  // y las puertas que conectan estos espacios.

  public static void makeAreas() {
    // Crear particiones del edificio y agregar cada una al edificio

    Partition building = new Partition("building");
    Partition groundFloor = new Partition("ground_floor");
    building.addArea(groundFloor);
    Partition basement = new Partition("basement");
    building.addArea(basement);
    Partition floor1 = new Partition("floor1");
    building.addArea(floor1);
    Partition exterior = new Partition("exterior");
    building.addArea(exterior);
    Partition stairs = new Partition("stairs");
    building.addArea(stairs);

    // Crear puertas y asignarlas a los espacios
    Door d1 = new Door("D1", "exterior", "parking");
    allDoors.add(d1);
    Door d2 = new Door("D2", "stairs", "parking");
    allDoors.add(d2);
    Door d3 = new Door("D3", "exterior", "hall");
    allDoors.add(d3);
    Door d4 = new Door("D4", "stairs", "hall");
    allDoors.add(d4);
    Door d5 = new Door("D5", "hall", "room1");
    allDoors.add(d5);
    Door d6 = new Door("D6", "hall", "room2");
    allDoors.add(d6);
    Door d7 = new Door("D7", "stairs", "corridor");
    allDoors.add(d7);
    Door d8 = new Door("D8", "corridor", "room3");
    allDoors.add(d8);
    Door d9 = new Door("D9", "corridor", "IT");
    allDoors.add(d9);

    // Crear espacios y asignar puertas a ellos
    Space parking = new Space("parking");
    parking.addDoor(d1);
    parking.addDoor(d2);
    Space room1 = new Space("room1");
    room1.addDoor(d5);
    Space room2 = new Space("room2");
    room2.addDoor(d6);
    Space room3 = new Space("room3");
    room3.addDoor(d8);
    Space hall = new Space("hall");
    hall.addDoor(d3);
    hall.addDoor(d4);
    Space it = new Space("IT");
    it.addDoor(d9);
    Space corridor = new Space("corridor");
    corridor.addDoor(d7);

    // Añadir espacios a sus respectivas particiones
    basement.addArea(parking);

    groundFloor.addArea(hall);
    groundFloor.addArea(room1);
    groundFloor.addArea(room2);

    floor1.addArea(room3);
    floor1.addArea(it);
    floor1.addArea(corridor);

    rootArea = building;

  }

  /**
   // findAreaById busca un área por su ID
   // empezando desde el rootArea (building)
   public static Area findAreaById(String id) {
   if (rootArea != null) {
   return rootArea.findAreaById(id);
   }
   return null;
   }**/

  // findAreaById busca un área por su ID
  // empezando desde el rootArea (building)
  public static Area findAreaById(String id) {
    FindAreaByIdVisitor visitor = new FindAreaByIdVisitor(id);
    if (rootArea != null) {
      rootArea.accept(visitor);
    }
    Area foundArea = visitor.getFoundArea();
    return foundArea;
  }

  // findDoorById busca una puerta por su ID
  // dentro de la lista de todas las puertas.
  public static Door findDoorById(String id) {
    logger.debug("Buscando puerta con id: {}", id);
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        logger.debug("Puerta encontrada: {}", door.getId());
        return door;
      }
    }
    logger.warn("Puerta con id {} no encontrada", id);
    return null;
  }

  // this is needed by RequestRefresh
  // getAllDoors devuelve todas las puertas
  public static ArrayList<Door> getAllDoors() {
    logger.debug("Lista de todas las puertas: {}", allDoors);
    return allDoors;
  }

}

