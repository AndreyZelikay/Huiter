package bel.huiter.services;

import bel.huiter.dao.PhotoDAO;
import bel.huiter.dao.PhotoDAOImpl;
import bel.huiter.exceptions.PhotoUploadException;
import bel.huiter.models.Photo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.*;

public class PhotoService {

    private final PhotoDAO photoDAO;
    private final Cloudinary cloudinary;

    public PhotoService() throws IOException {
        Properties cfg = new Properties();
        cfg.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("cloudinary.cfg.properties")));
        cloudinary = new Cloudinary(cfg);
        photoDAO = new PhotoDAOImpl();
    }

    public Set<Photo> uploadAll(List<String> base64Images) throws PhotoUploadException {
        Set<Photo> photos = new HashSet<>();

        List<String> errors = new ArrayList<>();

        int index = 0;
        for(String base64Photo: base64Images) {
            try {
                photos.add(this.upload(base64Photo));
            } catch (PhotoUploadException ignored) {
                errors.add("unable to upload" + index + "photo");
            }
        }

        if(!errors.isEmpty()) {
            String errorMessage = String.join( "\n", errors);
            throw new PhotoUploadException(errorMessage);
        }

        return photos;
    }

    public Photo upload(String base64Image) throws PhotoUploadException {
        Map<?,?> params = ObjectUtils.asMap(
                "overwrite", true,
                "format", "png",
                "resource_type", "image"
        );
        Map<?,?> uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(base64Image, params);
        } catch (IOException e) {
            throw new PhotoUploadException();
        }
        Photo photo = new Photo();
        photo.setUrl(uploadResult.get("url").toString());
        photo.setPublicId(uploadResult.get("public_id").toString());
        return photo;
    }

    public void save(Photo photo) {
        photoDAO.save(photo);
    }

    public Optional<Photo> findById(long id) {
        return photoDAO.findById(id);
    }

    public void update(Photo photo, String base64Image) throws IOException {
        Map<?,?> params = ObjectUtils.asMap(
                "public_id", photo.getPublicId(),
                "overwrite", true,
                "format", "png",
                "resource_type", "image"
        );
        Map<?,?> uploadResult = cloudinary.uploader().upload(base64Image, params);
        photo.setUrl(uploadResult.get("url").toString());
        photoDAO.update(photo);
    }

    public void delete(Photo photo) throws IOException {
        cloudinary.uploader().destroy(photo.getPublicId(), null);
        photoDAO.delete(photo);
    }
}
