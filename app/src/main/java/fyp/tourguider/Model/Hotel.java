package fyp.tourguider.Model;

import com.google.firebase.firestore.Blob;

public class Hotel {
    String name;
    Blob Image;
    int perNightRent;
   public Hotel(){

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

    public int getPerNightRent() {
        return perNightRent;
    }

    public void setPerNightRent(int perNightRent) {
        this.perNightRent = perNightRent;
    }

    public Hotel(String name, Blob image, int perNightRent) {
        this.name = name;
        this.Image = image;
        this.perNightRent = perNightRent;
    }
}
