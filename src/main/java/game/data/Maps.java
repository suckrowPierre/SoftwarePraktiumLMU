package game.data;

import com.google.gson.annotations.SerializedName;


public enum Maps {
    @SerializedName("Dizzy Highway")
    DizzyHighway("Dizzy Highway"),
    @SerializedName("Extra Crispy")
    ExtraCrispy("Extra Crispy"),
    @SerializedName("Lost Bearings")
    LostBearings("Lost Bearings"),
    @SerializedName("Death Trap")
    DeathTrap("Death Trap"),
    @SerializedName("Twister")
    Twister("Twister");

    private String name;

    private Maps(String name) {
        this.name = name;
    }

    public static Maps getByName(String v) {
        for (Maps m : Maps.values()) {
            if (v.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}
