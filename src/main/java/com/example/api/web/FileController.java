package com.example.api.web;

import com.example.api.model.DriveFile;
import com.example.api.model.DriveFileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

// Metadata only requests
@RestController
public class FileController {

    private DriveFileRepository driveFileRepository;

    public FileController(DriveFileRepository driveFileRepository) {
        this.driveFileRepository = driveFileRepository;
    }

    @PostMapping("/file")
    public DriveFile createFile(DriveFile file){
        return driveFileRepository.save(file);
    }

    @GetMapping("/file/{id}")
    @PreAuthorize("@fga.check('file', #id, 'viewer', 'user')")
    public DriveFile getFile(@PathVariable @P("id") String id){
        return driveFileRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable String id){
        driveFileRepository.deleteById(id);
    }

    @PutMapping("/file/{id}")
    public ResponseEntity<DriveFile> updateFile(@PathVariable String id, @RequestBody DriveFile file){
        return driveFileRepository.findById(id).map(update -> {
            update.setName(file.getName());
            update.setDescription(file.getDescription());
            update.setCreatedTime(file.getCreatedTime());
            update.setModifiedTime(file.getModifiedTime());
            update.setQuotaBytesUsed(file.getQuotaBytesUsed());
            update.setVersion(file.getVersion());
            update.setOriginalFilename(file.getOriginalFilename());
            update.setFileExtension(file.getFileExtension());
            DriveFile result = driveFileRepository.save(update);
            return ResponseEntity.ok().body(result);
        }).orElse(ResponseEntity.notFound().build());
    }


}
