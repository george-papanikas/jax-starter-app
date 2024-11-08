package gr.aueb.cf.jaxstarter.rest;

import gr.aueb.cf.jaxstarter.dto.TeacherInsertDTO;
import gr.aueb.cf.jaxstarter.dto.UserDTO;
import gr.aueb.cf.jaxstarter.model.Teacher;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/hello")
public class HelloRestController {

    private final Validator validator;

    public HelloRestController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @GET
    @Path("/get-hello-cf")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello Coding Factory";
    }

    @GET
    @Path("/get-hello-res")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getHelloRes() {
        //return Response.ok("Hello Coding Factory").build();
        return Response.status(Response.Status.OK).entity("Hello Coding Factory").build();
    }

    @GET
    @Path("/get-teacher")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeacher() {
        return Response
                .status(Response.Status.OK)
                .entity(new Teacher(1L, "123456", "Ath.", "Androutsos"))
                .build();
    }

    @POST
    @Path("/messages")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertMessage(@FormParam("message") String message, @Context UriInfo uriInfo) {
        //Insert to DB
        String id = "1";

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path(id).build();
        return Response.status(Response.Status.CREATED).location(uri).entity(message).build();
    }

    @GET
    @Path("/messages/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("id") String id) {
        // Get from DB the message with id == 1
        String message = "Hello World!!";
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOrders(@QueryParam("from") int from,
                              @QueryParam("to") int to,
                              @QueryParam("orderBy") List<String> orderBy){
        return Response.status(Response.Status.OK).entity("From: " + from + ", To: " + to + ", order by " + orderBy.toString()).build();
    }

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertUser(MultivaluedMap<String, String> params, @Context UriInfo uriInfo) {
        UserDTO userDto = mapToDto(params);
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDto);

        if(!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<UserDTO> violation : violations) {
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        //user with id == 1 inserted
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path("1").build(); // if id = "1" we add it to path
        return Response.status(Response.Status.CREATED).location(uri).build();
    }

    private UserDTO mapToDto(MultivaluedMap<String, String> params) {
        UserDTO userDto = new UserDTO();
        userDto.setUsername(params.getFirst("username"));
        userDto.setPassword(params.getFirst("password"));
        return userDto;
    }

    @POST
    @Path("/teachers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeacher(TeacherInsertDTO dto, @Context UriInfo uriInfo) {
        Set<ConstraintViolation<TeacherInsertDTO>> violations = validator.validate(dto);

        if(!violations.isEmpty()) {
            List<String> errors = new ArrayList<>();
            for (ConstraintViolation<TeacherInsertDTO> violation : violations) {
                errors.add(violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }

        //teacher with id == 1 inserted
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        URI uri = uriBuilder.path("1").build(); // if id = "1" we add it to path
        return Response.status(Response.Status.CREATED).location(uri).build();
    }
}
