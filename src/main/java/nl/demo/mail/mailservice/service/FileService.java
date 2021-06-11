package nl.demo.mail.mailservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: (functional) interface maken?
// met twee statische methoden
// met een abstracte methode: labda
@Slf4j
@Service
public class FileService {

    public File multipartFileToFile(MultipartFile body) throws IOException {
        File file = new File(Objects.requireNonNull(body.getOriginalFilename()));
        InputStream is = body.getInputStream();
        Files.copy(is, file.toPath());

        return file;
    }

    public List<File> multipartFileToFile(MultipartFile[] attach) throws IOException {
        List<File> fileList = new ArrayList<>();

        for (MultipartFile partFile : attach) {
            File file = new File(Objects.requireNonNull(partFile.getOriginalFilename()));
            InputStream is = partFile.getInputStream();
            Files.copy(is, file.toPath());
            fileList.add(file);
        }

        return fileList;
    }

}
