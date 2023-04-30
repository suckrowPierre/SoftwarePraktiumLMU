package communication.protocol_v2v1.sendobjects.bodycontents;

import com.google.gson.annotations.SerializedName;

public enum Protocols {
    @SerializedName("Version 0.1") V0V1("Version 0.1"),
    @SerializedName("Version 0.2") V0V2("Version 0.2"),
    @SerializedName("Version 1.0") V1V0("Version 1.0"),
    @SerializedName("Version 2.0") V2V0("Version 2.0"),
    @SerializedName("Version 2.1") V2V1("Version 2.1");

    private String version;


    private Protocols(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return this.version;
    }

    public static Protocols getbyVersion(String v){
        for (Protocols p : Protocols.values()){
            if(v.equals(p.version)){
                return p;
            }

        }
        return null;
    }
}
