package ba.unsa.etf.rpr.projekat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Service {

    private Integer serviceId;
    private String nameOfService;
    private LocalDate dateOfService;
    private String servicePerson;
    private String information;

    public Service(String nameOfService, LocalDate dateOfService, String servicePerson, String information) {
        this.nameOfService = nameOfService;
        this.dateOfService = dateOfService;
        this.servicePerson = servicePerson;
        this.information = information;
    }

    public Service() {
    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public LocalDate getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(LocalDate dateOfService) {
        this.dateOfService = dateOfService;
    }

    public String getServicePerson() {
        return servicePerson;
    }

    public void setServicePerson(String servicePerson) {
        this.servicePerson = servicePerson;
    }

    public String getInformationOfService() {
        return information;
    }

    public void setInformationOfService(String information) {
        this.information = information;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getServiceDateAsString(){
        return dateOfService.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
