/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
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
public class testClient {
   private Interface rmiServer;
   private Registry reg;
   private SinhVienFrm svf;
   private UpdateFrm uf;

    public testClient(String address, int port,SinhVienFrm svf){
        try {
            reg = LocateRegistry.getRegistry("localhost",8888);
            rmiServer = (Interface) reg.lookup(address);
            this.svf = svf;
            this.svf.getAllSV(getAll());
            this.svf.addSearchListener(new SearchListener());
            this.svf.addUpdateListener(new PreUpdateListener());
        } catch (NotBoundException | RemoteException e) {
        }
    }
   
    public List<SinhVien> getAll() throws RemoteException {
        return rmiServer.getAll();
    }


    public List<SinhVien> getByName(String name) throws RemoteException {
        return rmiServer.getByName(name);
    }


    public SinhVien getById(int id) throws RemoteException {
        return rmiServer.getById(id);
    }

    public boolean updateSV(SinhVien sv) throws RemoteException {
        return rmiServer.updateSV(sv);
    }

    private class UpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SinhVien s = uf.getSVData();
                if(updateSV(s)){
                    uf.dispose();
                    JOptionPane.showMessageDialog(svf,"success");
                    svf.getAllSV(getAll());
                }
                else{
                    JOptionPane.showMessageDialog(uf,"failed");
                }
            } catch (HeadlessException | RemoteException ex) {
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

    private class PreUpdateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(e.getActionCommand());
                SinhVien s = getById(id);
                uf = new UpdateFrm();
                uf.setVisible(true);
                uf.addUpdateListener(new UpdateListener());
                uf.setSVData(s);
            } catch (NumberFormatException | RemoteException ex) {
            }
        }

       
    }
}
