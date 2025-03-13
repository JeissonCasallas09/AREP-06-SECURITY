package edu.eci.arep.secureapp;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import edu.eci.arep.secureapp.controller.PropertyController;
import edu.eci.arep.secureapp.model.Property;
import edu.eci.arep.secureapp.service.PropertyService;

class PropertyControllerTest{

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private PropertyController propertyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProperty_Success() {
        Property property = new Property("Calle 123", 100000.0, "100m2", "Casa moderna");
        when(propertyService.createProperty(any(Property.class))).thenReturn(property);

        ResponseEntity<Property> response = propertyController.createProperty(property);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Calle 123", response.getBody().getAddress());
        assertEquals(100000.0, response.getBody().getPrice());
        assertEquals("100m2", response.getBody().getSize());
        assertEquals("Casa moderna", response.getBody().getDescription());
    }

    @Test
    void testCreateProperty_BadRequest() {
        when(propertyService.createProperty(any(Property.class))).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Property> response = propertyController.createProperty(new Property());

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetPropertyById_Success() {
        Property property = new Property("Calle 123", 100000.0, "100m2", "Casa moderna");
        when(propertyService.getPropertyById(anyLong())).thenReturn(property);

        ResponseEntity<Property> response = propertyController.getPropertyById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Calle 123", response.getBody().getAddress());
        assertEquals(100000.0, response.getBody().getPrice());
        assertEquals("100m2", response.getBody().getSize());
        assertEquals("Casa moderna", response.getBody().getDescription());
    }

    @Test
    void testGetPropertyById_NotFound() {
        when(propertyService.getPropertyById(anyLong())).thenThrow(NoSuchElementException.class);

        ResponseEntity<Property> response = propertyController.getPropertyById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetAllProperties_Success() {
        List<Property> properties = Arrays.asList(
                new Property("Calle 123", 100000.0, "100m2", "Casa moderna"),
                new Property("Avenida 456", 150000.0, "120m2", "Apartamento")
        );
        when(propertyService.getAllProperties()).thenReturn(properties);

        ResponseEntity<Iterable<Property>> response = propertyController.getAllProperties();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateProperty_Success() {
        Property updatedProperty = new Property("Calle 789", 120000.0, "110m2", "Casa remodelada");
        when(propertyService.updateProperty(any(Property.class))).thenReturn(updatedProperty);

        ResponseEntity<Property> response = propertyController.updateProperty(1L, updatedProperty);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Calle 789", response.getBody().getAddress());
        assertEquals(120000.0, response.getBody().getPrice());
        assertEquals("110m2", response.getBody().getSize());
        assertEquals("Casa remodelada", response.getBody().getDescription());
    }

    @Test
    void testUpdateProperty_NotFound() {
        when(propertyService.updateProperty(any(Property.class))).thenThrow(NoSuchElementException.class);

        ResponseEntity<Property> response = propertyController.updateProperty(1L, new Property());

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteProperty_Success() {
        doNothing().when(propertyService).deleteProperty(anyLong());

        ResponseEntity<Void> response = propertyController.deleteProperty(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteProperty_NotFound() {
        doThrow(NoSuchElementException.class).when(propertyService).deleteProperty(anyLong());

        ResponseEntity<Void> response = propertyController.deleteProperty(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
