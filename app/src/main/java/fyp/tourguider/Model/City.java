package fyp.tourguider.Model;

public class City {
    String cityName;
   public City(){

   }
    public City(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
