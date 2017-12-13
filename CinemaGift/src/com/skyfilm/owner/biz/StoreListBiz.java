package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.communication.StoreListCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class StoreListBiz {
	private StoreListCom storelistCom;

	public StoreListBiz() {
		this.storelistCom = (StoreListCom) CsqManger.getInstance().get(Type.STORELISTCOM);
	}

	/**
	 * 返回商品列表信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Goods> getStoreEntity(String type, String tag) throws CsqException {
		return storelistCom.getStroeList(type, tag);
	}

}
