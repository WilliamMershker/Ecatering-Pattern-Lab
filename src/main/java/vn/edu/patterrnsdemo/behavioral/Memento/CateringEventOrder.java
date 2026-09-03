package vn.edu.patterrnsdemo.behavioral.Memento;

import java.util.ArrayList;
import java.util.List;

public class CateringEventOrder
{
    private List<MenuItem> currentItems =
            new ArrayList<>();

    public void addItem(String name, double price)
    {
        currentItems.add(
                new MenuItem(name, price)
        );
    }

    public String getCurrentItemsString()
    {
        if (currentItems.isEmpty())
        {
            return "   [Danh sách món trống]";
        }

        StringBuilder sb = new StringBuilder();

        for (MenuItem item : currentItems)
        {
            sb.append("   - ")
                    .append(item)
                    .append("\n");
        }

        return sb.toString();
    }

    // Originator tạo Snapshot
    public OrderMemento saveToMemento()
    {
        return new OrderMemento(currentItems);
    }

    // Originator đọc Snapshot để phục hồi trạng thái
    public String restoreFromMemento(OrderMemento memento)
    {
        if (memento == null)
        {
            return "   -> Không tìm thấy bản nháp để phục hồi.\n";
        }

        this.currentItems =
                new ArrayList<>(
                        memento.getStateSnapshot()
                );

        return "   -> [Originator] Phục hồi trạng thái thành công.\n";
    }
}