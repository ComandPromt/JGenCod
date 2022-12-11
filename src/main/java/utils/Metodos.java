package utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Metodos {
	public static String calcularCampos(String tabla) {

		tabla = tabla.replace(tabla.substring(0, tabla.indexOf("(") + 1), "").trim();

		tabla = tabla.replace(");", "");

		String[] busqueda = tabla.split(",");

		String[] linea;

		String tipo;

		String resultado = "";

		for (String valor : busqueda) {

			valor = valor.trim();

			if (!valor.contains("FOREIGN") && !tieneClaveCompuesta(valor) && !valor.startsWith("PRIMARY KEY")) {

				linea = valor.split(" ");

				tipo = calcularTipo(lineaString(linea));

				resultado += tipo;

			}

			else {

			}

		}

		return resultado;

	}

	public static String lineaString(String[] linea) {

		String resultado = "";

		for (String valor : linea) {

			resultado += valor + " ";

		}

		resultado = resultado.trim();

		return resultado;

	}

	public static String calcularTipo(String text) {

		String resultado = "";

		try {

			text = text.toUpperCase();

			if (text.contains("PRIMARY KEY")) {

				resultado += "@Id\r\n";

			}

			resultado += "@Column(name =\"" + text.substring(0, text.indexOf(" ")).toLowerCase() + "\"";

			if (text.contains("(")) {

				resultado += ",length = " + text.substring(text.indexOf("(") + 1, text.indexOf(")"));

			}

			if (text.contains("DEFAULT")) {

				resultado += ",columnDefinition = \"" + text.substring(text.indexOf(" ") + 1, text.length()) + "\"";

			}

			resultado += ")\r\n";

			if (text.contains("AUTO_INCREMENT")) {

				resultado += "\r\n@GeneratedValue(strategy = GenerationType.AUTO)\r\n";

			}

			if (!text.contains("PRIMARY KEY") && text.contains("NOT NULL")) {

				resultado += "\r\n@NotNull\r\n";

			}

			if (!text.contains("PRIMARY KEY") && !text.contains("DEFAULT") && text.contains("NULL")
					&& !text.contains("NOT NULL")) {

				resultado += "\r\n@Column(nullable = true)\r\n";

			}

			resultado += "\r\nprivate " + saberTipoColumna(text) + "\r\n\r\n";

		}

		catch (Exception e) {

		}

		return resultado;

	}

	public static String saberTipoColumna(String text) {

		String tipo = text.substring(text.indexOf(" ") + 1, text.length());

		String resultado = "";

		resultado = saberNombreTipo(tipo) + " " + text.substring(0, text.indexOf(" ")).toLowerCase() + ";";

		return resultado;

	}

	public static String saberNombreTipo(String tipo) {

		String textoTipo = "";

		if (tipo.contains("VARCHAR") || tipo.contains("VARBINARY") || tipo.contains("TEXT") || tipo.contains("CLOB")) {

			textoTipo = "String";

		} else if (tipo.contains("TINYINT") || tipo.contains("BOOL")) {

			textoTipo = "boolean";

		} else if (tipo.contains("SMALLINT")) {

			textoTipo = "short";

		} else if (tipo.contains("BIT")) {

			textoTipo = "byte";

		} else if (tipo.contains("BIGINT")) {

			textoTipo = "long";

		} else if (tipo.contains("MEDIUMINT") || tipo.contains("INT") || tipo.contains("YEAR")) {

			textoTipo = "int";

		} else if (tipo.contains("FLOAT") || tipo.contains("DEC")) {

			textoTipo = "float";

		}

		else if (tipo.contains("DOUBLE")) {

			textoTipo = "double";

		}

		else if (tipo.contains("CHAR") || tipo.contains("BINARY")) {

			textoTipo = "char";

		}

		else if (tipo.contains("DATE")) {

			textoTipo = "LocalDate";

		}

		else if (tipo.contains("TIME")) {

			textoTipo = "LocalTime";

		} else if (tipo.contains("DATETIME")) {

			textoTipo = "LocalDateTime";

		}

		else if (tipo.contains("TIMESTAMP")) {

			textoTipo = "Instant";

		}

		return textoTipo;

	}

	public static boolean tieneClaveCompuesta(String text) {

		return Pattern.compile(".*PRIMARY KEY.*\\([a-zA-Z].*\\,.*\\).*", Pattern.CASE_INSENSITIVE).matcher(text).find();

	}

	public static String convertirAHibernate(String texto, boolean lombok) {

		String resultado = "";

		String[] tablas = texto.split(";");

		ArrayList<String> lista = new ArrayList();

		ArrayList<String> tablasNm = new ArrayList();

		ArrayList<String> tablasBusqueda = new ArrayList();

		ArrayList<String> clavesPrimarias = new ArrayList();

		ArrayList<String> clavesForaneas = new ArrayList();

		ArrayList<String> campos = new ArrayList();

		for (String valor : tablas) {

			lista.add(valor);

		}

		texto.replace("create", "CREATE");

		texto.replace("table", "TABLE");

		texto.replace("primary", "PRIMARY");

		texto.replace("foreign", "FOREIGN");

		texto.replace("key", "KEY");

		String busqueda = "";

		String tabla = "";

		for (String valor : lista) {

			if (valor.contains("CREATE TABLE")) {

				tabla = valor.substring(valor.indexOf(busqueda) + busqueda.length(), valor.indexOf("(")).trim()
						.toLowerCase();

				tabla = convertirACamelCase(tabla);

				busqueda = "CREATE TABLE";

				tablas = extraerLinea(valor, "PRIMARY KEY");

				for (String atributos : tablas) {

					tablasBusqueda.add(tabla);

					clavesPrimarias.add(atributos.trim());

				}

				tablas = extraerLinea(valor, "FOREIGN KEY");

				for (String atributos : tablas) {

					clavesForaneas.add(atributos.trim());

				}

				resultado += ponerCabecera(lombok);

				resultado += "@Table(name=\"" + tabla + "\")\r\n";

				if (tieneClaveCompuesta(valor) && tieneRelacionNm()) {

					tablasNm.add(ponerCabecera(lombok));

					resultado += "@Data\r\npublic class " + tabla + "Id implements Serializable{";

				}

				else {

					resultado += "\r\npublic class " + tabla + " {";

				}

				resultado += "\r\n\r\n";

				if (!tablasNm.isEmpty()) {

					// resultado += "@IdClass(" + tabla + "Id" + ".class)\r\n\r\n";

				}

				resultado += calcularCampos(valor);

				for (String atributo : campos) {

					if (clavesForaneas.contains(valor.toLowerCase()) || clavesForaneas.contains(valor.toUpperCase())) {

						tablasNm.add("\r\nprivate " + valor + " " + valor.toLowerCase());

					}

					else {

						tablasNm.add("private");

					}

				}

				resultado += "}\n\n";

			}

		}

		for (String primarysKeys : clavesPrimarias) {

			if (clavesForaneas.contains(primarysKeys.toLowerCase())
					|| clavesForaneas.contains(primarysKeys.toUpperCase())) {

			}

			tablasNm.add(busqueda);

		}

		return resultado;

	}

	public static boolean tieneRelacionNm() {

		return false;
	}

	public static String[] extraerLinea(String text, String busqueda) {

		String[] tablas;

		String copiaTabla;

		copiaTabla = text;

		copiaTabla.substring(text.indexOf(busqueda) + 11, text.length());

		copiaTabla.substring(text.indexOf("(") + 1, text.length());

		copiaTabla.substring(0, text.indexOf(")"));

		tablas = copiaTabla.split(",");

		return tablas;

	}

	public static String ponerCabecera(boolean lombok) {

		String resultado = "import jakarta.persistence.*;\r\n";

		if (lombok) {

			resultado += "import lombok.Getter;\r\n" + "import lombok.Setter;\r\n\r\n" + "@Getter\r\n"
					+ "\r\n@Setter\r\n\r\n";

		}

		resultado += "@Entity\r\n\r\n";

		return resultado;

	}

	public static String convertirACamelCase(String text) {

		return Character.toString(text.charAt(0)).toUpperCase() + text.toLowerCase().substring(1);

	}

	public static String limpiarCadena(String text) {

		text = text.trim();

		text = text.replace("   ", "  ");

		text = text.replace("  ", " ");

		return text;

	}

	public static String limpiarBuscandoPorString(String texto, String busqueda) {

		String copia = texto;

		boolean error = false;

		if (copia.contains(busqueda)) {

			texto = texto.replace(texto.substring(buscarPrimeraLinea(texto, busqueda), texto.indexOf(busqueda) +

					texto.substring(texto.indexOf(busqueda), texto.length()).indexOf(";") + 1), "").trim();

			while (!error) {

				try {

					while (!copia.substring(0, copia.indexOf(";") + 1).contains(busqueda)) {

						copia = copia.substring(copia.indexOf(";") + 1, copia.length());

					}

					texto = texto.replace(copia.substring(0, copia.indexOf(";") + 1).trim(), "");

					if (texto.contains(busqueda)) {

						copia = copia.substring(copia.indexOf(";") + 1, copia.length());

					}

					else {

						error = true;

					}

				}

				catch (Exception e) {

				}

			}

		}

		texto = texto.trim();

		texto = texto.replace("   ", "  ");

		texto = texto.replace("  ", " ");

		texto = texto.replace("\r\n", "\n");

		texto = texto.replace("\n\n", "\n");

		texto = texto.replace("\r\n", "");

		return texto;

	}

	public static int buscarPrimeraLinea(String texto, String busqueda) {

		int index = texto.indexOf(busqueda);

		int restar = 0;

		if (index > 0) {

			char caracter = texto.charAt(index);

			int i = index;

			while (i >= 0 && caracter != ';') {

				restar++;

				i--;

				caracter = texto.charAt(i);

			}

			restar--;

		}

		return index - restar;

	}
}
