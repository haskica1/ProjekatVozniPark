package ba.unsa.etf.rpr.projekat;

import java.util.List;

public class Storage {

    private Integer storageId;
    private String nameOfStorage;
    private User manager;
    private List<Part> parts;

    public Storage() {
    }

    public Storage(String nameOfStorage, User manager, List<Part> parts) {
        this.nameOfStorage = nameOfStorage;
        this.manager = manager;
        this.parts = parts;
    }

    public String getNameOfStorage() {
        return nameOfStorage;
    }

    public void setNameOfStorage(String nameOfStorage) {
        this.nameOfStorage = nameOfStorage;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public void addPart(Part part){
        parts.add(part);
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getNameOfManager(){
        return manager.getFirstName() + " " + manager.getLastName();
    }
}
