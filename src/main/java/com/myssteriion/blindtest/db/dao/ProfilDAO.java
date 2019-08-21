package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.ProfilDTO;
import com.myssteriion.blindtest.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfilDAO extends AbstractDAO<ProfilDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfilDAO.class);
	
	
	
	public ProfilDAO() {
		super("profil");
	}
	
	
	
	@Override
	public ProfilDTO save(ProfilDTO profilDto) throws DaoException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(name, avatar) ");
			sb.append("VALUES ('" + escapeValue( profilDto.getName() ) + "', '" + profilDto.getAvatar() + "')");
			
			statement.execute( sb.toString() );
			
			ProfilDTO profilDtoSaved = find(profilDto);
			LOGGER.info("profilDto inserted (" + profilDtoSaved.toString() + ").");
			
			return profilDtoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save profilDto.", e);
		}
	}
	
	@Override
	public ProfilDTO update(ProfilDTO profilDto) throws DaoException {
		
		Tool.verifyValue("profilDto", profilDto);
		Tool.verifyValue("profilDto -> id", profilDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET name = '" + escapeValue( profilDto.getName() ) + "', avatar = '" + profilDto.getAvatar() + "'" );
			sb.append("WHERE id = " + profilDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("profilDto updated (" + profilDto.toString() + ").");
			
			return profilDto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update profilDto.", e);
		}
	}
	
	@Override
	public ProfilDTO find(ProfilDTO profilDto) throws DaoException {
		
		Tool.verifyValue("profilDto", profilDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfilDTO profilDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(profilDto.getId()) )
				sb.append("WHERE name = '" + escapeValue( profilDto.getName() ) + "'");
			else
				sb.append("WHERE id = " + profilDto.getId());

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				if ( rs.next() )
					profilDtoToReturn = transformToDto(rs);
			}

			return profilDtoToReturn;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find profilDto.", e);
		}
	}
	
	@Override
	public List<ProfilDTO> findAll() throws DaoException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfilDTO> profilDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				while ( rs.next() )
					profilDtoList.add( transformToDto(rs) );
			}
			
			return profilDtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find all profilDto.", e);
		}
	}


	private ProfilDTO transformToDto(ResultSet rs) throws SQLException {

		ProfilDTO profilDtoToReturn;

		profilDtoToReturn = new ProfilDTO(rs.getString("name"), rs.getString("avatar"));
		profilDtoToReturn.setId( rs.getInt("id") );

		return profilDtoToReturn;
	}

}
