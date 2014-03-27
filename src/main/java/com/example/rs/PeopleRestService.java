package com.example.rs;

import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.validator.constraints.Length;

import com.example.model.Person;
import com.example.services.PeopleService;

@Path( "/people" ) 
public class PeopleRestService {
    @Inject private PeopleService peopleService;
    
    @Produces( { MediaType.APPLICATION_JSON } )
    @GET
    public @Valid Collection< Person > getPeople( 
    		@Min( 1 ) @QueryParam( "count" ) @DefaultValue( "1" ) final int count ) {
        return peopleService.getPeople( count );
    }

    @Produces( { MediaType.APPLICATION_JSON } )
    @Path( "/{email}" )
    @GET
    public @Valid Person getPerson( @NotNull @Length( min = 5, max = 255 ) @PathParam( "email" ) final String email ) {
        return peopleService.getByEmail( email );
    }

    @Valid
    @Produces( { MediaType.APPLICATION_JSON  } )
    @POST
    public Response addPerson( @Context final UriInfo uriInfo,
            @NotNull @Length( min = 5, max = 255 ) @FormParam( "email" ) final String email, 
            @FormParam( "firstName" ) final String firstName, 
            @FormParam( "lastName" ) final String lastName ) {        
        final Person person = peopleService.addPerson( email, firstName, lastName );
        return Response.created( uriInfo.getRequestUriBuilder().path( email ).build() ).entity( person ).build();
    }
    
    @DELETE
    public Response deleteAll() {
		peopleService.clear();
		return Response.ok().build();
	}
}
