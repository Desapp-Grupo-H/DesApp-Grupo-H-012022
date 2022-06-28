package ar.edu.unq.desapp.grupoh.backenddesappapi.webservice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateRange {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public LocalDateTime getStartDate() {
        return this.startDate;
    }
    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }
    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
}
