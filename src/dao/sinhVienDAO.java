/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SinhVien;

/**
 *
 * @author ADMIN
 */
public class sinhVienDAO {
    private Connection con;
    private String url = "jdbc:mysql://localhost:3306/sinhvien";
    private String username="root";
    private String password="letunglam";
    private String driver ="com.mysql.cj.jdbc.Driver";

    public sinhVienDAO() {
        con = this.getConnection();
    }
    
    
    public Connection getConnection(){
        con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,username, password);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException e) {
        }
        return con;
    }
    
    public List<SinhVien> getAll(){
        String query = "select *from student";
        List<SinhVien> sv = new ArrayList<SinhVien>();
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while(rs.next()){
                SinhVien s = new SinhVien();
                s.setId(rs.getInt("ma"));
                s.setTen(rs.getString("ten"));
                s.setDob(rs.getString("dob"));
                s.setKhoa(rs.getString("khoa"));
                s.setQue(rs.getString("quequan"));
                sv.add(s);
            }
        } catch (SQLException e) {
        }   
        return sv;
    }
    public List<SinhVien> getByName(String name){
        String query ="select *from student where ten like ?";
        List<SinhVien> sv = new ArrayList<SinhVien>();
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = con.prepareStatement(query);
            pst.setString(1,"%"+name+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                SinhVien s = new SinhVien();
                s.setId(rs.getInt("ma"));
                s.setTen(rs.getString("ten"));
                s.setDob(rs.getString("dob"));
                s.setKhoa(rs.getString("khoa"));
                s.setQue(rs.getString("quequan"));
                sv.add(s);
            }
        } catch (Exception e) {
        }
        return sv;
    }
    public SinhVien getById(int id){
        String query = "select *from student where ma =?";
        SinhVien s = new SinhVien();
        PreparedStatement pst;
        ResultSet rs;
        try {
            pst = con.prepareStatement(query);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            while(rs.next()){
                s.setId(rs.getInt("ma"));
                s.setTen(rs.getString("ten"));
                s.setDob(rs.getString("dob"));
                s.setKhoa(rs.getString("khoa"));
                s.setQue(rs.getString("quequan"));
            }
        } catch (Exception e) {
        }
        return s;
    }
    public boolean updateSV(SinhVien sv){
        boolean a = false;
        String query ="update student set ten=?,dob=?,khoa=?,quequan=? where ma=?";
        PreparedStatement pst;
        try {
            pst=con.prepareStatement(query);
            pst.setString(1,sv.getTen());
            pst.setString(2,sv.getDob());
            pst.setString(3,sv.getKhoa());
            pst.setString(4,sv.getQue());
            pst.setInt(5,sv.getId());
            if(pst.executeUpdate()>0){
                a=true;
            }
        } catch (Exception e) {
        }
        return a;
    }
}
