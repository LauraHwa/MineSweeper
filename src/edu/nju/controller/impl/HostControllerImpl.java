package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.service.HostControllerService;
import edu.nju.model.impl.ChessBoardModelImpl;
import edu.nju.model.impl.GameModelImpl;
import edu.nju.model.impl.ParameterModelImpl;
import edu.nju.network.host.HostInHandlerImpl;
import edu.nju.network.host.HostServiceImpl;

public class HostControllerImpl implements HostControllerService{
	private HostServiceImpl host;
	private HostInHandlerImpl hostInHandler;
	private ChessBoardModelImpl chessBoard;
	private GameModelImpl game;
	private ParameterModelImpl parameter;
	/**
	 * 作为主机建立网络连接
	 * @return
	 */
	public boolean serviceetupHost() {
		host = new HostServiceImpl();
		hostInHandler = new HostInHandlerImpl();
		if(host.init(hostInHandler)){
			chessBoard = (ChessBoardModelImpl) OperationQueue.getChessBoardModel();
			game =  (GameModelImpl) OperationQueue.getGameModel();
			parameter = (ParameterModelImpl) chessBoard.getParameterModel();
			chessBoard.addObserver(host);
			game.addObserver(host);
			parameter.addObserver(host);
		}
		return true;
	}
}
