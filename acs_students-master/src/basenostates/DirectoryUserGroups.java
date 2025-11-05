package basenostates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// DirectoryUserGroups administra los grupos de usuarios,
// cada uno con un conjunto de permisos y horarios para
// realizar acciones en las áreas.
public class DirectoryUserGroups {
  private static final Logger logger = LoggerFactory.getLogger(DirectoryUserGroups.class.getName());
  private static final ArrayList<UserGroup> userGroups = new ArrayList<>();
  private static final int THIS_YEAR = 2024;

  // makeUserGroups crea diferentes grupos de usuarios
  // (empleados, managers, etc.) con sus respectivos
  // permisos de acceso y horarios.
  public static void makeUserGroups() {
    final Area ground_floor = DirectoryAreas.findAreaById("ground_floor");
    final Area floor1 = DirectoryAreas.findAreaById("floor1");
    final Area exterior = DirectoryAreas.findAreaById("exterior");
    final Area stairs = DirectoryAreas.findAreaById("stairs");
    final Area building = DirectoryAreas.findAreaById("building");

    ArrayList<DayOfWeek> monToFri = new ArrayList<>(Arrays.asList(DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    ArrayList<DayOfWeek> monToSat = (ArrayList<DayOfWeek>) monToFri.clone();
    monToSat.add(DayOfWeek.SATURDAY);
    ArrayList<DayOfWeek> weekEnd = new ArrayList<>(Arrays.asList(DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY));
    ArrayList<DayOfWeek> allDays = (ArrayList<DayOfWeek>) monToFri.clone();
    allDays.addAll(weekEnd);

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    User bernat = new User("Bernat", "12345");
    User blai = new User("Blai", "77532");
    ArrayList<User> usersBlank = new ArrayList<>(Arrays.asList(bernat, blai));
    ArrayList<String> actionsBlank = new ArrayList<>();
    ArrayList<Area> areasBlank = new ArrayList<>();
    final UserGroup blank = new UserGroup("blank", null, actionsBlank, areasBlank, usersBlank);

    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    LocalDate firstDayEmployees = LocalDate.of(THIS_YEAR, 9, 1);
    LocalDate lastDayEmployees = LocalDate.of(THIS_YEAR + 1, 3, 30);
    Schedule scheduleEmployees = new Schedule(firstDayEmployees, lastDayEmployees, monToFri,
        LocalTime.of(9, 0), LocalTime.of(17, 0));
    ArrayList<String> actionsEmployees = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY));
    ArrayList<Area> areasEmployees = new ArrayList<>(Arrays.asList(
        ground_floor, floor1, exterior, stairs));
    User ernest = new User("Ernest", "74984");
    User eulalia = new User("Eulalia", "43295");
    ArrayList<User> userEmployees = new ArrayList<>(Arrays.asList(ernest, eulalia));
    final UserGroup employees = new UserGroup("employees", scheduleEmployees,
        actionsEmployees, areasEmployees, userEmployees);

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    LocalDate firstDayManagers = LocalDate.of(THIS_YEAR, 9, 1);
    LocalDate lastDayManagers = LocalDate.of(THIS_YEAR + 1, 3, 30);
    Schedule scheduleManagers = new Schedule(firstDayManagers, lastDayManagers, monToSat,
        LocalTime.of(8, 0), LocalTime.of(20, 0));
    ArrayList<String> actionsManagers = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY, Actions.UNLOCK, Actions.LOCK));
    ArrayList<Area> areasManagers = new ArrayList<>(Arrays.asList(building));
    User manel = new User("Manel", "95783");
    User marta = new User("Marta", "05827");
    ArrayList<User> userManagers = new ArrayList<>(Arrays.asList(manel, marta));
    final UserGroup managers = new UserGroup("managers", scheduleManagers,
        actionsManagers, areasManagers, userManagers);

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    LocalDate firstDayAdmin = LocalDate.of(THIS_YEAR, 1, 1);
    LocalDate lastDayAdmin = LocalDate.of(2100, 1, 1);
    Schedule scheduleAdmin = new Schedule(firstDayAdmin, lastDayAdmin, allDays,
        LocalTime.of(0, 0), LocalTime.of(23, 59, 59));
    ArrayList<String> actionsAdmins = new ArrayList<>(Arrays.asList(
        Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY, Actions.UNLOCK, Actions.LOCK));
    ArrayList<Area> areasAdmins = new ArrayList<>(Arrays.asList(building));
    User ana = new User("Ana", "11343");
    ArrayList<User> userAdmins = new ArrayList<>(Arrays.asList(ana));
    final UserGroup admin = new UserGroup("admin", scheduleAdmin,
        actionsAdmins, areasAdmins, userAdmins);

    userGroups.add(blank);
    userGroups.add(employees);
    userGroups.add(managers);
    userGroups.add(admin);
  }

  // findUserByCredential busca un usuario por su credencial en los distintos grupos.
  public static User findUserByCredential(String credential) {
    for (UserGroup group : userGroups) {
      User user = group.findUserByCredential(credential);
      if (user != null) {
        //Si lo ha encontrado devuelve el usuario
        return user;
      }
    }
    logger.warn("Usuario con credencial {} no encontrado.", credential);
    return null;
  }
}
