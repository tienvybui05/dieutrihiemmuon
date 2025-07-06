package uth.edu.dieutrihiemmuon.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="service_package")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idService;

    @Column(nullable = false, length = 50)
    private String serviceName;

    @Column(nullable = false)
    private String serviceDescription;

    @Column(nullable = false)
    private String patientType;

    @Column(nullable = false)
    private int numberOfTreatmentSessions;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "serviceTreatmentCycle",cascade = CascadeType.ALL)
    Set<TreatmentCycle> treatmentCycles = new HashSet<TreatmentCycle>();

    @OneToMany(mappedBy = "servicePackage",cascade = CascadeType.ALL)
    Set<Doctor> doctors = new HashSet<Doctor>();

    public ServicePackage() {
    }

    public ServicePackage(String serviceName, String serviceDescription, String patientType, int numberOfTreatmentSessions, Double price) {
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

    public String getPatientType() {
        return patientType;
    }

    public int getNumberOfTreatmentSessions() {
        return numberOfTreatmentSessions;
    }

    public Double getPrice() {
        return price;
    }

    public void setIdService(long idService) {
        this.idService = idService;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public void setNumberOfTreatmentSessions(int numberOfTreatmentSessions) {
        this.numberOfTreatmentSessions = numberOfTreatmentSessions;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Set<TreatmentCycle> getTreatmentCycles() {
        return treatmentCycles;
    }

    public void setTreatmentCycles(Set<TreatmentCycle> treatmentCycles) {
        this.treatmentCycles = treatmentCycles;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
}
