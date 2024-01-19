package com.ftn.PrviMavenVebProjekat.model;

public class Vozilo {
		private Long id;
		private String naziv;
		
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNaziv() {
			return naziv;
		}
		public void setNaziv(String naziv) {
			this.naziv = naziv;
		}
		
		public Vozilo(Long id, String naziv) {
			super();
			this.id = id;
			this.naziv = naziv;
		}
		public Vozilo(String naziv) {
			super();
			this.naziv = naziv;
		}
}
