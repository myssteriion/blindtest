package com.myssteriion.blindtest.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.common.SqlException;
import com.myssteriion.blindtest.model.dto.ProfilStatDTO;
import com.myssteriion.blindtest.tools.Tool;

@Component
public class ProfilStatDAO extends AbstractDAO<ProfilStatDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilStatDAO.class);
	
	
	
	@Override
	public ProfilStatDTO save(ProfilStatDTO profilStatDto) throws SqlException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO profil_stat(profil_id, played_games, listened_musics, found_musics) ");
			sb.append("VALUES (" + profilStatDto.getProfilId() + ", 0, 0, 0)");
			
			statement.execute( sb.toString() );
			
			ProfilStatDTO dtoSaved = find(profilStatDto);
			LOGGER.info("profilStatDto inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (SQLException e) {
			
			String message = "Can't save profilStatDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public ProfilStatDTO update(ProfilStatDTO profilStatDto) throws SqlException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		Tool.verifyValue("profilStatDto -> id", profilStatDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE profil_stat ");
			sb.append("SET played_games = " + profilStatDto.getPlayedGames() + ", ");
			sb.append("listened_musics = " + profilStatDto.getListenedMusics() + ", ");
			sb.append("found_musics = " + profilStatDto.getFoundMusics() + " ");
			sb.append("WHERE id = " + profilStatDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("profilStatDto updated (" + profilStatDto.toString() + ").");
			
			return profilStatDto;
		}
		catch (SQLException e) {
			
			String message = "Can't update profilStatDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public ProfilStatDTO find(ProfilStatDTO profilStatDto) throws SqlException {
		
		Tool.verifyValue("profilStatDto", profilStatDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilStatDTO profilDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil_stat ");
			
			if ( Tool.isNullOrEmpty(profilStatDto.getId()) )
				sb.append("WHERE profil_id = " + profilStatDto.getProfilId() );
			else
				sb.append("WHERE id = " + profilStatDto.getId());
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			if ( rs.next() ) {
				
				profilDtoToReturn = new ProfilStatDTO(rs.getString("profil_id"), rs.getInt("played_games"), rs.getInt("listened_musics"),
													  rs.getInt("found_musics") );
				profilDtoToReturn.setId( rs.getString("id") );
			}

			return profilDtoToReturn;
		}
		catch (SQLException e) {
			
			String message = "Can't find profilStatDto.";
			throw new SqlException(message, e);
		}
	}
	
	@Override
	public List<ProfilStatDTO> findAll() throws SqlException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilStatDTO> profilDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil_stat");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {

				ProfilStatDTO profilStatDto = new ProfilStatDTO(rs.getString("profil_id"), rs.getInt("played_games"), rs.getInt("listened_musics"), 
																rs.getInt("found_musics") );
				profilStatDto.setId( rs.getString("id") );
				profilDtoList.add(profilStatDto);
			}
			
			return profilDtoList;
		}
		catch (SQLException e) {
			
			String message = "Can't find all profilStatDto.";
			throw new SqlException(message, e);
		}
	}
	
}