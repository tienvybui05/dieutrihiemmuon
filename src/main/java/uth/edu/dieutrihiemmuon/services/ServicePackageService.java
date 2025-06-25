package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.repositories.IServicePackageRepository;

import java.util.List;
import java.util.Optional;


@Service
public class ServicePackageService implements IServicePackageService
{
    @Autowired
    private IServicePackageRepository servicePackageRepository;

    @Override
    public List<ServicePackage> getAllServicePackages() {
        return servicePackageRepository.findAll();
    }

    @Override
    public ServicePackage getServicePackageById(Long id) {
        return servicePackageRepository.findById(id).orElse(null);
    }

    @Override
    public void addServicePackage(ServicePackage servicePackage) {
        servicePackageRepository.save(servicePackage);
    }

    @Override
    public void updateServicePackage(Long id, ServicePackage updatedPackage) {
        Optional<ServicePackage> optional = servicePackageRepository.findById(id);
        if (optional.isPresent()) {
            ServicePackage existing = optional.get();
            existing.setServiceName(updatedPackage.getServiceName());
            existing.setPrice(updatedPackage.getPrice());
            // ...
            servicePackageRepository.save(existing);
        } else {
            throw new IllegalArgumentException("Không tìm thấy gói dịch vụ với ID = " + id);
        }
    }
    @Override
    public void deleteServicePackage(Long id) {
        if (servicePackageRepository.existsById(id)) {
            servicePackageRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Không tìm thấy dịch vụ với ID = " + id);
        }
    }
}