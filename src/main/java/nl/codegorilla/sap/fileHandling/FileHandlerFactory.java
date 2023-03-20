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
            case "sql" -> {
                return new SQLiteHandler();
            }
            default -> throw new InvalidFileException("Cannot get file type : " + type);
        }
    }
}