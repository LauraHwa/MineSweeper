package edu.nju.controller.service;

import edu.nju.view.MainFrame;

public interface ClientControllerService {
	/**
	 * 作为客户端建立网络连接。
	 * @param ip
	 * @return
	 */
	public boolean setupClient(String ip);

	boolean addConnection(MainFrame ui);
}
