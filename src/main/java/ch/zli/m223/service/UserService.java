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

    public List<UserEntity> findAll() {
        try {
            var query = entityManager.createQuery("FROM UserEntity", UserEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public UserEntity create(UserEntity user) throws Exception{
        try {
            entityManager.persist(user);
            entityManager.flush();
            entityManager.refresh(user);
    
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public UserEntity update(Long id, UserEntity user) {
        try {
            entityManager.merge(user);
            return user;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional
    public void delete(long id) throws Exception {
        try {
            UserEntity user = entityManager.find(UserEntity.class, id);
            var bookings = buchungService.findAllByUser(user.getId().toString());
    
            for (Buchung booking : bookings) {
                if (booking.getUser().getId() == user.getId()) {
                    entityManager.remove(user);
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }
}
