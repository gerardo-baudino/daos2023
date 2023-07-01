package com.tsti.dto;

import com.tsti.entity.Client;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class ClientDTO extends RepresentationModel<ClientDTO> {
    private Long id;
    private Long document;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private LocalDate birthDate;
    private String passportNumber;
    private LocalDate passportExpirationDate;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.document = client.getDocument();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.address = client.getAddress();
        this.email = client.getEmail();
        this.birthDate = client.getBirthDate();
        this.passportNumber = client.getPassportNumber();
        this.passportExpirationDate = client.getPassportExpirationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocument() {
        return document;
    }

    public void setDocument(Long document) {
        this.document = document;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(LocalDate passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "document=" + document +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", passportNumber='" + passportNumber + '\'' +
                ", passportExpirationDate=" + passportExpirationDate +
                '}';
    }
}
