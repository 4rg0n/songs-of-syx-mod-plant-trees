package com.github.argon.sos.planttree;


import com.github.argon.sos.planttree.log.Level;
import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.log.Loggers;
import com.github.argon.sos.planttree.util.JobUtil;
import game.GAME;
import init.resources.RESOURCES;
import init.sprite.SPRITES;
import lombok.NoArgsConstructor;
import script.SCRIPT;
import settlement.job.PlantTreeJob;
import util.info.INFO;

/**
 * Entry point
 */
@NoArgsConstructor
public final class PlantTreeModScript implements SCRIPT {
	private final static Logger log = Loggers.getLogger(PlantTreeModScript.class);

	private final INFO info = new INFO("Plant trees", "Plant trees on the map.");

	@Override
	public CharSequence name() {
		return info.name;
	}

	@Override
	public CharSequence desc() {
		return info.desc;
	}

	@Override
	public void initBeforeGameCreated() {
		Loggers.setLevels(Level.INFO);

		// will execute after all game resources are initialized
		// but before VIEW creation
		GAME.addOnInit(() -> {
			PlantTreeJob plantTreeJob = new PlantTreeJob(
				SPRITES.icons().m.agriculture,
				50,
				33,
				RESOURCES.WOOD(),
				3);

			JobUtil.addClearsJob(plantTreeJob);
			log.info("initialized");
		});
	}

	@Override
	public SCRIPT_INSTANCE createInstance() {
		return new Instance();
	}

	@Override
	public boolean forceInit() {
		return true;
	}
}
