package basenostates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

// Schedule define el horario en que un grupo de usuarios
// puede hacer acciones dentro de las áreas.
public class Schedule {

  private final LocalDate fromDay;
  private final LocalDate toDay;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final ArrayList<DayOfWeek> daysOfWeek;

  public Schedule(LocalDate fromDay, LocalDate toDay, ArrayList<DayOfWeek> daysOfWeek,
                  LocalTime startTime, LocalTime endTime) {
    this.fromDay = fromDay;
    this.toDay = toDay;
    this.daysOfWeek = daysOfWeek;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  // isWithinSchedule verifica si una fecha y hora dada
  // están dentro del horario permitido.
  public boolean isWithinSchedule(LocalDateTime now) {
    LocalDate today = now.toLocalDate();
    LocalTime nowTime = now.toLocalTime();
    DayOfWeek dayOfWeek = today.getDayOfWeek();
    if (!daysOfWeek.contains(dayOfWeek)) {
      return false;
    }
    if (today.isBefore(fromDay) || today.isAfter(toDay)) {
      return false;
    }
    if (nowTime.isBefore(startTime) || nowTime.isAfter(endTime)) {
      return false;
    }
    return true;

  }

}
