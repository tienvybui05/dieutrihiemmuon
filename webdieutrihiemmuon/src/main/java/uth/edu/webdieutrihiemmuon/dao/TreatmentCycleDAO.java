package uth.edu.webdieutrihiemmuon.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import uth.edu.webdieutrihiemmuon.models.TreatmentCycle;

public class TreatmentCycleDAO {
    private static EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;
    public TreatmentCycleDAO(String persistencName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistencName);
    }

    public boolean save (TreatmentCycle treatmentcycle){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(treatmentcycle);
            entityManager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    public boolean update (TreatmentCycle treatmentcycle){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(treatmentcycle);
            entityManager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    public boolean delete(Long id){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TreatmentCycle treatmentcycle = entityManager.find(TreatmentCycle.class, id);
            if(treatmentcycle != null){
                entityManager.remove(treatmentcycle);
                entityManager.getTransaction().commit();
                return true;
            } else{
                return false;
            }



        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    public TreatmentCycle findById(Long id){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.find(TreatmentCycle.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }

    public List<TreatmentCycle> findAll(){
        try {
            entityManager = entityManagerFactory.createEntityManager();
            return entityManager.createQuery("SELECT s FROM TreatmentCycle s",TreatmentCycle.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(entityManager != null){
                entityManager.close();
            }
        }
    }
}
