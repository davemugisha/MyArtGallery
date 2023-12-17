package com.example.MyArtGallery.Repository;

import com.example.MyArtGallery.Model.Image;
import com.example.MyArtGallery.Model.Keyword;
import com.example.MyArtGallery.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageInterface extends JpaRepository<Image,Integer> {
    List<Image> findByUser(User user);
    List<Image> findByKeywordListContaining(Keyword keyword);
}
