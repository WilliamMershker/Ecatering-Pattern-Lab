package vn.edu.patterrnsdemo.behavioral.Command;

import java.util.ArrayDeque;
import java.util.Queue;

public class WaiterInvoker
{
    private final Queue<KitchenCommand> commandQueue =
            new ArrayDeque<>();

    public void addCommand(KitchenCommand command)
    {
        commandQueue.offer(command);
    }

    public String processCommands()
    {
        if (commandQueue.isEmpty())
        {
            return "[Invoker] Hàng đợi hiện đang trống.\n";
        }

        StringBuilder result = new StringBuilder();

        result.append("[Invoker] Bắt đầu xử lý các lệnh trong hàng đợi:\n");

        while (!commandQueue.isEmpty())
        {
            KitchenCommand command = commandQueue.poll();

            result.append(command.execute());
        }

        return result.toString();
    }

    public int getQueueSize()
    {
        return commandQueue.size();
    }
}