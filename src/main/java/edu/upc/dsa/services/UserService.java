package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/user", description = "Endpoint to User Service")
@Path("/user")
public class UserService {
    private MyGameManager gm;

    public UserService() { this.gm = MyGameManagerImpl.getInstance(); }

    @POST
    @ApiOperation(value = "create a new User", notes = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Mail already registered")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(User user) throws UserExistingException {
        if (user.getGameId() == null) {
            return Response.status(500).entity(user).build();
        }
        try {
            this.gm.createUser(user.getUserId());
        }
        catch (UserExistingException e) {
            return Response.status(501).entity(user).build();
        } catch (ErrorIdStartGameException e) {
            return Response.status(501).entity(user).build();
        } catch (UserHasGameException e) {
            return Response.status(501).entity(user).build();
        }
        return Response.status(200).entity(user).build();
    }

    @GET
    @ApiOperation(value = "current level", notes = "Gets the current level of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @Path("/{id}/level")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLevel(@PathParam("id") String userId) throws NotUserOrGameException {
        int i = this.gm.userLevel(userId);
        return Response.status(200).entity(i).build();
    }

    @GET
    @ApiOperation(value = "current points", notes = "Gets the current points of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
    })
    @Path("/{id}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPoints(@PathParam("id") String userId) throws NotUserOrGameException {
        int i = this.gm.userLevel(userId);
        return Response.status(200).entity(i).build();
    }

    @GET
    @ApiOperation(value = "get all Users", notes = "Gets the users ordered by points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class, responseContainer = "List"),
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByOrder(@PathParam("id") String gameId) {
        List<User> users = this.gm.usersByPoints(gameId);
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {
        };
        return Response.status(200).entity(entity).build();
    }

    @PUT
    @ApiOperation(value = "end a Partida", notes = "End the current Game")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User or Game not found")
    })
    @Path("/{id}/end")
    public Response updateState(@PathParam("id") String userId) throws NotUserOrGameException {
        try {
            this.gm.endPartida(userId);
        }
        catch (NotUserOrGameException e) {
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }

    @GET
    @ApiOperation(value = "get all Partidas of a User", notes = "Gets the list of partidas of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User or Game not found")
    })
    @Path("/{id}/objects")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPartidasByUser(@PathParam("id") String userId) throws NotUserOrGameException {
        try {
            List<Partida> partidas = this.gm.partidaByUser(userId);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {
            };
            return Response.status(200).entity(entity).build();
        }
        catch (NotUserOrGameException e) {
            return Response.status(404).build();
        }
    }
}
