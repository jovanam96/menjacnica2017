package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.interfejsi.MenjacnicaInterface;

public class GUIKontroler {
	private static MenjacnicaGUI frame;
	private static MenjacnicaInterface sistem;
	
	private static DodajKursGUI dodajKurs;
	private static ObrisiKursGUI obrisiKurs;
	private static IzvrsiZamenuGUI izvrsiZamenu;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MenjacnicaGUI();
					frame.setVisible(true);
					sistem = new Menjacnica();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void prikaziDodajKursGUI() {
		dodajKurs = new DodajKursGUI();
		dodajKurs.setLocationRelativeTo(null);
		dodajKurs.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(Valuta valuta) {
		obrisiKurs = new ObrisiKursGUI(valuta);
		obrisiKurs.setLocationRelativeTo(frame);
		obrisiKurs.setVisible(true);
		
	}

	public static void prikaziIzvrsiZamenuGUI(Valuta valuta) {
		izvrsiZamenu = new IzvrsiZamenuGUI(valuta);
		izvrsiZamenu.setLocationRelativeTo(frame);
		izvrsiZamenu.setVisible(true);
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				frame.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(frame);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(frame,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziAboutProzor() {
		JOptionPane.showMessageDialog(frame,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			frame.prikaziSveValute();
			obrisiKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			sistem.dodajValutu(valuta);

			frame.prikaziSveValute();
			dodajKurs.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public static double izvrsiZamenu(Valuta valuta, double iznos, boolean prodaja ) {
		double konacniIznos = 0;
		try{
			konacniIznos = 
					sistem.izvrsiTransakciju(valuta,
							prodaja, 
							iznos);
		
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(frame, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		}
		return konacniIznos;

	}
	public static LinkedList<Valuta> vratiSveValute() {
		return sistem.vratiKursnuListu();
	}
}
