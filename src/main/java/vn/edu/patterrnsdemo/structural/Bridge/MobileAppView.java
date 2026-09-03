package vn.edu.patterrnsdemo.structural.Bridge;

public class MobileAppView implements DisplayPlatform
{
    @Override
    public String renderUI()
    {
        return "[Mobile App] Hiển thị bằng Grid View";
    }
}