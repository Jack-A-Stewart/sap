package nl.codegorilla.sap.fileHandling;

import nl.codegorilla.sap.exception.InvalidFileException;

public class FileHandlerFactory {

    public FileHandler createFileHandler(String type) {
        if (type == null || type.isEmpty()) {
            return null;
        }
        switch (type) {
            case "text/csv" -> {
                return new CSVHandler();
            }
            case "application/octet-stream" -> {
                return new SQLiteHandler();
            }
            default -> throw new InvalidFileException("Incorrect file type: " + type);
        }
    }
}