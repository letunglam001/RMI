/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dao.sinhVienDAO;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.SinhVien;

/**
 *
 * @author ADMIN
 */
public class serverControl extends UnicastRemoteObject implements Interface{
    private int serverport = 8888;
    private Registry reg;
    private sinhVienDAO svDAO;

    public serverControl() throws RemoteException {
        reg = LocateRegistry.createRegistry(serverport);
        try {
            reg.bind("rmiServer",this);
        } catch (AlreadyBoundException | RemoteException e) {
        }
        svDAO = new sinhVienDAO();
    }  

    @Override
    public List<SinhVien> getAll() throws RemoteException {
        return svDAO.getAll();
    }

    @Override
    public List<SinhVien> getByName(String name) throws RemoteException {
        return svDAO.getByName(name);
    }

    @Override
    public SinhVien getById(int id) throws RemoteException {
        return svDAO.getById(id);
    }

    @Override
    public boolean updateSV(SinhVien sv) throws RemoteException {
        return svDAO.updateSV(sv);
    }
    
    
}
