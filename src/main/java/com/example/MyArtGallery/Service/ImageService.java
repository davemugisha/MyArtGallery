package com.example.MyArtGallery.Service;

import com.example.MyArtGallery.Model.Image;
import com.example.MyArtGallery.Model.Keyword;
import com.example.MyArtGallery.Model.User;
import com.example.MyArtGallery.Repository.ImageInterface;
import com.example.MyArtGallery.Repository.KeywordInterface;
import com.example.MyArtGallery.Repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageInterface imageInterface;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private UserInterface userInterface;

    @Autowired
    private KeywordInterface keywordInterface;

    // Saving in Offline Mode (Working!!!)
    // private String IMAGE_FOLDER = "C:\\Users\\david\\Desktop\\MyArtGallery\\Images";

    // Save in Online Mode (Not Working, I think)
    private String IMAGE_FOLDER = "..\\..\\..\\..\\Images";

    public boolean uploadImage(MultipartFile file, String title, int user_id, List<Keyword> keywords) throws IOException {
        Image img = new Image();
        Optional<User> optionalUser = userInterface.findById(user_id);
        img.setImageTitle(title);
        img.setLikes(0);

        if (optionalUser.isPresent()) {
            img.setUser(optionalUser.get());
        }

        if(!keywords.isEmpty()){
            img.setKeywordList(keywords);
        }

        img = imageInterface.save(img);

        if (img != null) {
            String originalFileName = file.getOriginalFilename();
            String fileName = img.getImageId() + "_" + originalFileName;

            img.setUrl(fileName);
            imageInterface.save(img);
            file.transferTo(new File(IMAGE_FOLDER, fileName));

            return true;
        } else {
            return false;
        }
    }

    public Resource downloadImage(String imageUrl) throws MalformedURLException {
        Path filePath = Paths.get(IMAGE_FOLDER).resolve(imageUrl).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            return resource;
        } else {
            throw new RuntimeException("Image not found: " + imageUrl);
        }
    }

    public void incrementLikes(int imageId) {
        Optional<Image> optionalImage = imageInterface.findById(imageId);
        optionalImage.ifPresent(image -> {
            image.incrementLikes();
            imageInterface.save(image);
        });
    }

    public void deleteImage(int imageId) {
        Optional<Image> optionalImage = imageInterface.findById(imageId);
        optionalImage.ifPresent(image -> {
            deleteImageFile(image.getUrl(), IMAGE_FOLDER);
            imageInterface.deleteById(imageId);
        });
    }

    private void deleteImageFile(String imageUrl, String imageFolder) {
        try {
            Path imagePath = Paths.get(imageFolder, imageUrl);
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Image> filterByUser(User user){
        return imageInterface.findByUser(user);
    }

    public Image getImageById(int image_id){
        Optional<Image> optionalImage = imageInterface.findById(image_id);
        return optionalImage.orElse(null);
    }

    public List<Image> getImagesByKeyword(String keyword) {
        Keyword keywordEntity = keywordService.getKeywordByName(keyword);

        return imageInterface.findByKeywordListContaining(keywordEntity);

    }

    public void decrementLikes(int imageId) {
        Optional<Image> optionalImage = imageInterface.findById(imageId);
        optionalImage.ifPresent(image -> {
            image.decrementLikes();
            imageInterface.save(image);
        });
    }
}
