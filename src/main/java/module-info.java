module com.discipline.drms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.apache.commons.codec;
    requires org.slf4j;

    opens com.discipline.drms to javafx.fxml;
    exports com.discipline.drms;

    exports com.discipline.drms.admin_panel;
    opens com.discipline.drms.admin_panel to javafx.fxml;

    exports com.discipline.drms.master_records;
    opens com.discipline.drms.master_records to javafx.fxml;

    exports com.discipline.drms.login;
    opens com.discipline.drms.login to javafx.fxml;

    exports com.discipline.drms.province_panel;
    opens com.discipline.drms.province_panel to javafx.fxml;

    exports com.discipline.drms.utils;
    opens com.discipline.drms.utils to javafx.fxml;

    exports com.discipline.drms.utils.sql;
    opens com.discipline.drms.utils.sql to javafx.fxml;
}