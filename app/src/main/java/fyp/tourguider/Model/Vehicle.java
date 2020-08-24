package fyp.tourguider.Model;

import com.google.firebase.firestore.Blob;

public class Vehicle {
    String name;
    int perDayRent;
    Blob image;
   public Vehicle(){

   }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPerDayRent() {
        return perDayRent;
    }

    public void setPerDayRent(int perDayRent) {
        this.perDayRent = perDayRent;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Vehicle(String name, int perDayRent, Blob image) {
        this.name = name;
        this.perDayRent = perDayRent;
        this.image = image;
    }
}
