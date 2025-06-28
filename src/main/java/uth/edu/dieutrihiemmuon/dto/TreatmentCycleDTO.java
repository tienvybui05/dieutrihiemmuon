package uth.edu.dieutrihiemmuon.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.TreatmentCycle;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;

import java.time.LocalDate;

public class TreatmentCycleDTO {
    // Các thông tin được truyền từ form
    private Long serviceId;
    private Long doctorId;
    @FutureOrPresent(message = "Ngày bắt đầu phải từ hôm nay trở đi")
    @NotEmpty(message = "Vui lòng chọn ngày khám")
    private LocalDate startDate;


    // Gán tự động trong controller/service
    private Long userId;
    private LocalDate serviceBookingDate;

    private String executionStatus;      // mặc định: "Chưa thực hiện"
    private String paymentStatus;        // mặc định: "Chưa thanh toán"
    private String confirmationStatus;   // mặc định: "Đang chờ xác nhận"
    private String generalNotes;         // mặc định: null hoặc ""

    public TreatmentCycleDTO() {
    }
    public TreatmentCycleDTO(TreatmentCycle treatmentCycle) {
        this.serviceId = treatmentCycle.getServiceTreatmentCycle().getIdService();
        this.doctorId = treatmentCycle.getDoctorTreatmentCycle().getIdDoctor();
        this.startDate = treatmentCycle.getStartDate();
        this.userId = treatmentCycle.getUserTreatmentCycle().getIdUser();
        this.serviceBookingDate = treatmentCycle.getServiceBookingDate();
        this.executionStatus = treatmentCycle.getExecutionStatus();
        this.paymentStatus = treatmentCycle.getPaymentStatus();
        this.confirmationStatus = treatmentCycle.getConfirmationStatus();
        this.generalNotes = treatmentCycle.getGeneralNotes();
    }

    public TreatmentCycleDTO(Long serviceId, Long doctorId, LocalDate startDate, Long userId, LocalDate serviceBookingDate, String executionStatus, String paymentStatus, String confirmationStatus, String generalNotes) {
        this.serviceId = serviceId;
        this.doctorId = doctorId;
        this.startDate = startDate;
        this.userId = userId;
        this.serviceBookingDate = serviceBookingDate;
        this.executionStatus = executionStatus;
        this.paymentStatus = paymentStatus;
        this.confirmationStatus = confirmationStatus;
        this.generalNotes = generalNotes;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
