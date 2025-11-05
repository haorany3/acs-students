package basenostates;

// Esta clase implementa el patrón Visitor para buscar un área por su ID.
// Permite recorrer la jerarquía de áreas (Space o Partition) de manera recursiva.
public class FindAreaByIdVisitor implements Visitor{
  private String areaId;
  private Area foundArea = null;

  public FindAreaByIdVisitor(String areaId) {
    this.areaId = areaId;
  }

  public Area getFoundArea() {
    return foundArea;
  }

  // Visitar un espacio (Space): verifica si el nombre coincide con el ID buscado.
  @Override
  public void visitSpace(Space space) {
    if (space.getName().equals(areaId)) {
      foundArea = space;
    }
  }

  // Visitar una partición (Partition): verifica si el nombre coincide con el ID buscado
  // y, de no coincidir, recorre sus subáreas de forma recursiva.
  @Override
  public void visitPartition(Partition partition) {
    if (partition.getName().equals(areaId)) {
      foundArea = partition;
    } else {
      for (Area area : partition.getAreas()) {
        if (area instanceof Space) {
          visitSpace((Space) area);
        } else if (area instanceof Partition) {
          visitPartition((Partition) area);
        }
      }
    }
  }
}
