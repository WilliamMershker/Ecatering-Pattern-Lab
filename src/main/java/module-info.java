module vn.edu.patterrnsdemo
{
    requires javafx.controls;
    requires javafx.fxml;

    // Cho FXMLLoader truy cập PrimaryController và SecondaryController
    opens vn.edu.patterrnsdemo to javafx.fxml;

    // Cho phép JavaFX khởi chạy lớp App
    exports vn.edu.patterrnsdemo;
}