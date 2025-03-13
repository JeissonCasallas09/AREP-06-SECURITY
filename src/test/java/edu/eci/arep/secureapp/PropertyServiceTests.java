package edu.eci.arep.secureapp;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import edu.eci.arep.secureapp.model.Property;
import edu.eci.arep.secureapp.repository.PropertyRepository;
import edu.eci.arep.secureapp.service.PropertyService;

class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProperty_Success() {
        Property property = new Property( "Calle 123", 100000.0, "100m2", "Casa moderna");
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property createdProperty = propertyService.createProperty(property);

        assertNotNull(createdProperty);
        assertEquals("Calle 123", createdProperty.getAddress());
        assertEquals(100000.0, createdProperty.getPrice());
        assertEquals("100m2", createdProperty.getSize());
        assertEquals("Casa moderna", createdProperty.getDescription());
    }

    @Test
    void testGetAllProperties_Success() {
        List<Property> properties = Arrays.asList(
                new Property("Calle 123", 100000.0, "100m2", "Casa moderna"),
                new Property("Avenida 456", 150000.0, "120m2", "Apartamento")
        );
        when(propertyRepository.findAll()).thenReturn(properties);

        Iterable<Property> result = propertyService.getAllProperties();

        assertNotNull(result);
        assertEquals(2, ((List<Property>) result).size());
    }


    @Test
    void testUpdateProperty_NotFound() {
        Property updatedProperty = new Property("Calle 456", 120000.0, "110m2", "Casa remodelada");

        when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Property result = propertyService.updateProperty(updatedProperty);

        assertNull(result);
    }

    @Test
    void testDeleteProperty_Success() {
        doNothing().when(propertyRepository).deleteById(anyLong());

        propertyService.deleteProperty(1L);

        verify(propertyRepository, times(1)).deleteById(anyLong());
    }

    
}
