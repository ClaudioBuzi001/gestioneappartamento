package it.prova.gestioneappartamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.gestioneappartamento.connection.MyConnection;
import it.prova.gestioneappartamento.models.Appartamento;

public class AppartamentoDAO {

	public List<Appartamento> list() {

		List<Appartamento> result = new ArrayList<Appartamento>();

		try (Connection c = MyConnection.getConnection();
				Statement s = c.createStatement();
				// STRATEGIA EAGER FETCHING
				ResultSet rs = s.executeQuery("select * from appartamento")) {

			while (rs.next()) {
				// Prendiamo tutti gli appartamenti che troviamo
				Appartamento appartamentoTemp = new Appartamento();
				appartamentoTemp.setId(rs.getLong("id"));
				appartamentoTemp.setQuartiere(rs.getString("quartiere"));
				appartamentoTemp.setMetriQuadrati(rs.getInt("metriquadrati"));
				appartamentoTemp.setPrezzo(rs.getInt("prezzo"));
				appartamentoTemp.setDataCreazione(rs.getDate("datacreazione"));
				result.add(appartamentoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// rilancio in modo tale da avvertire il chiamante
			throw new RuntimeException(e);
		}
		return result;
	}

	public Appartamento findById(Long idAppartamento) {

		if (idAppartamento == null || idAppartamento < 1)
			throw new RuntimeException("Impossibile recuperare Appartamento: id mancante!");

		Appartamento result = null;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("select * from appartamento a where a.id=?;")) {

			ps.setLong(1, idAppartamento);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Appartamento();
					result.setId(rs.getLong("id"));
					result.setQuartiere(rs.getString("quartiere"));
					result.setMetriQuadrati(rs.getInt("metriquadrati"));
					result.setPrezzo(rs.getInt("prezzo"));
					result.setDataCreazione(rs.getDate("datacreazione"));

				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			// rilancio in modo tale da avvertire il chiamante
			throw new RuntimeException(e);
		}
		return result;
	}

	public int insert(Appartamento appartamento) {

		if (appartamento == null)
			throw new RuntimeException("Impossibile inserire Appartamento: input non valido!");

		int result = 0;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(
						"INSERT into appartamento(quartiere, prezzo, metriquadrati, datacreazione) values(?, ?, ?, ?); ")) {

			ps.setString(1, appartamento.getQuartiere());
			ps.setInt(2, appartamento.getPrezzo());
			ps.setInt(3, appartamento.getMetriQuadrati());
			ps.setDate(4, new java.sql.Date(appartamento.getDataCreazione().getTime()));

			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// rilancio in modo tale da avvertire il chiamante
			throw new RuntimeException(e);
		}
		return result;
	}

	public int update(Appartamento appartamento) {
		if (appartamento == null) {
			throw new RuntimeException("Errore articolo in input errato");
		}

		int result = -1;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement(
						"update appartamento set quartiere = ?, metriquadrati = ?, prezzo = ?, datacreazione = ? where id = ?;")) {
			// Setto le varie proprieta
			ps.setString(1, appartamento.getQuartiere());
			ps.setInt(2, appartamento.getMetriQuadrati());
			ps.setInt(3, appartamento.getPrezzo());
			ps.setDate(4, new java.sql.Date(appartamento.getDataCreazione().getTime()));
			// where
			ps.setLong(5, appartamento.getId());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	public int delete(Appartamento appartamento) {
		if (appartamento == null || appartamento.getId() < 1) {
			throw new RuntimeException("Errore appartamento in input errato");
		}

		int result = 0;
		try (Connection c = MyConnection.getConnection();
				PreparedStatement ps = c.prepareStatement("delete from appartamento where id = ?;")) {

			ps.setLong(1, appartamento.getId());

			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return result;
	}

	public List<Appartamento> findByExample(Appartamento example) {
		if (example == null )
			throw new RuntimeException("Errore: appartamento in input non valido");

		List<Appartamento> result = new ArrayList<Appartamento>();
		boolean quartiere = false;
		boolean metriQuadrati = false;
		boolean prezzo = false;
		boolean dataCreazione = false;
		String query = "select * from appartamento ";

		if (example.getQuartiere() != null && !example.getQuartiere().isEmpty()) {
			query += "where quartiere like ? "; // da settare dopo
			quartiere = true;

		}
		if (example.getPrezzo() > 0) {

			if (quartiere) {
				query += " and ";

			} else {
				query += " where ";

			}

			query += " prezzo > ? ";
			prezzo = true;

		}
		if (example.getMetriQuadrati() > 0) {

			if (quartiere || prezzo) {
				query += " and ";

			} else {
				query += " where ";

			}

			query += " metriquadrati > ? ";
			metriQuadrati = true;

		}
		if (example.getDataCreazione() != null) {
			if (quartiere || prezzo || metriQuadrati) {
				query += " and ";
			} else {
				query += " where ";
			}

			query += " datacreazione > ?";
			dataCreazione = true;
		}
		query += ";";

		try (Connection c = MyConnection.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
			// settare i vari parametri ? della query
			int indice = 1;
			if (quartiere) {
				ps.setString(indice, example.getQuartiere() + "%");
				indice++;
			}

			if (prezzo) {
				ps.setInt(indice, example.getPrezzo());
				indice++;
			}
			if (metriQuadrati) {
				ps.setInt(indice, example.getMetriQuadrati());
				indice++;
			}
			if (dataCreazione) {
				ps.setDate(indice, new java.sql.Date(example.getDataCreazione().getTime()));
				indice++;
			}
			// mandiamno la query
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Appartamento temp = new Appartamento();
					temp.setId(rs.getLong("id"));
					temp.setQuartiere(rs.getString("quartiere"));
					temp.setPrezzo(rs.getInt("prezzo"));
					temp.setMetriQuadrati(rs.getInt("metriquadrati"));
					temp.setDataCreazione(rs.getDate("datacreazione"));
					result.add(temp);
				}

			}

			// controllo se ha quartiere
			// se si mi metto la variabile quartiere a true e mi aggiungo alla stringa il
			// coso

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
