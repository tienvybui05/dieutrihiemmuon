package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import java.util.List;

public interface IServicePackageService
{
    /*List<ServicePackage> getAllServicePackages();
    ServicePackage getServicePackageById(Long id);
    void addServicePackage(ServicePackage servicePackage);
    void updateServicePackage(Long id,ServicePackage servicePackage);
    void deleteServicePackage(Long id);*/

    public  List<ServicePackageDTO> getServicePackages();
    public  ServicePackageDTO getServicePackage(long id);
    public  boolean addServicePackage(ServicePackageDTO servicePackageDTO);
    public  boolean updateServicePackage(ServicePackageDTO servicePackageDTO);
    public  boolean deleteServicePackage(long id);
    public  List<ServicePackage> searchServicePackage(String keyword);
    public  ServicePackageDTO findByServiceName(String serviceName);

}
