package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.exception.InvalidFileException;

public class FileHandlerFactory {

    public FileHandler createFileHandler(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        switch (type) {
            case "csv" -> {
                return new CSVHandler();
            }
            case "db" -> {
                return new SQLiteHandler();
            }
            default -> throw new InvalidFileException("Incorrect file type: " + type);
        }
    }
}