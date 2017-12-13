package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.Information;
import com.skyfilm.owner.exception.CsqException;
/**
 * 咨询接口
 * @author min.yuan
 *
 */
public interface InformationCom {
	/**
	 * 获取咨询信息
	 * @param User_id
	 * @param Page
	 * @param Page_Size
	 * @return
	 * @throws CsqException
	 */
	List<Information> getInformationList(String User_id,String Page,String Page_Size)throws CsqException;
}
