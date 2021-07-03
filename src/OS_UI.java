import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JLabel;

public class OS_UI {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel dtm;
	private JTextArea resultbox;

	/**
	 * Launch the application.
	 */
	public static void main_UI(ArrayList<Process> Result, StringBuilder log1, StringBuilder log2) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OS_UI window = new OS_UI(Result, log1, log2);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OS_UI(ArrayList<Process> result, StringBuilder log1, StringBuilder log2) {
		initialize(result, log1, log2);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 */
	private void initialize(ArrayList<Process> result, StringBuilder log1, StringBuilder log2) {
		frame = new JFrame();
		frame.setBounds(100, 100, 715, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// Gantt Chart
		JLabel lblNewLabel = new JLabel("Gantt Chart");
		lblNewLabel.setBounds(12, 10, 72, 15);
		frame.getContentPane().add(lblNewLabel);

		// table1
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 31, 677, 70);
		frame.getContentPane().add(scrollPane);

		String process[] = new String[result.size()];

		for (int i = 0; i < process.length; i++) {
			process[i] = "p" + Integer.toString(result.get(i).processID);
		}

		String contains[][] = new String[2][result.size()];

		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				for (int k = 0; k < result.size(); k++) {
					contains[i][k] = Integer.toString(result.get(k).cpuACT);
				}
			}

			else {
				for (int k = 0; k < result.size(); k++) {
					contains[i][k] = Integer.toString(result.get(k).cpuAT) + "-"
							+ Integer.toString(result.get(k).cpuDACT);
				}
			}
		}

		dtm = new DefaultTableModel(contains, process);
		table = new JTable(dtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		for (int i = 0; i < process.length; i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(result.get(i).cpuACT * 30);
			table.getColumnModel().getColumn(i).setMinWidth(result.get(i).cpuACT * 30);
			table.getColumnModel().getColumn(i).setWidth(result.get(i).cpuACT * 30);

		}
		tableCellCenter(table);

		scrollPane.setViewportView(table);

		// log
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 139, 337, 280);
		frame.getContentPane().add(scrollPane_2);

		// log output
		resultbox = new JTextArea();
		resultbox.setText(log1.toString());
		scrollPane_2.setViewportView(resultbox);

		// result
		JLabel lblNewLabel_1 = new JLabel("Result");
		lblNewLabel_1.setBounds(361, 121, 50, 15);
		frame.getContentPane().add(lblNewLabel_1);

		// result output
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(361, 139, 328, 280);
		frame.getContentPane().add(scrollPane_1);

		resultbox = new JTextArea();
		resultbox.setText(log2.toString());
		scrollPane_1.setViewportView(resultbox);

		JLabel lblNewLabel_2 = new JLabel("Log");
		lblNewLabel_2.setBounds(12, 121, 57, 15);
		frame.getContentPane().add(lblNewLabel_2);
	}

	public void tableCellCenter(JTable t) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); 

		TableColumnModel tcm = t.getColumnModel(); 

		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}
}
