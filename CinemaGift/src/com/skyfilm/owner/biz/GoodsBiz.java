package com.skyfilm.owner.biz;

import java.util.List;

import com.skyfilm.owner.bean.Goods;
import com.skyfilm.owner.bean.ProductInfo;
import com.skyfilm.owner.bean.StoreEntity;
import com.skyfilm.owner.bean.mine.MyBanner;
import com.skyfilm.owner.communication.GoodsCom;
import com.skyfilm.owner.communication.StoreCom;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;

public class GoodsBiz {
	private GoodsCom goodsCom;

	public GoodsBiz() {
		this.goodsCom = (GoodsCom) CsqManger.getInstance().get(Type.GOODSCOM);
	}
	
	/**
	 * 返回搜索商品信息
	 * 
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	public List<Goods> getSearchGoods(String Type, String Keyword) throws CsqException {
		return goodsCom.getSearchGoods(Type, Keyword);
	}

}
