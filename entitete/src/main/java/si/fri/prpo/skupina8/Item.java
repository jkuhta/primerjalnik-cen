package si.fri.prpo.skupina8;

public class Item{
    public double sugar_g;
    public double fiber_g;
    public double serving_size_g;
    public double sodium_mg;
    public String name;
    public double potassium_mg;
    public double fat_saturated_g;
    public double fat_total_g;
    public double calories;
    public double cholesterol_mg;
    public double protein_g;
    public double carbohydrates_total_g;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("sugar_g: ");
        sb.append(this.sugar_g);
        sb.append("\n fiber_g: ");
        sb.append(this.fiber_g);
        sb.append("\n serving_size_g: ");
        sb.append(this.serving_size_g);
        sb.append("\n sodium_mg: ");
        sb.append(this.sodium_mg);
        sb.append("\n name: ");
        sb.append(this.name);
        sb.append("\n potassium_mg: ");
        sb.append(this.potassium_mg);
        sb.append("\n fat_saturated_g: ");
        sb.append(this.fat_saturated_g);
        sb.append("\n fat_total_g: ");
        sb.append(this.fat_total_g);
        sb.append("\n calories: ");
        sb.append(this.calories);
        sb.append("\n cholesterol_mg: ");
        sb.append(this.cholesterol_mg);
        sb.append("\n protein_g: ");
        sb.append(this.protein_g);
        sb.append("\n carbohydrates_total_g: ");
        sb.append(this.carbohydrates_total_g);
        sb.append("\n\n");

        return sb.toString();
    }
}
