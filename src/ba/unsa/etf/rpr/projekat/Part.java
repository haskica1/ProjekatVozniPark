package ba.unsa.etf.rpr.projekat;

public class Part {
    private int partId;
    private String nameOfPart;
    private Boolean isUseful;


    public Part() {
    }

    public Part(int partId, String nameOfPart, Boolean isUseful) {
        this.partId = partId;
        this.nameOfPart = nameOfPart;
        this.isUseful = isUseful;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public String getNameOfPart() {
        return nameOfPart;
    }

    public void setNameOfPart(String nameOfPart) {
        this.nameOfPart = nameOfPart;
    }

    public Boolean isUsefulPart() {
        return isUseful;
    }

    public void setUsefulPart(Boolean useful) {
        isUseful = useful;
    }

    public String getUsefulByString() {
        if(isUseful)    return "USEFUL";
        return "NOT USEFUL";
    }

    @Override
    public String toString() {
        return partId + " " + nameOfPart;
    }
}
