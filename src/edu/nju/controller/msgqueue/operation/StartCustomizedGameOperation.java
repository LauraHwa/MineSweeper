package edu.nju.controller.msgqueue.operation;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.model.service.GameModelService;

public class StartCustomizedGameOperation extends MineOperation {
	private int height;
	private int width;
	private int nums;

	public StartCustomizedGameOperation(int height, int width, int nums) {
		// TODO Auto-generated constructor stub
		this.height = height;
		this.width = width;
		this.nums = nums;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		GameModelService game = OperationQueue.getGameModel();
		game.setGameLevel(String.valueOf(height)+" "+String.valueOf(width)+" "+String.valueOf(nums));
		game.startGame();
	}
}
