package com.skyfilm.owner.biz;

import java.util.HashMap;
import java.util.List;

import com.skyfilm.owner.bean.mine.FilmTheme;
import com.skyfilm.owner.bean.mine.MyAddress;
import com.skyfilm.owner.bean.mine.PublishWork;
import com.skyfilm.owner.communication.mine.AddressCom;
import com.skyfilm.owner.communication.mine.FeedBackCom;
import com.skyfilm.owner.communication.mine.PublishWorkCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

import android.provider.MediaStore.Files;

/**
 * 地址，反馈信息，发布作品
 * 
 * @author min.yuan
 *
 */
public class AddressBiz {
	private AddressCom addressCom;
	private FeedBackCom feedBackCom;
	private PublishWorkCom publishWorkCom;

	public AddressBiz() {
		addressCom = (AddressCom) CsqManger.getInstance().get(Type.ADDRESSCOM);
		feedBackCom = (FeedBackCom) CsqManger.getInstance().get(Type.FEEDBACKCOM);
		publishWorkCom = (PublishWorkCom) CsqManger.getInstance().get(Type.PUBLISHWORKCOM);
	}

	/**
	 * 添加地址
	 * 
	 * @param User_id
	 * @param addAddress
	 * @return
	 * @throws CsqException
	 */
	public String addAddress(String User_id, HashMap<String, String> addAddress) throws CsqException {
		return addressCom.addAddress(User_id, addAddress);
	}

	/**
	 * 设为默认地址
	 * 
	 * @param User_id
	 * @param Id
	 * @return
	 * @throws CsqException
	 */
	public String setReceiverAddress(String User_id, String Id) throws CsqException {
		return addressCom.setReceiverAddress(User_id, Id);
	}
	/**
	 * 删除地址
	 * 
	 * @param User_id
	 * @param Id
	 * @return
	 * @throws CsqException
	 */
	public String setDelAddress(String Id) throws CsqException {
		return addressCom.setDelAddress(Id);
	}

	/**
	 * 获取地址列表
	 * 
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	public List<MyAddress> getMyAddressList(String user_id) throws CsqException {
		return addressCom.getMyAddressList(user_id);
	}

	/**
	 * 提交反馈信息
	 * 
	 * @param User_id
	 * @param Content
	 * @return
	 * @throws CsqException
	 */
	public String feedBack(String User_id, String Content) throws CsqException {
		return feedBackCom.feedBack(User_id, Content);
	}

	/**
	 * 提交发布作品信息
	 * 
	 * @param User_id
	 * @param publishWork
	 * @return
	 * @throws CsqException
	 */
	public String publishWork(String User_id, PublishWork publishWork) throws CsqException {
		return publishWorkCom.publishWork(User_id, publishWork);
	}

	/**
	 * 获取影片主题
	 * 
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	public List<FilmTheme> getFilmThemeList(String User_id) throws CsqException {
		return publishWorkCom.getFilmThemeList(User_id);
	}
}
