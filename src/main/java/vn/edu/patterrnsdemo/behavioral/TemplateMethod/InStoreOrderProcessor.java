package vn.edu.patterrnsdemo.behavioral.TemplateMethod;

public class InStoreOrderProcessor extends OrderProcessTemplate
{
    @Override
    protected String cook()
    {
        return "[Bước 3 - Tại chỗ] Chế biến: "
                + "Đầu bếp chế biến món ăn và trình bày lên đĩa.\n";
    }

    @Override
    protected String deliver()
    {
        return "[Bước 4 - Tại chỗ] Giao món: "
                + "Nhân viên phục vụ mang món ăn đến bàn của khách.\n";
    }
}