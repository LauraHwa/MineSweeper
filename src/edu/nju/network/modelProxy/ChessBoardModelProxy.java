package edu.nju.network.modelProxy;

import edu.nju.controller.msgqueue.operation.DoubleClickOperation;
import edu.nju.controller.msgqueue.operation.LeftClickOperation;
import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.RightClickOperation;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.network.client.ClientService;

public class ChessBoardModelProxy extends ModelProxy implements ChessBoardModelService{

	public ChessBoardModelProxy(ClientService client) {
		this.net = client;
	}

	@Override
	public boolean initialize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean excavate(int x, int y) {
		MineOperation op = new LeftClickOperation(x, y);
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean mark(int x, int y) {
		MineOperation op = new RightClickOperation(x, y);
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean quickExcavate(int x, int y) {
		MineOperation op = new DoubleClickOperation(x, y);
		net.submitOperation(op);
		return true;
	}

	@Override
	public void setGameModel(GameModelService gameModel) {
		// TODO Auto-generated method stub
	}
}
