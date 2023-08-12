
package paneles;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

import com.buttons.simple.SimpleButton;
import com.comboBox.comboSuggestion.ComboBoxSuggestion;
import com.file.nativ.chooser.DemoJavaFxStage;

import textarea.CopyTextAreaScroll;
import utils.Metodos;

public class pnlChat extends javax.swing.JPanel {

	private ComboBoxSuggestion tipo = new ComboBoxSuggestion();

	private ComboBoxSuggestion accion = new ComboBoxSuggestion();

	CopyTextAreaScroll entrada;

	public pnlChat() {

		initComponents();

		tipo.addItem("JPA");

		tipo.addItem("HIBERNATE");

		accion.addItem("Hibernate");

	}

	@SuppressWarnings("unchecked")

	private void initComponents() {

		setBackground(new java.awt.Color(255, 255, 255));

		entrada = new CopyTextAreaScroll();

		entrada.setFontSize(30);

		entrada.setLabelText("aa");

		tipo = new ComboBoxSuggestion();

		accion = new ComboBoxSuggestion();

		tipo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		tipo.setEditable(false);

		accion.setFont(new Font("Tahoma", Font.PLAIN, 18));

		accion.setEditable(false);

		CopyTextAreaScroll resultado = new CopyTextAreaScroll();

		resultado.setText("");

		resultado.setLabelText("aa");

		resultado.setFontSize(30);

		SimpleButton btnNewButton = new SimpleButton("Generate");

		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!entrada.getText().trim().isEmpty()) {

					resultado.setText(
							generarCodigo(tipo.getSelectedItem().toString(), accion.getSelectedItem().toString()));

				}

			}

			private String generarCodigo(String tipo, String accion) {

				String resultado = "";

				entrada.setText(Metodos.limpiarCadena(entrada.getText()));

				resultado = Metodos.limpiarSQL(entrada.getText());

				switch (tipo) {

				case "JPA":

					switch (accion) {

					case "Hibernate":

						resultado = Metodos.convertirAHibernate(entrada.getText(), true, true);

						break;

					}

					break;

				}

				return resultado;

			}

		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");

		JButton btnNewButton_1 = new JButton("New button");

		btnNewButton_1.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					DemoJavaFxStage test = new DemoJavaFxStage();

					LinkedList<File> lista = new LinkedList<File>();

					lista = test.showOpenFileDialog(false, "xml");

					for (int i = 0; i < lista.size(); i++) {

						System.out.println(lista.get(i).getAbsolutePath());

					}

				}

				catch (Exception e1) {

				}

			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.TRAILING).addGroup(layout.createSequentialGroup()
				.addGap(31)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tipo, GroupLayout.PREFERRED_SIZE, 444, GroupLayout.PREFERRED_SIZE)
								.addGap(31).addComponent(accion, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addComponent(rdbtnNewRadioButton)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnNewButton_1))
						.addGroup(layout.createSequentialGroup()
								.addComponent(entrada, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
								.addComponent(resultado, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(330).addComponent(btnNewButton,
								GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
				.addGap(27)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(tipo, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(accion, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnNewRadioButton).addComponent(btnNewButton_1))
						.addGap(18)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(resultado, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE)
								.addComponent(entrada, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
						.addGap(99)));

		this.setLayout(layout);

	}
}
