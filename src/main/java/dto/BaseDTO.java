package dto;

import java.sql.Connection;
import java.sql.Statement;

public class BaseDTO {


    Connection connection;


    public BaseDTO(){

    }

    public BaseDTO(Connection connection) {
        this.connection = connection;
    }


}
