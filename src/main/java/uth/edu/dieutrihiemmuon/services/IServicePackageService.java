package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.models.ServicePackage;
import java.util.List;

public interface IServicePackageService
{
    List<ServicePackage> getAllServicePackages();
    ServicePackage getServicePackageById(Long id);
    void addServicePackage(ServicePackage servicePackage);
    void updateServicePackage(Long id,ServicePackage servicePackage);
    void deleteServicePackage(Long id);

}
