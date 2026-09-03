package vn.edu.patterrnsdemo.structural.Bridge;

public abstract class MenuAbstraction
{
    protected final DisplayPlatform platform;

    public MenuAbstraction(DisplayPlatform platform)
    {
        this.platform = platform;
    }

    public abstract String display();
}