package com.uniquindio.reporte;


import com.uniquindio.reporte.controllers.UserController;
import com.uniquindio.reporte.model.DTO.user.register.*;
import com.uniquindio.reporte.model.entities.User;
import com.uniquindio.reporte.service.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    //private UserService userService = Mockito.mock(UserService.class);


    @Test
    void testCreateUser() throws Exception {
        // Arrange
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "1", "123456789", "John Doe", "Medellín", "3001234567",
                "123 Main St", "johndoe@example.com", "Password123@"
        );
        User user = new User();
        user.setName("John Doe");

        doNothing().when(userService).createUser(createUserDTO);
        verify(userService, times(1)).createUser(createUserDTO);

        // Act
        ResponseEntity<User> response = (ResponseEntity<User>) userController.createUser(createUserDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        verify(userService, times(1)).createUser(createUserDTO);
    }


    @Test
    void testChangeUserPassword() throws Exception {
        // Arrange
        ChangeUserPassword changePasswordDto = new ChangeUserPassword("123456789", "NewPassword123@");

        doNothing().when(userService).changeUserPassword(changePasswordDto);

        // Act
        ResponseEntity<String> response = (ResponseEntity<String>) userController.changeUserPassword(changePasswordDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Contraseña actualizada correctamente", response.getBody());
        verify(userService, times(1)).changeUserPassword(changePasswordDto);
    }
}