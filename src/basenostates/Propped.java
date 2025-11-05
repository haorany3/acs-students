package basenostates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Esta clase forma parte del patrón de diseño State, que extiende de DoorState.
// Representa el estado de la puerta cuando está entreabierta.
// Y se implementan diferentes métodos que se pueden hacer cuando la puerta está en este estado.
public class Propped extends DoorState {
  private static final Logger logger = LoggerFactory.getLogger(Propped.class.getName());

  Propped(Door door) {
    super(door);
  }

  // metodo para bloquear la puerta
  @Override
  public void lock() {
    logger.warn("---No se puede bloquear la puerta {} mientras está entreabierta.", door.getId());
  }

  // metodo para desbloquear la puerta
  @Override
  public void unlock() {
    logger.warn("---La puerta {} ya está entreabierta.", door.getId());
  }

  // metodo para abrir la puerta
  @Override
  public void open() {
    logger.warn("---La puerta {} ya está entreabierta.", door.getId());
  }

  // metodo para cerrar la puerta
  @Override
  public void close() {
    logger.info("---Cerrando la puerta {}", door.getId());
    door.setClosed(true);
    door.setState(new Locked(door));
  }

  // metodo para desbloquear temporalmente la puerta
  @Override
  public void unlockShortly() {
    logger.warn("---La puerta {} está en estado entreabierto, "
        + "no se puede cambiar a desbloqueo temporal.", door.getId());
  }

  @Override
  public String toString() {
    return "propped";
  }
}