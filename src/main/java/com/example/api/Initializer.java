package com.example.api;

import com.example.api.model.DriveFile;
import com.example.api.model.DriveFileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class Initializer implements CommandLineRunner {

    private DriveFileRepository driveFileRepository;

    public Initializer(DriveFileRepository driveFileRepository) {
        this.driveFileRepository = driveFileRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Add some initial data
         driveFileRepository.save(new DriveFile("1", "file1", "description1", "createdTime1", "modifiedTime1", "quotaBytesUsed1", "version1", "originalFilename1", "fileExtension1"));
         driveFileRepository.save(new DriveFile("2", "file2", "description2", "createdTime2", "modifiedTime2", "quotaBytesUsed2", "version2", "originalFilename2", "fileExtension2"));
         driveFileRepository.save(new DriveFile("3", "file3", "description3", "createdTime3", "modifiedTime3", "quotaBytesUsed3", "version3", "originalFilename3", "fileExtension3"));
    }
}
