package uth.edu.dieutrihiemmuon.dto;

public class CheckScheduleDTO {
    // số chưa thực hiện
    private int notYetImplemented;
    // số đã thực hiện
    private int done;
    // sô lượng lịch trong ngày
    private int NumberOfCalendarsPerDay;
    public CheckScheduleDTO() {}
    public CheckScheduleDTO(int notYetImplemented, int done, int numberOfCalendarsPerDay) {
        this.notYetImplemented = notYetImplemented;
        this.done = done;
        NumberOfCalendarsPerDay = numberOfCalendarsPerDay;
    }

    public int getNotYetImplemented() {
        return notYetImplemented;
    }

    public void setNotYetImplemented(int notYetImplemented) {
        this.notYetImplemented = notYetImplemented;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public int getNumberOfCalendarsPerDay() {
        return NumberOfCalendarsPerDay;
    }

    public void setNumberOfCalendarsPerDay(int numberOfCalendarsPerDay) {
        NumberOfCalendarsPerDay = numberOfCalendarsPerDay;
    }
}
