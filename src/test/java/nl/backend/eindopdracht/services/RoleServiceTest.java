package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.order.OrderOutputDto;
import nl.backend.eindopdracht.dtos.role.RoleOutputDto;
import nl.backend.eindopdracht.models.Role;
import nl.backend.eindopdracht.models.User;
import nl.backend.eindopdracht.repositories.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleService roleService;

    List<Role> roles = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Role role1 = new Role();
        role1.setId(1L);
        role1.setRoleName("MANAGER");
        roles.add(role1);

        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("MANAGER");
        roles.add(role2);

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setFName("John");
        user.setMName("T");
        user.setLName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setAddress("Scheldestraat");
        user.setZipcode("1001 BV");
        user.setResidence("Amsterdam");
        user.setPNumber("06-52894575");
        user.setHouseNumber("12A");
        user.setEnabled(true);

        Set<User> users = new HashSet<>();
        users.add(user);
        role1.setUsers(users);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllRoles() {
        when(roleRepository.findAll()).thenReturn(roles);
        //Act
        List<RoleOutputDto> result = roleService.getAllRoles();
        //Assert
        assertEquals(roles.size(), result.size());
    }


}