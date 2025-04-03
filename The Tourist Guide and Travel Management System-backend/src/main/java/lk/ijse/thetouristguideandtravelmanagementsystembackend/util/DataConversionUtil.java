package lk.ijse.thetouristguideandtravelmanagementsystembackend.util;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

/**
 * Utility class for data conversion operations in the Tourist Guide and Travel Management System.
 * Provides methods for converting between different data formats, managing file storage operations.
 */
public class DataConversionUtil {

    // Define the directory where images will be stored
    private static final String IMAGE_UPLOAD_PATH = System.getProperty("user.home") + "/touristapp/upload/tour-images/";

    /**
     * Saves a MultipartFile to the file system and returns the file path
     *
     * @param file The MultipartFile to save
     * @return String containing the relative path to the saved file
     * @throws FileConversionException if the file cannot be saved
     */
    public static String saveToFileSystem(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            // Create directories if they don't exist
            File directory = new File(IMAGE_UPLOAD_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename to prevent collisions
            String fileExtension = getFileExtension(file);
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            String filePath = IMAGE_UPLOAD_PATH + fileName;

            // Save the file
            File destFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(destFile)) {
                fos.write(file.getBytes());
            }
            if (destFile.exists()) {
                System.out.println("File saved successfully at: " + destFile.getAbsolutePath());
            } else {
                System.out.println("Failed to save file at: " + destFile.getAbsolutePath());
            }

            return filePath;

        } catch (IOException e) {
            throw new FileConversionException("Failed to save file to file system", e);
        }
    }

    /**
     * Deletes a file from the file system
     *
     * @param filePath The path of the file to delete
     * @return true if successfully deleted, false otherwise
     */
    public static boolean deleteFromFileSystem(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    /**
     * Converts a file path to a MultipartFile for use in DTOs
     *
     * @param filePath The path of the file to convert
     * @return MultipartFile representation of the file
     */
    public static MultipartFile pathToMultipartFile(String filePath) {
        try {
            if (filePath == null || filePath.isEmpty()) {
                return null;
            }

            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                return null;
            }

            String fileName = path.getFileName().toString();
            String contentType = determineContentType(fileName);
            byte[] content = Files.readAllBytes(path);

            return new MockMultipartFile(
                    "image",
                    fileName,
                    contentType,
                    content
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Determines the content type based on file extension
     *
     * @param fileName The name of the file
     * @return The appropriate content type
     */
    private static String determineContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.toLowerCase().endsWith(".png")) {
            return "image/png";
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }

    /**
     * Validates if the file is an image with supported format.
     *
     * @param file The file to validate
     * @return true if the file is a supported image type, false otherwise
     */
    public static boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png")
        );
    }

    /**
     * Gets the file extension from a MultipartFile.
     *
     * @param file The MultipartFile
     * @return The file extension (e.g., "jpg", "png")
     */
    public static String getFileExtension(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return "jpg"; // Default extension
        }

        String fileName = file.getOriginalFilename();
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "jpg"; // Default extension
    }

    // Keep these methods for backward compatibility or additional functionality
    public static String toBase64(MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }
            byte[] fileContent = file.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new FileConversionException("Failed to convert file to Base64", e);
        }
    }

    public static byte[] fromBase64(String base64String) {
        try {
            if (base64String == null || base64String.isEmpty()) {
                return new byte[0];
            }
            return Base64.getDecoder().decode(base64String);
        } catch (IllegalArgumentException e) {
            throw new FileConversionException("Failed to decode Base64 string", e);
        }
    }
    public static String fileToBase64(String filePath) {
        try {
            if (filePath == null || filePath.isEmpty()) {
                throw new FileConversionException("File path is null or empty");
            }

            Path path = Paths.get(filePath);
            if (!Files.exists(path) || !Files.isReadable(path)) {
                throw new FileConversionException("File does not exist or is not readable: " + filePath);
            }

            byte[] fileContent = Files.readAllBytes(path);
            if (fileContent.length == 0) {
                throw new FileConversionException("File is empty: " + filePath);
            }

            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new FileConversionException("Failed to convert file to base64: " + filePath, e);
        }
    }
}