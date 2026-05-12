module de.ehks._1_selfhautkrebs {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.ehks._1_selfhautkrebs to javafx.fxml;
    opens de.ehks._1_selfhautkrebs.model to javafx.fxml;

    exports de.ehks._1_selfhautkrebs;
    exports de.ehks._1_selfhautkrebs.model;
}
