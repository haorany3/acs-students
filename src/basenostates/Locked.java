package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Esta clase forma parte del patrón de diseño State, que extiende del DoorState.
//Representa el estado de la puerta cuando está bloqueada.
//Y implementamos diferentes metodos que se pueden hacer cuando la puerta está bloqueada.
public class Locked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Locked.class.getName());

  public Locked(Door door) {
    super(door);
  }

  //metodo para bloquear la puerta
  @Override
  public void lock() {
    logger.warn("---La puerta ya está bloqueada.");
  }

  //metodo para desbloquear la puerta
  @Override
  public void unlock() {
    logger.info("---Desbloqueando la puerta {}", door.getId());
    door.setState(new Unlocked(door));
  }

  //metodo para abrir la puerta
  @Override
  public void open() {
    logger.warn("---No se puede abrir la puerta {} porque está bloqueada.", door.getId());
  }

  //metodo para cerrar la puerta
  @Override
  public void close() {
    logger.warn("---No se puede cerrar la puerta {} porque está bloqueada.", door.getId());
  }

  // metodo para desbloquear temporalmente la puerta
  @Override
  public void unlockShortly() {
    logger.info("---Desbloqueando temporalmente la puerta {}", door.getId());
    door.setState(new UnlockShortly(door));
  }

  @Override
  public String toString() {
    return "locked";
  }
}
