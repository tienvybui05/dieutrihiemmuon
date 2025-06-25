package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.models.ServicePackage;
import java.util.List;

public interface IServicePackageService
{
    List<ServicePackage> getAllServicePackages();
    ServicePackage getServicePackageById(String id);
    void addServicePackage(ServicePackage servicePackage);
    void updateServicePackage(ServicePackage servicePackage);
    void deleteServicePackage(String id);

}
