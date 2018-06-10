package com.wilsonfranca.kalaha.auth.person;

import com.wilsonfranca.kalaha.auth.SignupFormData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private PersonRepository personRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Person register(SignupFormData signupFormData) {

        Person person = Person.from(signupFormData);
        person.setPassword(bCryptPasswordEncoder.encode(signupFormData.getPassword()));
        Optional<Person> persisted = personRepository.findByEmail(person.getEmail());

        persisted.ifPresent(p -> {
            throw new PersonExistsException();
        });

        Person toPersist = personRepository.save(person);

        logger.info("New User [{}] registered with id [{}]", person.getEmail(), person.getId());

        return toPersist;
    }

    public Optional<Person> getByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
