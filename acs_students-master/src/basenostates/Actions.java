package basenostates;

// La clase Actions define constantes que representan posibles
// acciones que pueden hacer las puertas y áreas
public final class Actions {
  // possible actions in reader and area requests
  public static final String LOCK = "lock";
  public static final String UNLOCK = "unlock";
  public static final String UNLOCK_SHORTLY = "unlock_shortly";
  // possible actions in door requests
  public static final String OPEN = "open";
  public static final String CLOSE = "close";
}
