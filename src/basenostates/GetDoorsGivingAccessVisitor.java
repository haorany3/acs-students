package basenostates;

import java.util.ArrayList;

// Esta clase implementa el patrón Visitor para recopilar todas las puertas
// que dan acceso a los espacios dentro de una jerarquía de áreas (Space y Partition).
public class GetDoorsGivingAccessVisitor implements Visitor{
  private final ArrayList<Door> doors = new ArrayList<>();

  public ArrayList<Door> getDoors() {
    return doors;
  }

  // Visitar un espacio (Space): agrega todas las puertas de ese espacio a la lista.
  @Override
  public void visitSpace(Space space) {
    doors.addAll(space.getDoors());
  }

  // Visitar una partición (Partition): recorre todas las subáreas de la partición
  // y las visita recursivamente.
  @Override
  public void visitPartition(Partition partition) {
    for (Area area : partition.getAreas()) {
      if (area instanceof Space) {
        visitSpace((Space) area);
      } else if (area instanceof Partition) {
        visitPartition((Partition) area);
      }
    }
  }
}
