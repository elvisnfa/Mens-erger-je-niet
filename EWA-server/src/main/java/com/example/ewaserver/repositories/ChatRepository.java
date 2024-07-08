package com.example.ewaserver.repositories;

import com.example.ewaserver.models.Chat;
import com.example.ewaserver.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public class ChatRepository implements EntityRepository<Chat>{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Chat> findAll() {
        TypedQuery<Chat> query = this.em.createQuery("select l from Chat l", Chat.class);
        return query.getResultList();
    }

    @Override
    public List<Chat> findByQuery(String jpqlName, Object... params) {
        return null;
    }

    @Override
    public Chat findById(int id) {
        return null;
    }

    @Override
    public Chat Save(Chat entity) {
        return this.em.merge(entity);
    }

    @Override
    public Chat update(Chat entity, int id) {
        return null;
    }

    @Override
    public Chat deleteById(int id) {
        return null;
    }

    public List<Chat> chatsWithFriend(long id, long friendId) {
        TypedQuery<Chat> query = em.createQuery(
                "SELECT c FROM Chat c " +
                        "WHERE (c.fromUser.userId = :userId1 AND c.toUser.userId = :userId2) " +
                        "   OR (c.fromUser.userId = :userId2 AND c.toUser.userId = :userId1) order by c.date",
                Chat.class
        );

        query.setParameter("userId1", id);
        query.setParameter("userId2", friendId);

        return query.getResultList();
    }
    public List<User> searchUsersByUsername(String keyword) {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :keyword, '%')", User.class)
                .setParameter("keyword", keyword);
        return query.getResultList();
    }
}
