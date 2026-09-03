package vn.edu.patterrnsdemo.structural.Flyweight;

public class IngredientTag
{
    // Intrinsic State - trạng thái được chia sẻ
    private final String tagName;
    private final String iconUrl;
    private final String warningMessage;

    public IngredientTag(
            String tagName,
            String iconUrl,
            String warningMessage)
    {
        this.tagName = tagName;
        this.iconUrl = iconUrl;
        this.warningMessage = warningMessage;
    }

    public String getTagName()
    {
        return tagName;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public String getWarningMessage()
    {
        return warningMessage;
    }

    // positionOnScreen là Extrinsic State
    public String drawTag(int positionOnScreen)
    {
        return "Tag [" + tagName + "]"
                + " | Icon: " + iconUrl
                + " | Cảnh báo: " + warningMessage
                + " | Vị trí X = " + positionOnScreen;
    }
}