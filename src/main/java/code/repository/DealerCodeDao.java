package code.repository;

import code.domain.DealerCode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DealerCodeDao {

    @PersistenceContext
    private EntityManager entityManager;

    public DealerCode findById(Long id) {
        return entityManager.find(DealerCode.class, id);
    }

    public List<DealerCode> findAll() {
        return entityManager.createQuery("SELECT d FROM DealerCode d", DealerCode.class).getResultList();
    }

    @Transactional
    public DealerCode findByDealerIdAndYear(Integer dealerId, Short year) {
        TypedQuery<DealerCode> query = entityManager.createQuery(
                "SELECT d FROM DealerCode d WHERE d.year = :year AND d.dealerId = :dealerId",
                DealerCode.class
        );
        query.setParameter("year", year);
        query.setParameter("dealerId", dealerId);
        DealerCode singleResult = null;
        try {
            singleResult = query.getSingleResult();
        } catch (NoResultException e){
            return null;
        } finally {
            return singleResult;
        }
    }

    @Transactional
    public DealerCode create(DealerCode dealerCode) {
        entityManager.persist(dealerCode);
        return dealerCode;
    }

    @Transactional
    public DealerCode update(DealerCode dealerCode) {
        return entityManager.merge(dealerCode);
    }

    @Transactional
    public void delete(Long id) {
        DealerCode dealerCode = findById(id);
        if (dealerCode != null) {
            entityManager.remove(dealerCode);
        }
    }
}
