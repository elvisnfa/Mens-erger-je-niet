package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Token;
import com.example.ewaserver.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class TokenRepository implements EntityRepository<Token>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Token> findAll() {
        return null;
    }

    @Override
    public Token findById(int id) {
        return null;
    }


    @Override
    public Token Save(Token entity) {
        return this.em.merge(entity);
    }

    @Override
    public Token update(Token entity, int id) {
        return null;
    }

    @Override
    public Token deleteById(int id) {
        int numDeleted = this.em.createQuery("delete from Token t where t.user.userId = ?1")
                .setParameter(1, id)
                .executeUpdate();
        if (numDeleted > 0) {
            return new Token();
        } else {
            return null;
        }
    }



    public User findByRefreshToken (long id, String refreshToken, Date expired_at){
        return this.em.createQuery("select u from User u inner join Token t on u.userId = t.user.userId " +
                        "where u.userId = ?1 and t.refreshToken = ?2 and t.expired_at >= ?3",
                User.class)
                .setParameter(1, id)
                .setParameter(2, refreshToken)
                .setParameter(3, expired_at)
                .getSingleResult();
    }

    @Override
    public List<Token> findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
