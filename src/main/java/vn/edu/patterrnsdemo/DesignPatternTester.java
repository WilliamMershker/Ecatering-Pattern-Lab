/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vn.edu.patterrnsdemo;

// ======================== BEHAVIORAL ========================

// Chain of Responsibility
import vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility.AccountCheck;
import vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility.BalanceCheck;
import vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility.OrderValidator;

// Command
import vn.edu.patterrnsdemo.behavioral.ChainOfResponsibility.StockCheck;
import vn.edu.patterrnsdemo.behavioral.Command.*;

// Interpreter
import vn.edu.patterrnsdemo.behavioral.Mediator.CustomerComponent;
import vn.edu.patterrnsdemo.behavioral.interpreter.Expression;
import vn.edu.patterrnsdemo.behavioral.interpreter.PromotionRuleInterpreter;

// Iterator
import vn.edu.patterrnsdemo.behavioral.Iterator.MenuCollection;
import vn.edu.patterrnsdemo.behavioral.Iterator.MenuIterator;

// Mediator
import vn.edu.patterrnsdemo.behavioral.Mediator.DriverComponent;
import vn.edu.patterrnsdemo.behavioral.Mediator.KitchenComponent;
import vn.edu.patterrnsdemo.behavioral.Mediator.OrderMediatorHub;

// Memento
import vn.edu.patterrnsdemo.behavioral.Memento.CateringEventOrder;
import vn.edu.patterrnsdemo.behavioral.Memento.OrderDraftStore;
import vn.edu.patterrnsdemo.behavioral.Memento.OrderMemento;

// Observer
import vn.edu.patterrnsdemo.behavioral.Observer.CustomerApp;
import vn.edu.patterrnsdemo.behavioral.Observer.DriverApp;
import vn.edu.patterrnsdemo.behavioral.Observer.OrderObserver;
import vn.edu.patterrnsdemo.behavioral.Observer.OrderSubject;
import vn.edu.patterrnsdemo.behavioral.Observer.RestaurantAdminPage;

// State
import vn.edu.patterrnsdemo.behavioral.State.OrderContext;

// Strategy
import vn.edu.patterrnsdemo.behavioral.Strategy.DeliveryFeeCalculator;
import vn.edu.patterrnsdemo.behavioral.Strategy.EconomicalShipping;
import vn.edu.patterrnsdemo.behavioral.Strategy.ExpressShipping;
import vn.edu.patterrnsdemo.behavioral.Strategy.OffPeakShipping;

// Template Method
import vn.edu.patterrnsdemo.behavioral.TemplateMethod.DeliveryOrderProcessor;
import vn.edu.patterrnsdemo.behavioral.TemplateMethod.InStoreOrderProcessor;
import vn.edu.patterrnsdemo.behavioral.TemplateMethod.OrderProcessTemplate;

// Visitor
import vn.edu.patterrnsdemo.behavioral.visitor.DeliveryOrder;
import vn.edu.patterrnsdemo.behavioral.visitor.InStoreOrder;
import vn.edu.patterrnsdemo.behavioral.visitor.OrderElement;
import vn.edu.patterrnsdemo.behavioral.visitor.OrderReportVisitor;


// ======================== CREATIONAL ========================

// Abstract Factory
import vn.edu.patterrnsdemo.creational.abstractfactory.AsianMenuFactory;
import vn.edu.patterrnsdemo.creational.abstractfactory.MenuFactory;
import vn.edu.patterrnsdemo.creational.abstractfactory.MenuItem;
import vn.edu.patterrnsdemo.creational.abstractfactory.WesternMenuFactory;

// Builder
import vn.edu.patterrnsdemo.creational.builder.Order;

// Factory Method
import vn.edu.patterrnsdemo.creational.factorymethod.FoodCreator;
import vn.edu.patterrnsdemo.creational.factorymethod.RegularFoodCreator;
import vn.edu.patterrnsdemo.creational.factorymethod.SeasonalFoodCreator;

// Prototype
import vn.edu.patterrnsdemo.creational.prototype.MenuPrototypeManager;
import vn.edu.patterrnsdemo.creational.prototype.SpecialCombo;

// Singleton
import vn.edu.patterrnsdemo.creational.singleton.AppConfig;


// ======================== STRUCTURAL ========================

// Adapter
import vn.edu.patterrnsdemo.structural.Adapter.MomoPaymentAdapter;
import vn.edu.patterrnsdemo.structural.Adapter.OrderPayment;

// Bridge
import vn.edu.patterrnsdemo.structural.Bridge.DisplayPlatform;
import vn.edu.patterrnsdemo.structural.Bridge.MeatMenu;
import vn.edu.patterrnsdemo.structural.Bridge.MenuAbstraction;
import vn.edu.patterrnsdemo.structural.Bridge.MobileAppView;
import vn.edu.patterrnsdemo.structural.Bridge.SmartTVView;
import vn.edu.patterrnsdemo.structural.Bridge.VegetarianMenu;
import vn.edu.patterrnsdemo.structural.Bridge.WebsiteView;

// Composite
import vn.edu.patterrnsdemo.structural.Composite.ComboItem;
import vn.edu.patterrnsdemo.structural.Composite.MenuComponent;
import vn.edu.patterrnsdemo.structural.Composite.SingleItem;

// Decorator
import vn.edu.patterrnsdemo.structural.Decorator.BasicItem;
import vn.edu.patterrnsdemo.structural.Decorator.ExtraCheese;
import vn.edu.patterrnsdemo.structural.Decorator.ExtraEgg;

