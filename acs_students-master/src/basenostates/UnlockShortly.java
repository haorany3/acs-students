package basenostates;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Esta clase forma parte del patrón de diseño State, que extiende del DoorState.
// Representa el estado de la puerta cuando está desbloqueada temporalmente.
// Y se implementan diferentes métodos que se pueden hacer cuando la puerta está en este estado.
public class UnlockShortly extends DoorState implements Observer {
  private static final Logger logger = LoggerFactory.getLogger(UnlockShortly.class.getName());
  private final Clock clock = Clock.getInstance(1);
  private final LocalDateTime unlockStartTime;  // Momento en que se desbloqueó la puerta

  public UnlockShortly(Door door) {
    super(door);
    clock.start();
    clock.addObserver(this);
    unlockStartTime = LocalDateTime.now();
  }

  // metodo que es llamado cuando el reloj notifica que ha terminado el tiempo.
  public void update(Observable o, Object arg) {
    LocalDateTime currentTime = LocalDateTime.now();  // Obtiene el tiempo actual

    Duration duration = Duration.between(unlockStartTime, currentTime);

    // Verifica si han pasado más de 10 segundos
    if (duration.getSeconds() > 10) {
      lock();  // Bloquea la puerta
      clock.deleteObserver(this);  // Elimina la puerta como observadora
      clock.checkObservers();  // Verifica si hay más observadores. Si no, apaga el reloj.
    }
  }

  //metodo para bloqeuar puerta
  @Override
  public void lock() {
    if (door.isClosed()) {
      logger.info("---Bloqueando la puerta {}", door.getId());
      door.setState(new Locked(door));
    } else {
      logger.warn("---No se puede bloquear una puerta abierta.");
      door.setState(new Propped(door));
    }
  }

  // metodo para desblouqear puerta
  @Override
  public void unlock() {
    logger.warn("---La puerta {} está en estado de desbloqueo temporal", door.getId());
  }

  // metodo para abrir la puerta
  @Override
  public void open() {
    logger.info("---Abriendo la puerta {}", door.getId());
    door.setClosed(false);
  }

  // metodo para cerrar la puerta
  @Override
  public void close() {
    logger.info("---Cerrando la puerta {}", door.getId());
    door.setClosed(true);
  }

  // metodo para desbloquear temporalmente la puerta
  @Override
  public void unlockShortly() {
    logger.warn("---La puerta {} está en estado de desbloqueo temporal", door.getId());
  }

  @Override
  public String toString() {
    return "unlocked_shortly";
  }


}

