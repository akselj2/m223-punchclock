package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.eclipse.microprofile.jwt.JsonWebToken;

import java.lang.Long;

import ch.zli.m223.model.Buchung;

@ApplicationScoped
public class BuchungService {
    @Inject
    private EntityManager entityManager;
    @Inject
    JsonWebToken jwt;

    /**
     * TODO: Finish adjustments, just like BuchungController.java
     * TODO: Error catching
     */

    @Transactional
    public Buchung createBuchung(Buchung buchung) {
        entityManager.persist(buchung);
        return buchung;
    }

    public List<Buchung> findAll() {
        var query = entityManager.createQuery("FROM Buchung", Buchung.class);
        return query.getResultList();
    }

    public List<Buchung> findAllByUser(String id) {
        List<Buchung> bookings = new ArrayList<>();
        var query = entityManager.createQuery("FROM Buchung", Buchung.class);
        for (Buchung booking : query.getResultList()) {
            if (booking.getUser().getId().toString().equals(id)) {
                bookings.add(booking);
            } 

        }
        return bookings;

    }

    @Transactional
    public Buchung findById(Long id) {
        var query = entityManager.find(Buchung.class, id);
        return query;
    }

    @Transactional
    public void deleteEntry(Long id) {
        Buchung entry = entityManager.find(Buchung.class, id);
        if (entry != null) {
            entityManager.remove(entry);
        } else {
            System.out.println("didn't work :(");
        }
    }

    @Transactional
    public Buchung editEntry(Long id, Buchung entry) {
        entry.setId(id);
        return entityManager.merge(entry);
    }
}
