package uth.edu.dieutrihiemmuon.dto;

import jakarta.persistence.Column;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.User;

import java.time.LocalDate;

public class WorkscheduledoctorDTO {
    private long idCustomer;
   private long idDoctor;
   private long idSchedule;
   private String NameDoctor;
   private String NameCustomer;
   private String NameService;
   private double price;
   private LocalDate serviceBookingDate;
   private String executionStatus;
   private String paymentStatus;
   private String confirmationStatus;
   private String generalNotes;

   private boolean isScheduled;
    public WorkscheduledoctorDTO() {}
     public WorkscheduledoctorDTO(TreatmentCycle treatmentCycle)
     {
         Doctor doctor=  treatmentCycle.getDoctorTreatmentCycle();
         User doctorUser = doctor.getUser();
         ServicePackage servicePackage =  treatmentCycle.getServiceTreatmentCycle();
         User user =  treatmentCycle.getUserTreatmentCycle();
         this.idCustomer = user.getIdUser();
         this.idDoctor = doctor.getIdDoctor();
         this.idSchedule = treatmentCycle.getIdTreatmentCycle();
         this.NameDoctor = doctorUser.getFullName();
         this.NameCustomer = user.getFullName();
         this.NameService = servicePackage.getServiceName();
         this.price = servicePackage.getPrice();
         this. serviceBookingDate = treatmentCycle.getServiceBookingDate();
         this.executionStatus = treatmentCycle.getExecutionStatus();
         this.paymentStatus = treatmentCycle.getPaymentStatus();
         this.confirmationStatus = treatmentCycle.getConfirmationStatus();
         if(treatmentCycle.getGeneralNotes() == null)
         {
             this.generalNotes = "Ghi chú";
         }
         else {
             this.generalNotes = treatmentCycle.getGeneralNotes();
         }

     }
    public WorkscheduledoctorDTO(TreatmentCycle treatmentCycle ,boolean check)
    {
        Doctor doctor=  treatmentCycle.getDoctorTreatmentCycle();
        User doctorUser = doctor.getUser();
        ServicePackage servicePackage =  treatmentCycle.getServiceTreatmentCycle();
        User user =  treatmentCycle.getUserTreatmentCycle();
        this.idCustomer = user.getIdUser();
        this.idDoctor = doctor.getIdDoctor();
        this.idSchedule = treatmentCycle.getIdTreatmentCycle();
        this.NameDoctor = doctorUser.getFullName();
        this.NameCustomer = user.getFullName();
        this.NameService = servicePackage.getServiceName();
        this.price = servicePackage.getPrice();
        this. serviceBookingDate = treatmentCycle.getServiceBookingDate();
        this.executionStatus = treatmentCycle.getExecutionStatus();
        this.paymentStatus = treatmentCycle.getPaymentStatus();
        this.confirmationStatus = treatmentCycle.getConfirmationStatus();
        if(treatmentCycle.getGeneralNotes() == null)
        {
            this.generalNotes = "Ghi chú";
        }
        else {
            this.generalNotes = treatmentCycle.getGeneralNotes();
        }
        this.isScheduled = check;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameDoctor() {
        return NameDoctor;
    }

    public void setNameDoctor(String nameDoctor) {
        NameDoctor = nameDoctor;
    }

    public long getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(long idDoctor) {
        this.idDoctor = idDoctor;
    }

    public long getIdSchedule() {
        return idSchedule;
    }

    public void setIdSchedule(long idSchedule) {
        this.idSchedule = idSchedule;
    }

    public String getNameCustomer() {
        return NameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        NameCustomer = nameCustomer;
    }

    public String getNameService() {
        return NameService;
    }

    public void setNameService(String nameService) {
        NameService = nameService;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getServiceBookingDate() {
        return serviceBookingDate;
    }

    public void setServiceBookingDate(LocalDate serviceBookingDate) {
        this.serviceBookingDate = serviceBookingDate;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }
}
