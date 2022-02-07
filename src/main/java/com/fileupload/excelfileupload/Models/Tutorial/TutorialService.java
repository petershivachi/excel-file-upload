package com.fileupload.excelfileupload.Models.Tutorial;

import com.fileupload.excelfileupload.Helpers.ExcelHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TutorialService {
    @Autowired TutorialRepository tutorialRepository;

    public void save(MultipartFile file){
        try {
            List<Tutorial> tutorials = ExcelHelper.excelToTutorial(file.getInputStream());
            tutorialRepository.saveAll(tutorials);
        }catch (IOException e){
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    public List<Tutorial> getAllTutorials(){
        return tutorialRepository.findAll();
    }
}
