package com.admission.utils;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.SneakyThrows;

public class ResourceUtil {

    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

    private static final Path RESOURCES_PATH = CURRENT_FOLDER.resolve(Paths.get("src/main/resources"));

    public static ImageIcon getAvatar(String path) {
        return new ImageIcon(RESOURCES_PATH.resolve(path).toString());
    }

    public static File getFileByPath(String pathFile) {
        Path path = RESOURCES_PATH.resolve(Paths.get(pathFile));
        return path.toFile();
    }

    public static ImageIcon getImageIcon(String path) {
        return new ImageIcon(RESOURCES_PATH.resolve("images").resolve(path).toString());
    }

    @SneakyThrows
    public static String saveFile(String newFileName, String uploadPath, File file) {
        Path path = RESOURCES_PATH.resolve(Paths.get(uploadPath));
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path filePath;
        try (InputStream inputStream = new FileInputStream(file)) {
            String fileName = file.getName();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String newFile = newFileName + fileType;
            filePath = path.resolve(newFile);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return uploadPath + "/" + newFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileExtension(File file) {
        String extension = null;
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }

    public static boolean isImageExtension(String extension) {
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }

    public static boolean isExcelExtension(String extension) {
        return extension.equals("xlsx");
    }

}
