package vn.edu.patterrnsdemo;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;

/**
 * Điểm khởi động của E-Catering Pattern Lab.
 *
 * Title bar được dựng bằng JavaFX để giao diện dark đồng nhất trên toàn bộ
 * ứng dụng. Content host giúp đổi FXML mà không làm mất title bar.
 */
public class App extends Application {

    private static final double WINDOW_WIDTH = 1200;
    private static final double WINDOW_HEIGHT = 800;

    private static Stage primaryStage;
    private static StackPane contentHost;
    private static Button maximizeButton;

    private double dragOffsetX;
    private double dragOffsetY;
    private ResizeDirection resizeDirection = ResizeDirection.NONE;
    private boolean resizing;
    private double resizeStartScreenX;
    private double resizeStartScreenY;
    private double resizeStartX;
    private double resizeStartY;
    private double resizeStartWidth;
    private double resizeStartHeight;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Icon của ứng dụng trên taskbar và Alt + Tab
        Image appIcon = new Image(
                requireResource("/images/ECateringPatternLab-icon.png").toExternalForm()
        );
        primaryStage.getIcons().add(appIcon);

        Parent firstView = loadFXML("primary");
        contentHost = new StackPane(firstView);
        contentHost.getStyleClass().add("window-content-host");
        VBox.setVgrow(contentHost, Priority.ALWAYS);

        HBox titleBar = createTitleBar();
        VBox windowShell = new VBox(titleBar, contentHost);
        windowShell.getStyleClass().add("app-window");

        URL stylesheet = requireResource("styles.css");
        windowShell.getStylesheets().add(stylesheet.toExternalForm());

        Scene scene = new Scene(windowShell, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setFill(Color.web("#070a14"));
        installResizeSupport(scene);

        primaryStage.setTitle(
                "E-Catering System | Design Pattern Intelligence Lab"
        );
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1020);
        primaryStage.setMinHeight(690);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();
        primaryStage.show();

