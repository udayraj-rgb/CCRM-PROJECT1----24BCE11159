package edu.ccrm.io; 

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Service to create backups of exported data
public class BackupService {
    public void backupExports() {
        try {
            Path source = Paths.get("exports"); // folder to backup
            // If no exports folder exists, stop
            if (!Files.exists(source)) {
                System.out.println("No exports folder found to backup.");
                return;
            }
            String timestamp = LocalDateTime.now()
                               .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path target = Paths.get("backups", "backup_" + timestamp);
            Files.createDirectories(target);
            // Walk through all files and folders inside "exports"
            Files.walk(source).forEach(src -> {
                try {
                    Path dest = target.resolve(source.relativize(src));
                    if (Files.isDirectory(src)) {
                        Files.createDirectories(dest); 
                    } else {
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Backup created at " + target.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
