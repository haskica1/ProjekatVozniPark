package ba.unsa.etf.rpr.projekat;

public class Equipment {

    private Integer equipmentId;
    private String nameOfEquipment;
    private String information;

    public Equipment(String nameOfEquipment, String information) {
        this.nameOfEquipment = nameOfEquipment;
        this.information = information;
    }

    public String getNameOfEquipment() {
        return nameOfEquipment;
    }

    public void setNameOfEquipment(String nameOfEquipment) {
        this.nameOfEquipment = nameOfEquipment;
    }

    public String getInformationOfEquipment() {
        return information;
    }

    public void setInformationOfEquipment(String information) {
        this.information = information;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return equipmentId + " " + nameOfEquipment + " " + information;
    }
}
