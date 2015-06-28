package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class StartGameOperation extends MineOperation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String level;
	public StartGameOperation(String level) {
		// TODO Auto-generated constructor stub
		this.level=level;
	}
	
	
	@Override
	public void execute() {
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel(level);
		game.startGame();
	}
}
