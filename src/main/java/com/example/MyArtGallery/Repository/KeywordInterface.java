package com.example.MyArtGallery.Repository;

import com.example.MyArtGallery.Model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordInterface extends JpaRepository<Keyword, Integer> {
    Optional<Keyword> findByKeywordName(String keywordName);
    List<Keyword> findByKeywordNameIn(List<String> keywordName);
}
