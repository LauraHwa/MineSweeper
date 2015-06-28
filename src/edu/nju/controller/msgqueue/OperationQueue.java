package edu.nju.controller.msgqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import edu.nju.controller.msgqueue.operation.MineOperation;
import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;

/**
 * 操作队列，所有的操作需要加入队列，该队列自行按操作到达的先后顺序处理操作。
 * @author 晨晖
 *
 */
public class OperationQueue implements Runnable{
	/**
	 * 可以发生阻塞的安全队列
	 */
	private static BlockingQueue<MineOperation> queue;
	
	public static boolean isRunning;
	/**
	 * 是否为己方操作
	 */
	public static boolean isHost = true;
	public static boolean isClient = false;
	
	private static ChessBoardModelService chessBoard;
	private static GameModelService gameModel;
	private static ChessBoardModelService chessBoardBack;
	private static GameModelService gameModelBack;
	
	public OperationQueue(ChessBoardModelService chess, GameModelService game){
		queue = new ArrayBlockingQueue<MineOperation>(1000);
		isRunning = true;
		chessBoard = chess;
		chessBoardBack = chess;
		gameModel = game;
		gameModelBack = game;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunning){
			MineOperation operation = getNewMineOperation();
			operation.execute();
			isHost = true;
		}
	}
	
	
	public static boolean addMineOperation (MineOperation operation){
		try {
			queue.put(operation);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 取出最上方的操作
	 * @return
	 */
	private static MineOperation getNewMineOperation (){
		MineOperation  operation = null;
		try {
			operation = queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return operation;
	}
	
	public static ChessBoardModelService getChessBoardModel(){
		return chessBoard;
	}
	
	public static GameModelService getGameModel(){
		return gameModel;
	}
	
	public static void setProxy(ChessBoardModelService chessBoardProxy, GameModelService gameModelProxy){
		chessBoard = chessBoardProxy;
		gameModel = gameModelProxy;
	}
	
	public static void backToSingle(){
		chessBoard = chessBoardBack;
		gameModel = gameModelBack;
		isHost = true;
		isClient = false;
	}
}
