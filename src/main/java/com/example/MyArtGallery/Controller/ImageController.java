package com.example.MyArtGallery.Controller;

import com.example.MyArtGallery.Model.Image;
import com.example.MyArtGallery.Model.Keyword;
import com.example.MyArtGallery.Service.ImageService;
import com.example.MyArtGallery.Service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    private final ImageService imageService;
    private final KeywordService keywordService;

    @Autowired
    public ImageController(ImageService imageService, KeywordService keywordService) {
        this.imageService = imageService;
        this.keywordService = keywordService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @RequestParam("title") String title,
                                              @RequestParam("user_id") Integer user_id,
                                              @RequestParam("keywords") List<String> keywordNames) {
        List<Keyword> keywordsList = keywordService.saveAndGetAllKeywords(keywordNames);

        try {
            boolean result = imageService.uploadImage(file,title,user_id,keywordsList);
            if (result) {
                return ResponseEntity.ok("Image uploaded successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to upload image");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @GetMapping(value = "/download/{imageUrl:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> downloadImage(@PathVariable String imageUrl) {
        try {
            Resource resource = imageService.downloadImage(imageUrl);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable int imageId) {
        imageService.deleteImage(imageId);
        Image img = imageService.getImageById(imageId);

        if (img == null) {
            return ResponseEntity.ok("Image deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found");
        }
    }

    @PostMapping("/{imageId}/like")
    public ResponseEntity<String> likeImage(@PathVariable int imageId) {
        imageService.incrementLikes(imageId);
        return ResponseEntity.ok("Image liked successfully");
    }

    @PostMapping("/{imageId}/dislike")
    public ResponseEntity<String> dislikeImage(@PathVariable int imageId) {
        imageService.decrementLikes(imageId);
        return ResponseEntity.ok("Image disliked successfully");
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> searchImagesByKeyword(@PathVariable String keyword) {
        List<Image> images = imageService.getImagesByKeyword(keyword);

        if (!images.isEmpty()) {
            return ResponseEntity.ok(images);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.EMPTY_LIST);
        }
    }


}
