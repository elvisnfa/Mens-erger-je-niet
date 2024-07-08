package com.example.ewaserver.repositories;

import com.example.ewaserver.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserRepository implements EntityRepository<User> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = this.em.createQuery("select u from User u", User.class);

        return query.getResultList();
    }

    @Override
    public User findById(int id) {
        return this.em.find(User.class, id);
    }

    @Override
    public User Save(User entity) {
        return this.em.merge(entity);
    }

    @Override
    public User update(User entity, int id) {
        return null;
    }

    @Override
    public User deleteById(int id) {
        User user = this.findById(id);
        em.remove(user);
        return user;
    }

    public int getAmounOfUsers() {
        return this.em.createNamedQuery("amountUsers", Number.class).getSingleResult().intValue();
    }


    public User findByEmail(String email) {
        return this.em.createQuery("select u from User u where u.email = ?1", User.class).setParameter(1, email).getSingleResult();
    }

    public User findByUsername(String username) {
        return this.em.createQuery("select u from User u where u.username = ?1", User.class).setParameter(1, username).getSingleResult();
    }

    public User findByRefreshToken(long id, String refreshToken, Date expired_at) {
        return this.em.createQuery("select u from User u inner join Token t on u.userId = t.user.userId where u.userId = ?1 and t.refreshToken = ?2 and t.expired_at >= ?3",
                User.class).setParameter(1, id).setParameter(2, refreshToken).setParameter(3, expired_at).getSingleResult();
    }

    @Override
    public List<User> findByQuery(String jpqlName, Object... params) {
        return null;
    }
}
