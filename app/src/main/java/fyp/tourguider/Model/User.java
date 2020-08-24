package fyp.tourguider.Model;

public class User {
    String name;
    String role;
    String email;
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String city;
   public User(){

   }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    int hourlyRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public User(String name, String role, String email, int hourlyRate,String city,String phone) {
        this.name = name;
        this.role = role;
        this.email = email;
        this.hourlyRate = hourlyRate;
        this.city=city;
        this.phone=phone;
    }

}
