package com.example.truyenfull.controller;

import com.example.truyenfull.model.Chaper;
import com.example.truyenfull.repository.ChapterRepository;
import com.example.truyenfull.util.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.truyenfull.util.ResponseUtil.returnListChapter;

@RestController
@RequestMapping("/api")
public class ChaperController {
    @Autowired
    ChapterRepository chapterRepository;

    @GetMapping(value = "/chapter",produces = "application/json")
    public String getAllChapter() throws JsonProcessingException {
        return returnListChapter(chapterRepository.findAll()).toString();
    }

    @PostMapping("/chapter")
    public Chaper createChapter(@Valid @RequestBody Chaper chaper){
            return chapterRepository.save(chaper);
    }


}
