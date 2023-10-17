package com.example.final_coursework.utill;

import com.example.final_coursework.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtill {

    public static<T>T execute(String sql, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            pstm.setObject((i+1),params[i]);
        }

        if(sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet rst = pstm.executeQuery();
            return (T) rst;
        }
        return (T)(Boolean)(pstm.executeUpdate()>0);
    }
}
