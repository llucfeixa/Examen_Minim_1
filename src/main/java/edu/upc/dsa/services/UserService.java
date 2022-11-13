package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.*;
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
    @ApiOperation(value = "creates a new User", notes = "Registers a new User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = User.class),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "User already exists")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newUser(UserRegister userRegister) throws UserExistingException {
        User user = new User();
        if (userRegister.getUserRegisterId() == null) {
            return Response.status(500).entity(user).build();
        }
        try {
            this.gm.createUser(userRegister.getUserRegisterId());
            user = this.gm.getUser(userRegister.getUserRegisterId());
        }
        catch (UserExistingException e) {
            return Response.status(501).entity(user).build();
        }
        return Response.status(200).entity(user).build();
    }

    @POST
    @ApiOperation(value = "start a new Partida", notes = "Creates a new partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Error in the id"),
            @ApiResponse(code = 502, message = "User is already playing")
    })
    @Path("/partida")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newGame(UserGameId userGameId) throws ErrorIdStartGameException, UserHasGameException {
        if (userGameId.getUserId() == null || userGameId.getGameId() == null) {
            return Response.status(500).entity(userGameId).build();
        }
        try {
            this.gm.startPartida(userGameId.getGameId(), userGameId.getUserId());
        }
        catch (ErrorIdStartGameException e) {
            return Response.status(501).entity(userGameId).build();
        }
        catch (UserHasGameException e) {
            return Response.status(502).entity(userGameId).build();
        }
        return Response.status(200).entity(userGameId).build();
    }

    @GET
    @ApiOperation(value = "current level", notes = "Gets the current level of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = IntegerREST.class),
    })
    @Path("/{id}/level")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLevel(@PathParam("id") String userId) throws NotUserOrGameException {
        IntegerREST levels = new IntegerREST(this.gm.userLevel(userId));
        return Response.status(200).entity(levels).build();
    }

    @GET
    @ApiOperation(value = "current points", notes = "Gets the current points of the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = IntegerREST.class),
    })
    @Path("/{id}/points")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserPoints(@PathParam("id") String userId) throws NotUserOrGameException {
        IntegerREST points = new IntegerREST(this.gm.userPoints(userId));
        return Response.status(200).entity(points).build();
    }

    @PUT
    @ApiOperation(value = "next Level", notes = "Advances to the next level")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User or Game not found")
    })
    @Path("/{id}/nextLevel")
    public Response nextLevel(@PathParam("id") String userId) throws NotUserOrGameException {
        try {
            this.gm.nextLevel(20, userId,"13-11-2022");
        }
        catch (NotUserOrGameException e) {
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }

    @PUT
    @ApiOperation(value = "end a Partida", notes = "End the current Game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "User or Game not found")
    })
    @Path("/{id}/end")
    public Response endPartida(@PathParam("id") String userId) throws NotUserOrGameException {
        try {
            this.gm.endPartida(userId);
        }
        catch (NotUserOrGameException e) {
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "get all Partidas of a User", notes = "Gets the list of partidas of a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Partida.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{id}/partidas")
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

    @POST
    @ApiOperation(value = "get activity of user", notes = "Gets the activity of a user in a game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = ListActivity.class, responseContainer = "List")
    })
    @Path("/activities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivityByUser(UserGameId userGameId) {
        List<ListActivity> activities = this.gm.activityByUser(userGameId.getUserId(), userGameId.getGameId());
        GenericEntity<List<ListActivity>> entity = new GenericEntity<List<ListActivity>>(activities) {
        };
        return Response.status(200).entity(entity).build();
    }
}
