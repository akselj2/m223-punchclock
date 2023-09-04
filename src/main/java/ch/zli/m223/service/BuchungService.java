package ch.zli.m223.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

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
    public void delete(Long id) {
        Buchung booking = entityManager.find(Buchung.class, id);
        if (booking.getUser().getId().toString().equals(jwt.getName()) || jwt.getGroups().iterator().next().equals("Admin")) {
            entityManager.remove(booking);
        } else {
            System.out.println("didn't work :(");
        }
    }

    @Transactional
    public Response editEntry(Long id, Buchung booking) {
        try {
            if (booking.getUser().getId().toString().equals(jwt.getName())
                    || jwt.getGroups().iterator().next().equals("Admin")) {
                entityManager.merge(booking);
                return Response.ok(booking).build();
            } else {
                // Nicht eigene Buchung und oder Kein Admin zum bearbeiten
                return Response.status(Response.Status.BAD_REQUEST).build();

            }

        } catch (Exception e) {
            throw e;
        }
    }

    
}
