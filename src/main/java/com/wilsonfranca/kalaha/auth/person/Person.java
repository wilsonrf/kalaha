package com.wilsonfranca.kalaha.auth.person;

import com.wilsonfranca.kalaha.auth.SignupFormData;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Document
public class Person {

    @Id
    private ObjectId id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private boolean accountLocked;

    private boolean accountExpired;

    private boolean credentialsExpired;

    private boolean enabled;

    private List<String> authorities;

    public Person() {
        this.accountExpired = false;
        this.accountLocked = false;
        this.credentialsExpired = false;
        this.enabled = true;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAuthorities() {
        if(Objects.isNull(authorities)) {
            authorities = new ArrayList<>();
        }
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void addAuthorities(String... authority) {
        Arrays.asList(authority)
                .stream()
                .map(s -> s.startsWith("ROLE") ? s : "ROLE_"+s)
                .forEach(s -> getAuthorities().add(s));
    }

    public static Person from(SignupFormData signupFormData) {

        Person person = new Person();
        person.setEmail(signupFormData.getEmail());
        person.setPassword(signupFormData.getPassword());
        person.setFirstName(signupFormData.getFirstName());
        person.setLastName(signupFormData.getLastName());
        person.addAuthorities("USER", "PLAYER");

        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
