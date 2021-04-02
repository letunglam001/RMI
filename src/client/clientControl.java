/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.SinhVien;
import server.Interface;

/**
 *
 * @author ADMIN
 */
public class clientControl {
    private Interface rmiServer;
    private Registry reg;
    private SinhVienFrm svf;
    private UpdateFrm uf;

    public clientControl(String address,int port,SinhVienFrm svf) {
        try {
            reg = LocateRegistry.getRegistry("localhost", port);
            rmiServer = (Interface) reg.lookup("rmiServer");
            this.svf =svf;
            this.svf.getAllSV(getAll());
            this.svf.addSearchListener(new SearchListener());
            this.svf.addUpdateListener(new PreUpdateListener());
        } catch (Exception e) {
        }
    }

    public List<SinhVien> getAll() throws RemoteException {
        return rmiServer.getAll();
    }
    public List<SinhVien> getByName(String name) throws RemoteException{
        return rmiServer.getByName(name);
    }
    public SinhVien getById(int id) throws RemoteException{
        return rmiServer.getById(id);
    }
    public boolean updateSV(SinhVien sv) throws RemoteException{
        return rmiServer.updateSV(sv);
    }

    private class UpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SinhVien sv = uf.getSVData();
                if(updateSV(sv)){
                    svf.getAllSV(getAll());
                    JOptionPane.showMessageDialog(svf, "updateSuccess");
                    uf.dispose();
                }
                else{
                    JOptionPane.showMessageDialog(uf, "updateFailed");
                }
            } catch (RemoteException ex) {
                Logger.getLogger(clientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class PreUpdateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int ma = Integer.parseInt(e.getActionCommand());
                SinhVien s = getById(ma);
                uf = new UpdateFrm();
                uf.setSVData(s);
                uf.addUpdateListener(new UpdateListener());
                uf.setVisible(true);
            } catch (RemoteException ex) {
                Logger.getLogger(clientControl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    private class SearchListener implements ActionListener {

       
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = svf.getSearch();
                svf.getAllSV(getByName(name));
            } catch (RemoteException ex) {
            }
        }
    }
}
