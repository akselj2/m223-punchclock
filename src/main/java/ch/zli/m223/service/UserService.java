package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.ApplicationPath;

import ch.zli.m223.model.UserEntity;
import io.vertx.ext.auth.User;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager entityManager;

    public UserEntity findById(String userId) {
        var query = entityManager.createQuery("FROM user", User.class);
    }
    /**
     * FILL IN WITH FOLLOWING DATA ON 04.08.23
     * CREATE
     * FIND BY VARIOUS METHODS
     * DELETE
     * EDIT
     */

    public List<UserEntity> findAll() {
        var query = entityManager.createQuery("FROM UserEntity", UserEntity.class);
        return query.getResultList();
    }

    @Transactional
    public UserEntity create(UserEntity user) {
        entityManager.persist(user);
        entityManager.flush();
        entityManager.refresh(user);

        return user;
    }

    @Transactional
    public void delete(Long id) {
        UserEntity user = entityManager.find(user, id);

    }
}
