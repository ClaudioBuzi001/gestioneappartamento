package it.prova.gestioneappartamento.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneappartamento.dao.AppartamentoDAO;
import it.prova.gestioneappartamento.models.Appartamento;

public class TestAppartamento {

	public static void main(String[] args) {
		// Mi creo i connettori
		AppartamentoDAO connettore = new AppartamentoDAO();

		// TEST LIST
		testList(connettore);
		
		// Test findById
		testFindById(connettore);
		
		//Test insert
		testInsert(connettore);

	}

	// METODI STATICI PER TEST

	private static void testList(AppartamentoDAO connettore) {
		System.out.println("_------------INIZIO tesList---------------------_");

		List<Appartamento> result = connettore.list();

		System.out.println(result.size());
		for (Appartamento i : result) {
			System.out.println(i.getQuartiere());
		}
		System.out.println("_-----------testList PASSED-------------_");

	}

	private static void testFindById(AppartamentoDAO connettore) {
		System.out.println("_-----------INIZIO testFindById-----------_");

		Appartamento result = connettore.findById(3L);

		if (result == null) {
			throw new RuntimeException("_----------testFindById FAILED----------_");
		}

		System.out.println(result.getQuartiere() + " " + result.getPrezzo());
		System.out.println("_---------FINE testFindById PASSED-----------_");

	}
	
	public static void testInsert(AppartamentoDAO connettore) {
		System.out.println("_------------------INIZIO testInsert-----------_");
		Date dataDaInserire = null;
		
		try {
			dataDaInserire = new SimpleDateFormat("dd/MM/yyyy").parse("12/07/2001");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Appartamento appartamentoDaInserire = new Appartamento("Piazza Sempione", 220, 475000, dataDaInserire);
		
		int result = connettore.insert(appartamentoDaInserire);
		if(result == 0)
			System.out.println("_----------testInsert FAILED------------_");
		
		System.out.println("_----------testInsert PASSED-------------_");
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
