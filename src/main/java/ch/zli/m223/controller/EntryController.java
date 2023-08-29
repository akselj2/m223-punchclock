package ch.zli.m223.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.lang.Long;
import java.time.LocalDateTime;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Entries.", description = "Returns a list of all entries.")
    public List<Entry> index() {
        return entryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new entry.", description = "Creates a new entry and returns the newly added entry.")
    public Entry create(Entry entry) {
        LocalDateTime checkIn = entry.getCheckIn();
        LocalDateTime checkOut = entry.getCheckOut();

        if(checkIn.compareTo(checkOut) < 0) {
            System.out.println("it was correct :))))");
            return entryService.createEntry(entry);
        } else {
            System.out.println("you tried, line 49 entry controller");
            return null;
        }
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes an entry.", description = "Deletes a previously created entry from the database.")
    public void delete(Long id) {
        entryService.deleteEntry(id);
    }

    @PUT
    @Path("/edit/{id}")
    @Operation(summary = "Updates an entry.", description = "Updates an existing entry in the database.")
    public Entry editEntry(Long id, Entry entry) {
        return entryService.editEntry(id, entry);
    }

}
