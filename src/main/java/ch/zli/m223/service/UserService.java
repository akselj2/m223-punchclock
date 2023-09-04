package ch.zli.m223.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.ApplicationPath;

import ch.zli.m223.controller.BuchungController;
import ch.zli.m223.model.Buchung;
import ch.zli.m223.model.UserEntity;
import io.vertx.ext.auth.User;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager entityManager;

    @Inject 
    BuchungService buchungService;

    public UserEntity findById(String id) throws Exception {
        try {
            var query = entityManager.find(UserEntity.class, id);
            return query;
        } catch (Exception e) {
            throw e;
        }
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
    public UserEntity update(Long id, UserEntity user) {
        entityManager.merge(user);
        return user;
    }

    @Transactional
    public void delete(Long id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        var bookings = buchungService.findAllByUser(user.getId().toString());

        for (Buchung booking : bookings) {
            if (booking.getUser().getId() == user.getId()) {
                entityManager.remove(user);
            }
        }
    }
}
