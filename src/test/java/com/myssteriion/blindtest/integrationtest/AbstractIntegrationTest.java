package com.myssteriion.blindtest.integrationtest;

import com.myssteriion.blindtest.AbstractTest;
import com.myssteriion.blindtest.db.EntityManager;
import com.myssteriion.blindtest.db.exception.DaoException;
import com.myssteriion.blindtest.model.common.Theme;
import com.myssteriion.blindtest.model.dto.MusicDTO;
import com.myssteriion.blindtest.model.dto.ProfileDTO;
import com.myssteriion.blindtest.rest.exception.ConflictException;
import com.myssteriion.blindtest.rest.exception.NotFoundException;
import com.myssteriion.blindtest.service.GameService;
import com.myssteriion.blindtest.service.MusicService;
import com.myssteriion.blindtest.service.ProfileService;
import com.myssteriion.blindtest.service.ProfileStatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractIntegrationTest extends AbstractTest {

	@Autowired
	protected EntityManager em;

	@Autowired
	protected MusicService musicService;

	@Autowired
	protected ProfileService profileService;

    @Autowired
    protected ProfileStatService profileStatService;

	@Autowired
	protected GameService gameService;



	protected static final List<MusicDTO> MUSICS_LIST = Arrays.asList(
			new MusicDTO("musicName80", Theme.ANNEES_80),
			new MusicDTO("musicName90", Theme.ANNEES_90),
			new MusicDTO("musicName00", Theme.ANNEES_2000)
	);

	protected static final List<ProfileDTO> PROFILES_LIST = Arrays.asList(
			new ProfileDTO("name1"),
			new ProfileDTO("name2"),
			new ProfileDTO("name3")
	);



	protected void clearDataBase() throws DaoException, SQLException {

		try ( Statement statement = em.createStatement() ) {
			statement.executeUpdate("DELETE FROM music");
			statement.executeUpdate("DELETE FROM profile_stat");
			statement.executeUpdate("DELETE FROM profile");
		}
	}

	protected void insertData() throws ConflictException, DaoException, NotFoundException {

		for (MusicDTO musicDto : MUSICS_LIST)
			musicService.save(musicDto);

		for (ProfileDTO profileDTO : PROFILES_LIST)
			profileService.save(profileDTO);
	}

}
