package vn.edu.patterrnsdemo.structural.Bridge;

public class SmartTVView implements DisplayPlatform
{
    @Override
    public String renderUI()
    {
        return "[SmartTV] Hiển thị toàn màn hình với chữ cỡ lớn";
    }
}