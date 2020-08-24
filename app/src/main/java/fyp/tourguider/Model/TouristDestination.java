package fyp.tourguider.Model;

import com.google.firebase.firestore.Blob;

public class TouristDestination {
    String name;
    Blob Image;
    String city;
    public TouristDestination(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getImage() {
        return Image;
    }

    public void setImage(Blob image) {
        Image = image;
    }

    public TouristDestination(String name, Blob image,String city) {
        this.name = name;
        this.Image = image;
        this.city=city;
    }
}
