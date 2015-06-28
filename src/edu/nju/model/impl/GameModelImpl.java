package edu.nju.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.nju.model.service.ChessBoardModelService;
import edu.nju.model.service.GameModelService;
import edu.nju.model.service.StatisticModelService;
import edu.nju.model.state.GameResultState;
import edu.nju.model.state.GameState;
import edu.nju.model.vo.GameVO;
import edu.nju.network.host.HostThread;

public class GameModelImpl extends BaseModel implements GameModelService{
	
	private StatisticModelService statisticModel;
	private ChessBoardModelService chessBoardModel;
	
	private List<GameLevel> levelList;
	
	private GameState gameState;
	private int width;
	private int height;
	private int mineNum;
	private String level;
	
	private GameResultState gameResultStae;
	private int time;
	
	private long startTime;

	public GameModelImpl(StatisticModelService statisticModel, ChessBoardModelService chessBoardModel){
		this.statisticModel = statisticModel;
		this.chessBoardModel = chessBoardModel;
		gameState = GameState.OVER;
		
		chessBoardModel.setGameModel(this);
		
		levelList = new ArrayList<GameLevel>();
		levelList.add(new GameLevel(0,"大",16,30,99));
		levelList.add(new GameLevel(1,"中",16,16,40));
		levelList.add(new GameLevel(2,"小",9,9,10));
	}

	public boolean startGame() {
		gameState = GameState.RUN;
		startTime = Calendar.getInstance().getTimeInMillis() - 1000;
		
		GameLevel gl = null;
		for(GameLevel tempLevel : levelList){
			if(tempLevel.getName().equals(level)){
				gl = tempLevel;
				break;
			}
		}
		
		if(gl == null&&width==0&&height == 0){
			gl = levelList.get(2);
			level = "小";
		}
		
		if(gl != null){
			level = gl.getName();
			height = gl.getWidth();
			width = gl.getHeight();
			mineNum = gl.getMineNum();
		}
		
		this.chessBoardModel.initialize(width, height, mineNum);
		
		super.updateChange(new UpdateMessage("start",this.convertToDisplayGame()));
		return true;
	}
	
	public boolean gameOver(GameResultState result) {
		this.gameState = GameState.OVER;
		this.gameResultStae = result;
		this.time = (int)(Calendar.getInstance().getTimeInMillis() - startTime)/1000;
		
		boolean isConnected = HostThread.isConnected;
		if(!isConnected){
			this.statisticModel.setLevel(this.level);
			this.statisticModel.recordStatistic(result, time);
		}
		super.updateChange(new UpdateMessage("end",this.convertToDisplayGame()));		
		if(!isConnected){
			this.statisticModel.showStatistics();
		}
		
		return true;
	}
	
	public boolean showRecord(){
		this.statisticModel.showStatistics();
		return false;
	}

	@Override
	public boolean setGameLevel(String level) {
		//输入校验
		
		String[] levelInfo = level.split(" ");
		if(levelInfo.length == 3){
			this.level = "自定义";
			int h = Integer.parseInt(levelInfo[0]);
			int w = Integer.parseInt(levelInfo[1]);
			int n = Integer.parseInt(levelInfo[2]);
			if(levelList.size() == 4){
				levelList.remove(3);
			}
			levelList.add(new GameLevel(3, "自定义", h, w, n));
		}else{
			this.level = level;
		}
		return true;
	}

	@Override
	public boolean setGameSize(int width, int height, int mineNum) {
		// TODO Auto-generated method stub
		//输入校验
		this.width = width;
		this.height = height;
		this.mineNum = mineNum;
		return true;
	}
	
	private GameVO convertToDisplayGame(){
		return new GameVO(gameState, width, height, level, gameResultStae, time);
	}
	
	@Override
	public List<GameLevel> getGameLevel() {
		// TODO Auto-generated method stub
		return this.levelList;
	}
}