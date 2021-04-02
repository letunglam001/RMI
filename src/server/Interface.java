/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.SinhVien;

/**
 *
 * @author ADMIN
 */
public interface Interface extends Remote{
    public List<SinhVien> getAll() throws RemoteException;
    public List<SinhVien> getByName(String name) throws RemoteException;
    public SinhVien getById(int id) throws RemoteException;
    public boolean updateSV(SinhVien sv) throws RemoteException;
}
