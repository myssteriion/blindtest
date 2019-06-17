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
import com.myssteriion.blindtest.model.music.MusicDTO;
import com.myssteriion.blindtest.model.music.Theme;
import com.myssteriion.blindtest.tools.Tool;

@Component
public class MusicDAO extends AbstractDAO<MusicDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicDAO.class);
	
	
	
	@Override
	public MusicDTO find(MusicDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto", dto);
		
		try ( Statement statement = em.createStatement() ) {
			
			MusicDTO dtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM musique ");
			sb.append("WHERE name = '" + dto.getName() + "' AND theme = '" + dto.getTheme() + "'");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				dtoToReturn = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("nbPlayed") );
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
	public List<MusicDTO> findAll() throws EntityManagerException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<MusicDTO> dtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM musique");
			
			ResultSet rs = statement.executeQuery( sb.toString() );
			while ( rs.next() ) {
				
				MusicDTO dto = new MusicDTO( rs.getString("name"), Theme.valueOf(rs.getString("theme")), rs.getInt("nbPlayed") );
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
	public MusicDTO saveOrUpdate(MusicDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto", dto);

		MusicDTO dtoFinded = find(dto);
		MusicDTO dtoSaved = (dtoFinded == null) ? save(dto) : update(dtoFinded);
		
		return dtoSaved;
	}
	
	
	
	private MusicDTO save(MusicDTO dto) throws EntityManagerException {
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO musique(name, theme, nbPlayed) ");
			sb.append("VALUES ('" + dto.getName() + "', '" + dto.getTheme() + "', " + dto.getNbPlayed() + ")");
			
			statement.execute( sb.toString() );
			
			MusicDTO dtoSaved = find(dto);
			LOGGER.info( "DTO inserted (" + dtoSaved.toString() + ").");
			
			return dtoSaved;
		}
		catch (Exception e) {
			
			String message = "Can't save dto.";
			LOGGER.error(message, e);
			throw new EntityManagerException(message, e);
		}
	}
	
	private MusicDTO update(MusicDTO dto) throws EntityManagerException {
		
		Tool.verifyValue("dto -> id", dto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE musique ");
			sb.append("SET nbPlayed = " + dto.getNbPlayed() + " ");
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
