package android.singidunum.ac.recipesapp.meals;


public class Meal {

    private String name,id;
    private String image;

    public Meal(String name, String image, String id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}
