package com.example.MyArtGallery.Service;

import com.example.MyArtGallery.Model.Image;
import com.example.MyArtGallery.Model.Keyword;
import com.example.MyArtGallery.Repository.KeywordInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KeywordService {

    private final KeywordInterface keywordInterface;
    @Autowired
    public KeywordService(KeywordInterface keywordInterface) {
        this.keywordInterface = keywordInterface;
    }

    public List<Keyword> getAllKeyWords(){
        return keywordInterface.findAll();
    }

    public Keyword getKeywordByName(String keywordName){
        Optional<Keyword> optionalKeyword = keywordInterface.findByKeywordName(keywordName);
        return optionalKeyword.orElse(null);
    }

    public List<Keyword> saveAndGetAllKeywords(List<String> keywordNames) {
        // Retrieve existing keywords
        List<Keyword> existingKeywords = keywordInterface.findByKeywordNameIn(keywordNames);

        // Extract existing keyword names
        List<String> existingKeywordNames = existingKeywords.stream()
                .map(Keyword::getKeywordName)
                .toList();

        // Identify new keyword names
        List<String> newKeywordNames = keywordNames.stream()
                .filter(keywordName -> !existingKeywordNames.contains(keywordName))
                .toList();

        // Create and save new keywords
        List<Keyword> newKeywords = newKeywordNames.stream()
                .map(keywordName -> {
                    Keyword keyword = new Keyword();
                    keyword.setKeywordName(keywordName);
                    return keywordInterface.save(keyword);
                })
                .toList();

        // Combine existing and new keywords

        return Stream.concat(existingKeywords.stream(), newKeywords.stream())
                .collect(Collectors.toList());
    }
}
