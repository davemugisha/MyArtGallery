package com.example.MyArtGallery.Controller;

import com.example.MyArtGallery.Model.Keyword;
import com.example.MyArtGallery.Service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/keywords")
public class KeywordController {

    private final KeywordService keywordService;
    @Autowired
    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Keyword>> getAllKeywords() {
        List<Keyword> keywords = keywordService.getAllKeyWords();

        if (!keywords.isEmpty()) {
            return ResponseEntity.ok(keywords);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
