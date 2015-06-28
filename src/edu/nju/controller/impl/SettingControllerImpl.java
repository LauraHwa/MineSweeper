package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.SettingControllerService;

public class SettingControllerImpl implements SettingControllerService{

	@Override
	public boolean setEasyGameLevel() {
		OperationQueue.addMineOperation(new StartGameOperation("小"));
		return true;
	}

	@Override
	public boolean setHardGameLevel() {
		OperationQueue.addMineOperation(new StartGameOperation("中"));
		return true;
	}

	@Override
	public boolean setHellGameLevel() {
		OperationQueue.addMineOperation(new StartGameOperation("大"));
		return true;
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		OperationQueue.addMineOperation(new StartGameOperation(String.valueOf(height)+" "+String.valueOf(width)+" "+String.valueOf(nums)));
		return true;
	}

}
