package com.example.MyArtGallery.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;
    @Column()
    private String imageTitle;
    @Column()
    private Date uploadDate;
    @Column()
    private Integer likes;
    @Column(nullable = true,length = 500,unique = true)
    private String url;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "image_keyword",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywordList;

    @PrePersist
    protected void onCreate() {
        this.uploadDate = Date.valueOf(LocalDate.now());
    }

    public Image() {
    }

    public Image(int imageId, String imageTitle, Date uploadDate, Integer likes, String url, User user, List<Keyword> keywordList) {
        this.imageId = imageId;
        this.imageTitle = imageTitle;
        this.uploadDate = uploadDate;
        this.likes = likes;
        this.url = url;
        this.user = user;
        this.keywordList = keywordList;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Keyword> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<Keyword> keywordList) {
        this.keywordList = keywordList;
    }
    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        this.likes--;
    }
}
