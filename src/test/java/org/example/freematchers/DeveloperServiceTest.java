package org.example.freematchers;

import org.example.freematchers.dto.request.DeveloperRequest;
import org.example.freematchers.dto.response.DeveloperResponse;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.mapper.DeveloperMapper;
import org.example.freematchers.model.Developer;
import org.example.freematchers.repository.DeveloperRepository;
import org.example.freematchers.service.DeveloperService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @InjectMocks
    private DeveloperService developerService;

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private DeveloperMapper developerMapper;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Nested
    class registeringANewDeveloper{

        //Teste do metodo registeringANewDeveloper, verificando se o processo de requisição -> entidade é feito corretamente.
        @Test
        void deveCriarDesenvolvedorComSucesso(){

            List<String> skills = List.of("Java", "Spring Boot", "SQL");

            DeveloperRequest request = new DeveloperRequest(
                    1L,
                    "Carlos",
                    "carlos@gmail.com",
                    "11332211",
                    30,
                    skills
            );

            when(passwordEncoder.encode("11332211")).thenReturn("encryptedPassword");
            when(developerMapper.developerRequestToDeveloper(request)).thenReturn(buildDeveloper(request));

            developerService.registeringANewDeveloper(request);

            ArgumentCaptor<Developer> developerCaptor = ArgumentCaptor.forClass(Developer.class);
            verify(developerRepository).save(developerCaptor.capture());

            Developer developerCaptured = developerCaptor.getValue();
            assertEquals("encryptedPassword", developerCaptured.getPassword());

        }


        @Test
        void deveEncontrarDevPeloId(){

            List<String> skills = List.of("Java", "Spring Boot", "SQL");

            var developer = new Developer(
                    1L,
                    "Carlos",
                    "carlos@gmail.com",
                    "11332211",
                    30,
                    skills
            );

            var expectedResponse = new DeveloperResponse(
                    developer.getName(),
                    developer.getEmail(),
                    developer.getWorkload(),
                    developer.getSkills()
            );

            when(developerRepository.findById(1L)).thenReturn(Optional.of(developer));
            when(developerMapper.developerToDeveloperResponse(developer)).thenReturn(expectedResponse);

            Long id = 1L;

            DeveloperResponse response = developerService.getDevById(id);

            assertEquals(expectedResponse, response);

            verify(developerRepository).findById(id);

        }

        @Test
        void deveLancarIdNotFoundExceptionQuandoIdNaoFoiEncontrado(){

            Long id = 99L;

            when(developerRepository.findById(id)).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> developerService.getDevById(id));

            verify(developerRepository).findById(id);

        }

    }

    private Developer buildDeveloper(DeveloperRequest request) {
        Developer dev = new Developer();
        dev.setEmail(request.email());
        dev.setName(request.name());
        dev.setSkills(request.skills());
        return dev;
    }

}
