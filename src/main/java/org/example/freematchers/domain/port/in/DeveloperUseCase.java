package org.example.freematchers.domain.port.in;

import org.example.freematchers.domain.model.Developer;

public interface DeveloperUseCase {
    Developer registeringANewDeveloper(Developer developer);
    Developer getDevById(Long id);
    Developer updateDeveloper(Developer developer, Long id);
    Developer updateSkillsDeveloper(Developer developer, Long id);
}
