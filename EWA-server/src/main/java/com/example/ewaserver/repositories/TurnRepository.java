package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Lobby;
import com.example.ewaserver.models.Playerposition;
import com.example.ewaserver.models.Turn;
import com.example.ewaserver.models.TurnPK;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TurnRepository implements EntityRepository<Turn>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Turn> findAll() {
        TypedQuery<Turn> query = this.em.createQuery("select t from Turn t", Turn.class);

        return query.getResultList();
    }

    @Override
    public Turn findById(int id) {
        return this.em.find(Turn.class, id);
    }

    @Override
    public Turn Save(Turn entity) {
        return this.em.merge(entity);
    }

    @Override
    public Turn update(Turn entity, int id) {
        return this.em.merge(entity);
    }

    @Override
    public Turn deleteById(int id) {
        Turn turn = this.findById(id);
        em.remove(turn);
        return turn;
    }


    public Turn findbyTunrpk(TurnPK turnPK) {
        return this.em.find(Turn.class, turnPK);
    }


    public void removeAllThrewLast(int lobbyId) {
        String jpql = "UPDATE Turn t SET t.threwAsLast = false WHERE t.lobby.idLobby = :lobbyId";
        this.em.createQuery(jpql)
                .setParameter("lobbyId", lobbyId)
                .executeUpdate();
    }

    public Turn findByQuerySingleResult(String jpqlName, Object... params) {
        TypedQuery<Turn> query =
                this.em.createNamedQuery(jpqlName, Turn.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<Turn> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null; // or handle the case when no result is found
        } else {
            return resultList.get(0);
        }
    }

    @Override
    public List<Turn> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Turn> query =
                this.em.createNamedQuery(jpqlName, Turn.class);

        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }
        return query.getResultList();
    }

}
