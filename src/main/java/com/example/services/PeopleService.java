package com.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.example.exceptions.PersonAlreadyExistsException;
import com.example.exceptions.PersonNotFoundException;
import com.example.model.Person;

@Service
public class PeopleService {
    private final ConcurrentMap< String, Person > persons = new ConcurrentHashMap< String, Person >(); 
        
    public Collection< Person > getPeople( int count ) {
    	return new ArrayList< Person >( persons.values() )
    	    .subList( 0, Math.min( count, persons.size() ) );
    }
    
    public Person getByEmail( final String email ) {
        final Person person = persons.get( email );
        
        if( person == null ) {
            throw new PersonNotFoundException( email );
        }
        
        return person;
    }

    public Person addPerson( final String email, final String firstName, final String lastName ) {
        final Person person = new Person( email );
        person.setFirstName( firstName );
        person.setLastName( lastName );
                
        if( persons.putIfAbsent( email, person ) != null ) {
            throw new PersonAlreadyExistsException( email );
        }
        
        return person;
    }

	public void clear() {
		persons.clear();		
	}
}
