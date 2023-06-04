/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

/**
 *
 * @author Administrator
 */
public class NguoiChoi {

    private int idNguoiChoi = 1;
    private String tenNguoiChoi;
    private int diemSo;

    public String getTenNguoiChoi() {
        return tenNguoiChoi;
    }

    public int getDiemSo() {
        return diemSo;
    }

    public int getIdNguoiChoi() {
        return idNguoiChoi;
    }

    public void setTenNguoiChoi(String tenNguoiChoi) {
        this.tenNguoiChoi = tenNguoiChoi;
    }

    public void setDiemSo(int diemSo) {
        this.diemSo = diemSo;
    }

    public void setIdNguoiChoi(int idNguoiChoi) {
        this.idNguoiChoi = idNguoiChoi;
    }

    public NguoiChoi(String tenNguoiChoi, int diemSo) {
        this.tenNguoiChoi = tenNguoiChoi;
        this.diemSo = diemSo;
        this.idNguoiChoi = idNguoiChoi++;
    }

    public NguoiChoi(int idNguoiChoi, String tenNguoiChoi, int diemSo) {
        this.tenNguoiChoi = tenNguoiChoi;
        this.diemSo = diemSo;
        this.idNguoiChoi = idNguoiChoi;
    }

}
