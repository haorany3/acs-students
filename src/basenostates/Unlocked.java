package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Unlocked forma parte del patrón de diseño State, que extiende del DoorState.
//Esta clase representa los estados de la puerta cuando esta desbloqueada.
//Cuando una puerta está desbloqueada, se puede abrir, cerrar y bloquear la puerta.
public class Unlocked extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Unlocked.class.getName());

  public Unlocked(Door door) {
    super(door);
  }

  //metodo para bloquear la puerta
  @Override
  public void lock() {
    if (door.isClosed()) {
      logger.info("---Bloqueando la puerta {}", door.getId());
      door.setState(new Locked(door));
    } else {
      logger.warn("---No se puede bloquear una puerta abierta.");
    }
  }

  //metodo para desbloquear la puerta
  @Override
  public void unlock() {
    logger.warn("---La puerta ya está desbloqueada.");
  }
  //metodo para abrir la puerta

  @Override
  public void open() {
    if (door.isClosed()) {
      logger.info("---Abriendo la puerta {}", door.getId());
      door.setClosed(false);
    } else {
      logger.warn("---La puerta {} ya está abierta.", door.getId());
    }
  }

  //metodo para cerrar la puerta
  @Override
  public void close() {
    if (!door.isClosed()) {
      logger.info("---Cerrando la puerta {}", door.getId());
      door.setClosed(true);
    } else {
      logger.warn("---La puerta {} ya está cerrada.", door.getId());
    }
  }

  // metodo para desbloquear temporalmente la puerta
  @Override
  public void unlockShortly() {
    logger.warn("---La puerta esta desbloqueada {}", door.getId());
  }

  public String toString() {
    return "unlocked";
  }
}

