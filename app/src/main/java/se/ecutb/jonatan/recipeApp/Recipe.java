package se.ecutb.jonatan.recipeApp;

public class Recipe {
    private String recipeName;
    private String recipeType;
    private Integer cookTime;
    private Integer apiID;

    public Recipe(Integer apiID, String recipeName, String recipeType, Integer cookTime) {
        this.recipeName = recipeName;
        this.recipeType = recipeType;
        this.cookTime = cookTime;
        this.apiID = apiID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getApiID() {
        return apiID;
    }

    public void setApiID(Integer apiID) {
        this.apiID = apiID;
    }

    @Override
    public String toString() {
        return "FoodData{" +
                "recipeName='" + recipeName + '\'' +
                ", recipeType='" + recipeType + '\'' +
                ", cookTime=" + cookTime +
                ", apiID=" + apiID +
                '}';
    }
}


