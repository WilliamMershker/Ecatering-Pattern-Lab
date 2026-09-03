package vn.edu.patterrnsdemo.behavioral.Memento;

import java.util.Stack;

public class OrderDraftStore
{
    // Lịch sử Undo
    private final Stack<OrderMemento> mementoHistory =
            new Stack<>();

    // Lịch sử Redo
    private final Stack<OrderMemento> redoHistory =
            new Stack<>();

    public void saveDraft(OrderMemento memento)
    {
        mementoHistory.push(memento);

        // Có chỉnh sửa mới thì lịch sử Redo cũ không còn hợp lệ
        redoHistory.clear();
    }

    public OrderMemento undo(OrderMemento currentState)
    {
        if (mementoHistory.isEmpty())
        {
            return null;
        }

        // Lưu trạng thái hiện tại để có thể Redo
        redoHistory.push(currentState);

        // Trả Snapshot gần nhất
        return mementoHistory.pop();
    }

    public OrderMemento redo(OrderMemento currentState)
    {
        if (redoHistory.isEmpty())
        {
            return null;
        }

        // Trạng thái hiện tại trở lại lịch sử Undo
        mementoHistory.push(currentState);

        // Phục hồi trạng thái vừa Undo
        return redoHistory.pop();
    }

    public int getHistorySize()
    {
        return mementoHistory.size();
    }

    public int getRedoSize()
    {
        return redoHistory.size();
    }
}