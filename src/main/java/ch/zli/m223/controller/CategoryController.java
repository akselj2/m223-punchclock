package ch.zli.m223.controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.List;
import java.lang.Long;
import ch.zli.m223.model.Category;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;

import ch.zli.m223.service.CategoryService;

@Path("/categories")
@Tag(name = "Categories", description = "Handling of categories")
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Index all Categories.", description = "Returns a list of all Categories")
    public List<Category> index() {
        return categoryService.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new Category.", description = "Creates a new Category.")
    public Category create(Category category) {
        String title = category.getTitle();

        if(title != null) {
            System.out.println("category controller line 42, worked");
            return categoryService.createCategory(category);
        } else {
            System.out.println("category controller line 45, didn't work");
            return null;
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a Category", description = "Deletes a previously created Category from the database")
    public void delete(Long id) {
        categoryService.deleteCategory(id);
    }

    @PUT
    @Path("/edit/{id}")
    @Operation(summary = "Updates a Category.", description = "Updates an existing Category.")
    public Category editCategory(Long id, Category category) {
        return categoryService.editCategory(id, category);
    }
}
