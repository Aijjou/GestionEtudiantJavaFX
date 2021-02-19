package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import model.Etudiant;

public class EtudiantDaoFile implements IEtudiantDao {

	File file = new File("test2/etudiants.txt");
	List<Etudiant> etudiants;

	public List<Etudiant> getAll() {

		return recuperationListEtudiant();
	}

	public String add(Etudiant e) {
		String message = "Probl√®me enregistrement";
		List<Etudiant> listeEtudiants = null;
		System.out.println(e);

		if (recuperationListEtudiant().size() != 0) {
			long max = 0;
			listeEtudiants = recuperationListEtudiant();
			for (int i = 0; i < listeEtudiants.size(); i++) {

				if (listeEtudiants.get(i).getId() > max) {
					max = listeEtudiants.get(i).getId();
				}

			}

			System.out.println(max);
			for (int i = 0; i < listeEtudiants.size(); i++) {

				if (listeEtudiants.get(i).getId().longValue() == e.getId().longValue()) {
					e.setId(max + 1);
					listeEtudiants.add(e);
					if (enregistrementFile(listeEtudiants)) {
						message = "Enregistement effectu√©";
					}
					break;
				} else {
					listeEtudiants.add(e);
					if (enregistrementFile(listeEtudiants)) {
						message = "Enregistement effectu√©";
					}
					break;
				}

			}
			return message;
		} else {

			listeEtudiants = new ArrayList<>();
			listeEtudiants.add(e);
			if (enregistrementFile(listeEtudiants)) {
				message = "Enregistrement effectu√©";
			} else

				return message;

		}

		return message;
	}

	private boolean enregistrementFile(List<Etudiant> liste) {
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			objectOutputStream.writeObject(liste);
			objectOutputStream.close();
			return true;
		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}

	}

	private List<Etudiant> recuperationListEtudiant() {

		List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();
		try {

			if (file.length() != 0) {
				FileInputStream os = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(os);
				etudiants = (List<Etudiant>) ois.readObject();
				ois.close();
			} else {
				return listeEtudiants;
			}
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			return listeEtudiants;
		} catch (IOException e) {

			e.printStackTrace();
			return listeEtudiants;
		}

		return etudiants;

	}

	public Etudiant update(Etudiant e) {

		List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

		if (recuperationListEtudiant().size() != 0) {

			listeEtudiants = recuperationListEtudiant();

			for (int i = 0; i < listeEtudiants.size(); i++) {
				System.out.println(e);
				if (listeEtudiants.get(i).getId().longValue() == e.getId().longValue()) {
					System.out.println("trouvÈ etudiant ‡ modifier");
					listeEtudiants.remove(i);
					listeEtudiants.add(e);

				}

			}

			enregistrementFile(listeEtudiants);

		}
		return e;
	}

	@Override
	public void delete(long idEtudiant) {
		List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();

		if (recuperationListEtudiant().size() != 0) {

			listeEtudiants = recuperationListEtudiant();

			for (int i = 0; i < listeEtudiants.size(); i++) {

				if (listeEtudiants.get(i).getId() == idEtudiant) {

					listeEtudiants.remove(i);

				}

			}

			enregistrementFile(listeEtudiants);

		}

	}

	@Override
	public Etudiant getEtudiantByName(String nom) {
		List<Etudiant> listeEtudiants;
		Etudiant trouvEtudiant = new Etudiant();

		if (recuperationListEtudiant().size() != 0) {

			listeEtudiants = recuperationListEtudiant();

			for (int i = 0; i < listeEtudiants.size(); i++) {

				if (listeEtudiants.get(i).getNomString().contains(nom)) {
					System.out.println("trouvÈ etudiant");
					trouvEtudiant = listeEtudiants.get(i);
					break;

				}

			}
			return trouvEtudiant;

		}
		return null;

	}

	@Override
	public Etudiant getEtudiantById(long idEtudiant) {

		List<Etudiant> listeEtudiants;
		Etudiant trouvEtudiant = new Etudiant();

		if (recuperationListEtudiant().size() != 0) {

			listeEtudiants = recuperationListEtudiant();

			for (int i = 0; i < listeEtudiants.size(); i++) {

				if (listeEtudiants.get(i).getId() == idEtudiant) {

					trouvEtudiant = listeEtudiants.get(i);

				}

			}
			return trouvEtudiant;

		}
		return null;

	}

}
