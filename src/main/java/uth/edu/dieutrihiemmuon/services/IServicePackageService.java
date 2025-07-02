package uth.edu.dieutrihiemmuon.services;

import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import java.util.List;

public interface IServicePackageService
{
    public  List<ServicePackageDTO> getServicePackages();
    public  ServicePackageDTO getServicePackage(long id);
    public  boolean addServicePackage(ServicePackageDTO servicePackageDTO);
    public  boolean updateServicePackage(ServicePackageDTO servicePackageDTO);
    public  boolean deleteServicePackage(long id);
    public  List<ServicePackage> searchServicePackage(String keyword);
    public  ServicePackageDTO findByServiceName(String serviceName);
    public  long countServicePackage();
}
