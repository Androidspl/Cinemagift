package com.skyfilm.owner.communication.mine;

import java.util.List;

import com.skyfilm.owner.bean.mine.FilmTheme;
import com.skyfilm.owner.bean.mine.Onsell;
import com.skyfilm.owner.bean.mine.PublishWork;
import com.skyfilm.owner.exception.CsqException;

import android.provider.MediaStore.Files;

/**
 * 发布作品
 * 
 * @author min.yuan
 *
 */
public interface PublishWorkCom {
	// Publish_Work，Get_Film_Theme
	/**
	 * 发布作品
	 * @param User_id
	 * @param publishWork
	 * @return
	 * @throws CsqException
	 */
	String publishWork(String User_id, PublishWork publishWork) throws CsqException;

	/**
	 * 获取影片主题
	 * 
	 * @param User_id
	 * @return
	 * @throws CsqException
	 */
	List<FilmTheme> getFilmThemeList(String User_id) throws CsqException;
}
