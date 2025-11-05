package basenostates;

import basenostates.requests.RequestReader;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//La clase Door representa una puerta de la vida real. Cada puerta tiene su propio id.
//Lo necesitamos para saber su estado actual para así controlar quien entra o sale por la puerta
public class Door {
  private static final Logger logger = LoggerFactory.getLogger(Door.class.getName());
  private final String id;
  private boolean closed; // physically
  private final String fromSpace;  // Espacio de origen
  private final String toSpace;    // Espacio de destino
  private DoorState state; // Estado actual de la puerta

  public Door(String id, String fromSpace, String toSpace) {
    this.id = id;
    this.fromSpace = fromSpace;
    this.toSpace = toSpace;
    closed = true;
    this.state = new Unlocked(this); // Estado inicial: desbloqueado
  }

  // processRequest procesa una solicitud de acceso a la puerta,
  // verificando si la acción solicitada está autorizada o no,
  // si está autorizada pues lo ejecuta
  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      logger.warn("no autorizado");
    }
    String stateName = getStateName();
    request.setDoorStateName(stateName);
    logger.debug("Estado de la puerta: {}", stateName);
  }

  // doAction ejecuta una acción sobre la puerta
  // (open, close, lock, unlock, unlock shortly)
  // dependiendo de su estado actual
  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        // TODO
        state.lock();
        break;
      // fall through
      case Actions.UNLOCK:
        // TODO
        state.unlock();
        break;
      // fall through
      case Actions.UNLOCK_SHORTLY:
        // TODO
        state.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public String getFromSpace() {
    return fromSpace;
  }

  public String getToSpace() {
    return toSpace;
  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.toString();
  }

  //Convierte la información de una puerta en un formato string
  //Nos proporciona su id, su estado y si esta cerrada o no
  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  //Convierte la información de una puerta en formato json
  //Nos proporciona su id, su estado y si esta cerrada o no
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("toSpace", toSpace);
    json.put("fromSpace", fromSpace);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }

  public void setState(DoorState state) {

    this.state = state; //Cambio de estado
  }

  public void setClosed(boolean closed) {
    this.closed = closed;
  }
}
