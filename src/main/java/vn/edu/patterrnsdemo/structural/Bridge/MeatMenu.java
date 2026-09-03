package vn.edu.patterrnsdemo.structural.Bridge;

public class MeatMenu extends MenuAbstraction
{
    public MeatMenu(DisplayPlatform platform)
    {
        super(platform);
    }

    @Override
    public String display()
    {
        return "Thực đơn Mặn -> " + platform.renderUI();
    }
}