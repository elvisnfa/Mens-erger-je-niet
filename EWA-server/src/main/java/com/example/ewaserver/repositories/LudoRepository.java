package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Playerposition;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class LudoRepository implements EntityRepository<Playerposition>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Playerposition> findAll() {
        TypedQuery<Playerposition> query = this.em.createQuery("select p from Playerposition p", Playerposition.class);

        return query.getResultList();
    }


    @Override
    public Playerposition findById(int id) {
        return this.em.find(Playerposition.class, id);
    }

    @Override
    public Playerposition Save(Playerposition entity) {
        return this.em.merge(entity);
    }

    @Override
    public Playerposition update(Playerposition entity, int id) {
        return null;
    }

    @Override
    public Playerposition deleteById(int id) {
        Playerposition playerposition = this.findById(id);
        em.remove(playerposition);
        return playerposition;
    }

    @Override
    public List<Playerposition> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Playerposition> query =
                this.em.createNamedQuery(jpqlName, Playerposition.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }
}
