package vn.edu.patterrnsdemo;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Điều khiển Pattern Lab: tìm kiếm, lọc nhóm, hiển thị metadata,
 * chạy kiểm thử và dựng console nhiều màu.
 */
public class PrimaryController implements Initializable {

    private static final Map<String, PatternDetail> PATTERNS = createPatternCatalog();
    private static final Map<String, PatternStructure> STRUCTURES = createStructureCatalog();

    @FXML
    private StackPane primaryRoot;

    @FXML
    private ListView<String> patternListView;

    @FXML
    private TextField searchField;

    @FXML
    private ToggleButton allFilterButton;

    @FXML
    private ToggleButton creationalFilterButton;

    @FXML
    private ToggleButton structuralFilterButton;

    @FXML
    private ToggleButton behavioralFilterButton;

    @FXML
    private Label totalPatternLabel;

    @FXML
    private Label visiblePatternCountLabel;

    @FXML
    private Label workspaceEyebrow;

    @FXML
    private Label workspaceTitle;

    @FXML
    private Label workspaceSubtitle;

    @FXML
    private Label patternNumberLabel;

    @FXML
    private Label patternFamilyLabel;

    @FXML
    private Label patternIntentLabel;

    @FXML
    private Label readyLabel;

    @FXML
    private Label statusChipText;

    @FXML
    private Label footerLoadedLabel;

    @FXML
    private Region onlinePulseDot;

    @FXML
    private Region footerStatusDot;

    @FXML
    private Region readyDot;

    @FXML
    private StackPane statusChip;

    @FXML
    private Button runButton;

    @FXML
    private StackPane consoleCard;

    @FXML
    private StackPane workspaceViewHost;

    @FXML
    private StackPane structureCard;

    @FXML
    private Pane structureCanvas;

    @FXML
    private ToggleButton outputViewButton;

    @FXML
    private ToggleButton structureViewButton;

    @FXML
    private HBox outputActionsBox;

    @FXML
    private Region workspaceModeDot;

    @FXML
    private Label workspaceModeTitle;

    @FXML
    private Label workspaceModeCaption;

    @FXML
    private Label structurePatternName;

    @FXML
    private Label structureFamilyBadge;

    @FXML
    private Label structureMechanismLabel;

    @FXML
    private ScrollPane consoleScrollPane;

    @FXML
    private TextFlow consoleTextFlow;

    @FXML
    private StackPane toastPane;

    @FXML
    private Label toastIcon;

    @FXML
    private Label toastTitle;

    @FXML
    private Label toastMessage;

    private final ObservableList<String> visiblePatterns = FXCollections.observableArrayList();
    private PatternFamily activeFamily;
    private SequentialTransition toastAnimation;
    private Timeline consoleRevealTimeline;
    private ScaleTransition runButtonPulse;
    private PauseTransition runButtonReset;
    private PauseTransition structureResizeDebounce;
    private ParallelTransition workspaceViewTransition;
    private ParallelTransition structureDiagramAnimation;
    private PatternStructure activeStructure;
    private PatternStructure renderedStructure;
    private double renderedCanvasWidth = -1;
    private double renderedCanvasHeight = -1;
    private boolean structureRenderDirty = true;
    private boolean structureViewActive;
    private boolean animationsEnabled;
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        animationsEnabled = !Boolean.getBoolean("ecatering.disableAnimations");
        configurePatternList();
        configureFilters();
        configureWorkspaceViews();
        configureStructureViewer();
        configureSearch();
        configureKeyboardShortcuts();
        if (animationsEnabled) {
            configureAnimations();
        }

        totalPatternLabel.setText(String.valueOf(PATTERNS.size()));
        footerLoadedLabel.setText(PATTERNS.size() + " pattern implementations loaded");

