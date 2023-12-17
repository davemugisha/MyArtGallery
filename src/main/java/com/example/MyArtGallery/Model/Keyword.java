package com.example.MyArtGallery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "keyword")
public class Keyword{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int keywordId;
    @Column(nullable = false)
    private String keywordName;
    @JsonIgnore
    @ManyToMany(mappedBy = "keywordList", fetch = FetchType.LAZY)
    private List<Image> imageList;
    public Keyword() {
    }

    public Keyword(int keywordId, String keywordName, List<Image> imageList) {
        this.keywordId = keywordId;
        this.keywordName = keywordName;
        this.imageList = imageList;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
