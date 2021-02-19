package services;

import java.util.List;

import model.Etudiant;

public interface IEtudiantService {

	public List<Etudiant> listEtudiant();

	public String ajouterEtudiant(Etudiant e);

	public Etudiant modifierEtudiant(Etudiant e);

	public void supprimeUnEtudiant(long idEtudiant);

	public Etudiant chercherUnetudiantParSonNom(String nom);

	public Etudiant chercherUnEtudiantParSonId(long idEtudiant);

}
