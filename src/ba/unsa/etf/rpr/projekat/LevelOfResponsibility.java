package ba.unsa.etf.rpr.projekat;

public enum LevelOfResponsibility {
    ADMINISTRATOR(1),
    VEHICLE(2),
    STORAGE(3),
    SERVICE(4),
    EMPLOYEE(5),
    WORKER(6),
    DIRECTOR(7);

    private final int levelCode;

    private LevelOfResponsibility(int levelCode){
        this.levelCode = levelCode;
    }


    public int getLevelCode() {
        return this.levelCode;
    }

}
