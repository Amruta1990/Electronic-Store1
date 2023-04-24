package com.happytech.electronicstore.Service.Impl;

import com.happytech.electronicstore.Service.FileService;
import com.happytech.electronicstore.exception.BadApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static java.io.File.separator;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        log.info("/filename ={}", originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = filename + extension;
        String fullpathwithFileName = path+ separator + fileNameWithExtension;
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){


            //file save

            File folder=new File(path);
            if(!folder.exists()){
                //ctreate the folder
                folder.mkdirs();
            }

            //upload

            Files.copy(file.getInputStream(), Paths.get(fullpathwithFileName));
            return fileNameWithExtension;


    }
    else{
        throw new BadApiRequest("File with this" + extension + "not allowed !!");

        }



    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;



        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
