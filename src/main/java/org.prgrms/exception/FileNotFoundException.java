package org.prgrms.exception;

public class FileNotFoundException extends RuntimeException {

  public FileNotFoundException(String file) {
    super("File name : " + file + "not found");
  }
}
