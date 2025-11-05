package basenostates;

// La clase abstracta DoorState representa el estado de una puerta.
// Esta forma parte del patrón de diseño State y permite que la puerta cambie de estado
// según las acciones que se realicen sobre ella. Las clases que implementan este estado son:
// Locked, Unlocked, Propped y UnlockShortly, que extienden de DoorState.
// A través de esta clase, cada estado define su propio comportamiento para las distintas acciones
// que una puerta puede realizar, como:
// bloquear, desbloquear, abrir, cerrar y desbloquear temporalmente.
public abstract class DoorState {
  protected Door door;

  DoorState(Door door) {
    this.door = door;
  }

  public abstract void lock(); //metodo para bloquear la puerta

  public abstract void unlock(); //metodo para desbloquear la puerta

  public abstract void open(); //metodo para abrir la puerta

  public abstract void close(); //metodo para cerrar la puerta

  public abstract void unlockShortly(); //metodo para desbloquear la puerta temporalmente

  public abstract String toString();
}
