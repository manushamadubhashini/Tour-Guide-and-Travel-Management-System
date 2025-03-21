package lk.ijse.thetouristguideandtravelmanagementsystembackend.util;

import lk.ijse.thetouristguideandtravelmanagementsystembackend.advicer.FileConversionException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

/**
 * Utility class for data conversion operations in the Tourist Guide and Travel Management System.
 * Provides methods for converting between different data formats, such as files to Base64.
 */
public class DataConversionUtil {

    /**
     * Converts a MultipartFile to a Base64 encoded string.
     *
     * @param file The MultipartFile to convert
     * @return Base64 encoded string representation of the file
     * @throws FileConversionException if the file cannot be converted
     */
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

    /**
     * Converts a Base64 encoded string back to a byte array.
     *
     * @param base64String The Base64 encoded string
     * @return Byte array of the decoded content
     * @throws FileConversionException if the string cannot be decoded
     */
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
            return "";
        }

        String fileName = file.getOriginalFilename();
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }
    public static MultipartFile convertToMultipartFile(String base64Image) {
        try {
            if (base64Image == null || base64Image.isEmpty()) {
                return null;
            }

            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            if (imageBytes.length == 0) {
                return null;
            }

            return new MockMultipartFile(
                    "image",
                    "image.jpg",
                    "image/jpeg",
                    imageBytes
            );
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}