package vn.edu.patterrnsdemo.structural.Bridge;

public class WebsiteView implements DisplayPlatform
{
    @Override
    public String renderUI()
    {
        return "[Website] Hiển thị bằng danh mục Tab ngang";
    }
}