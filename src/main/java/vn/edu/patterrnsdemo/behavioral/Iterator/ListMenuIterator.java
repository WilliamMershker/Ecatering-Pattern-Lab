package vn.edu.patterrnsdemo.behavioral.Iterator;

import java.util.List;
import java.util.NoSuchElementException;

public class ListMenuIterator implements MenuIterator
{
    private final List<MenuItem> items;
    private int position;

    public ListMenuIterator(List<MenuItem> items)
    {
        this.items = items;
        this.position = 0;
    }

    @Override
    public boolean hasNext()
    {
        return position < items.size();
    }

    @Override
    public MenuItem next()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException(
                    "Không còn món ăn nào trong Iterator."
            );
        }

        MenuItem item = items.get(position);
        position++;

        return item;
    }
}