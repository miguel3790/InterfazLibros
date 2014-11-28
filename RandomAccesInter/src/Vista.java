 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Vista extends JFrame {
	private JTable table;
	private JTextField textField;
	private BookStore b = new BookStore();
	private String columnaFiltrada;
	private String[][] datosBusqueda;
	private String[] nombreColumnas = {"ID","TITLE","AUTHOR","PUBLISHER","YEAR","STOCK"};
    
	// TAMAÑO MINIMO DE LA VENTANA (700,500)
	
	
    public Vista() {
        
    	this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                System.out.println("componentResized to: " + getWidth() +"px de ancho, y "+ getHeight() + " px de alto.");
            }
        });
    	
    	// No permitir el resize de menos de 700,500
    	
        setTitle("Menu Example");
        setSize(701, 487);
        
        
        
        
        JPanel menu = new JPanel();
        getContentPane().add(menu, BorderLayout.WEST);
        menu.setLayout(new GridLayout(8, 1, 0, 0));
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_2 = new JPanel();
        panel.add(panel_2, BorderLayout.NORTH);
        panel_2.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_3 = new JPanel();
        panel_2.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 2, 0, 0));
        
        JPanel panel_4 = new JPanel();
        panel_3.add(panel_4);
        panel_4.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_6 = new JPanel();
        panel_4.add(panel_6, BorderLayout.CENTER);
        panel_6.setLayout(new BorderLayout(0, 0));
        
        // Argumentos??
        JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		    	  JComboBox aux =(JComboBox)e.getSource();
		    	  columnaFiltrada = (String) aux.getSelectedItem();
		      }
		});
		for(int i=0; i<nombreColumnas.length; i++){
			comboBox.addItem(nombreColumnas[i]);
		}
		
		
		
		columnaFiltrada = (String) comboBox.getSelectedItem();
        panel_6.add(comboBox, BorderLayout.NORTH);
        
        JPanel panel_5 = new JPanel();
        panel_3.add(panel_5);
        panel_5.setLayout(new BorderLayout(0, 0));
        
        JPanel panel_7 = new JPanel();
        panel_5.add(panel_7, BorderLayout.NORTH);
        panel_7.setLayout(new BorderLayout(0, 0));
        
        textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
            	JTextField textField = (JTextField) e.getSource();            	
            	datosBusqueda = b.getBooks(columnaFiltrada, textField.getText());            	
            	table.setModel(getTableInfo());
            	table.repaint();
            }
		});
		
		
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        panel_5.add(textField, BorderLayout.CENTER);
        textField.setColumns(10);
        
        JButton btnNewButton = new JButton("Buscar");
        panel_5.add(btnNewButton, BorderLayout.EAST);
        
        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);
        panel.add(panel_1, BorderLayout.SOUTH);
        
        JButton btnNewButton_1 = new JButton("Add/Delete");
        panel_1.add(btnNewButton_1);
        
   
        table = new JTable();    
        table.setModel(getTableInfo());        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnInicio = new JMenu("Inicio");
        menuBar.add(mnInicio);
        
        JMenuItem mntmSalir = new JMenuItem("Salir");
        mnInicio.add(mntmSalir);
        
        JMenu mnArchivo = new JMenu("Archivo");
        menuBar.add(mnArchivo);
        
        JMenuItem mntmAadirCsv = new JMenuItem("A\u00F1adir CSV");
        mnArchivo.add(mntmAadirCsv);
    }
    
    private DefaultTableModel getTableInfo(){
		DefaultTableModel tablainfo = new DefaultTableModel(datosBusqueda, nombreColumnas){
			public boolean isCellEditable(int row, int column) {
			       return false;
			    }
		};
		return tablainfo;
	}
    
    
    public static void main(String[] args) {
        Vista me = new Vista();
        me.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        me.setVisible(true);
    }
}
