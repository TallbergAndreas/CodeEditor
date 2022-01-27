module ax.ha.it.startermodule {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires java.desktop;
    requires org.fxmisc.richtext;
    requires reactfx;
    requires flowless;
    requires javafx.graphics;


    opens ax.ha.it.starter to javafx.fxml;
    exports ax.ha.it.starter;
}