package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.ErrorIdStartGameException;
import edu.upc.dsa.exceptions.NotUserOrGameException;
import edu.upc.dsa.exceptions.UserExistingException;
import edu.upc.dsa.exceptions.UserHasGameException;
import edu.upc.dsa.models.Game;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")
public class GameService {
    private MyGameManager gm;

    public GameService() { this.gm = MyGameManagerImpl.getInstance(); }

    @POST
    @ApiOperation(value = "create a new Game", notes = "Creates a new game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Game.class),
            @ApiResponse(code = 500, message = "Missing Information"),
            @ApiResponse(code = 501, message = "Mail already registered")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newGame(Game game) {
        if (game.getGameId() == null || game.getDescription() == null || game.getLevels() == 0) {
            return Response.status(500).entity(game).build();
        }
        this.gm.createGame(game.getGameId(), game.getDescription(), game.getLevels());
        return Response.status(200).entity(game).build();
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
}
