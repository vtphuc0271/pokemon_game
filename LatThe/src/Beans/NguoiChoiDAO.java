/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class NguoiChoiDAO {

    public boolean ThemDuLieu(NguoiChoi nguoiChoi) {
        try {
            Connection con = ConnectToSQL.getConnection();
            String sql = "INSERT INTO `nguoichoi`(`idNguoiChoi`,`tenNguoiChoi`,`diemSo`) VALUES (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, null);
            ps.setString(2, nguoiChoi.getTenNguoiChoi());
            ps.setInt(3, nguoiChoi.getDiemSo());
            int kq = ps.executeUpdate();
            return kq == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<NguoiChoi> LayTopTheoDiem() {
        ArrayList<NguoiChoi> arr = new ArrayList<>();
        try {
            Connection con = ConnectToSQL.getConnection();
            String sql = "SELECT `idNguoiChoi`, `tenNguoiChoi`, `diemSo` FROM `nguoichoi` ORDER BY `diemSo` DESC LIMIT 0,10";
            Statement stm = (Statement) con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int idNguoiChoi = rs.getInt("idNguoiChoi");
                String tenNguoiChoi = rs.getString("tenNguoiChoi");
                int diemSo = rs.getInt("diemSo");
                NguoiChoi temp = new NguoiChoi(idNguoiChoi, tenNguoiChoi, diemSo);

                arr.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }
}
