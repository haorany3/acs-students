package basenostates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// UserGroup representa un grupo de usuarios que tienen permisos y
// horarios determinados para acceder a áreas y realizar acciones (lock, unlock, etc.)
public class UserGroup {
  private static final Logger logger = LoggerFactory.getLogger(UserGroup.class.getName());
  private final String name;
  private final ArrayList<String> actions;
  private final ArrayList<Area> areas;
  private final ArrayList<User> users;
  private final Schedule schedule;

  public UserGroup(String name, Schedule schedule, ArrayList<String> actions,
                   ArrayList<Area> areas, ArrayList<User> users) {
    this.name = name;
    this.schedule = schedule;
    this.actions = actions;
    this.users = users;
    this.areas = areas;

    for (User u : users) {
      u.setGroup(this);
    }

  }

  public String getName() {
    return name;
  }

  public User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        logger.debug("Usuario encontrado: {}", user);
        return user;
      }
    }
    logger.debug("Usuario no encontrado");
    return null;
  }

  //hasSpace verifica si el grupo tiene acceso a un espacio específico
  public boolean hasSpace(String areaName) {
    // Obtener todos los espacios en las áreas
    ArrayList<Area> allAreas = getSpaces();
    for (Area area : allAreas) {
      if (area.getName().equals(areaName)) {
        // Devuelve true si se ha encontrado el espacio
        logger.info("Este usuario tiene acceso a esta area {}", area.name);
        return true;
      }
    }
    logger.warn("Este usuario no tiene acceso a este espacio");
    return false;
  }

  public ArrayList<Area> getSpaces() {
    ArrayList<Area> spaces = new ArrayList<>();
    for (Area area : areas) {
      // Agregar espacios de áreas (particiones) recursivamente
      spaces.add(area);
      for (Area space : area.getSpaces()) {
        // Solo agregar si no está ya presente
        if (!containsArea(spaces, space)) {
          spaces.add(space);
        }
      }
    }
    return spaces;
  }

  // Metodo para verificar si el área ya existe en la lista
  private boolean containsArea(ArrayList<Area> spaces, Area area) {
    for (Area existingArea : spaces) {
      if (existingArea.getName().equals(area.getName())) {
        // Devuelve true si ya existe el area
        return true;
      }
    }
    return false;
  }

  // hasAction verifica si el grupo tiene permiso para realizar una acción
  public boolean hasAction(String action) {
    return actions.contains(action);
  }

  // isWithinSchedule verifica si la acción está dentro del horario permitido del grupo
  public boolean isWithinSchedule(LocalDateTime now) {
    return schedule.isWithinSchedule(now);
  }

  @Override
  public String toString() {
    return "UserGroup{"
        + "name='" + name + '\''
        + ", actions=" + actions
        + ", schedule=" + schedule.toString()
        + '}';
  }
}

