/*
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package edu.nju.view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class RecordDialog {
	public static void main(String[] args){
		JFrame jf = new JFrame();
		jf.setLocation(300, 200);
		RecordDialog rd = new RecordDialog(jf);
		rd.show();
	}
	/**
	 *  
	 */
	public RecordDialog(JFrame parent) {
		super();
		initialization(parent);
	}

	public boolean show(String[] names, double[] score) {
		clear = false;
		this.names = names;
		this.score = score;
		dialog.setVisible(true);
		return clear;
	}
	
	public void show(){
		this.names = new String[]{"Unknow Name","Unknow Name","Unknow Name","Unknow Name"};
		this.score = new double[]{0.0,0.0,0.0,0.0};
		show(names,score);
	}

	private void initialization(JFrame parent) {
		dialog = new JDialog(parent, "record", true);

		okBtn = new JButton("ok");
		okBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		okBtn.setBounds(100, 155, 70, 23);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear = false;
				dialog.setVisible(false);
			}
		});

		clearBtn = new JButton("clear");
		clearBtn.setFont(new Font("Monospaced", Font.PLAIN, 12));
		clearBtn.setBounds(192, 155, 70, 23);
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear = true;
				int length = names.length;
				for (int i = 0; i != length; ++i) {
					names[i] = "Unknow Name";
					score[i] = 0.0;
				}
				textPanel.repaint();
				File data = new File("save.dat");
				try {
					FileWriter fw = new FileWriter(data);
					fw.write("");
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		line = new JSeparator();
		line.setBounds(20, 140, 245, 4);

		panel = new JPanel();
		panel.setLayout(null);

		textPanel = new DescribeTextPanel();
		panel.add(textPanel);

		panel.add(okBtn);
		panel.add(clearBtn);
		panel.add(line);

		dialog.setContentPane(panel);
		dialog.setBounds(parent.getLocation().x + 50,
				parent.getLocation().y + 50, 300, 230);

		clear = false;

	}

	private class DescribeTextPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		DescribeTextPanel() {
			super();
			setBounds(0, 0, 290, 130);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			int length = names.length;
			for (int i = 0; i != length; i++) {
				g.drawString(names[i], 20, 30 * (i + 1));
				g.drawString(String.valueOf(score[i]),150, 30 * (i + 1));
				g.drawString(rank[i], 230, 30 * (i + 1));
			}
		}
	}

	public boolean isClear() {
		return clear;
	}

	private final String[] rank = { "Easy", "Hard", "Hell", "Custom" };
  	private JDialog dialog;

	private JPanel panel;

	private JButton okBtn;

	private JButton clearBtn;

	private JSeparator line;

	private String names[];

	private double score[];

	private JPanel textPanel;

	private boolean clear;
}