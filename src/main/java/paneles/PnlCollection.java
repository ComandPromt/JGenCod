package paneles;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.buttons.simple.SimpleButton;
import com.comboBox.comboSuggestion.ComboBoxSuggestion;
import com.image.resizable.ResizedImage;

import textarea.CopyTextAreaScroll;

public class PnlCollection extends javax.swing.JPanel {

	ResizedImage preview;

	ComboBoxSuggestion<String> accion;

	ComboBoxSuggestion<String> tipo;

	public PnlCollection() {

		initComponents();

	}

	@SuppressWarnings("unchecked")

	private void initComponents() {

		preview = new ResizedImage();

		preview.setAspectRatio(true);

		accion = new ComboBoxSuggestion<String>();

		accion.addItemListener(new ItemListener() {

			@Override

			public void itemStateChanged(ItemEvent e) {

				switch (tipo.getSelectedIndex()) {

				default:

				case 0:

					break;

				case 1:

					break;

				case 2:

					switch (accion.getSelectedIndex()) {

					case 0:

						preview.setIcon(new ImageIcon(PnlCollection.class.getResource("/images/charts/bar.png")));

						break;

					}

					break;

				}

			}
		});

		tipo = new ComboBoxSuggestion<String>();

		setBackground(new java.awt.Color(255, 255, 255));

		CopyTextAreaScroll salida = new CopyTextAreaScroll();

		salida.setEditable(false);

		salida.setFontSize(30);

		salida.setLabelText("");

		SimpleButton btnNewButton = new SimpleButton("Generate");

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch (tipo.getSelectedIndex()) {

				case 2:

					switch (accion.getSelectedIndex()) {

					case 0:

						salida.setText("public javaswingdev.chart.BarChart chart;\n" + "\n"
								+ "chart = new javaswingdev.chart.BarChart();	\n" + "\n"
								+ "chart.setTitle(\"Chart Data\");\n" + "\n"
								+ "chart.addLegend(\"Income\", Color.decode(\"#f5af19\"), Color.decode(\"#f12711\"));\n"
								+ "\n"
								+ "chart.addLegend(\"Expense\", Color.decode(\"#a044ff\"), Color.decode(\"#6a3093\"));\n"
								+ "\n"
								+ "chart.addLegend(\"Profit\", Color.decode(\"#38ef7d\"), Color.decode(\"#11998e\"));\n"
								+ "\n"
								+ "chart.addLegend(\"Cost\", Color.decode(\"#0575E6\"), Color.decode(\"#021B79\"));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"January\", new double[] { 500, 200, 80, 89 }));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"February\", new double[] { 600, 750, 90, 150 }));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"March\", new double[] { 200, 350, 460, 900 }));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"April\", new double[] { 480, 150, 750, 700 }));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"May\", new double[] { 350, 540, 300, 150 }));\n"
								+ "\n"
								+ "chart.addData(new ModelChart(\"June\", new double[] { 450, 500, 700, 900 }));\n"
								+ "\n" + "chart.start();");

						break;

					}

					break;

				}

			}

		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		tipo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				accion.removeAllItems();

				switch (tipo.getSelectedIndex()) {

				default:
				case 0:
					break;

				case 2:

					accion.addItem("Bar");

					break;

				case 13:

					accion.addItem("Gradient");

					accion.addItem("Shadow");

					break;

				}

			}

		});

		tipo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tipo.setEditable(false);

		tipo.addItem("Alert");

		tipo.addItem("Button");

		tipo.addItem("Chart");

		tipo.addItem("CheckBox");

		tipo.addItem("ComboBox");

		tipo.addItem("Dialog");

		tipo.addItem("Drag & Drop");

		tipo.addItem("Image");

		tipo.addItem("InfoFile");

		tipo.addItem("Label");

		tipo.addItem("Methods");

		tipo.addItem("Native File Chooser");

		tipo.addItem("Option Pane");

		tipo.addItem("Panel");

		tipo.addItem("Progress Bar");

		tipo.addItem("Slider");

		tipo.addItem("Spinner");

		tipo.addItem("Switch");

		tipo.addItem("Text Area");

		tipo.addItem("Text Field");

		tipo.addItem("Text Image");

		tipo.addItem("Title Bar");

		accion.setFont(new Font("Tahoma", Font.PLAIN, 18));

		accion.setEditable(false);

		preview.setHorizontalAlignment(SwingConstants.CENTER);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addGap(32)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(salida, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tipo, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(accion, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(preview, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipo, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(accion, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(preview, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
								.addComponent(salida, GroupLayout.PREFERRED_SIZE, 516, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		this.setLayout(layout);
	}// </editor-fold>//GEN-END:initComponents
}
