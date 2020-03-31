package bel.huiter.Services;

import bel.huiter.DAO.PhotoDAO;
import bel.huiter.DAO.PhotoDAOImpl;
import bel.huiter.models.Photo;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

public class PhotoService {

    private PhotoDAO photoDAO;
    private Cloudinary cloudinary;

    public PhotoService() throws IOException {
        Properties cfg = new Properties();
        cfg.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("cloudinary.cfg.properties")));
        cloudinary = new Cloudinary(cfg);
        photoDAO = new PhotoDAOImpl();
    }

    public Photo upload(String base64Image) throws IOException {
        Map<?,?> params = ObjectUtils.asMap(
                "overwrite", true,
                "format", "png",
                "resource_type", "image"
        );
        Map<?,?> uploadResult = cloudinary.uploader().upload(base64Image, params);
        Photo photo = new Photo();
        photo.setUrl(uploadResult.get("url").toString());
        photo.setPublicId(uploadResult.get("public_id").toString());
        System.out.println(photo.getPublicId());
        return photo;
    }

    public void save(Photo photo) {
        photoDAO.save(photo);
    }

    public Optional<Photo> findById(long id) {
        return photoDAO.find(id);
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
