package br.com.salao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.Transient;

@Entity
public class Horarios {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Transient
	private String horarios[] = {"09:00","09:30","10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", 
			"17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00"};

}
