package uth.edu.webdieutrihiemmuon.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="service_package")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idService;

    @Column(unique = true, nullable = false, length = 50)
    private String serviceName;

    @Column(nullable = false)
    private String serviceDescription;

    @Column(nullable = false)
    private String patientType;

    @Column(nullable = false)
    private int numberOfTreatmentSessions;

    @Column(nullable = false)
    private Double price;

    @OneToMany(mappedBy = "servicePackage",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<TreatmentCycle> treatmentCycles = new HashSet<TreatmentCycle>();

    public ServicePackage() {
    }

    public ServicePackage(String serviceName, String serviceDescription, String patientType, int numberOfTreatmentSessions, Double price) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.patientType = patientType;
        this.numberOfTreatmentSessions = numberOfTreatmentSessions;
        this.price = price;
    }

    public int getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescribe() {
        return serviceDescription;
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

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDescribe(String describe) {
        this.serviceDescription = describe;
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
}
