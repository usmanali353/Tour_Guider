package fyp.tourguider.Model;

public class Booking {
    String tourStartDate;
    String date;
    String CustomerId;
    String CustomerName;
    String CustomerPhone;
    String selectedDestination;
    String selectedHotel;
    String selectedVehicle;
    String selectedGuider;
    public Booking(){

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    int days,hotelRent,vehicleRent,guiderRent,Total;

    public String getTourStartDate() {
        return tourStartDate;
    }

    public void setTourStartDate(String tourStartDate) {
        this.tourStartDate = tourStartDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        CustomerPhone = customerPhone;
    }

    public String getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(String selectedDestination) {
        this.selectedDestination = selectedDestination;
    }

    public String getSelectedHotel() {
        return selectedHotel;
    }

    public void setSelectedHotel(String selectedHotel) {
        this.selectedHotel = selectedHotel;
    }

    public String getSelectedVehicle() {
        return selectedVehicle;
    }

    public void setSelectedVehicle(String selectedVehicle) {
        this.selectedVehicle = selectedVehicle;
    }

    public String getSelectedGuider() {
        return selectedGuider;
    }

    public void setSelectedGuider(String selectedGuider) {
        this.selectedGuider = selectedGuider;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHotelRent() {
        return hotelRent;
    }

    public void setHotelRent(int hotelRent) {
        this.hotelRent = hotelRent;
    }

    public int getVehicleRent() {
        return vehicleRent;
    }

    public void setVehicleRent(int vehicleRent) {
        this.vehicleRent = vehicleRent;
    }

    public int getGuiderRent() {
        return guiderRent;
    }

    public void setGuiderRent(int guiderRent) {
        this.guiderRent = guiderRent;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public Booking(String tourStartDate, String date, String customerId, String customerName, String customerPhone, String selectedDestination, String selectedHotel, String selectedVehicle, String selectedGuider, int days, int hotelRent, int vehicleRent, int guiderRent, int total,String status) {
        this.tourStartDate = tourStartDate;
        this.date = date;
        this.CustomerId = customerId;
        this.CustomerName = customerName;
        this.CustomerPhone = customerPhone;
        this.selectedDestination = selectedDestination;
        this.selectedHotel = selectedHotel;
        this.selectedVehicle = selectedVehicle;
        this.selectedGuider = selectedGuider;
        this.days = days;
        this.hotelRent = hotelRent;
        this.vehicleRent = vehicleRent;
        this.guiderRent = guiderRent;
        this.Total = total;
        this.status=status;
    }
}
