package it.prova.gestioneappartamento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

}