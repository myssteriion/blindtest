package com.myssteriion.blindtest.db.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Duration;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ProfilStatDAO extends AbstractDAO<ProfilStatDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilStatDAO.class);
	
	
	
	public ProfilStatDAO() {
		super("profil_stat");
	}
	
	
	
	@Override
	public ProfilStatDTO save(ProfilStatDTO profilStatDto) throws DaoException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(profil_id, played_games, listened_musics, found_musics, best_scores) ");
			sb.append("VALUES (" + profilStatDto.getProfilId() + ", 0, 0, 0, \"{}\")");
			
			statement.execute( sb.toString() );
			
			ProfilStatDTO profilStatDtoSaved = find(profilStatDto);
			LOGGER.info("profilStatDto inserted (" + profilStatDtoSaved.toString() + ").");
			
			return profilStatDtoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save profilStatDto.", e);
		}
	}
	
	@Override
	public ProfilStatDTO update(ProfilStatDTO profilStatDto) throws DaoException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		Tool.verifyValue("profilStatDto -> id", profilStatDto.getId());
		
		try ( Statement statement = em.createStatement() ) {

			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET played_games = " + profilStatDto.getPlayedGames() + ", ");
			sb.append("listened_musics = " + profilStatDto.getListenedMusics() + ", ");
			sb.append("found_musics = " + profilStatDto.getFoundMusics() + ", ");
			sb.append("best_scores = " + Tool.MAPPER.writeValueAsString(profilStatDto.getBestScores()) + " ");
			sb.append("WHERE id = " + profilStatDto.getId());

			statement.execute( sb.toString() );
			LOGGER.info("profilStatDto updated (" + profilStatDto.toString() + ").");
			
			return profilStatDto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update profilStatDto.", e);
		}
		catch (JsonProcessingException e) {
			throw new DaoException("Can't parse 'bestScores' profilStatDto.", e);
		}
	}
	
	@Override
	public ProfilStatDTO find(ProfilStatDTO profilStatDto) throws DaoException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilStatDTO profilDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(profilStatDto.getId()) )
				sb.append("WHERE profil_id = " + profilStatDto.getProfilId() );
			else
				sb.append("WHERE id = " + profilStatDto.getId());

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				if ( rs.next() ) {
					HashMap<Duration, Integer> bestScores = Tool.MAPPER.readValue(rs.getString("best_scores"), new TypeReference<HashMap<Duration, Integer>>() {});
					profilDtoToReturn = new ProfilStatDTO(rs.getInt("profil_id"), rs.getInt("played_games"),
														  rs.getInt("listened_musics"), rs.getInt("found_musics"), bestScores);
					profilDtoToReturn.setId( rs.getInt("id") );
				}
			}

			return profilDtoToReturn;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find profilStatDto.", e);
		}
		catch (IOException e) {
			throw new DaoException("Can't parse 'bestScores' profilStatDto.", e);
		}
    }
	
	@Override
	public List<ProfilStatDTO> findAll() throws DaoException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilStatDTO> profilDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				while ( rs.next() ) {
					HashMap<Duration, Integer> bestScores = Tool.MAPPER.readValue(rs.getString("best_scores"), new TypeReference<HashMap<Duration, Integer>>() {});
					ProfilStatDTO profilStatDto = new ProfilStatDTO(rs.getInt("profil_id"), rs.getInt("played_games"),
																	rs.getInt("listened_musics"), rs.getInt("found_musics"), bestScores);
					profilStatDto.setId( rs.getInt("id") );
					profilDtoList.add(profilStatDto);
				}
			}

			return profilDtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find all profilStatDto.", e);
		}
		catch (IOException e) {
			throw new DaoException("Can't parse 'bestScores' profilStatDto.", e);
		}
	}
	
}
