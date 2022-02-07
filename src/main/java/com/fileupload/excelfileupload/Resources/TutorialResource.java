package com.fileupload.excelfileupload.Resources;

import com.fileupload.excelfileupload.Helpers.ExcelHelper;
import com.fileupload.excelfileupload.Models.Tutorial.Tutorial;
import com.fileupload.excelfileupload.Models.Tutorial.TutorialService;
import com.fileupload.excelfileupload.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("/api/tutorials")
@Controller
public class TutorialResource {
    @Autowired
    TutorialService tutorialService;

    @PostMapping("upload")
    public ResponseEntity<Response> uploadFile(@RequestParam("file") MultipartFile file){
        String message = "";
        if (ExcelHelper.hasExcelFormat(file)){
            try{
                tutorialService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new Response(message));
            }catch (Exception e){
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(message));
    }

    @GetMapping("all")
    public ResponseEntity<List<Tutorial>> getAllTutorials(){
        try{
            List<Tutorial> tutorials = tutorialService.getAllTutorials();
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
