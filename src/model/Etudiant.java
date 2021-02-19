package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Etudiant implements Serializable {

	private static final long serialVersionUID = 1L;
	private static long count = 0;
	private Long id;
	String nomString;
	String prenomString;
	String motDePasseString;
	String imageString;
	Date datenaissance;
	List<Integer> notes;

	public Etudiant(String nomString, String prenomString, String motDePasseString, Date date) {
		super();
		count += 1;
		this.id = count;
		this.nomString = nomString;
		this.prenomString = prenomString;
		this.motDePasseString = motDePasseString;
		this.datenaissance = date;
		this.notes = new ArrayList<Integer>();
	}

	public Etudiant() {
		count += 1;
		this.id = count;
		this.notes = new ArrayList<Integer>();

	}

	public Etudiant(Long l) {
		count += 1;
		this.id = count;

	}

	public Etudiant(String nomString, String prenomString, String motDePasseString, String image, Date date) {
		super();
		count += 1;
		this.id = count;
		this.nomString = nomString;
		this.prenomString = prenomString;
		this.motDePasseString = motDePasseString;
		this.imageString = image;
		this.datenaissance = date;
		this.notes = new ArrayList<Integer>();
	}

//	public Double getMoyenne() {
////		return (double) (notes.stream().mapToInt(Integer::intValue).sum() / notes.stream().count());
//	}

	public String getNomString() {
		return nomString;
	}

	public void setNomString(String nomString) {
		this.nomString = nomString;
	}

	public String getPrenomString() {
		return prenomString;
	}

	public void setPrenomString(String prenomString) {
		this.prenomString = prenomString;
	}

	public String getMotDePasseString() {
		return motDePasseString;
	}

	public void setMotDePasseString(String motDePasseString) {
		this.motDePasseString = motDePasseString;
	}

	public List<Integer> getNotes() {
		return notes;
	}

	public void setNotes(List<Integer> notes) {
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageString() {
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public Date getDatenaissance() {
		return datenaissance;
	}

	public void setDatenaissance(Date datenaissance) {
		this.datenaissance = datenaissance;
	} 

	@Override
	public String toString() {
		return "Etudiant [id=" + id + ", nomString=" + nomString + ", prenomString=" + prenomString
				+ ", motDePasseString=" + motDePasseString + ", imageString=" + imageString + ", datenaissance="
				+ datenaissance + ", notes=" + notes + "]";
	}

}