// Facade
import vn.edu.patterrnsdemo.structural.Facade.OrderFacade;

// Flyweight
import vn.edu.patterrnsdemo.structural.Flyweight.IngredientTag;
import vn.edu.patterrnsdemo.structural.Flyweight.TagFactory;

// Proxy
import vn.edu.patterrnsdemo.structural.Proxy.AccessControlProxy;
import vn.edu.patterrnsdemo.structural.Proxy.ReportService;

/**
 *
 * @author Dat
 */
public class DesignPatternTester
{

    public static String runTest(String patternName)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Kết Quả Kiểm Thử Mẫu Thiết Kế: ")
                .append(patternName.toUpperCase()).append("\n");
        switch (patternName)
        {
            case "Singleton":
            {
                AppConfig c1 = AppConfig.getInstance();
                AppConfig c2 = AppConfig.getInstance();

                sb.append("[1] DB kết nối: ")
                        .append(c1.getDatabaseUrl())
                        .append("\n");

                sb.append("[2] Thuế VAT mặc định: ")
                        .append(c1.getVatRate() * 100)
                        .append("%\n");

                sb.append("[3] Mã đối tượng 1: ")
                        .append(System.identityHashCode(c1))
                        .append("\n");

                sb.append("[4] Mã đối tượng 2: ")
                        .append(System.identityHashCode(c2))
                        .append("\n");

                sb.append("==> Đánh giá: ")
                        .append(c1 == c2 ? "PASS — Cùng một đối tượng"
                                : "FAIL — Khác đối tượng");

                break;
            }

            case "Abstract Factory":
            {
                sb.append("=== ABSTRACT FACTORY PATTERN ===\n");

                // 1. Tạo họ sản phẩm Thực đơn Á
                MenuFactory asianFactory = new AsianMenuFactory();

                MenuItem asianFood = asianFactory.createFood();
                MenuItem asianDrink = asianFactory.createDrink();

                sb.append("[1] Thực đơn Á\n");
                sb.append("    Món ăn: ")
                        .append(asianFood.getName())
                        .append("\n");

                sb.append("    Đồ uống: ")
                        .append(asianDrink.getName())
                        .append("\n\n");

                // 2. Tạo họ sản phẩm Thực đơn Âu
                MenuFactory westernFactory = new WesternMenuFactory();

                MenuItem westernFood = westernFactory.createFood();
                MenuItem westernDrink = westernFactory.createDrink();

                sb.append("[2] Thực đơn Âu\n");
                sb.append("    Món ăn: ")
                        .append(westernFood.getName())
                        .append("\n");

                sb.append("    Đồ uống: ")
                        .append(westernDrink.getName())
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Abstract Factory tạo ra ")
                        .append("các sản phẩm thuộc cùng một họ, đảm bảo ")
                        .append("món ăn và đồ uống luôn đồng bộ theo thực đơn.");

                break;
            }

            case "Factory Method":
            {
                sb.append("=== FACTORY METHOD PATTERN ===\n");

                FoodCreator seasonalCreator = new SeasonalFoodCreator();

                vn.edu.patterrnsdemo.creational.factorymethod.MenuItem seasonalFood
                        = seasonalCreator.factoryMethod();

                sb.append("[1] Danh mục món theo mùa\n");
                sb.append("    Creator: SeasonalFoodCreator\n");
                sb.append("    Món được tạo: ")
                        .append(seasonalFood.getName())
                        .append("\n\n");

                FoodCreator regularCreator = new RegularFoodCreator();

                vn.edu.patterrnsdemo.creational.factorymethod.MenuItem regularFood
                        = regularCreator.factoryMethod();

                sb.append("[2] Danh mục món thường xuyên\n");
                sb.append("    Creator: RegularFoodCreator\n");
                sb.append("    Món được tạo: ")
                        .append(regularFood.getName())
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Factory Method giao việc ")
                        .append("khởi tạo sản phẩm cho các Creator con, ")
                        .append("giúp Client không phụ thuộc trực tiếp ")
                        .append("vào lớp sản phẩm cụ thể.");

                break;
            }

            case "Builder":
            {
                sb.append("=== BUILDER PATTERN ===\n");

                Order order = new Order.OrderBuilder(
                        "Nguyễn Văn A",
                        java.util.List.of(
                                "Pizza",
                                "Coca",
                                "Burger"
                        )
                )
                        .setNote("Ít đá")
                        .setCouponCode("SALE20")
                        .setDeliveryTime("18:00")
                        .setGpsLocation("10.7769, 106.7009")
                        .build();

                sb.append("[1] Khách hàng: ")
                        .append(order.getCustomerName())
                        .append("\n");

                sb.append("[2] Danh sách món: ")
                        .append(order.getItems())
                        .append("\n");

                sb.append("[3] Ghi chú: ")
                        .append(order.getNote())
                        .append("\n");

                sb.append("[4] Mã giảm giá: ")
                        .append(order.getCouponCode())
                        .append("\n");

                sb.append("[5] Thời gian giao: ")
                        .append(order.getDeliveryTime())
                        .append("\n");

                sb.append("[6] Tọa độ GPS: ")
                        .append(order.getGpsLocation())
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Builder Pattern cho phép ")
                        .append("xây dựng đối tượng Order phức tạp theo Fluent API, ")
                        .append("tránh Telescoping Constructor và tạo ra ")
                        .append("đối tượng Order bất biến.");

                break;
            }

            case "Prototype":
            {
                sb.append("=== PROTOTYPE PATTERN ===\n");

                // 1. Tạo Prototype gốc - thực đơn ngày hôm qua
                SpecialCombo familyCombo =
                        new SpecialCombo("Combo Gia Đình", 500000);

                familyCombo.addDish("Gà quay", 200000);
                familyCombo.addDish("Lẩu hải sản", 250000);
                familyCombo.addDish("Nước ngọt 1.5L", 50000);

                // 2. Đăng ký Prototype vào Manager
                MenuPrototypeManager manager =
                        new MenuPrototypeManager();

                manager.registerPrototype(
                        "FAMILY_COMBO",
                        familyCombo
                );

                sb.append("[1] Prototype gốc:\n");
                sb.append("    ")
                        .append(familyCombo)
                        .append("\n\n");

                // 3. Clone từ Prototype Manager
                SpecialCombo todayCombo =
                        (SpecialCombo) manager.createClone("FAMILY_COMBO");

                // 4. Thay đổi thông tin bản clone
                todayCombo.setComboName(
                        "Combo Gia Đình Khuyến Mãi"
                );

                todayCombo.setPrice(450000);

                // Thay đổi giá món con để kiểm tra Deep Copy
                todayCombo.getDishes()
                        .get(0)
                        .setPrice(180000);

                // Thêm món mới vào bản clone
                todayCombo.addDish(
                        "Trái cây dĩa",
                        60000
                );

                sb.append("[2] Bản Clone sau khi chỉnh sửa:\n");
                sb.append("    ")
                        .append(todayCombo)
                        .append("\n\n");

                // 5. Kiểm tra lại Prototype gốc
                sb.append("[3] Prototype gốc sau khi bản Clone thay đổi:\n");
                sb.append("    ")
                        .append(familyCombo)
                        .append("\n\n");

                // 6. Chứng minh món con không dùng chung object
                boolean deepCopy =
                        familyCombo.getDishes().get(0)
                                != todayCombo.getDishes().get(0);

                sb.append("[4] Kiểm tra Deep Copy: ")
                        .append(deepCopy
                                ? "PASS - Các món con là object độc lập"
                                : "FAIL - Các món con đang dùng chung object")
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Prototype Pattern cho phép ")
                        .append("nhân bản Combo có sẵn từ bộ nhớ; ")
                        .append("SpecialCombo thực hiện Deep Copy nên thay đổi ")
                        .append("các món trong bản Clone không ảnh hưởng bản gốc.");

                break;
            }

            case "Composite":
            {
                sb.append("=== COMPOSITE PATTERN ===\n");

                // 1. Các món đơn - Leaf
                MenuComponent burger =
                        new SingleItem("Burger", 50000);

                MenuComponent fries =
                        new SingleItem("Fries", 20000);

                MenuComponent coke =
                        new SingleItem("Coke", 15000);

                // 2. Combo con
                ComboItem combo1 =
                        new ComboItem("Combo 1");

                combo1.add(burger);
                combo1.add(fries);

                // 3. Combo lớn chứa cả Combo con và món đơn
                ComboItem combo2 =
                        new ComboItem("Combo Full");

                combo2.add(combo1);
                combo2.add(coke);

                sb.append("[1] Các món đơn:\n");

                sb.append("    Burger: ")
                        .append(burger.getPrice())
                        .append("đ\n");

                sb.append("    Fries: ")
                        .append(fries.getPrice())
                        .append("đ\n");

                sb.append("    Coke: ")
                        .append(coke.getPrice())
                        .append("đ\n\n");

                sb.append("[2] ")
                        .append(combo1.getName())
                        .append(":\n");

                sb.append("    (50000 + 20000) x 90% = ")
                        .append(combo1.getPrice())
                        .append("đ\n\n");

                sb.append("[3] ")
                        .append(combo2.getName())
                        .append(":\n");

                sb.append("    (")
                        .append(combo1.getPrice())
                        .append(" + ")
                        .append(coke.getPrice())
                        .append(") x 90% = ")
                        .append(combo2.getPrice())
                        .append("đ\n\n");

                sb.append("[4] Giá cuối cùng: ")
                        .append(combo2.getPrice())
                        .append("đ\n\n");

                sb.append("==> Đánh giá: Đạt - Composite Pattern cho phép ")
                        .append("xử lý món đơn và Combo thông qua cùng MenuComponent; ")
                        .append("ComboItem có thể chứa cả SingleItem và ComboItem, ")
                        .append("đồng thời tính giá đệ quy qua toàn bộ cấu trúc cây.");

                break;
            }

            case "Decorator":
            {
                sb.append("=== DECORATOR PATTERN ===\n");

                // Món gốc
                vn.edu.patterrnsdemo.structural.Decorator.MenuComponent burger =
                        new BasicItem("Burger", 50000);

                sb.append("[1] Món gốc:\n");
                sb.append("    Tên: ")
                        .append(burger.getName())
                        .append("\n");

                sb.append("    Giá: ")
                        .append(String.format("%,.0fđ", burger.getPrice()))
                        .append("\n\n");

                // Thêm phô mai
                vn.edu.patterrnsdemo.structural.Decorator.MenuComponent cheeseBurger =
                        new ExtraCheese(burger);

                sb.append("[2] Sau khi thêm phô mai:\n");
                sb.append("    Tên: ")
                        .append(cheeseBurger.getName())
                        .append("\n");

                sb.append("    Giá: ")
                        .append(String.format("%,.0fđ", cheeseBurger.getPrice()))
                        .append("\n\n");

                // Tiếp tục bọc thêm trứng
                vn.edu.patterrnsdemo.structural.Decorator.MenuComponent fullBurger =
                        new ExtraEgg(cheeseBurger);

                sb.append("[3] Sau khi thêm tiếp trứng ốp la:\n");
                sb.append("    Tên: ")
                        .append(fullBurger.getName())
                        .append("\n");

                sb.append("    Giá: ")
                        .append(String.format("%,.0fđ", fullBurger.getPrice()))
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Decorator Pattern cho phép ")
                        .append("bổ sung topping động tại Runtime; mỗi Decorator ")
                        .append("gọi hành vi của baseComponent trước rồi mới ")
                        .append("cộng thêm tên và giá của chính nó.");

                break;
            }
            case "Bridge":
            {
                sb.append("=== BRIDGE PATTERN ===\n");

                // Các Implementor - nền tảng hiển thị
                DisplayPlatform mobile = new MobileAppView();
                DisplayPlatform web = new WebsiteView();
                DisplayPlatform tv = new SmartTVView();

                // Kết hợp độc lập Menu và Platform
                MenuAbstraction vegetarianMobile =
                        new VegetarianMenu(mobile);

                MenuAbstraction vegetarianWeb =
                        new VegetarianMenu(web);

                MenuAbstraction meatMobile =
                        new MeatMenu(mobile);

                MenuAbstraction meatTV =
                        new MeatMenu(tv);

                sb.append("[1] Thực đơn Chay trên Mobile:\n");
                sb.append("    ")
                        .append(vegetarianMobile.display())
                        .append("\n\n");

                sb.append("[2] Thực đơn Chay trên Website:\n");
                sb.append("    ")
                        .append(vegetarianWeb.display())
                        .append("\n\n");

                sb.append("[3] Thực đơn Mặn trên Mobile:\n");
                sb.append("    ")
                        .append(meatMobile.display())
                        .append("\n\n");

                sb.append("[4] Thực đơn Mặn trên SmartTV:\n");
                sb.append("    ")
                        .append(meatTV.display())
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Bridge Pattern tách ")
                        .append("Danh mục thực đơn khỏi Nền tảng hiển thị, ")
                        .append("cho phép hai nhánh mở rộng độc lập mà không ")
                        .append("làm bùng nổ số lượng lớp.");

                break;
            }

            case "Flyweight":
            {
                sb.append("=== FLYWEIGHT PATTERN ===\n");

                // 1. Yêu cầu các Tag dùng chung từ Factory
                IngredientTag vegetarianTag =
                        TagFactory.getTag(
                                "Chay",
                                "vegetarian.png",
                                "Phù hợp món chay"
                        );

                IngredientTag peanutTag =
                        TagFactory.getTag(
                                "Có Đậu Phộng",
                                "peanut.png",
                                "Cảnh báo dị ứng đậu phộng"
                        );

                IngredientTag spicyTag =
                        TagFactory.getTag(
                                "Cay Nhiều",
                                "spicy.png",
                                "Mức độ cay cao"
                        );

                // 2. Hiển thị cùng một Flyweight tại nhiều vị trí khác nhau
                sb.append("[1] Tag Chay trên món thứ nhất:\n");
                sb.append("    ")
                        .append(vegetarianTag.drawTag(10))
                        .append("\n\n");

                sb.append("[2] Cùng Tag Chay trên món khác:\n");
                sb.append("    ")
                        .append(vegetarianTag.drawTag(50))
                        .append("\n\n");

                sb.append("[3] Tag Có Đậu Phộng:\n");
                sb.append("    ")
                        .append(peanutTag.drawTag(80))
                        .append("\n\n");

                sb.append("[4] Tag Cay Nhiều:\n");
                sb.append("    ")
                        .append(spicyTag.drawTag(120))
                        .append("\n\n");

                // 3. Yêu cầu lại Tag "Chay"
                IngredientTag vegetarianTag2 =
                        TagFactory.getTag(
                                "Chay",
                                "vegetarian.png",
                                "Phù hợp món chay"
                        );

                // 4. Chứng minh Factory trả về object cũ
                boolean reused =
                        vegetarianTag == vegetarianTag2;

                sb.append("[5] Hai lần lấy Tag Chay có cùng object: ")
                        .append(reused)
                        .append("\n");

                sb.append("[6] Tổng số object Flyweight thực tế đã tạo: ")
                        .append(TagFactory.getTotalTags())
                        .append("\n\n");

                sb.append("==> Đánh giá: ")
                        .append(reused ? "Đạt" : "Không đạt")
                        .append(" - Flyweight Pattern tái sử dụng các IngredientTag ")
                        .append("có cùng Intrinsic State; vị trí hiển thị được truyền ")
                        .append("từ bên ngoài dưới dạng Extrinsic State, giúp giảm ")
                        .append("số lượng object và tiết kiệm bộ nhớ.");

                break;
            }

            case "Facade":
            {
                sb.append("=== FACADE PATTERN ===\n");

                // Client chỉ làm việc với Facade
                OrderFacade orderFacade = new OrderFacade();

                String cartId = "CART-001";
                String userId = "USER-001";

                sb.append("[1] Giỏ hàng: ")
                        .append(cartId)
                        .append("\n");

                sb.append("[2] Khách hàng: ")
                        .append(userId)
                        .append("\n\n");

                // Một lời gọi duy nhất che giấu toàn bộ hệ thống con
                sb.append(orderFacade.quickPlaceOrder(cartId, userId));

                sb.append("\n==> Đánh giá: Đạt - Facade Pattern cung cấp ")
                        .append("một giao diện đơn giản quickPlaceOrder(), ")
                        .append("che giấu việc phối hợp giữa StockService, ")
                        .append("InvoiceService và SmsService.");

                break;
            }

            case "Adapter":
            {
                sb.append("=== ADAPTER PATTERN ===\n");

                double invoiceAmount = 155000.50;

                sb.append("[1] Hệ thống E-Catering yêu cầu thanh toán:\n");
                sb.append("    Số tiền: ")
                        .append(invoiceAmount)
                        .append("đ\n\n");

                // Client chỉ biết interface chuẩn OrderPayment
                OrderPayment paymentProcessor =
                        new MomoPaymentAdapter();

                sb.append("[2] Thanh toán thông qua MomoPaymentAdapter:\n");

                sb.append(paymentProcessor.executePay(invoiceAmount));

                sb.append("\n==> Đánh giá: Đạt - Adapter Pattern chuyển đổi ")
                        .append("giao diện executePay(double) của E-Catering ")
                        .append("sang makeDeposit(int) của MoMo SDK, giúp hai ")
                        .append("hệ thống không tương thích có thể làm việc với nhau.");

                break;
            }

            case "Proxy":
            {
                sb.append("=== PROXY PATTERN ===\n");

                int storeId = 101;

                // 1. STAFF - không có quyền
                sb.append("[1] Tài khoản STAFF:\n");

                ReportService staffProxy =
                        new AccessControlProxy("STAFF");

                try
                {
                    sb.append(staffProxy.viewRevenueReport(storeId));
                }
                catch (SecurityException e)
                {
                    sb.append("    -> TỪ CHỐI: ")
                            .append(e.getMessage())
                            .append("\n");
                }

                sb.append("\n");

                // 2. ADMIN - có quyền
                sb.append("[2] Tài khoản ADMIN:\n");

                ReportService adminProxy =
                        new AccessControlProxy("ADMIN");

                try
                {
                    sb.append(adminProxy.viewRevenueReport(storeId));
                }
                catch (SecurityException e)
                {
                    sb.append("    -> TỪ CHỐI: ")
                            .append(e.getMessage())
                            .append("\n");
                }

                sb.append("\n");

                // 3. STORE_OWNER - cũng có quyền
                sb.append("[3] Tài khoản STORE_OWNER:\n");

                ReportService ownerProxy =
                        new AccessControlProxy("STORE_OWNER");

                try
                {
                    sb.append(ownerProxy.viewRevenueReport(storeId));
                }
                catch (SecurityException e)
                {
                    sb.append("    -> TỪ CHỐI: ")
                            .append(e.getMessage())
                            .append("\n");
                }

                sb.append("\n==> Đánh giá: Đạt - Protection Proxy kiểm tra ")
                        .append("quyền truy cập trước khi chuyển tiếp yêu cầu ")
                        .append("đến RealReportService. Chỉ ADMIN và STORE_OWNER ")
                        .append("được phép xem dữ liệu doanh thu.");

                break;
            }

            case "Template Method":
            {
                sb.append("=== TEMPLATE METHOD PATTERN ===\n");

                // Kịch bản 1: Ăn tại nhà hàng
                sb.append("[1] ĐƠN ĂN TẠI CHỖ:\n");

                OrderProcessTemplate inStore =
                        new InStoreOrderProcessor();

                sb.append(inStore.processOrder())
                        .append("\n");

                // Kịch bản 2: Giao tận nhà
                sb.append("[2] ĐƠN GIAO TẬN NHÀ:\n");

                OrderProcessTemplate delivery =
                        new DeliveryOrderProcessor();

                sb.append(delivery.processOrder())
                        .append("\n");

                sb.append("==> Đánh giá: Đạt - Template Method cố định ")
                        .append("quy trình 5 bước Tiếp nhận -> Kiểm tra thanh toán ")
                        .append("-> Chế biến -> Giao hàng -> Đóng đơn. ")
                        .append("Các lớp con chỉ thay đổi logic cook() và deliver().");

                break;
            }

            case "State":
            {
                sb.append("=== STATE PATTERN ===\n");

                // Kịch bản 1: Chuyển trạng thái bình thường
                sb.append("[1] VÒNG ĐỜI ĐƠN HÀNG:\n");

                OrderContext order1 = new OrderContext();

                sb.append("    Trạng thái ban đầu: ")
                        .append(order1.getCurrentState().getStateName())
                        .append("\n");

                // New -> Cooking
                sb.append(order1.triggerNext());

                sb.append("    Trạng thái hiện tại: ")
                        .append(order1.getCurrentState().getStateName())
                        .append("\n");

                // Cooking -> Shipping
                sb.append(order1.triggerNext());

                sb.append("    Trạng thái hiện tại: ")
                        .append(order1.getCurrentState().getStateName())
                        .append("\n");

                // Shipping -> giao thành công
                sb.append(order1.triggerNext())
                        .append("\n");

                // Kịch bản 2: Hủy khi mới tạo
                sb.append("[2] HỦY ĐƠN KHI ĐANG Ở NEW STATE:\n");

                OrderContext order2 = new OrderContext();

                sb.append(order2.triggerCancel())
                        .append("\n");

                // Kịch bản 3: Hủy khi đang nấu
                sb.append("[3] HỦY ĐƠN KHI ĐANG NẤU:\n");

                OrderContext order3 = new OrderContext();

                order3.triggerNext(); // New -> Cooking

                sb.append(order3.triggerCancel())
                        .append("\n");

                // Kịch bản 4: Hủy khi đang giao
                sb.append("[4] HỦY ĐƠN KHI ĐANG GIAO:\n");

                OrderContext order4 = new OrderContext();

                order4.triggerNext(); // New -> Cooking
                order4.triggerNext(); // Cooking -> Shipping

                sb.append(order4.triggerCancel())
                        .append("\n");

                sb.append("==> Đánh giá: Đạt - State Pattern đóng gói ")
                        .append("hành vi của từng trạng thái vào các lớp riêng biệt. ")
                        .append("OrderContext không cần sử dụng if-else để kiểm tra trạng thái.");

                break;
            }

            case "Strategy":
            {
                double distance = 12.5;

                DeliveryFeeCalculator calculator = new DeliveryFeeCalculator();

                sb.append("=== STRATEGY PATTERN ===\n");
                sb.append("Quãng đường giao hàng: ")
                        .append(distance)
                        .append(" km\n\n");

                // Khách hàng chọn giao hàng tiết kiệm
                calculator.setStrategy(new EconomicalShipping());

                sb.append("[1] ")
                        .append(calculator.getCurrentStrategyName())
                        .append("\n");

                sb.append("    Phí vận chuyển: ")
                        .append(String.format("%,.0fđ", calculator.calculate(distance)))
                        .append("\n\n");

                // Khách hàng đổi sang giao hàng siêu tốc ngay trong runtime
                calculator.setStrategy(new ExpressShipping());

                sb.append("[2] Đổi chiến lược tại Runtime -> ")
                        .append(calculator.getCurrentStrategyName())
                        .append("\n");

                sb.append("    Phí vận chuyển: ")
                        .append(String.format("%,.0fđ", calculator.calculate(distance)))
                        .append("\n\n");

                // Khách hàng tiếp tục đổi sang giao hàng giờ thấp điểm
                calculator.setStrategy(new OffPeakShipping());

                sb.append("[3] Đổi chiến lược tại Runtime -> ")
                        .append(calculator.getCurrentStrategyName())
                        .append("\n");

                sb.append("    Phí vận chuyển: ")
                        .append(String.format("%,.0fđ", calculator.calculate(distance)))
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Strategy Pattern cho phép ")
                        .append("thay đổi thuật toán tính phí giao hàng linh hoạt ")
                        .append("tại Runtime thông qua setStrategy().");

                break;
            }

            case "Command":
            {
                sb.append("=== COMMAND PATTERN ===\n");

                // Receiver thực hiện công việc thật
                Chef chef = new Chef();

                // Invoker quản lý hàng đợi Command
                WaiterInvoker kitchenQueue = new WaiterInvoker();

                // Client tạo các Command.
                // Client chỉ mô tả món cần nấu, không trực tiếp gọi logic nấu ăn.
                KitchenCommand command1 =
                        new CookDishCommand(
                                "Bít tết bò",
                                2,
                                chef
                        );

                KitchenCommand command2 =
                        new CookDishCommand(
                                "Pizza Hải Sản",
                                1,
                                chef
                        );

                KitchenCommand command3 =
                        new CookDishCommand(
                                "Mì Ý Sốt Bò Bằm",
                                3,
                                chef
                        );

                // Đưa các Command vào Queue
                kitchenQueue.addCommand(command1);
                kitchenQueue.addCommand(command2);
                kitchenQueue.addCommand(command3);

                sb.append("[1] Đã đưa các yêu cầu nấu ăn vào hàng đợi.\n");

                sb.append("    Số Command đang chờ: ")
                        .append(kitchenQueue.getQueueSize())
                        .append("\n\n");

                // Invoker thực thi lần lượt các Command
                sb.append("[2] Nhà bếp xử lý Queue:\n");

                sb.append(kitchenQueue.processCommands());

                sb.append("\n[3] Số Command còn lại: ")
                        .append(kitchenQueue.getQueueSize())
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Command Pattern đóng gói ")
                        .append("mỗi yêu cầu nấu ăn thành một đối tượng KitchenCommand; ")
                        .append("các lệnh được đưa vào Queue và thực thi tuần tự, ")
                        .append("giúp tách biệt bên gửi yêu cầu khỏi Chef thực hiện.");

                break;
            }

            case "Observer":
            {
                sb.append("=== OBSERVER PATTERN ===\n");

                OrderSubject orderSubject = new OrderSubject("ORD-999");

                // Tạo 3 Observer
                OrderObserver customerApp =
                        new CustomerApp("Trần Văn B");

                OrderObserver driverApp =
                        new DriverApp("Nguyễn Văn A");

                OrderObserver adminPage =
                        new RestaurantAdminPage();

                // Đăng ký nhận thông báo
                orderSubject.attach(customerApp);
                orderSubject.attach(driverApp);
                orderSubject.attach(adminPage);

                sb.append("[1] Đã đăng ký 3 Observer:\n");
                sb.append("    - ")
                        .append(customerApp.getObserverName())
                        .append("\n");

                sb.append("    - ")
                        .append(driverApp.getObserverName())
                        .append("\n");

                sb.append("    - ")
                        .append(adminPage.getObserverName())
                        .append("\n\n");

                // Shipper xác nhận đã lấy hàng
                sb.append("[2] Tài xế xác nhận: Đã lấy hàng\n");

                // Trạng thái đơn hàng chuyển sang Đang giao
                orderSubject.setOrderStatus("Đang giao");

                // Subject tự động thông báo cho tất cả Observer
                sb.append(orderSubject.notifyObservers());

                sb.append("\n==> Đánh giá: Đạt - Observer Pattern cho phép ")
                        .append("Subject tự động thông báo cho nhiều Observer ")
                        .append("khi trạng thái đơn hàng thay đổi.");

                break;
            }

            case "Mediator":
            {
                sb.append("=== MEDIATOR PATTERN ===\n");

                // 1. Tạo tổng đài trung gian
                OrderMediatorHub mediator =
                        new OrderMediatorHub();

                // 2. Tạo các Component
                KitchenComponent kitchen =
                        new KitchenComponent(mediator);

                DriverComponent driver =
                        new DriverComponent(mediator);

                CustomerComponent customer =
                        new CustomerComponent(mediator);

                // 3. Đăng ký các Component với Mediator
                mediator.setKitchen(kitchen);
                mediator.setDriver(driver);
                mediator.setCustomer(customer);

                // Kịch bản 1:
                // Nhà bếp nấu xong -> Mediator thông báo tài xế
                sb.append("[1] Nhà bếp hoàn thành món:\n");

                sb.append(kitchen.finishCooking())
                        .append("\n");

                // Kịch bản 2:
                // Tài xế tới nơi -> Mediator thông báo khách
                sb.append("[2] Tài xế đã tới điểm giao:\n");

                sb.append(driver.reportArrived())
                        .append("\n");

                // Kịch bản 3:
                // Khách yêu cầu đổi món -> Mediator thông báo nhà bếp
                sb.append("[3] Khách hàng yêu cầu đổi món:\n");

                sb.append(customer.requestChangeDish())
                        .append("\n");

                sb.append("==> Đánh giá: Đạt - Mediator Pattern tập trung ")
                        .append("toàn bộ giao tiếp vào OrderMediatorHub; ")
                        .append("Kitchen, Driver và Customer không gọi trực tiếp ")
                        .append("lẫn nhau mà chỉ gửi tín hiệu thông qua Mediator.");

                break;
            }

            case "Chain of Responsibility":
            {
                sb.append("=== CHAIN OF RESPONSIBILITY PATTERN ===\n");

                // 1. Tạo các mắt xích
                OrderValidator accountCheck = new AccountCheck();
                OrderValidator stockCheck = new StockCheck();
                OrderValidator balanceCheck = new BalanceCheck();

                // 2. Nối chuỗi:
                // Account -> Stock -> Balance
                accountCheck.setNext(stockCheck);
                stockCheck.setNext(balanceCheck);

                sb.append("[1] Chuỗi kiểm duyệt:\n");
                sb.append("    AccountCheck -> StockCheck -> BalanceCheck\n\n");

                // Kịch bản A: hợp lệ
                sb.append("[2] Kịch bản A - Đơn hàng 150.000đ:\n");

                StringBuilder logA = new StringBuilder();

                boolean resultA =
                        accountCheck.validate(
                                "NguyenVanA",
                                150000,
                                logA
                        );

                sb.append(logA);

                sb.append("    Kết quả: ")
                        .append(resultA
                                ? "HỢP LỆ - THÔNG QUA"
                                : "BỊ TỪ CHỐI")
                        .append("\n\n");

                // Kịch bản B: lỗi số dư
                sb.append("[3] Kịch bản B - Đơn hàng 550.000đ:\n");

                StringBuilder logB = new StringBuilder();

                boolean resultB =
                        accountCheck.validate(
                                "NguyenVanA",
                                550000,
                                logB
                        );

                sb.append(logB);

                sb.append("    Kết quả: ")
                        .append(resultB
                                ? "HỢP LỆ - THÔNG QUA"
                                : "BỊ TỪ CHỐI")
                        .append("\n\n");

                // Kịch bản C: lỗi kho
                sb.append("[4] Kịch bản C - Đơn hàng 750.000đ:\n");

                StringBuilder logC = new StringBuilder();

                boolean resultC =
                        accountCheck.validate(
                                "NguyenVanA",
                                750000,
                                logC
                        );

                sb.append(logC);

                sb.append("    Kết quả: ")
                        .append(resultC
                                ? "HỢP LỆ - THÔNG QUA"
                                : "BỊ TỪ CHỐI")
                        .append("\n\n");

                sb.append("==> Đánh giá: Đạt - Chain of Responsibility ")
                        .append("cho phép đơn hàng đi tuần tự qua các mắt xích ")
                        .append("AccountCheck -> StockCheck -> BalanceCheck; ")
                        .append("khi một bước thất bại, chuỗi lập tức dừng.");

                break;
            }

            case "Memento":
            {
                sb.append("=== MEMENTO PATTERN ===\n");

                // Originator
                CateringEventOrder order =
                        new CateringEventOrder();

                // Caretaker
                OrderDraftStore draftStore =
                        new OrderDraftStore();

                // =====================================================
                // PHIÊN BẢN NGÀY HÔM QUA
                // =====================================================

                sb.append("[1] Đơn tiệc - Phiên bản ngày hôm qua:\n");

                order.addItem(
                        "Bít tết bò Mỹ",
                        1200000
                );

                order.addItem(
                        "Rượu vang đỏ",
                        800000
                );

                sb.append(order.getCurrentItemsString())
                        .append("\n");

                // Lưu Snapshot
                OrderMemento yesterdaySnapshot =
                        order.saveToMemento();

                draftStore.saveDraft(
                        yesterdaySnapshot
                );

                sb.append("   -> [Caretaker] Đã lưu bản nháp.\n");
                sb.append("   -> Timestamp: ")
                        .append(yesterdaySnapshot.getTimestamp())
                        .append("\n\n");

                // =====================================================
                // PHIÊN BẢN HÔM NAY
                // =====================================================

                sb.append("[2] Khách chỉnh sửa đơn hôm nay:\n");

                order.addItem(
                        "Tôm hùm Alaska nướng phô mai",
                        2500000
                );

                order.addItem(
                        "Súp bào ngư",
                        1500000
                );

                sb.append(order.getCurrentItemsString())
                        .append("\n");

                // =====================================================
                // UNDO
                // =====================================================

                sb.append("[3] Khách chọn UNDO:\n");

                // Snapshot trạng thái hiện tại dùng cho Redo
                OrderMemento currentSnapshot =
                        order.saveToMemento();

                OrderMemento undoSnapshot =
                        draftStore.undo(currentSnapshot);

                sb.append(
                        order.restoreFromMemento(undoSnapshot)
                );

                sb.append("   Trạng thái sau Undo:\n");

                sb.append(order.getCurrentItemsString())
                        .append("\n");

                // =====================================================
                // REDO
                // =====================================================

                sb.append("[4] Khách chọn REDO:\n");

                OrderMemento stateBeforeRedo =
                        order.saveToMemento();

                OrderMemento redoSnapshot =
                        draftStore.redo(stateBeforeRedo);

                sb.append(
                        order.restoreFromMemento(redoSnapshot)
                );

                sb.append("   Trạng thái sau Redo:\n");

                sb.append(order.getCurrentItemsString())
                        .append("\n");

                sb.append("==> Đánh giá: Đạt - Memento Pattern lưu ")
                        .append("Snapshot bất biến của Catering Event Order; ")
                        .append("Caretaker chỉ quản lý các Memento bằng Stack ")
                        .append("và hỗ trợ Undo/Redo mà không cần biết ")
                        .append("chi tiết trạng thái bên trong.");

                break;
            }

            case "Iterator":
            {
                sb.append("=== ITERATOR PATTERN ===\n");

                // 1. Tạo tập hợp thực đơn
                MenuCollection menu = new MenuCollection();

                menu.addDish(
                        "Salad Ức Gà Sốt Mè Rang",
                        85000
                );

                menu.addDish(
                        "Bít Tết Bò Úc Sốt Tiêu Xanh",
                        250000
                );

                menu.addDish(
                        "Pizza Hải Sản Viền Phô Mai",
                        180000
                );

                menu.addDish(
                        "Nước Ép Chanh Dây",
                        45000
                );

                sb.append("[1] Đã thêm 4 món vào MenuCollection.\n\n");

                // 2. Client lấy Iterator
                MenuIterator iterator =
                        menu.createIterator();

                sb.append("[2] Duyệt thực đơn bằng MenuIterator:\n");

                int index = 1;

                while (iterator.hasNext())
                {
                    vn.edu.patterrnsdemo.behavioral.Iterator.MenuItem item
                            = iterator.next();

                    sb.append("    ")
                            .append(index)
                            .append(". ")
                            .append(item.getName())
                            .append(" - ")
                            .append(String.format("%,.0fđ", item.getPrice()))
                            .append("\n");

                    index++;
                }

                sb.append("\n==> Đánh giá: Đạt - Iterator Pattern cho phép ")
                        .append("Client duyệt các MenuItem thông qua hasNext() và next() ")
                        .append("mà không cần biết MenuCollection đang sử dụng ")
                        .append("ArrayList, LinkedList hay cấu trúc lưu trữ nào khác.");

                break;
            }

            case "Interpreter":
            {
                String rule = "ORDER_TOTAL > 500000 "
                        + "AND CUSTOMER_TYPE == VIP";

                vn.edu.patterrnsdemo.behavioral.interpreter.OrderContext promotionOrder =
                        new vn.edu.patterrnsdemo.behavioral.interpreter.OrderContext(
                                750_000,
                                "VIP",
                                8
                        );

                PromotionRuleInterpreter interpreter =
                        new PromotionRuleInterpreter();

                Expression rootExpression = interpreter.parse(rule);
                boolean accepted = rootExpression.interpret(promotionOrder);

                sb.append("[1] Quy tắc: ")
                        .append(rule)
                        .append("\n");

                sb.append("[2] Tổng đơn: ")
                        .append(promotionOrder.getOrderTotal())
                        .append(" VND\n");

                sb.append("[3] Loại khách: ")
                        .append(promotionOrder.getCustomerType())
                        .append("\n");

                sb.append("[4] Số món: ")
                        .append(promotionOrder.getItemCount())
                        .append("\n");

                sb.append("==> Kết quả áp dụng khuyến mãi: ")
                        .append(accepted ? "ĐẠT" : "KHÔNG ĐẠT");

                break;
            }

            case "Visitor":
            {
                OrderElement[] reportOrders =
                        {
                        new InStoreOrder("IS-001", 1_200_000, 120_000),
                        new DeliveryOrder("DL-001", 850_000, 40_000),
                        new vn.edu.patterrnsdemo.behavioral.visitor.CateringEventOrder(
                                "CE-001", 5_000_000, 500_000
                        )
                };

                OrderReportVisitor reportVisitor = new OrderReportVisitor();

                for (OrderElement order : reportOrders)
                {
                    order.accept(reportVisitor);
                }

                sb.append(reportVisitor.getFullReport());
                break;
            }

            default:
                sb.append("Không Tìm Thấy Mẫu Thiết Kế Theo Yêu Cầu");
        }
        return sb.toString();
    }
}