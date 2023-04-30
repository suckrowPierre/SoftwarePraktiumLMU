package game.data.cards;

import com.google.gson.annotations.SerializedName;

public enum CardName {
    //Damage
    Spam,
    Trojan,
    Virus,
    Worm,
    //Programming
    Again,
    BackUp,
    MoveI,
    MoveII,
    MoveIII,
    PowerUp,
    TurnLeft,
    TurnRight,
    UTurn,
    //Special,
    EnergyRoutine,
    RepeatRoutine,
    SandboxRoutine,
    SpamFolder,
    SpeedRoutine,
    WeaselRoutine,
    Null,
    //Upgrade
    @SerializedName("ADMIN PRIVILEGE") AdminPrivilege,
    @SerializedName("REAR LASER") RearLaser,
    @SerializedName("MEMORY SWAP") MemorySwap,
    @SerializedName("SPAM BLOCKER") SpamBlocker


}
