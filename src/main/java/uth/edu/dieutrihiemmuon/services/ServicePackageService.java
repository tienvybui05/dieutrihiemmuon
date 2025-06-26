package uth.edu.dieutrihiemmuon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uth.edu.dieutrihiemmuon.dto.DoctorDTO;
import uth.edu.dieutrihiemmuon.dto.ServicePackageDTO;
import uth.edu.dieutrihiemmuon.models.Doctor;
import uth.edu.dieutrihiemmuon.models.ServicePackage;
import uth.edu.dieutrihiemmuon.models.User;
import uth.edu.dieutrihiemmuon.repositories.IServicePackageRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ServicePackageService implements IServicePackageService
{
    @Autowired
    private IServicePackageRepository servicePackageRepository;
    @Override
    public List<ServicePackageDTO> getServicePackages() {
        List<ServicePackage> servicePackages = servicePackageRepository.findAll();
        List<ServicePackageDTO> servicePackageDTOS = new ArrayList<ServicePackageDTO>();
        for(ServicePackage servicePackage : servicePackages)
        {
            servicePackageDTOS.add(new ServicePackageDTO(servicePackage));
        }
        return servicePackageDTOS;
    }

    @Override
    public ServicePackageDTO getServicePackage(long id) {
        try {
            ServicePackage servicePackage =  servicePackageRepository.findById(id);
            if(servicePackage != null)
            {
                ServicePackageDTO servicePackageDTO = new ServicePackageDTO(servicePackage);
                return servicePackageDTO;
            }else {
                return null;
            }
        }catch (Exception e) {
            System.out.println("Lỗi khi tìm kiếm dịch vụ");
            return null;
        }
    }

    @Override
    public boolean addServicePackage(ServicePackageDTO servicePackageDTO) {
        try {
            ServicePackage servicePackage = new ServicePackage();
            servicePackage.setServiceName(servicePackageDTO.getServiceName());
            servicePackage.setServiceDescription(servicePackageDTO.getServiceDescription());
            servicePackage.setPatientType(servicePackageDTO.getPatientType());
            servicePackage.setNumberOfTreatmentSessions(servicePackageDTO.getNumberOfTreatmentSessions());
            servicePackage.setPrice(servicePackageDTO.getPrice());

            servicePackageRepository.save(servicePackage);

            return true;
        } catch (RuntimeException e) {
            throw new RuntimeException("Lỗi khi thêm dịch vụ: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean updateServicePackage(ServicePackageDTO servicePackageDTO) {
        try{
            ServicePackage servicePackage =  servicePackageRepository.findById(servicePackageDTO.getIdService());
            if(servicePackage == null)
            {
                return false;
            }
            servicePackage.setServiceName(servicePackageDTO.getServiceName());
            servicePackage.setServiceDescription(servicePackageDTO.getServiceDescription());
            servicePackage.setPatientType(servicePackageDTO.getPatientType());
            servicePackage.setNumberOfTreatmentSessions(servicePackageDTO.getNumberOfTreatmentSessions());
            servicePackage.setPrice(servicePackageDTO.getPrice());
            if(servicePackageRepository.save(servicePackage)!=null) {
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật dịch vụ"+e);
        }
    }

    @Override
    public boolean deleteServicePackage(long id) {
        try {
            if (servicePackageRepository.existsById(id)) {
                servicePackageRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa gói dịch vụ", e);
        }
    }

    @Override
    public List<ServicePackage> searchServicePackage(String keyword) {
        return List.of();
    }

    @Override
    public ServicePackageDTO findByServiceName(String serviceName) {
        Optional<ServicePackage> optional = servicePackageRepository.findByServiceName(serviceName);
        if (optional.isPresent()) {
            ServicePackage entity = optional.get();
            ServicePackageDTO dto = new ServicePackageDTO();
            dto.setIdService(entity.getIdService());
            dto.setServiceName(entity.getServiceName());
            dto.setServiceDescription(entity.getServiceDescription());
            dto.setPatientType(entity.getPatientType());
            dto.setNumberOfTreatmentSessions(entity.getNumberOfTreatmentSessions());
            dto.setPrice(entity.getPrice());
            return dto;
        }
        return null;
    }

    /*@Autowired
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
    }*/
}