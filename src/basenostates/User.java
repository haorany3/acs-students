package basenostates;

import java.time.LocalDateTime;

// User representa a un usuario. Se necesita
// para verificar si el usuario tiene permisos para
// hacer acciones en áreas.
public class User {
  private final String name;
  private final String credential;
  private UserGroup group;

  public User(String name, String credential) {
    this.name = name;
    this.credential = credential;
    this.group = null;
  }

  public String getCredential() {
    return credential;
  }

  public void setGroup(UserGroup newGroup) {
    this.group = newGroup;
  }

  public boolean canSendRequest(LocalDateTime now) {
    return group != null && group.isWithinSchedule(now);
  }

  public boolean canDoAction(String action) {
    return group != null && group.hasAction(action);
  }

  public boolean canBeInSpace(String space) {
    return group != null && group.hasSpace(space);
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
