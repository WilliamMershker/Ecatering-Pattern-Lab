package vn.edu.patterrnsdemo.behavioral.Memento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class OrderMemento
{
    // Snapshot trạng thái tại một thời điểm
    private final List<MenuItem> stateSnapshot;

    // Thời điểm tạo Snapshot
    private final Long timestamp;

    OrderMemento(List<MenuItem> currentState)
    {
        List<MenuItem> copiedItems = new ArrayList<>();

        for (MenuItem item : currentState)
        {
            copiedItems.add(item.copy());
        }

        this.stateSnapshot =
                Collections.unmodifiableList(copiedItems);

        this.timestamp = System.currentTimeMillis();
    }

    /*
     * Không public:
     * chỉ các lớp nội bộ cùng package sử dụng.
     * Caretaker không gọi phương thức này.
     */
    List<MenuItem> getStateSnapshot()
    {
        List<MenuItem> copiedItems = new ArrayList<>();

        for (MenuItem item : stateSnapshot)
        {
            copiedItems.add(item.copy());
        }

        return copiedItems;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }
}