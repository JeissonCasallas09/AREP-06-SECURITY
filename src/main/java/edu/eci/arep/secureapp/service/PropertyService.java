package edu.eci.arep.secureapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arep.secureapp.model.Property;
import edu.eci.arep.secureapp.repository.PropertyRepository;


@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository){
        this.propertyRepository=propertyRepository;
    }

    public Property createProperty(Property property){
        return propertyRepository.save(property);
    }

    public Property getPropertyById(Long id){
        return propertyRepository.findById(id).orElse(null);
    }

    public Iterable<Property> getAllProperties(){
        return propertyRepository.findAll();
    }

    public void deleteProperty(Long id){
        propertyRepository.deleteById(id);
    }

    public Property updateProperty(Property property) {
        return propertyRepository.findById(property.getId())
            .map(existingProperty -> {
                existingProperty.setAddress(property.getAddress());
                existingProperty.setPrice(property.getPrice());
                existingProperty.setSize(property.getSize());
                existingProperty.setDescription(property.getDescription());
                return propertyRepository.save(existingProperty);
            }).orElse(null);
    }
    
}
