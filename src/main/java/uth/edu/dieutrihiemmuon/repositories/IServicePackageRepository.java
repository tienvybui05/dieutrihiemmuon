package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.dieutrihiemmuon.models.ServicePackage;

import java.util.Optional;

@Repository
public interface IServicePackageRepository extends JpaRepository<ServicePackage, Long>
{
    public ServicePackage findById(long id);
    Optional<ServicePackage> findByServiceName(String serviceName);
}
