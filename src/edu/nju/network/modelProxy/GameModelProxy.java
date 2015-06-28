package edu.nju.network.modelProxy;


import java.util.List;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.controller.msgqueue.operation.StartCustomizedGameOperation;
import edu.nju.controller.msgqueue.operation.StartEasyGameOperation;
import edu.nju.controller.msgqueue.operation.StartHardGameOperation;
import edu.nju.controller.msgqueue.operation.StartHellGameOperation;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.model.impl.GameLevel;
import edu.nju.model.service.GameModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.network.client.ClientService;
/**
 * GameModel的代理，在网络对战时替代Client端的相应Model。
 * @author 晨晖
 *
 */
public class GameModelProxy extends ModelProxy implements GameModelService{
	
	
	public GameModelProxy(ClientService client){
		this.net = client;
	}

	@Override
	public boolean setGameLevel(String level) {
		MineOperation op;
		switch(level){
		case "小": op = new StartEasyGameOperation(); break;
		case "中": op = new StartHardGameOperation(); break;
		case "大": op = new StartHellGameOperation(); break;
		default:
			String[] levelInfo = level.split(" ");
			if(levelInfo.length == 3){
				int h = Integer.parseInt(levelInfo[0]);
				int w = Integer.parseInt(levelInfo[1]);
				int n = Integer.parseInt(levelInfo[2]);
				op = new StartCustomizedGameOperation(h, w, n);
			}else{
				op = new StartEasyGameOperation();
			}
			break;
		}
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean startGame() {
		MineOperation op = new StartGameOperation();
		net.submitOperation(op);
		return true;
	}

	@Override
	public boolean gameOver(GameResultState result) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showRecord() {
		// TODO Auto-generated method stub
		return false;
	}
}
