package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.ShowRecordOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.MenuControllerService;

public class MenuControllerImpl implements MenuControllerService{

	@Override
	public boolean startGame() {
		OperationQueue.addMineOperation(new StartGameOperation());
		return true;
	}
	
	public boolean showRecord() {
		OperationQueue.addMineOperation(new ShowRecordOperation());
		return true;
	}
}
