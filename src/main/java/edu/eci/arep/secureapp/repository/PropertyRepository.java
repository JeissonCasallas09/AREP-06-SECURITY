package edu.eci.arep.secureapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.eci.arep.secureapp.model.Property;



@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {


}
