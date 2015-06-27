package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.StartCustomizedGameOperation;
import edu.nju.controller.msgqueue.operation.StartEasyGameOperation;
import edu.nju.controller.msgqueue.operation.StartHardGameOperation;
import edu.nju.controller.msgqueue.operation.StartHellGameOperation;
import edu.nju.controller.service.SettingControllerService;

public class SettingControllerImpl implements SettingControllerService{

	@Override
	public boolean setEasyGameLevel() {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new StartEasyGameOperation());
		return true;
	}

	@Override
	public boolean setHardGameLevel() {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new StartHardGameOperation());
		return true;
	}

	@Override
	public boolean setHellGameLevel() {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new StartHellGameOperation());
		return true;
	}

	@Override
	public boolean setCustomizedGameLevel(int height, int width, int nums) {
		// TODO Auto-generated method stub
		OperationQueue.addMineOperation(new StartCustomizedGameOperation(height, width, nums));
		return true;
	}

}
