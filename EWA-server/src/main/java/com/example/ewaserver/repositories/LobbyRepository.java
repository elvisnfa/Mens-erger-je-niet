package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class LobbyRepository implements EntityRepository<Lobby> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Lobby> findAll() {
        TypedQuery<Lobby> query = this.em.createQuery("select l from Lobby l", Lobby.class);

        return query.getResultList();
    }


    @Override
    public Lobby findById(int id) {
        return this.em.find(Lobby.class, id);
    }

    @Override
    public Lobby Save(Lobby entity) {
        return this.em.merge(entity);
    }

    @Override
    public Lobby update(Lobby entity, int id) {
        return null;
    }

    @Override
    public Lobby deleteById(int id) {
        Lobby lobby = this.findById(id);
        em.remove(lobby);
        return lobby;
    }

    @Override
    public List<Lobby> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Lobby> query =
                this.em.createNamedQuery(jpqlName, Lobby.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }
}
