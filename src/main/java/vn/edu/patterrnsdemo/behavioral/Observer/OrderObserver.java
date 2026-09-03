package vn.edu.patterrnsdemo.behavioral.Observer;

public interface OrderObserver
{
    String update(String orderStatus);
    String getObserverName();
}