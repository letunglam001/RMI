/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.EventObject;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author ADMIN
 */
public class button implements TableCellEditor,TableCellRenderer,ActionListener{
    private JTable jTable;
    private JButton button;
    private Action action;
    private Object value;
    private int col;

    public button(JTable jTable, Action action, int col) {
        button = new JButton("Update");
        this.action = action;
        button.addActionListener(this);
        this.jTable = jTable;
        this.col = col;
        this.jTable.getColumnModel().getColumn(col).setCellEditor(this);
        this.jTable.getColumnModel().getColumn(col).setCellRenderer(this);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        int row = jTable.convertRowIndexToModel(jTable.getEditingRow());
        String ma = String.valueOf(jTable.getValueAt(row, 0));
        ActionEvent evt = new ActionEvent(value,ActionEvent.ACTION_PERFORMED, ma);
        this.action.actionPerformed(evt);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.value=value;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    public void cancelCellEditing() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return button;
    }
}
