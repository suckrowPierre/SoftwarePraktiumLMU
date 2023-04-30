package util;

public enum Filenames {  JSONUserClientOutputMessages("communication/client/message_outputs/UserClientOutputMessages.json"),
  JSONServerOutputMessages("communication/server/message_outputs/ServerOutputMessages.json");

  private String filepath;

  private Filenames(String filepath) {
    this.filepath = filepath;
  }

  public String getFilepath() {
    return this.filepath;
  }
}
