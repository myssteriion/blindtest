package com.myssteriion.blindtest.db.dao;

import com.myssteriion.blindtest.db.AbstractDAO;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
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
public class ProfileDAO extends AbstractDAO<ProfileDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDAO.class);
	
	
	
	public ProfileDAO() {
		super("profile");
	}
	
	
	
	@Override
	public ProfileDTO save(ProfileDTO profileDto) throws DaoException {
		
		Tool.verifyValue("profileDto", profileDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + "(name, avatar) ");
			sb.append("VALUES ('" + escapeValue( profileDto.getName() ) + "', '" + profileDto.getAvatar() + "')");
			
			statement.execute( sb.toString() );
			
			ProfileDTO profileDtoSaved = find(profileDto);
			LOGGER.info("profileDto inserted (" + profileDtoSaved.toString() + ").");
			
			return profileDtoSaved;
		}
		catch (SQLException e) {
			throw new DaoException("Can't save profileDto.", e);
		}
	}
	
	@Override
	public ProfileDTO update(ProfileDTO profileDto) throws DaoException {
		
		Tool.verifyValue("profileDto", profileDto);
		Tool.verifyValue("profileDto -> id", profileDto.getId());
		
		try ( Statement statement = em.createStatement() ) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE " + tableName + " ");
			sb.append("SET name = '" + escapeValue( profileDto.getName() ) + "', avatar = '" + profileDto.getAvatar() + "'" );
			sb.append("WHERE id = " + profileDto.getId());
			
			statement.execute( sb.toString() );
			LOGGER.info("profileDto updated (" + profileDto.toString() + ").");
			
			return profileDto;
		}
		catch (SQLException e) {
			throw new DaoException("Can't update profileDto.", e);
		}
	}
	
	@Override
	public ProfileDTO find(ProfileDTO profileDto) throws DaoException {
		
		Tool.verifyValue("profileDto", profileDto);
		
		try ( Statement statement = em.createStatement() ) {
			
			ProfileDTO profileDtoToReturn = null;
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName + " ");
			
			if ( Tool.isNullOrEmpty(profileDto.getId()) )
				sb.append("WHERE name = '" + escapeValue( profileDto.getName() ) + "'");
			else
				sb.append("WHERE id = " + profileDto.getId());

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				if ( rs.next() )
					profileDtoToReturn = transformToDto(rs);
			}

			return profileDtoToReturn;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find profileDto.", e);
		}
	}
	
	@Override
	public List<ProfileDTO> findAll() throws DaoException {
		
		try ( Statement statement = em.createStatement() ) {
			
			List<ProfileDTO> profileDtoList = new ArrayList<>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM " + tableName);

			try ( ResultSet rs = statement.executeQuery(sb.toString()) ) {
				while ( rs.next() )
					profileDtoList.add( transformToDto(rs) );
			}
			
			return profileDtoList;
		}
		catch (SQLException e) {
			throw new DaoException("Can't find all profileDto.", e);
		}
	}


	private ProfileDTO transformToDto(ResultSet rs) throws SQLException {

		ProfileDTO profileDtoToReturn;

		profileDtoToReturn = new ProfileDTO(rs.getString("name"), rs.getString("avatar"));
		profileDtoToReturn.setId( rs.getInt("id") );

		return profileDtoToReturn;
	}

}
