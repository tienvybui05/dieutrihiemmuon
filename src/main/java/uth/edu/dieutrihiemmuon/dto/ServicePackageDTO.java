package uth.edu.dieutrihiemmuon.dto;

import jakarta.validation.constraints.*;
import uth.edu.dieutrihiemmuon.models.ServicePackage;

public class ServicePackageDTO {
    private long idService;

    @NotBlank
    private String serviceName;
    @NotBlank
    private String serviceDescription;
    @NotBlank
    private String patientType;
    @NotNull
    @Min(1)
    private int numberOfTreatmentSessions;
    @NotNull
    @DecimalMin("0.0")
    private double price;

    public ServicePackageDTO() {}

    public ServicePackageDTO(ServicePackage servicePackage) {
        this.idService = servicePackage.getIdService();
        this.serviceName = servicePackage.getServiceName();
        this.serviceDescription = servicePackage.getServiceDescription();
        this.patientType = servicePackage.getPatientType();
        this.numberOfTreatmentSessions = servicePackage.getNumberOfTreatmentSessions();
        this.price = servicePackage.getPrice();
    }
    public ServicePackageDTO(long idService, String serviceName, String serviceDescription,String patientType, int numberOfTreatmentSessions, double price) {
        this.idService = idService;
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.patientType = patientType;
        this.numberOfTreatmentSessions = numberOfTreatmentSessions;
        this.price = price;
    }

    public long getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getPatientType() {
        return patientType;
    }

    public int getNumberOfTreatmentSessions() {
        return numberOfTreatmentSessions;
    }

    public double getPrice() {
        return price;
    }

    public void setIdService(long idService) {
        this.idService = idService;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public void setNumberOfTreatmentSessions(int numberOfTreatmentSessions) {
        this.numberOfTreatmentSessions = numberOfTreatmentSessions;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
