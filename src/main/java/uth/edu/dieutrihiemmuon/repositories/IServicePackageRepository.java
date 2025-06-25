package uth.edu.dieutrihiemmuon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uth.edu.dieutrihiemmuon.models.ServicePackage;

@Repository
public interface IServicePackageRepository extends JpaRepository<ServicePackage, Long>
{
    public ServicePackage findById(long id);
}
