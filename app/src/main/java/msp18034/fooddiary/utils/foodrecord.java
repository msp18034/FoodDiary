package msp18034.fooddiary.utils;

public class foodrecord {

    private String image, foodName, calorie;

    public foodrecord(String image, String foodName, String calorie) {
        super();
        this.image = image;
        this.foodName = foodName;
        this.calorie = calorie;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
    public String getCalorie() {
        return calorie;
    }
    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}


