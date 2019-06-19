package com.myssteriion.blindtest.db.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.EntityManagerException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;

@Component
public class ProfilDAO extends AbstractDAO<ProfilDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilDAO.class);
	
	
	
	@Override
	public ProfilDTO find(ProfilDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilDTO dtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil ");
			sb.append("WHERE name = '" + dto.getName() + "'");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				dtoToReturn = new ProfilDTO( rs.getString("name"), rs.getString("avatar"), rs.getInt("playedGames"), rs.getInt("listenedMusics"), rs.getInt("foundMusics") );
				dtoToReturn.setId( rs.getString("id") );
			}

			return dtoToReturn;
		}
		catch (Exception e) {
			
			String message = "Can't find dto.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
	@Override
	public List<ProfilDTO> findAll() throws EntityManagerException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilDTO> dtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM profil");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				ProfilDTO dto = new ProfilDTO( rs.getString("name"), rs.getString("avatar"), rs.getInt("playedGames"), rs.getInt("listenedMusics"), rs.getInt("foundMusics") );
				dto.setId( rs.getString("id") );
				dtoList.add(dto);
			}
			
			return dtoList;
		}
		catch (Exception e) {
			
			String message = "Can't find all dto.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
	@Override
	public ProfilDTO saveOrUpdate(ProfilDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto", dto);

		ProfilDTO foudDTO = find(dto);
		ProfilDTO savedDTO = (foudDTO == null) ? save(dto) : update(foudDTO);
		
		return savedDTO;
	}
	
	
	
	private ProfilDTO save(ProfilDTO dto) throws EntityManagerException {
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO profil(name, avatar, playedGames, listenedMusics, foundMusics) ");
			sb.append("VALUES ('" + dto.getName() + "', '" + dto.getAvatar() + "', 0, 0, 0)");
			
			statement.execute( sb.toString() );
			
			ProfilDTO dtoSaved = find(dto);
			LOGGER.info( "DTO inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (Exception e) {
			
			String message = "Can't save dto.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
	private ProfilDTO update(ProfilDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto -> id", dto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE profil ");
			sb.append("SET avatar = " + dto.getAvatar() + ", playedGames = " + dto.getPlayedGames() + ", listenedMusics = " + dto.getListenedMusics() + ", foundMusics = " + dto.getFoundMusics() + " ");
			sb.append("WHERE id = " + dto.getId());
			
			statement.execute( sb.toString() );
			return dto;
		}
		catch (Exception e) {
			
			String message = "Can't update dto.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}

}
