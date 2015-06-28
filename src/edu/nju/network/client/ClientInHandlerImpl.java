package edu.nju.network.client;

import java.util.Observable;

import edu.nju.model.impl.UpdateMessage;
import edu.nju.network.TransformObject;
import edu.nju.network.modelProxy.ChessBoardModelProxy;
import edu.nju.network.modelProxy.GameModelProxy;

public class ClientInHandlerImpl extends Observable implements ClientInHandler{

	@Override
	public void inputHandle(Object data) {
		TransformObject obj = (TransformObject) data;
		
		this.setChanged();
		this.notifyObservers(obj);
	}
}
