package vn.edu.patterrnsdemo.creational.prototype;

import java.util.ArrayList;
import java.util.List;

public class SpecialCombo implements MenuItemPrototype
{
    private String comboName;
    private double price;
    private List<DishItem> dishes;

    public SpecialCombo(String comboName, double price)
    {
        this.comboName = comboName;
        this.price = price;
        this.dishes = new ArrayList<>();
    }

    public void addDish(String name, double dishPrice)
    {
        dishes.add(new DishItem(name, dishPrice));
    }

    public String getComboName()
    {
        return comboName;
    }

    public void setComboName(String comboName)
    {
        this.comboName = comboName;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public List<DishItem> getDishes()
    {
        return dishes;
    }

    @Override
    public SpecialCombo clone()
    {
        SpecialCombo clonedCombo =
                new SpecialCombo(this.comboName, this.price);

        for (DishItem dish : this.dishes)
        {
            clonedCombo.dishes.add(dish.clone());
        }

        return clonedCombo;
    }

    @Override
    public String toString()
    {
        return comboName
                + " - Giá combo: " + price
                + "đ - Món: " + dishes;
    }
}