package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.UserHasLobby;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserHasLobbyRepository implements EntityRepository<UserHasLobby>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserHasLobby> findAll() {
        TypedQuery<UserHasLobby> query = this.em.createQuery("select l from UserHasLobby l", UserHasLobby.class);

        return query.getResultList();
    }


    @Override
    public UserHasLobby findById(int id) {
        return this.em.find(UserHasLobby.class, id);
    }

    @Override
    public UserHasLobby Save(UserHasLobby entity) {
        return this.em.merge(entity);
    }

    @Override
    public UserHasLobby update(UserHasLobby entity, int id) {
        return null;
    }

    @Override
    public UserHasLobby deleteById(int id) {
        UserHasLobby userHasLobby = this.findById(id);
        em.remove(userHasLobby);
        return userHasLobby;
    }

    @Override
    public List<UserHasLobby> findByQuery(String jpqlName, Object... params) {
        TypedQuery<UserHasLobby> query =
                this.em.createNamedQuery(jpqlName, UserHasLobby.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }
}
