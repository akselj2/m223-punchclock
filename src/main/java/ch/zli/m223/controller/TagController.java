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

import ch.zli.m223.service.TagService;

@Path("/tags")
@Tag(name = "Tags", description = "Handling of tags")
public class TagController {
    
    @Inject
    TagService tagService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Tags", description = "Returns a list of all Tags in the database")
    public List<ch.zli.m223.model.Tag> index() {
        return tagService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new Tag", description = "Creates a new Tag")
    public ch.zli.m223.model.Tag create(ch.zli.m223.model.Tag tag) {
        String tagName = ((ch.zli.m223.model.Tag) tag).getTagName();

        if(tagName != null) {
            return tagService.createTag(tag);
        } else {
            return null;
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Tag", description = "Deletes a previously existing Tag from the database")
    public void delete(Long id) {
        tagService.deleteTag(id);
    }

    @PUT
    @Path("/edit/{id}")
    @Operation(summary = "Updates a Tag.", description = "Updates an existing Tag")
    public ch.zli.m223.model.Tag editTag(Long id, ch.zli.m223.model.Tag tag) {
        return tagService.editTag(id, tag);
    }
}
