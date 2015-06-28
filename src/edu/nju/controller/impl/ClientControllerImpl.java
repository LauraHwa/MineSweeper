package edu.nju.controller.impl;

import edu.nju.controller.msgqueue.OperationQueue;
import edu.nju.controller.msgqueue.operation.StartGameOperation;
import edu.nju.controller.service.ClientControllerService;
import edu.nju.controller.service.GameControllerService;
import edu.nju.controller.service.MenuControllerService;
import edu.nju.controller.service.SettingControllerService;
import edu.nju.model.service.ParameterModelService;
import edu.nju.network.client.ClientInHandlerImpl;
import edu.nju.network.client.ClientServiceImpl;
import edu.nju.network.modelProxy.ChessBoardModelProxy;
import edu.nju.network.modelProxy.GameModelProxy;
import edu.nju.network.modelProxy.ParameterModelProxy;
import edu.nju.view.MainFrame;

public class ClientControllerImpl implements ClientControllerService{
	private MainFrame ui;
	private GameModelProxy proxyGameModel;
	private ChessBoardModelProxy proxyChessBoard;
	private ParameterModelProxy proxyParameter;
	/**
	 * 作为客户端建立网络连接。
	 * @param ip
	 * @return
	 */
	public boolean setupClient(String ip) {
		ClientServiceImpl client = new ClientServiceImpl();
		ClientInHandlerImpl clientInHandler = new ClientInHandlerImpl();
		if(client.init(ip, clientInHandler)){
			proxyGameModel = new GameModelProxy(client);
			proxyChessBoard = new ChessBoardModelProxy(client);
			proxyParameter = new ParameterModelProxy();
			OperationQueue.setProxy(proxyChessBoard, proxyGameModel);
			clientInHandler.addObserver(proxyGameModel);
			clientInHandler.addObserver(proxyChessBoard);
			clientInHandler.addObserver(proxyParameter);
			proxyGameModel.addObserver(ui);
			proxyChessBoard.addObserver(ui.getMineBoard());
			proxyParameter.addObserver(ui.getMineNumberLabel());
			OperationQueue.isClient = true;
			OperationQueue.isHost = false;
			OperationQueue.addMineOperation(new StartGameOperation());
		}
		return true;
	}

	@Override
	public boolean addConnection(MainFrame ui) {
		this.ui = ui;
		return true;
	}
}
