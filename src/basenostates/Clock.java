package basenostates;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Esta clase representa un reloj que se utiliza para notificar a los observadores
// sobre los cambios de tiempo de manera periódica, actuando como un temporizador.
public class Clock extends Observable {
  private static final Logger logger = LoggerFactory.getLogger("basenostates.fita2");
  private LocalDateTime date;
  private Timer timer;
  private int period; // Period in milliseconds
  private boolean isActive = false;
  private static Clock instance;


  public Clock(int period) {
    this.period = period;
  }

  public static Clock getInstance(int period) {
    if (instance == null) {
      instance = new Clock(period);
    }
    return instance;
  }

  // Inicia el temporizador, ejecutando la tarea repetidamente cada 'period' segundos.
  public void start() {
    if (!isActive) {
      // Inicia el reloj solo si no está activo
      timer = new Timer();
      TimerTask repeatedTask = new TimerTask() {
        public void run() {
          setChanged();
          notifyObservers(); // Notifica a todos los observadores
          date = LocalDateTime.now();
          logger.info("run() executado en {}", date);
        }
      };
      timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * period); // Ejecuta cada 'period' segundos
      isActive = true;
    }
  }

  // Detiene el temporizador y cambia el estado de 'isActive' a falso.
  public void stop() {
    if (isActive) {
      timer.cancel(); // Detenemos el reloj
      isActive = false;
    }
  }

  // Verifica si hay observadores activos
  public void checkObservers() {
    if (this.countObservers() == 0) {
      stop();  // Detenemos el reloj si no hay observadores
    }
  }
}