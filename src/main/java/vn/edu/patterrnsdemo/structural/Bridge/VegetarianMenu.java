package vn.edu.patterrnsdemo.structural.Bridge;

public class VegetarianMenu extends MenuAbstraction
{
    public VegetarianMenu(DisplayPlatform platform)
    {
        super(platform);
    }

    @Override
    public String display()
    {
        return "Thực đơn Chay -> " + platform.renderUI();
    }
}