package com.pair.settlement.dbutil;

import com.pair.settlement.owner.Owner;
import com.pair.settlement.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DatabaseInsert {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long saveUser(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    @Transactional
    public Long saveOwner(Owner owner) {
        entityManager.persist(owner);
        return owner.getId();
    }

}