        patternListView.setItems(visiblePatterns);
        patternListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldPattern, newPattern) -> updatePatternDetails(newPattern)
        );

        applyFilters();
        showConsoleWelcome();
        setExecutionState(ExecutionState.IDLE);
    }

    private void configureWorkspaceViews() {
        ToggleGroup viewGroup = new ToggleGroup();
        outputViewButton.setToggleGroup(viewGroup);
        structureViewButton.setToggleGroup(viewGroup);
        outputViewButton.setSelected(true);
        structureViewActive = false;
    }

    private void configureStructureViewer() {
        structureResizeDebounce = new PauseTransition(Duration.millis(45));
        structureResizeDebounce.setOnFinished(event -> renderStructureDiagram());

        structureCanvas.widthProperty().addListener(
                (observable, oldWidth, newWidth) -> scheduleStructureRender()
        );
        structureCanvas.heightProperty().addListener(
                (observable, oldHeight, newHeight) -> scheduleStructureRender()
        );
    }

    private void configurePatternList() {
        patternListView.setCellFactory(list -> new ListCell<String>() {
            @Override
            protected void updateItem(String patternName, boolean empty) {
                super.updateItem(patternName, empty);

                if (empty || patternName == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                PatternDetail detail = PATTERNS.get(patternName);
                Region categoryDot = new Region();
                categoryDot.getStyleClass().addAll(
                        "pattern-category-dot",
                        detail.family.dotStyleClass
                );

                Label nameLabel = new Label(patternName);
                nameLabel.getStyleClass().add("pattern-name");

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Label familyCode = new Label(detail.family.shortCode);
                familyCode.getStyleClass().add("pattern-family-code");

                HBox row = new HBox(categoryDot, nameLabel, spacer, familyCode);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setSpacing(9);

                setText(null);
                setGraphic(row);
            }
        });

        Label placeholder = new Label("Không tìm thấy Design Pattern");
        placeholder.getStyleClass().add("empty-list-label");
        patternListView.setPlaceholder(placeholder);
    }

    private void configureFilters() {
        ToggleGroup group = new ToggleGroup();
        allFilterButton.setToggleGroup(group);
        creationalFilterButton.setToggleGroup(group);
        structuralFilterButton.setToggleGroup(group);
        behavioralFilterButton.setToggleGroup(group);
        allFilterButton.setSelected(true);
    }

    private void configureSearch() {
        searchField.textProperty().addListener((observable, oldText, newText) -> applyFilters());
    }

    private void configureKeyboardShortcuts() {
        primaryRoot.addEventFilter(KeyEvent.KEY_PRESSED, this::handleShortcut);
    }

    private void handleShortcut(KeyEvent event) {
        if (event.isControlDown() && event.getCode() == KeyCode.K) {
            searchField.requestFocus();
            searchField.selectAll();
            event.consume();
            return;
        }

        if (event.isControlDown() && event.getCode() == KeyCode.D) {
            if (structureViewActive) {
                showOutputView();
            } else {
                showStructureView();
            }
            event.consume();
            return;
        }

        if (event.getCode() == KeyCode.ENTER) {
            handleRunTestButton();
            event.consume();
            return;
        }

        if (event.getCode() == KeyCode.ESCAPE) {
            handleClearConsole();
            event.consume();
        }
    }

    private void configureAnimations() {
        startPulse(onlinePulseDot, 0.46, 1.0, 1050);
        startPulse(footerStatusDot, 0.52, 1.0, 1250);
        startPulse(readyDot, 0.45, 1.0, 900);

        runButton.setOnMouseEntered(event -> {
            if (!running) {
                animateScale(runButton, 1.025, 135);
            }
        });
        runButton.setOnMouseExited(event -> {
            if (!running) {
                animateScale(runButton, 1.0, 135);
            }
        });
        runButton.setOnMousePressed(event -> {
            if (!running) {
                animateScale(runButton, 0.975, 75);
            }
        });
        runButton.setOnMouseReleased(event -> {
            if (!running) {
                animateScale(runButton, runButton.isHover() ? 1.025 : 1.0, 105);
            }
        });
    }

    private void startPulse(Node node, double fromOpacity, double toOpacity, double durationMillis) {
        FadeTransition pulse = new FadeTransition(Duration.millis(durationMillis), node);
        pulse.setFromValue(fromOpacity);
        pulse.setToValue(toOpacity);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(FadeTransition.INDEFINITE);
        pulse.play();
    }

    private void animateScale(Node node, double targetScale, double durationMillis) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(durationMillis), node);
        transition.setToX(targetScale);
        transition.setToY(targetScale);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();
    }

    @FXML
    private void showOutputView() {
        switchWorkspaceView(false);
    }

    @FXML
    private void showStructureView() {
        switchWorkspaceView(true);
    }

    private void switchWorkspaceView(boolean showStructure) {
        outputViewButton.setSelected(!showStructure);
        structureViewButton.setSelected(showStructure);

        Node incoming = showStructure ? structureCard : consoleCard;
        Node outgoing = showStructure ? consoleCard : structureCard;
        structureViewActive = showStructure;

        workspaceModeDot.getStyleClass().remove("mode-structure");
        if (showStructure) {
            workspaceModeDot.getStyleClass().add("mode-structure");
            workspaceModeTitle.setText("PATTERN STRUCTURE VIEWER");
            workspaceModeCaption.setText("Bản đồ vai trò và quan hệ của mẫu đang chọn");
        } else {
            workspaceModeTitle.setText("LIVE EXECUTION CONSOLE");
            workspaceModeCaption.setText("Kết quả thực thi nghiệp vụ theo thời gian thực");
        }

        outputActionsBox.setVisible(!showStructure);
        outputActionsBox.setManaged(!showStructure);

        if (workspaceViewTransition != null) {
            workspaceViewTransition.stop();
        }

        incoming.setManaged(true);
        incoming.setVisible(true);

        if (!animationsEnabled || outgoing == null || !outgoing.isVisible()) {
            incoming.setOpacity(1);
            incoming.setTranslateY(0);
            outgoing.setVisible(false);
            outgoing.setManaged(false);
            if (showStructure) {
                Platform.runLater(this::renderStructureDiagram);
            }
            return;
        }

        incoming.setOpacity(0);
        incoming.setTranslateY(8);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(120), outgoing);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(205), incoming);
        fadeIn.setToValue(1);

        TranslateTransition slideIn = new TranslateTransition(Duration.millis(230), incoming);
        slideIn.setToY(0);
        slideIn.setInterpolator(Interpolator.EASE_OUT);

        workspaceViewTransition = new ParallelTransition(fadeOut, fadeIn, slideIn);
        workspaceViewTransition.setOnFinished(event -> {
            outgoing.setVisible(false);
            outgoing.setManaged(false);
            outgoing.setOpacity(1);
            if (showStructure) {
                renderStructureDiagram();
            }
        });
        workspaceViewTransition.play();

        if (showStructure) {
            Platform.runLater(this::renderStructureDiagram);
        }
    }

    @FXML
    private void showAllPatterns() {
        activeFamily = null;
        allFilterButton.setSelected(true);
        applyFilters();
    }

    @FXML
    private void showCreationalPatterns() {
        activeFamily = PatternFamily.CREATIONAL;
        creationalFilterButton.setSelected(true);
        applyFilters();
    }

    @FXML
    private void showStructuralPatterns() {
        activeFamily = PatternFamily.STRUCTURAL;
        structuralFilterButton.setSelected(true);
        applyFilters();
    }

    @FXML
    private void showBehavioralPatterns() {
        activeFamily = PatternFamily.BEHAVIORAL;
        behavioralFilterButton.setSelected(true);
        applyFilters();
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        searchField.requestFocus();
    }

    private void applyFilters() {
        String selectedPattern = patternListView.getSelectionModel().getSelectedItem();
        String query = searchField.getText() == null
                ? ""
                : searchField.getText().trim().toLowerCase(Locale.ROOT);

        visiblePatterns.clear();
        for (PatternDetail detail : PATTERNS.values()) {
            boolean matchesFamily = activeFamily == null || detail.family == activeFamily;
            boolean matchesSearch = query.isEmpty()
                    || detail.name.toLowerCase(Locale.ROOT).contains(query)
                    || detail.intent.toLowerCase(Locale.ROOT).contains(query)
                    || detail.family.displayName.toLowerCase(Locale.ROOT).contains(query);

            if (matchesFamily && matchesSearch) {
                visiblePatterns.add(detail.name);
            }
        }

        visiblePatternCountLabel.setText(String.format(Locale.ROOT, "%02d", visiblePatterns.size()));

        if (selectedPattern != null && visiblePatterns.contains(selectedPattern)) {
            patternListView.getSelectionModel().select(selectedPattern);
        } else if (!visiblePatterns.isEmpty()) {
            patternListView.getSelectionModel().selectFirst();
        } else {
            updatePatternDetails(null);
        }
    }

    private void updatePatternDetails(String patternName) {
        PatternDetail detail = PATTERNS.get(patternName);
        if (detail == null) {
            workspaceEyebrow.setText("PATTERN CATALOG");
            workspaceTitle.setText("Không tìm thấy Pattern");
            workspaceSubtitle.setText("Thử từ khóa khác hoặc chọn lại bộ lọc ở thanh điều hướng.");
            patternNumberLabel.setText("--");
            patternFamilyLabel.setText("NO RESULT");
            patternIntentLabel.setText("Search again");
            runButton.setDisable(true);
            updateStructureViewer(null);
            return;
        }

        workspaceEyebrow.setText(
                detail.family.displayName.toUpperCase(Locale.ROOT)
                        + "  •  DESIGN PATTERN "
                        + String.format(Locale.ROOT, "%02d/%02d", detail.number, PATTERNS.size())
        );
        workspaceTitle.setText(detail.name);
        workspaceSubtitle.setText(detail.description);
        patternNumberLabel.setText(String.format(Locale.ROOT, "%02d", detail.number));
        patternFamilyLabel.setText(detail.family.displayName);
        patternIntentLabel.setText(detail.intent);
        runButton.setDisable(running);
        updateStructureViewer(patternName);

        if (!running) {
            setExecutionState(ExecutionState.IDLE);
        }
    }

    @FXML
    private void handleRunTestButton() {
        if (running) {
            return;
        }

        showOutputView();

        String selectedPattern = patternListView.getSelectionModel().getSelectedItem();
        if (selectedPattern == null) {
            renderSingleMessage("! HỆ THỐNG: Hãy chọn một Design Pattern trước khi chạy.",
                    "console-warning");
            showToast(ToastType.WARNING, "CHƯA CHỌN PATTERN", "Chọn một mục trong Pattern Navigator.");
            return;
        }

        running = true;
        runButton.setDisable(true);
        runButton.setText("◼   ĐANG THỰC THI...");
        startRunButtonAnimation();
        setExecutionState(ExecutionState.RUNNING);
        renderRunningMessage(selectedPattern);

        PauseTransition simulatedBoot = new PauseTransition(Duration.millis(320));
        simulatedBoot.setOnFinished(event -> executeSelectedPattern(selectedPattern));
        simulatedBoot.play();
    }

    private void executeSelectedPattern(String selectedPattern) {
        long startedAt = System.nanoTime();

        try {
            String output = DesignPatternTester.runTest(selectedPattern);
            long elapsedMillis = Math.max(1L, (System.nanoTime() - startedAt) / 1_000_000L);

            renderTestOutput(selectedPattern, output, elapsedMillis, () -> {
                setExecutionState(ExecutionState.SUCCESS);
                showToast(
                        ToastType.SUCCESS,
                        "EXECUTION COMPLETED",
                        selectedPattern + " chạy thành công trong " + elapsedMillis + " ms"
                );
                finishRunButton(ExecutionState.SUCCESS);
            });
        } catch (RuntimeException exception) {
            String errorMessage = safeMessage(exception);
            renderExecutionError(selectedPattern, exception, () -> {
                setExecutionState(ExecutionState.ERROR);
                showToast(ToastType.ERROR, "EXECUTION FAILED", errorMessage);
                finishRunButton(ExecutionState.ERROR);
            });
        }
    }

    private void startRunButtonAnimation() {
        if (runButtonReset != null) {
            runButtonReset.stop();
        }

        runButton.getStyleClass().removeAll("run-button-success", "run-button-error");
        runButton.getStyleClass().add("run-button-running");

        if (!animationsEnabled) {
            return;
        }

        runButton.setScaleX(1);
        runButton.setScaleY(1);
        runButtonPulse = new ScaleTransition(Duration.millis(520), runButton);
        runButtonPulse.setFromX(1);
        runButtonPulse.setFromY(1);
        runButtonPulse.setToX(1.018);
        runButtonPulse.setToY(1.018);
        runButtonPulse.setAutoReverse(true);
        runButtonPulse.setCycleCount(ScaleTransition.INDEFINITE);
        runButtonPulse.setInterpolator(Interpolator.EASE_BOTH);
        runButtonPulse.play();
    }

    private void finishRunButton(ExecutionState state) {
        if (runButtonPulse != null) {
            runButtonPulse.stop();
        }

        runButton.setScaleX(1);
        runButton.setScaleY(1);
        runButton.getStyleClass().remove("run-button-running");

        boolean success = state == ExecutionState.SUCCESS;
        runButton.getStyleClass().add(success ? "run-button-success" : "run-button-error");
        runButton.setText(success ? "✓   HOÀN TẤT" : "×   THẤT BẠI");

        if (animationsEnabled) {
            ScaleTransition pop = new ScaleTransition(Duration.millis(170), runButton);
            pop.setFromX(0.97);
            pop.setFromY(0.97);
            pop.setToX(1);
            pop.setToY(1);
            pop.setInterpolator(Interpolator.EASE_OUT);
            pop.play();
        }

        runButtonReset = new PauseTransition(Duration.millis(animationsEnabled ? 720 : 0));
        runButtonReset.setOnFinished(event -> resetRunButton());
        runButtonReset.play();
    }

    private void resetRunButton() {
        runButton.getStyleClass().removeAll(
                "run-button-running", "run-button-success", "run-button-error"
        );
        runButton.setScaleX(1);
        runButton.setScaleY(1);
        runButton.setText("▶   KÍCH HOẠT TEST");
        running = false;
        runButton.setDisable(patternListView.getSelectionModel().getSelectedItem() == null);
    }

    private void updateStructureViewer(String patternName) {
        activeStructure = patternName == null ? null : STRUCTURES.get(patternName);
        structureRenderDirty = true;

        if (activeStructure == null) {
            structurePatternName.setText("NO BLUEPRINT SELECTED");
            structureFamilyBadge.setText("PATTERN CATALOG");
            structureMechanismLabel.setText(
                    "Chọn một Design Pattern để xem vai trò và hướng tương tác giữa các thành phần."
            );
        } else {
            structurePatternName.setText(patternName.toUpperCase(Locale.ROOT) + " BLUEPRINT");
            structureFamilyBadge.setText(activeStructure.family.displayName.toUpperCase(Locale.ROOT));
            structureMechanismLabel.setText(activeStructure.mechanism);
        }

        scheduleStructureRender();
    }

    private void scheduleStructureRender() {
        if (structureResizeDebounce == null || !structureViewActive) {
            return;
        }
        structureResizeDebounce.playFromStart();
    }

    private void renderStructureDiagram() {
        if (!structureViewActive || !structureCard.isManaged()) {
            return;
        }

        double canvasWidth = structureCanvas.getWidth();
        double canvasHeight = structureCanvas.getHeight();
        if (canvasWidth < 220 || canvasHeight < 150) {
            Platform.runLater(this::scheduleStructureRender);
            return;
        }

        boolean sameStructure = activeStructure == renderedStructure;
        boolean sameSize = Math.abs(canvasWidth - renderedCanvasWidth) < 2
                && Math.abs(canvasHeight - renderedCanvasHeight) < 2;
        if (!structureRenderDirty && sameStructure && sameSize) {
            return;
        }

        structureRenderDirty = false;
        renderedStructure = activeStructure;
        renderedCanvasWidth = canvasWidth;
        renderedCanvasHeight = canvasHeight;

        if (structureDiagramAnimation != null) {
            structureDiagramAnimation.stop();
        }
        structureDiagramAnimation = new ParallelTransition();

        structureCanvas.getChildren().clear();

        if (activeStructure == null) {
            Label placeholder = new Label("SELECT A PATTERN TO GENERATE ITS BLUEPRINT");
            placeholder.getStyleClass().add("structure-empty-label");
            placeholder.setAlignment(Pos.CENTER);
            placeholder.setPrefWidth(canvasWidth);
            placeholder.relocate(0, Math.max(0, canvasHeight / 2 - 14));
            structureCanvas.getChildren().add(placeholder);
            return;
        }

        int nodeCount = activeStructure.nodes.size();
        double nodeWidth = activeStructure.layout == StructureLayout.LINEAR
                ? clamp(canvasWidth * 0.19, 116, 154)
                : clamp(canvasWidth * 0.22, 128, 166);
        double nodeHeight = clamp(canvasHeight * 0.25, 66, 78);

        List<Point2D> positions = calculateNodePositions(
                activeStructure.layout,
                nodeCount,
                canvasWidth,
                canvasHeight,
                nodeWidth,
                nodeHeight
        );

        String familyToken = activeStructure.family.name().toLowerCase(Locale.ROOT);

        for (StructureEdge edge : activeStructure.edges) {
            if (edge.fromIndex >= positions.size() || edge.toIndex >= positions.size()) {
                continue;
            }

            Point2D fromCenter = positions.get(edge.fromIndex);
            Point2D toCenter = positions.get(edge.toIndex);
            Point2D start = rectangleBoundaryPoint(
                    fromCenter, toCenter, nodeWidth, nodeHeight
            );
            Point2D end = rectangleBoundaryPoint(
                    toCenter, fromCenter, nodeWidth, nodeHeight
            );

            Line line = new Line(start.getX(), start.getY(), end.getX(), end.getY());
            line.getStyleClass().addAll("structure-edge", "structure-edge-" + familyToken);
            line.setMouseTransparent(true);

            Polygon arrow = createArrowHead(start, end);
            arrow.getStyleClass().addAll(
                    "structure-arrow", "structure-arrow-" + familyToken
            );
            arrow.setMouseTransparent(true);

            Label relation = new Label(edge.label.toUpperCase(Locale.ROOT));
            relation.getStyleClass().add("structure-relation-label");
            relation.setAlignment(Pos.CENTER);
            relation.setPrefWidth(84);
            relation.setMouseTransparent(true);

            double midX = (start.getX() + end.getX()) / 2;
            double midY = (start.getY() + end.getY()) / 2;
            relation.relocate(midX - 42, midY - 19);

            structureCanvas.getChildren().addAll(line, arrow, relation);
            animateDiagramElement(line, 25);
            animateDiagramElement(arrow, 80);
            animateDiagramElement(relation, 110);
        }

        for (int index = 0; index < activeStructure.nodes.size(); index++) {
            StructureNode nodeModel = activeStructure.nodes.get(index);
            Point2D center = positions.get(index);
            StackPane nodeView = createStructureNode(
                    nodeModel,
                    index,
                    nodeWidth,
                    nodeHeight,
                    familyToken
            );
            nodeView.relocate(
                    center.getX() - nodeWidth / 2,
                    center.getY() - nodeHeight / 2
            );
            structureCanvas.getChildren().add(nodeView);
            animateStructureNode(nodeView, 90 + index * 65);
        }

        if (animationsEnabled && !structureDiagramAnimation.getChildren().isEmpty()) {
            structureDiagramAnimation.play();
        }
    }

    private StackPane createStructureNode(StructureNode nodeModel,
                                          int index,
                                          double width,
                                          double height,
                                          String familyToken) {
        Label role = new Label(nodeModel.role.toUpperCase(Locale.ROOT));
        role.getStyleClass().add("structure-node-role");

        Label name = new Label(nodeModel.name);
        name.getStyleClass().add("structure-node-name");
        name.setWrapText(true);
        name.setMaxWidth(width - 24);

        VBox copy = new VBox(3, role, name);
        copy.setAlignment(Pos.CENTER_LEFT);

        Label indexBadge = new Label(String.format(Locale.ROOT, "%02d", index + 1));
        indexBadge.getStyleClass().add("structure-node-index");
        StackPane.setAlignment(indexBadge, Pos.TOP_RIGHT);
        StackPane.setMargin(indexBadge, new Insets(7, 8, 0, 0));

        StackPane node = new StackPane(copy, indexBadge);
        node.getStyleClass().addAll(
                "structure-node",
                "structure-node-" + familyToken
        );
        if (index == 0) {
            node.getStyleClass().add("structure-node-primary");
        }
        node.setPadding(new Insets(10, 12, 9, 12));
        node.setPrefSize(width, height);
        node.setMinSize(width, height);
        node.setMaxSize(width, height);
        node.setMouseTransparent(true);
        return node;
    }

    private List<Point2D> calculateNodePositions(StructureLayout layout,
                                                 int count,
                                                 double width,
                                                 double height,
                                                 double nodeWidth,
                                                 double nodeHeight) {
        List<Point2D> points = new ArrayList<Point2D>();

        if (layout == StructureLayout.LINEAR) {
            for (int index = 0; index < count; index++) {
                double fraction = count == 1 ? 0.5 : (double) index / (count - 1);
                points.add(safePoint(
                        nodeWidth / 2 + 13 + fraction * (width - nodeWidth - 26),
                        height * 0.50,
                        width,
                        height,
                        nodeWidth,
                        nodeHeight
                ));
            }
            return points;
        }

        double[][] normalized;
        switch (layout) {
            case HUB:
                normalized = new double[][]{
                        {0.50, 0.50}, {0.16, 0.26}, {0.84, 0.26}, {0.50, 0.80}
                };
                break;
            case TREE:
                normalized = new double[][]{
                        {0.50, 0.22}, {0.18, 0.72}, {0.50, 0.72}, {0.82, 0.72}
                };
                break;
            case BRANCH:
                normalized = new double[][]{
                        {0.14, 0.50}, {0.43, 0.50}, {0.78, 0.27}, {0.78, 0.73}
                };
                break;
            case DIAMOND:
                normalized = new double[][]{
                        {0.14, 0.50}, {0.43, 0.25}, {0.43, 0.75}, {0.82, 0.50}
                };
                break;
            case LOOP:
                normalized = new double[][]{
                        {0.16, 0.50}, {0.43, 0.25}, {0.78, 0.32}, {0.68, 0.76}
                };
                break;
            default:
                normalized = new double[][]{{0.50, 0.50}};
        }

        for (int index = 0; index < count; index++) {
            double[] coordinate = normalized[Math.min(index, normalized.length - 1)];
            points.add(safePoint(
                    width * coordinate[0],
                    height * coordinate[1],
                    width,
                    height,
                    nodeWidth,
                    nodeHeight
            ));
        }
        return points;
    }

    private Point2D safePoint(double x,
                              double y,
                              double canvasWidth,
                              double canvasHeight,
                              double nodeWidth,
                              double nodeHeight) {
        double safeX = clamp(x, nodeWidth / 2 + 12, canvasWidth - nodeWidth / 2 - 12);
        double safeY = clamp(y, nodeHeight / 2 + 12, canvasHeight - nodeHeight / 2 - 12);
        return new Point2D(safeX, safeY);
    }

    private Point2D rectangleBoundaryPoint(Point2D center,
                                           Point2D toward,
                                           double rectangleWidth,
                                           double rectangleHeight) {
        double deltaX = toward.getX() - center.getX();
        double deltaY = toward.getY() - center.getY();

        if (Math.abs(deltaX) < 0.001 && Math.abs(deltaY) < 0.001) {
            return center;
        }

        double horizontalScale = Math.abs(deltaX) < 0.001
                ? Double.POSITIVE_INFINITY
                : (rectangleWidth / 2) / Math.abs(deltaX);
        double verticalScale = Math.abs(deltaY) < 0.001
                ? Double.POSITIVE_INFINITY
                : (rectangleHeight / 2) / Math.abs(deltaY);
        double scale = Math.min(horizontalScale, verticalScale);

        return new Point2D(
                center.getX() + deltaX * scale,
                center.getY() + deltaY * scale
        );
    }

    private Polygon createArrowHead(Point2D start, Point2D end) {
        double angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
        double length = 8;
        double spread = Math.toRadians(25);

        double leftX = end.getX() - length * Math.cos(angle - spread);
        double leftY = end.getY() - length * Math.sin(angle - spread);
        double rightX = end.getX() - length * Math.cos(angle + spread);
        double rightY = end.getY() - length * Math.sin(angle + spread);

        return new Polygon(
                end.getX(), end.getY(),
                leftX, leftY,
                rightX, rightY
        );
    }

    private void animateDiagramElement(Node node, double delayMillis) {
        if (!animationsEnabled) {
            node.setOpacity(1);
            return;
        }

        node.setOpacity(0.38);
        FadeTransition fade = new FadeTransition(Duration.millis(230), node);
        fade.setDelay(Duration.millis(delayMillis));
        fade.setFromValue(0.38);
        fade.setToValue(1);
        structureDiagramAnimation.getChildren().add(fade);
    }

    private void animateStructureNode(Node node, double delayMillis) {
        if (!animationsEnabled) {
            node.setOpacity(1);
            node.setScaleX(1);
            node.setScaleY(1);
            node.setTranslateY(0);
            return;
        }

        node.setOpacity(0.55);
        node.setScaleX(0.96);
        node.setScaleY(0.96);
        node.setTranslateY(7);

        FadeTransition fade = new FadeTransition(Duration.millis(220), node);
        fade.setFromValue(0.55);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(Duration.millis(260), node);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        TranslateTransition slide = new TranslateTransition(Duration.millis(260), node);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition entrance = new ParallelTransition(fade, scale, slide);
        entrance.setDelay(Duration.millis(delayMillis));
        structureDiagramAnimation.getChildren().add(entrance);
    }

    private static double clamp(double value, double minimum, double maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    @FXML
    private void handleClearConsole() {
        if (running) {
            return;
        }
        showConsoleWelcome();
        setExecutionState(ExecutionState.IDLE);
    }

    @FXML
    private void handleCopyConsole() {
        String output = getConsolePlainText();
        if (output.trim().isEmpty()) {
            showToast(ToastType.WARNING, "CONSOLE EMPTY", "Chưa có nội dung để sao chép.");
            return;
        }

        ClipboardContent content = new ClipboardContent();
        content.putString(output);
        Clipboard.getSystemClipboard().setContent(content);
        showToast(ToastType.SUCCESS, "COPIED TO CLIPBOARD", "Đã sao chép toàn bộ kết quả console.");
    }

    private String getConsolePlainText() {
        StringBuilder output = new StringBuilder();
        for (Node node : consoleTextFlow.getChildren()) {
            if (node instanceof Text) {
                output.append(((Text) node).getText());
            }
        }
        return output.toString();
    }

    private void renderRunningMessage(String patternName) {
        consoleTextFlow.getChildren().clear();
        appendConsoleLine("$ e-catering test --pattern \"" + patternName + "\"", "console-command");
        appendConsoleLine("", "console-normal");
        appendConsoleLine("● Initializing pattern runtime...", "console-running");
        appendConsoleLine("● Resolving business scenario...", "console-running");
        appendConsoleLine("● Executing automated test...", "console-running");
        animateConsole();
        revealConsoleLines(null);
    }

    private void renderTestOutput(String patternName,
                                  String output,
                                  long elapsedMillis,
                                  Runnable onFinished) {
        consoleTextFlow.getChildren().clear();
        appendConsoleLine("$ e-catering test --pattern \"" + patternName + "\"", "console-command");
        appendConsoleLine(
                "  EXECUTION REPORT  •  " + elapsedMillis + " ms  •  UTF-8",
                "console-meta"
        );
        appendConsoleLine("────────────────────────────────────────────────────────────", "console-divider-text");

        if (output == null || output.trim().isEmpty()) {
            appendConsoleLine("Không có dữ liệu trả về từ DesignPatternTester.", "console-warning");
        } else {
            String[] lines = output.split("\\R", -1);
            for (String line : lines) {
                appendConsoleLine(line, classifyOutputLine(line));
            }
        }

        appendConsoleLine("", "console-normal");
        appendConsoleLine("✓ Process finished successfully · exit code 0", "console-success");
        animateConsole();
        revealConsoleLines(onFinished);
    }

    private void renderExecutionError(String patternName,
                                      RuntimeException exception,
                                      Runnable onFinished) {
        consoleTextFlow.getChildren().clear();
        appendConsoleLine("$ e-catering test --pattern \"" + patternName + "\"", "console-command");
        appendConsoleLine("────────────────────────────────────────────────────────────", "console-divider-text");
        appendConsoleLine("✕ EXECUTION FAILED", "console-error");
        appendConsoleLine(exception.getClass().getSimpleName() + ": " + safeMessage(exception),
                "console-error");
        appendConsoleLine("Process finished · exit code 1", "console-error");
        animateConsole();
        revealConsoleLines(onFinished);
    }

    private void renderSingleMessage(String message, String styleClass) {
        consoleTextFlow.getChildren().clear();
        appendConsoleLine(message, styleClass);
        animateConsole();
        revealConsoleLines(null);
    }

    private void showConsoleWelcome() {
        consoleTextFlow.getChildren().clear();
        appendConsoleLine("E-CATERING PATTERN LAB  /  INTERACTIVE CONSOLE", "console-meta");
        appendConsoleLine("────────────────────────────────────────────────────────────", "console-divider-text");
        appendConsoleLine("› Chọn một Design Pattern trong Navigator.", "console-muted");
        appendConsoleLine("› Nhấn KÍCH HOẠT TEST hoặc phím Enter để bắt đầu.", "console-muted");
        appendConsoleLine("› Ctrl+K: tìm kiếm  •  Esc: xóa console", "console-hint");
        revealConsoleLines(null);
    }

    private void appendConsoleLine(String line, String styleClass) {
        Text text = new Text(line + System.lineSeparator());
        text.getStyleClass().addAll("console-text", styleClass);
        consoleTextFlow.getChildren().add(text);
    }

    private String classifyOutputLine(String line) {
        String trimmed = line.trim();
        String lower = trimmed.toLowerCase(Locale.ROOT);

        if (trimmed.isEmpty()) {
            return "console-normal";
        }
        if (lower.contains("exception") || lower.contains("error") || lower.contains("lỗi")
                || lower.contains("false") || lower.contains("không đạt")) {
            return "console-error";
        }
        if (lower.contains("true") || lower.contains("thành công") || lower.contains("đạt")) {
            return "console-success";
        }
        if (trimmed.startsWith("==>") || trimmed.startsWith("[") || trimmed.endsWith(":")) {
            return "console-accent";
        }
        if (trimmed.startsWith("-") || trimmed.startsWith("•")) {
            return "console-value";
        }
        return "console-normal";
    }

    private void animateConsole() {
        if (!animationsEnabled) {
            consoleCard.setOpacity(1);
            consoleCard.setTranslateY(0);
            return;
        }

        consoleCard.setOpacity(0.45);
        consoleCard.setTranslateY(7);

        FadeTransition fade = new FadeTransition(Duration.millis(230), consoleCard);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(230), consoleCard);
        slide.setToY(0);

        new ParallelTransition(fade, slide).play();
    }

    /**
     * Hiện console theo từng dòng. Các dòng chưa tới lượt không chiếm chỗ,
     * nhờ đó thanh cuộn lớn dần tự nhiên như một terminal đang chạy thật.
     */
    private void revealConsoleLines(Runnable onFinished) {
        if (consoleRevealTimeline != null) {
            consoleRevealTimeline.stop();
        }

        List<Node> lines = new ArrayList<Node>(consoleTextFlow.getChildren());
        if (lines.isEmpty()) {
            if (onFinished != null) {
                onFinished.run();
            }
            return;
        }

        if (!animationsEnabled) {
            for (Node line : lines) {
                line.setManaged(true);
                line.setVisible(true);
                line.setOpacity(1);
                line.setTranslateX(0);
            }
            scrollConsoleToBottom();
            if (onFinished != null) {
                onFinished.run();
            }
            return;
        }

        for (Node line : lines) {
            line.setManaged(false);
            line.setVisible(false);
            line.setOpacity(0);
            line.setTranslateX(6);
        }

        consoleRevealTimeline = new Timeline();
        final double staggerMillis = 42;
        final double fadeMillis = 125;

        for (int index = 0; index < lines.size(); index++) {
            Node line = lines.get(index);
            double startMillis = index * staggerMillis;

            consoleRevealTimeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(startMillis), event -> {
                        line.setManaged(true);
                        line.setVisible(true);
                        consoleScrollPane.setVvalue(1.0);
                    })
            );
            consoleRevealTimeline.getKeyFrames().add(
                    new KeyFrame(
                            Duration.millis(startMillis + fadeMillis),
                            new KeyValue(line.opacityProperty(), 1, Interpolator.EASE_OUT),
                            new KeyValue(line.translateXProperty(), 0, Interpolator.EASE_OUT)
                    )
            );
        }

        consoleRevealTimeline.setOnFinished(event -> {
            scrollConsoleToBottom();
            if (onFinished != null) {
                onFinished.run();
            }
        });
        consoleRevealTimeline.play();
    }

    private void scrollConsoleToBottom() {
        Platform.runLater(() -> consoleScrollPane.setVvalue(1.0));
    }

    private void setExecutionState(ExecutionState state) {
        statusChip.getStyleClass().removeAll(
                "status-idle", "status-running", "status-success", "status-error"
        );
        readyDot.getStyleClass().removeAll(
                "ready-idle", "ready-running", "ready-success", "ready-error"
        );

        statusChip.getStyleClass().add(state.statusStyleClass);
        readyDot.getStyleClass().add(state.readyStyleClass);
        statusChipText.setText(state.chipText);
        readyLabel.setText(state.readyText);

        if (animationsEnabled && state != ExecutionState.IDLE) {
            ScaleTransition pop = new ScaleTransition(Duration.millis(155), statusChip);
            pop.setFromX(0.92);
            pop.setFromY(0.92);
            pop.setToX(1);
            pop.setToY(1);
            pop.setInterpolator(Interpolator.EASE_OUT);
            pop.play();
        }
    }

    private void showToast(ToastType type, String title, String message) {
        if (toastAnimation != null) {
            toastAnimation.stop();
        }

        toastPane.getStyleClass().removeAll("toast-success", "toast-warning", "toast-error");
        toastPane.getStyleClass().add(type.styleClass);
        toastIcon.setText(type.icon);
        toastTitle.setText(title);
        toastMessage.setText(message);

        toastPane.setManaged(true);
        toastPane.setVisible(true);
        toastPane.setOpacity(0);
        toastPane.setTranslateY(-12);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(180), toastPane);
        fadeIn.setToValue(1);
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(210), toastPane);
        slideIn.setToY(0);

        PauseTransition hold = new PauseTransition(Duration.seconds(2.4));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(220), toastPane);
        fadeOut.setToValue(0);
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(220), toastPane);
        slideOut.setToY(-8);

        toastAnimation = new SequentialTransition(
                new ParallelTransition(fadeIn, slideIn),
                hold,
                new ParallelTransition(fadeOut, slideOut)
        );
        toastAnimation.setOnFinished(event -> {
            toastPane.setVisible(false);
            toastPane.setManaged(false);
        });
        toastAnimation.play();
    }

    private static String safeMessage(RuntimeException exception) {
        String message = exception.getMessage();
        return message == null || message.trim().isEmpty()
                ? "Lỗi không xác định khi thực thi pattern"
                : message;
    }

    private static Map<String, PatternStructure> createStructureCatalog() {
        Map<String, PatternStructure> structures =
                new LinkedHashMap<String, PatternStructure>();

        putStructure(structures, "Singleton", StructureLayout.LINEAR,
                "Client luôn đi qua getInstance(); lớp chỉ khởi tạo một AppConfig và trả lại đúng thể hiện dùng chung đó.",
                parts(
                        part("Caller", "Client"),
                        part("Access point", "getInstance()"),
                        part("Single object", "AppConfig"),
                        part("Shared state", "Configuration")
                ),
                link(0, 1, "calls"), link(1, 2, "returns"), link(2, 3, "owns"));

        putStructure(structures, "Abstract Factory", StructureLayout.BRANCH,
                "Client làm việc với AbstractFactory; mỗi factory cụ thể tạo ra một họ sản phẩm tương thích mà Client không biết lớp thật.",
                parts(
                        part("Consumer", "Client"),
                        part("Factory contract", "AbstractFactory"),
                        part("Product family", "Product A"),
                        part("Product family", "Product B")
                ),
                link(0, 1, "uses"), link(1, 2, "creates"), link(1, 3, "creates"));

        putStructure(structures, "Factory Method", StructureLayout.LINEAR,
                "Creator định nghĩa factoryMethod(); lớp con ghi đè phương thức này để quyết định ConcreteProduct được tạo lúc chạy.",
                parts(
                        part("Consumer", "Client"),
                        part("Base type", "Creator"),
                        part("Creation hook", "factoryMethod()"),
                        part("Result", "ConcreteProduct")
                ),
                link(0, 1, "requests"), link(1, 2, "delegates"), link(2, 3, "creates"));

        putStructure(structures, "Builder", StructureLayout.LINEAR,
                "Director điều phối từng bước qua Builder; ConcreteBuilder tích lũy trạng thái và xuất ra Product hoàn chỉnh.",
                parts(
                        part("Orchestrator", "Director"),
                        part("Build contract", "Builder"),
                        part("Implementation", "ConcreteBuilder"),
                        part("Result", "Product")
                ),
                link(0, 1, "directs"), link(1, 2, "implemented"), link(2, 3, "builds"));

        putStructure(structures, "Prototype", StructureLayout.LINEAR,
                "Client yêu cầu clone() trên một Prototype đã cấu hình để nhận bản sao mới mà không phụ thuộc constructor cụ thể.",
                parts(
                        part("Consumer", "Client"),
                        part("Prototype", "Configured Object"),
                        part("Copy operation", "clone()"),
                        part("Result", "Cloned Object")
                ),
                link(0, 1, "selects"), link(1, 2, "invokes"), link(2, 3, "copies"));

        putStructure(structures, "Adapter", StructureLayout.LINEAR,
                "Adapter nhận lời gọi theo Target interface, chuyển đổi dữ liệu rồi ủy quyền cho Adaptee có giao diện không tương thích.",
                parts(
                        part("Consumer", "Client"),
                        part("Expected API", "Target"),
                        part("Translator", "Adapter"),
                        part("Legacy API", "Adaptee")
                ),
                link(0, 1, "calls"), link(1, 2, "routes"), link(2, 3, "adapts"));

        putStructure(structures, "Bridge", StructureLayout.HUB,
                "Abstraction giữ tham chiếu tới Implementor và ủy quyền công việc; hai hệ phân cấp có thể mở rộng độc lập.",
                parts(
                        part("Abstraction", "Service"),
                        part("Consumer", "Client"),
                        part("Extension", "RefinedAbstraction"),
                        part("Implementation", "Implementor")
                ),
                link(1, 0, "uses"), link(2, 0, "extends"), link(0, 3, "delegates"));

        putStructure(structures, "Composite", StructureLayout.TREE,
                "Leaf và Composite cùng hiện thực Component; Composite chứa các Component con để Client xử lý phần tử và cả cây thống nhất.",
                parts(
                        part("Common contract", "Component"),
                        part("Terminal node", "Leaf"),
                        part("Container", "Composite"),
                        part("Nested item", "Child Component")
                ),
                link(0, 1, "implemented"), link(0, 2, "implemented"), link(2, 3, "contains"));

        putStructure(structures, "Decorator", StructureLayout.LINEAR,
                "Decorator giữ một Component bên trong, chuyển tiếp lời gọi rồi bổ sung hành vi trước hoặc sau mà không sửa lớp gốc.",
                parts(
                        part("Consumer", "Client"),
                        part("Contract", "Component"),
                        part("Wrapper", "Decorator"),
                        part("Wrapped object", "ConcreteComponent")
                ),
                link(0, 1, "uses"), link(1, 2, "decorated"), link(2, 3, "wraps"));

        putStructure(structures, "Facade", StructureLayout.BRANCH,
                "Client gọi một API đơn giản trên Facade; Facade phối hợp nhiều subsystem phức tạp phía sau.",
                parts(
                        part("Consumer", "Client"),
                        part("Unified API", "Facade"),
                        part("Internal module", "Subsystem A"),
                        part("Internal module", "Subsystem B")
                ),
                link(0, 1, "calls"), link(1, 2, "coordinates"), link(1, 3, "coordinates"));

        putStructure(structures, "Flyweight", StructureLayout.HUB,
                "FlyweightFactory tái sử dụng trạng thái nội tại dùng chung; Client chỉ truyền trạng thái ngoại tại cho từng lần xử lý.",
                parts(
                        part("Object pool", "FlyweightFactory"),
                        part("Consumer", "Client"),
                        part("Shared object", "Flyweight"),
                        part("Runtime data", "Extrinsic State")
                ),
                link(1, 0, "requests"), link(0, 2, "reuses"), link(1, 3, "supplies"));

        putStructure(structures, "Proxy", StructureLayout.LINEAR,
                "Proxy cùng interface với RealSubject, chặn lời gọi để kiểm tra quyền, cache hoặc lazy-load trước khi chuyển tiếp.",
                parts(
                        part("Consumer", "Client"),
                        part("Contract", "Subject"),
                        part("Gatekeeper", "Proxy"),
                        part("Real service", "RealSubject")
                ),
                link(0, 1, "calls"), link(1, 2, "represented"), link(2, 3, "controls"));

        putStructure(structures, "Chain of Responsibility", StructureLayout.LINEAR,
                "Request chạy qua chuỗi Handler; mỗi mắt xích xử lý hoặc chuyển tiếp cho Handler kế tiếp cho tới khi hoàn tất.",
                parts(
                        part("Input", "Request"),
                        part("Handler", "Validation"),
                        part("Handler", "Authorization"),
                        part("Handler", "Processing")
                ),
                link(0, 1, "enters"), link(1, 2, "passes"), link(2, 3, "passes"));

        putStructure(structures, "Command", StructureLayout.LINEAR,
                "Invoker chỉ biết Command; ConcreteCommand đóng gói yêu cầu và gọi Receiver để thực hiện nghiệp vụ thật.",
                parts(
                        part("Configurator", "Client"),
                        part("Trigger", "Invoker"),
                        part("Request object", "Command"),
                        part("Executor", "Receiver")
                ),
                link(0, 1, "configures"), link(1, 2, "executes"), link(2, 3, "invokes"));

        putStructure(structures, "Interpreter", StructureLayout.TREE,
                "Parser dựng cây Expression; các Terminal và Non-terminal cùng interpret(context) rồi kết hợp kết quả từ dưới lên.",
                parts(
                        part("Grammar root", "Expression"),
                        part("Leaf rule", "TerminalExpression"),
                        part("Composite rule", "And / Or"),
                        part("Input data", "OrderContext")
                ),
                link(0, 1, "contains"), link(0, 2, "composes"), link(2, 3, "interprets"));

        putStructure(structures, "Iterator", StructureLayout.LINEAR,
                "Aggregate tạo Iterator; Client gọi hasNext()/next() để duyệt phần tử mà không thấy cấu trúc lưu trữ bên trong.",
                parts(
                        part("Consumer", "Client"),
                        part("Collection", "Aggregate"),
                        part("Cursor", "Iterator"),
                        part("Data", "Elements")
                ),
                link(0, 1, "requests"), link(1, 2, "creates"), link(2, 3, "traverses"));

        putStructure(structures, "Mediator", StructureLayout.HUB,
                "Các Colleague không gọi trực tiếp nhau; mọi thông điệp đi qua Mediator để giảm phụ thuộc chéo.",
                parts(
                        part("Coordinator", "Mediator"),
                        part("Participant", "Colleague A"),
                        part("Participant", "Colleague B"),
                        part("Participant", "Colleague C")
                ),
                link(1, 0, "notifies"), link(2, 0, "notifies"), link(0, 3, "coordinates"));

        putStructure(structures, "Memento", StructureLayout.LOOP,
                "Originator tạo snapshot Memento; Caretaker chỉ lưu giữ và trả lại snapshot để Originator phục hồi trạng thái trước đó.",
                parts(
                        part("State owner", "Originator"),
                        part("Snapshot", "Memento"),
                        part("History", "Caretaker"),
                        part("Recovered data", "Previous State")
                ),
                link(0, 1, "snapshots"), link(1, 2, "stored by"), link(2, 3, "retrieves"),
                link(3, 0, "restores"));

        putStructure(structures, "Observer", StructureLayout.HUB,
                "Observer đăng ký với Subject; khi trạng thái thay đổi, Subject phát thông báo tới toàn bộ subscriber.",
                parts(
                        part("Publisher", "Subject"),
                        part("Subscriber", "Observer A"),
                        part("Subscriber", "Observer B"),
                        part("Subscriber", "Observer C")
                ),
                link(1, 0, "subscribes"), link(0, 2, "notifies"), link(0, 3, "notifies"));

        putStructure(structures, "State", StructureLayout.HUB,
                "Context ủy quyền hành vi cho State hiện tại; ConcreteState có thể chuyển Context sang trạng thái khác lúc chạy.",
                parts(
                        part("State owner", "Context"),
                        part("Consumer", "Client"),
                        part("Behavior", "State"),
                        part("Implementation", "ConcreteState")
                ),
                link(1, 0, "requests"), link(0, 2, "delegates"), link(2, 3, "implemented"));

        putStructure(structures, "Strategy", StructureLayout.BRANCH,
                "Context giữ Strategy interface và có thể hoán đổi thuật toán cụ thể mà Client lựa chọn trong lúc chạy.",
                parts(
                        part("Consumer", "Client"),
                        part("Strategy host", "Context"),
                        part("Algorithm", "Strategy A"),
                        part("Algorithm", "Strategy B")
                ),
                link(0, 1, "configures"), link(1, 2, "selects"), link(1, 3, "switches"));

        putStructure(structures, "Template Method", StructureLayout.TREE,
                "AbstractClass cố định trình tự trong templateMethod(); ConcreteClass chỉ ghi đè các primitive operation được cho phép.",
                parts(
                        part("Algorithm base", "AbstractClass"),
                        part("Skeleton", "templateMethod()"),
                        part("Hook", "primitiveOperation()"),
                        part("Specialization", "ConcreteClass")
                ),
                link(0, 1, "defines"), link(0, 2, "calls"), link(0, 3, "extended"));

        putStructure(structures, "Visitor", StructureLayout.DIAMOND,
                "accept(visitor) chọn đúng loại OrderElement; visitor.visit(this) thực hiện lần dispatch thứ hai để chạy báo cáo tương ứng.",
                parts(
                        part("Orchestrator", "Client"),
                        part("Visited object", "OrderElement"),
                        part("Operation set", "OrderVisitor"),
                        part("Concrete type", "ConcreteOrder")
                ),
                link(0, 1, "accept"), link(0, 2, "provides"), link(1, 3, "dispatch 1"),
                link(3, 2, "dispatch 2"));

        return structures;
    }

    private static void putStructure(Map<String, PatternStructure> structures,
                                     String patternName,
                                     StructureLayout layout,
                                     String mechanism,
                                     List<StructureNode> nodes,
                                     StructureEdge... edges) {
        PatternDetail detail = PATTERNS.get(patternName);
        if (detail == null) {
            throw new IllegalArgumentException("Không tìm thấy pattern: " + patternName);
        }
        structures.put(patternName, new PatternStructure(
                detail.family,
                layout,
                mechanism,
                nodes,
                java.util.Arrays.asList(edges)
        ));
    }

    private static List<StructureNode> parts(StructureNode... nodes) {
        return java.util.Arrays.asList(nodes);
    }

    private static StructureNode part(String role, String name) {
        return new StructureNode(role, name);
    }

    private static StructureEdge link(int fromIndex, int toIndex, String label) {
        return new StructureEdge(fromIndex, toIndex, label);
    }

    private static Map<String, PatternDetail> createPatternCatalog() {
        Map<String, PatternDetail> patterns = new LinkedHashMap<String, PatternDetail>();

        add(patterns, 1, "Singleton", PatternFamily.CREATIONAL, "Single Instance",
                "Bảo đảm một lớp chỉ có một thể hiện và cung cấp điểm truy cập dùng chung trong toàn hệ thống.");
        add(patterns, 2, "Abstract Factory", PatternFamily.CREATIONAL, "Product Families",
                "Tạo các họ đối tượng liên quan mà không phụ thuộc vào lớp cụ thể của từng sản phẩm.");
        add(patterns, 3, "Factory Method", PatternFamily.CREATIONAL, "Deferred Creation",
                "Để lớp con quyết định kiểu đối tượng cụ thể sẽ được khởi tạo cho từng tình huống nghiệp vụ.");
        add(patterns, 4, "Builder", PatternFamily.CREATIONAL, "Step-by-step Build",
                "Tách quá trình dựng đối tượng phức tạp khỏi biểu diễn cuối cùng của đối tượng đó.");
        add(patterns, 5, "Prototype", PatternFamily.CREATIONAL, "Object Cloning",
                "Tạo đối tượng mới bằng cách sao chép một nguyên mẫu đã được cấu hình sẵn.");

        add(patterns, 6, "Adapter", PatternFamily.STRUCTURAL, "Interface Conversion",
                "Chuyển đổi interface hiện có thành interface mà phía Client đang cần sử dụng.");
        add(patterns, 7, "Bridge", PatternFamily.STRUCTURAL, "Decouple Layers",
                "Tách phần trừu tượng khỏi phần triển khai để hai phía có thể thay đổi độc lập.");
        add(patterns, 8, "Composite", PatternFamily.STRUCTURAL, "Tree Structure",
                "Tổ chức đối tượng theo cấu trúc cây và xử lý phần tử đơn lẻ như một nhóm thống nhất.");
        add(patterns, 9, "Decorator", PatternFamily.STRUCTURAL, "Dynamic Features",
                "Bổ sung trách nhiệm cho đối tượng ở thời điểm chạy mà không sửa lớp gốc.");
        add(patterns, 10, "Facade", PatternFamily.STRUCTURAL, "Simplified API",
                "Cung cấp một cổng giao tiếp đơn giản cho một hệ thống con có nhiều thành phần phức tạp.");
        add(patterns, 11, "Flyweight", PatternFamily.STRUCTURAL, "Shared State",
                "Chia sẻ trạng thái dùng chung để giảm số lượng đối tượng và tối ưu bộ nhớ.");
        add(patterns, 12, "Proxy", PatternFamily.STRUCTURAL, "Controlled Access",
                "Dùng đối tượng đại diện để kiểm soát quyền truy cập tới đối tượng thật.");

        add(patterns, 13, "Chain of Responsibility", PatternFamily.BEHAVIORAL, "Handler Pipeline",
                "Chuyển yêu cầu qua chuỗi bộ xử lý cho đến khi có thành phần phù hợp tiếp nhận.");
        add(patterns, 14, "Command", PatternFamily.BEHAVIORAL, "Encapsulated Action",
                "Đóng gói một yêu cầu thành đối tượng để hỗ trợ hàng đợi, lịch sử và hoàn tác.");
        add(patterns, 15, "Interpreter", PatternFamily.BEHAVIORAL, "Rule Evaluation",
                "Biểu diễn ngữ pháp thành cây biểu thức và thông dịch quy tắc trên dữ liệu thực tế.");
        add(patterns, 16, "Iterator", PatternFamily.BEHAVIORAL, "Sequential Access",
                "Duyệt tuần tự tập hợp mà không để lộ cấu trúc lưu trữ bên trong.");
        add(patterns, 17, "Mediator", PatternFamily.BEHAVIORAL, "Central Coordination",
                "Điều phối giao tiếp giữa nhiều đối tượng thông qua một trung tâm trung gian.");
        add(patterns, 18, "Memento", PatternFamily.BEHAVIORAL, "State Snapshot",
                "Lưu và phục hồi trạng thái trước đó mà không phá vỡ tính đóng gói của đối tượng.");
        add(patterns, 19, "Observer", PatternFamily.BEHAVIORAL, "Event Subscription",
                "Tự động thông báo cho nhiều đối tượng phụ thuộc khi trạng thái nguồn thay đổi.");
        add(patterns, 20, "State", PatternFamily.BEHAVIORAL, "State-driven Behavior",
                "Cho phép đối tượng thay đổi hành vi khi trạng thái nội tại của nó thay đổi.");
        add(patterns, 21, "Strategy", PatternFamily.BEHAVIORAL, "Interchangeable Logic",
                "Đóng gói các thuật toán để lựa chọn và thay thế linh hoạt trong lúc chạy.");
        add(patterns, 22, "Template Method", PatternFamily.BEHAVIORAL, "Algorithm Skeleton",
                "Định nghĩa khung thuật toán chung và để lớp con tùy biến một số bước cụ thể.");
        add(patterns, 23, "Visitor", PatternFamily.BEHAVIORAL, "Double Dispatch",
                "Bổ sung thao tác mới trên nhiều loại đơn hàng bằng Double Dispatch mà không sửa logic bên trong.");

        return patterns;
    }

    private static void add(Map<String, PatternDetail> patterns,
                            int number,
                            String name,
                            PatternFamily family,
                            String intent,
                            String description) {
        patterns.put(name, new PatternDetail(number, name, family, intent, description));
    }

    private enum StructureLayout {
        LINEAR,
        HUB,
        TREE,
        BRANCH,
        DIAMOND,
        LOOP
    }

    private enum PatternFamily {
        CREATIONAL("Creational", "CR", "category-dot-creational"),
        STRUCTURAL("Structural", "ST", "category-dot-structural"),
        BEHAVIORAL("Behavioral", "BH", "category-dot-behavioral");

        private final String displayName;
        private final String shortCode;
        private final String dotStyleClass;

        PatternFamily(String displayName, String shortCode, String dotStyleClass) {
            this.displayName = displayName;
            this.shortCode = shortCode;
            this.dotStyleClass = dotStyleClass;
        }
    }

    private enum ExecutionState {
        IDLE("IDLE", "READY TO EXECUTE", "status-idle", "ready-idle"),
        RUNNING("RUNNING", "EXECUTING PATTERN", "status-running", "ready-running"),
        SUCCESS("SUCCESS", "EXECUTION COMPLETE", "status-success", "ready-success"),
        ERROR("ERROR", "EXECUTION FAILED", "status-error", "ready-error");

        private final String chipText;
        private final String readyText;
        private final String statusStyleClass;
        private final String readyStyleClass;

        ExecutionState(String chipText,
                       String readyText,
                       String statusStyleClass,
                       String readyStyleClass) {
            this.chipText = chipText;
            this.readyText = readyText;
            this.statusStyleClass = statusStyleClass;
            this.readyStyleClass = readyStyleClass;
        }
    }

    private enum ToastType {
        SUCCESS("✓", "toast-success"),
        WARNING("!", "toast-warning"),
        ERROR("×", "toast-error");

        private final String icon;
        private final String styleClass;

        ToastType(String icon, String styleClass) {
            this.icon = icon;
            this.styleClass = styleClass;
        }
    }

    private static final class PatternDetail {
        private final int number;
        private final String name;
        private final PatternFamily family;
        private final String intent;
        private final String description;

        private PatternDetail(int number,
                              String name,
                              PatternFamily family,
                              String intent,
                              String description) {
            this.number = number;
            this.name = name;
            this.family = family;
            this.intent = intent;
            this.description = description;
        }
    }

    private static final class PatternStructure {
        private final PatternFamily family;
        private final StructureLayout layout;
        private final String mechanism;
        private final List<StructureNode> nodes;
        private final List<StructureEdge> edges;

        private PatternStructure(PatternFamily family,
                                 StructureLayout layout,
                                 String mechanism,
                                 List<StructureNode> nodes,
                                 List<StructureEdge> edges) {
            this.family = family;
            this.layout = layout;
            this.mechanism = mechanism;
            this.nodes = nodes;
            this.edges = edges;
        }
    }

    private static final class StructureNode {
        private final String role;
        private final String name;

        private StructureNode(String role, String name) {
            this.role = role;
            this.name = name;
        }
    }

    private static final class StructureEdge {
        private final int fromIndex;
        private final int toIndex;
        private final String label;

        private StructureEdge(int fromIndex, int toIndex, String label) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
            this.label = label;
        }
    }
}
