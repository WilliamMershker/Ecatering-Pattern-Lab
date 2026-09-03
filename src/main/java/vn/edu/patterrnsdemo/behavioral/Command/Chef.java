package vn.edu.patterrnsdemo.behavioral.Command;

public class Chef
{
    public String cookDish(String dishName, int quantity)
    {
        return "   -> [Chef] Đang chế biến "
                + quantity
                + " phần "
                + dishName
                + ".\n";
    }
}