        if (!Boolean.getBoolean("ecatering.disableAnimations")) {
            playStartupAnimation(windowShell);
        }
    }

    /**
     * Hiệu ứng mở ứng dụng ngắn và nhẹ: cửa sổ mờ dần, trượt lên và
     * phóng về đúng kích thước. Chuyển động chỉ chạy một lần khi khởi động.
     */
    private void playStartupAnimation(Node windowShell) {
        windowShell.setOpacity(0);
        windowShell.setTranslateY(18);
        windowShell.setScaleX(0.985);
        windowShell.setScaleY(0.985);

        FadeTransition fade = new FadeTransition(Duration.millis(360), windowShell);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(420), windowShell);
        slide.setFromY(18);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition scale = new ScaleTransition(Duration.millis(420), windowShell);
        scale.setFromX(0.985);
        scale.setFromY(0.985);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(fade, slide, scale).play();
    }

    private HBox createTitleBar() {
        StackPane miniLogo = new StackPane();
        miniLogo.getStyleClass().add("window-mini-logo");
        Label miniLogoText = new Label("DP");
        miniLogoText.getStyleClass().add("window-mini-logo-text");
        miniLogo.getChildren().add(miniLogoText);

        Label windowTitle = new Label("E-CATERING  /  DESIGN PATTERN INTELLIGENCE LAB");
        windowTitle.getStyleClass().add("window-title-text");

        Label runtimeBadge = new Label("JAVAFX DESKTOP");
        runtimeBadge.getStyleClass().add("window-runtime-badge");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button minimizeButton = createWindowButton("—", "window-minimize");
        maximizeButton = createWindowButton("□", "window-maximize");
        Button closeButton = createWindowButton("×", "window-close");

        minimizeButton.setOnAction(event -> primaryStage.setIconified(true));
        maximizeButton.setOnAction(event -> toggleMaximize());
        closeButton.setOnAction(event -> primaryStage.close());

        HBox titleBar = new HBox(
                miniLogo, windowTitle, runtimeBadge, spacer,
                minimizeButton, maximizeButton, closeButton
        );
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setSpacing(8);
        titleBar.getStyleClass().add("window-titlebar");

        titleBar.setOnMousePressed(event -> handleTitleBarPressed(event, titleBar));
        titleBar.setOnMouseDragged(event -> handleTitleBarDragged(event, titleBar));
        titleBar.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY
                    && event.getClickCount() == 2
                    && !isWindowControl(event.getTarget(), titleBar)) {
                toggleMaximize();
            }
        });

        primaryStage.maximizedProperty().addListener((observable, oldValue, maximized) ->
                maximizeButton.setText(maximized ? "❐" : "□"));

        return titleBar;
    }

    private Button createWindowButton(String text, String specificStyleClass) {
        Button button = new Button(text);
        button.setFocusTraversable(false);
        button.getStyleClass().addAll("window-control", specificStyleClass);
        return button;
    }

    private void handleTitleBarPressed(MouseEvent event, Node titleBar) {
        if (isWindowControl(event.getTarget(), titleBar) || primaryStage.isMaximized()) {
            return;
        }
        dragOffsetX = event.getSceneX();
        dragOffsetY = event.getSceneY();
    }

    private void handleTitleBarDragged(MouseEvent event, Node titleBar) {
        if (isWindowControl(event.getTarget(), titleBar) || primaryStage.isMaximized()) {
            return;
        }
        primaryStage.setX(event.getScreenX() - dragOffsetX);
        primaryStage.setY(event.getScreenY() - dragOffsetY);
    }

    private static boolean isWindowControl(Object target, Node titleBar) {
        if (!(target instanceof Node)) {
            return false;
        }

        Node current = (Node) target;
        while (current != null && current != titleBar) {
            if (current.getStyleClass().contains("window-control")) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private static void toggleMaximize() {
        primaryStage.setMaximized(!primaryStage.isMaximized());
    }

    /**
     * Khôi phục khả năng kéo viền để resize cho StageStyle.UNDECORATED.
     */
    private void installResizeSupport(Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            if (primaryStage.isMaximized() || resizing) {
                scene.setCursor(Cursor.DEFAULT);
                return;
            }
            resizeDirection = detectResizeDirection(event, scene);
            scene.setCursor(resizeDirection.cursor);
        });

        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() != MouseButton.PRIMARY
                    || resizeDirection == ResizeDirection.NONE
                    || primaryStage.isMaximized()) {
                return;
            }

            resizing = true;
            resizeStartScreenX = event.getScreenX();
            resizeStartScreenY = event.getScreenY();
            resizeStartX = primaryStage.getX();
            resizeStartY = primaryStage.getY();
            resizeStartWidth = primaryStage.getWidth();
            resizeStartHeight = primaryStage.getHeight();
            event.consume();
        });

        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!resizing) {
                return;
            }
            resizeWindow(event);
            event.consume();
        });

        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (resizing) {
                resizing = false;
                event.consume();
            }
        });
    }

    private ResizeDirection detectResizeDirection(MouseEvent event, Scene scene) {
        final double edge = 6.0;
        boolean left = event.getSceneX() <= edge;
        boolean right = event.getSceneX() >= scene.getWidth() - edge;
        boolean top = event.getSceneY() <= edge;
        boolean bottom = event.getSceneY() >= scene.getHeight() - edge;

        if (top && left) {
            return ResizeDirection.NORTH_WEST;
        }
        if (top && right) {
            return ResizeDirection.NORTH_EAST;
        }
        if (bottom && left) {
            return ResizeDirection.SOUTH_WEST;
        }
        if (bottom && right) {
            return ResizeDirection.SOUTH_EAST;
        }
        if (left) {
            return ResizeDirection.WEST;
        }
        if (right) {
            return ResizeDirection.EAST;
        }
        if (top) {
            return ResizeDirection.NORTH;
        }
        if (bottom) {
            return ResizeDirection.SOUTH;
        }
        return ResizeDirection.NONE;
    }

    private void resizeWindow(MouseEvent event) {
        double deltaX = event.getScreenX() - resizeStartScreenX;
        double deltaY = event.getScreenY() - resizeStartScreenY;

        if (resizeDirection.hasEast()) {
            primaryStage.setWidth(Math.max(primaryStage.getMinWidth(), resizeStartWidth + deltaX));
        }
        if (resizeDirection.hasSouth()) {
            primaryStage.setHeight(Math.max(primaryStage.getMinHeight(), resizeStartHeight + deltaY));
        }
        if (resizeDirection.hasWest()) {
            double width = Math.max(primaryStage.getMinWidth(), resizeStartWidth - deltaX);
            primaryStage.setX(resizeStartX + resizeStartWidth - width);
            primaryStage.setWidth(width);
        }
        if (resizeDirection.hasNorth()) {
            double height = Math.max(primaryStage.getMinHeight(), resizeStartHeight - deltaY);
            primaryStage.setY(resizeStartY + resizeStartHeight - height);
            primaryStage.setHeight(height);
        }
    }

    /**
     * Chuyển màn hình nhưng giữ nguyên title bar và kích thước cửa sổ.
     */
    public static void setRoot(String fxml) throws IOException {
        if (contentHost == null) {
            throw new IllegalStateException("Cửa sổ ứng dụng chưa được khởi tạo");
        }

        Parent nextView = loadFXML(fxml);
        nextView.setOpacity(0);
        contentHost.getChildren().setAll(nextView);

        FadeTransition transition = new FadeTransition(Duration.millis(220), nextView);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        URL resource = requireResource(fxml + ".fxml");
        return new FXMLLoader(resource).load();
    }

    private static URL requireResource(String fileName) throws IOException {
        URL resource = App.class.getResource(fileName);
        if (resource == null) {
            throw new IOException("Không tìm thấy resource: " + fileName);
        }
        return resource;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private enum ResizeDirection {
        NONE(Cursor.DEFAULT),
        NORTH(Cursor.N_RESIZE),
        SOUTH(Cursor.S_RESIZE),
        EAST(Cursor.E_RESIZE),
        WEST(Cursor.W_RESIZE),
        NORTH_EAST(Cursor.NE_RESIZE),
        NORTH_WEST(Cursor.NW_RESIZE),
        SOUTH_EAST(Cursor.SE_RESIZE),
        SOUTH_WEST(Cursor.SW_RESIZE);

        private final Cursor cursor;

        ResizeDirection(Cursor cursor) {
            this.cursor = cursor;
        }

        private boolean hasNorth() {
            return this == NORTH || this == NORTH_EAST || this == NORTH_WEST;
        }

        private boolean hasSouth() {
            return this == SOUTH || this == SOUTH_EAST || this == SOUTH_WEST;
        }

        private boolean hasEast() {
            return this == EAST || this == NORTH_EAST || this == SOUTH_EAST;
        }

        private boolean hasWest() {
            return this == WEST || this == NORTH_WEST || this == SOUTH_WEST;
        }
    }
}
