module com.example.currencyexchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;


    opens com.example.currencyexchange to javafx.fxml;
    exports com.example.currencyexchange;
}