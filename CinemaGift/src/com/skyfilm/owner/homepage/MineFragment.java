package com.skyfilm.owner.homepage;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseThreadFragment;
import com.skyfilm.owner.biz.UserBiz;
import com.skyfilm.owner.biz.UserInfoBiz;
import com.skyfilm.owner.exception.CsqException;
import com.skyfilm.owner.mine.BecomeStylistActivity;
import com.skyfilm.owner.mine.ChannelExpandActivity;
import com.skyfilm.owner.mine.CrowdFundingActivity;
import com.skyfilm.owner.mine.CrowdFundingGoodsActivity;
import com.skyfilm.owner.mine.CustomerActivity;
import com.skyfilm.owner.mine.IssueActivity;
import com.skyfilm.owner.mine.MyAddressActivity;
import com.skyfilm.owner.mine.MyAttentionActivity;
import com.skyfilm.owner.mine.MyCollectActivity;
import com.skyfilm.owner.mine.MyLoveActivity;
import com.skyfilm.owner.mine.MyOrderActivity;
import com.skyfilm.owner.mine.NoticeActivity;
import com.skyfilm.owner.mine.OnsellActivity;
import com.skyfilm.owner.mine.SetActivity;
import com.skyfilm.owner.mine.SetMyDataActivity;
import com.skyfilm.owner.user.User;
import com.skyfilm.owner.utils.CsqManger;
import com.skyfilm.owner.utils.CsqManger.Type;
import com.skyfilm.owner.utils.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我的
 * 
 * @author min.yuan
 *
 */
public class MineFragment extends BaseThreadFragment implements OnClickListener {
	private View rootView;
	private ImageView left;

	private LinearLayout design_type;// 设计方向，显隐
	private TextView wdzp;// 我的作品，显隐
	private LinearLayout my_works;// 我的作品，显隐
	private LinearLayout goods_fans;// 商品和粉丝，显隐
	private TextView become_stylist;// 成为设计师，显隐

	private RelativeLayout rl0;//我的资料
	
	private TextView goods;//
	private TextView attentionNum;// 关注人数

	private ImageView head_icon;
	private ImageView vip_grade;
	private TextView nick_name;
	private TextView identity;

	private TextView onsell;// 在售商品
	private TextView crowd_funding;// 众筹商品
	private TextView issue;// 发布作品

	private TextView my_order;// 我的订单
	private TextView participantZC;// 参与的众筹

	private TextView my_fond;// 我的喜欢
	private TextView my_collection;// 我的收藏
	private TextView my_attention;// 我的关注

	private TextView address_manage;// 地址管理
	private TextView customer_service;// 客服咨询
	private TextView set;// 设置
	private TextView channel_expand;// 渠道拓展

	private UserInfoBiz userInfoBiz;
	private static final int GETUSERINFO = 0x0108;
	private List<String> labelList;

	private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(false)
			.showImageForEmptyUri(R.drawable.pic_morentouxiang).showImageOnFail(R.drawable.pic_morentouxiang).build();
	ImageLoader imageLoader = ImageLoader.getInstance();
	private String state = "general";// 身份
	private User user;
	protected UserBiz userBiz;

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = View.inflate(getActivity(), R.layout.mine_f, null);
		userBiz = (UserBiz) CsqManger.getInstance().get(Type.USERBIZ);
		user = userBiz.getCurrentUser();
		initData();
		initUserInfo();
		return rootView;
	}

	private void initData() {
		userInfoBiz = (UserInfoBiz) CsqManger.getInstance().get(Type.USERINFOBIZ);
		left = (ImageView) rootView.findViewById(R.id.left);
		// TODO right2消息图片
		left.setOnClickListener(this);
		design_type = (LinearLayout) rootView.findViewById(R.id.design_type);
		wdzp = (TextView) rootView.findViewById(R.id.wdzp);
		my_works = (LinearLayout) rootView.findViewById(R.id.my_works);
		become_stylist = (TextView) rootView.findViewById(R.id.become_stylist);
		head_icon = (ImageView) rootView.findViewById(R.id.head_icon);
		// vip_grade = (ImageView) rootView.findViewById(R.id.vip_grade);
		nick_name = (TextView) rootView.findViewById(R.id.nick_name);
		identity = (TextView) rootView.findViewById(R.id.identity);
		attentionNum = (TextView) rootView.findViewById(R.id.attentionNum);
		goods_fans = (LinearLayout) rootView.findViewById(R.id.goods_fans);
		attentionNum = (TextView) rootView.findViewById(R.id.attentionNum);
		goods = (TextView) rootView.findViewById(R.id.goods);
		
		nick_name.setText("编辑您的昵称");

		((TextView) rootView.findViewById(R.id.onsell)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.crowd_funding)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.issue)).setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.my_order)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.participantZC)).setOnClickListener(this);

		my_fond = ((TextView) rootView.findViewById(R.id.my_fond));
		my_collection = ((TextView) rootView.findViewById(R.id.my_collection));
		my_attention = ((TextView) rootView.findViewById(R.id.my_attention));
		my_fond.setOnClickListener(this);
		my_collection.setOnClickListener(this);
		my_attention.setOnClickListener(this);

		become_stylist.setOnClickListener(this);

		((TextView) rootView.findViewById(R.id.address_manage)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.customer_service)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.set)).setOnClickListener(this);
		((TextView) rootView.findViewById(R.id.channel_expand)).setOnClickListener(this);
		((RelativeLayout) rootView.findViewById(R.id.rl0)).setOnClickListener(this);

		new CsqRunnable(GETUSERINFO).start();
		// TODO 判断身份
		// if ("stylist".equals(state)) {
		// design_type.setVisibility(View.VISIBLE);
		// wdzp.setVisibility(View.VISIBLE);
		// my_works.setVisibility(View.VISIBLE);
		// goods_fans.setVisibility(View.VISIBLE);
		goods.setText("商品 53");
		attentionNum.setText("粉丝 269");
		// become_stylist.setVisibility(View.GONE);
		// vip_grade.setVisibility(View.VISIBLE);
		// } else if ("brand".equals(state)) {
		// vip_grade.setVisibility(View.INVISIBLE);
//		 design_type.setVisibility(View.VISIBLE);
//		 wdzp.setVisibility(View.VISIBLE);
//		 my_works.setVisibility(View.VISIBLE);
//		 goods_fans.setVisibility(View.VISIBLE);
//		 become_stylist.setVisibility(View.GONE);
		// } else {
		// vip_grade.setVisibility(View.INVISIBLE);
//		design_type.setVisibility(View.GONE);
//		wdzp.setVisibility(View.GONE);
//		my_works.setVisibility(View.GONE);
//		goods_fans.setVisibility(View.GONE);
//		become_stylist.setVisibility(View.VISIBLE);
		// }
	}

	protected void initUserInfo() {
		// imageLoader.displayImage(userBiz.getCurrentUser().getHead_pic(),
		// iv_head_pic, options);
		// String name = StringUtil.isNull(user.getNick_name()) ?
		// user.getUser_name() : user.getNick_name();
		// if (StringUtil.isNull(name)) {
		// name = hidePhoneNumber(user.getPhone());
		// }
		// nick_name.setText(user.getUser_name()+user.getNick_name());
		// identity.setText("设计师"+user.getPhone());
	}

	@Override
	protected Object doInBackground(int operate, Object... objs) throws CsqException {
		if (GETUSERINFO == operate) {
			// return userInfoBiz.getUserInfo(User_id);
		}
		return null;
	}

	@Override
	protected boolean handleResult(boolean result, int operate, Object obj) {
		if (result && GETUSERINFO == operate) {
			labelList = new ArrayList<>();
			labelList.add("玩具设计");
			labelList.add("平面设计");
			labelList.add("模型设计");
			labelList.add("玩具设计");
			labelList.add("平面设计");
			labelList.add("模型设计");
			for(String label:labelList){
				TextView view = (TextView)View.inflate(getActivity(), R.layout.mine_ll_item, null);
				view.setText(label);
				design_type.addView(view);
			}
			// UserInfo userInfo = (UserInfo) obj;
			// state = userInfo.getIdentity();
			// imageLoader.displayImage(userInfo.getPic(), head_icon, options);
			// nick_name.setText(userInfo.getName());
			// my_fond.setText("我的喜欢" + userInfo.getMy_fond());
			// my_collection.setText("我的收藏" + userInfo.getMy_collection());
			// my_attention.setText("我的关注" + userInfo.getMy_attention());
			// if ("stylist".equals(state)) {// 设计师
			// String vip_grade2 = userInfo.getVip_grade();
			// if (getVip(vip_grade2) != 0) {
			// vip_grade.setImageResource(getVip(vip_grade2));
			// } else {
			// vip_grade.setImageResource(R.drawable.xiaoxin002);
			// }
			// identity.setText(userInfo.getIdentity());
			// attentionNum.setText(userInfo.getAttentionNum() + "人关注");
			// } else if ("brand".equals(state)) {// 品牌商
			// identity.setText(userInfo.getIdentity());
			// attentionNum.setText(userInfo.getAttentionNum() + "人关注");
			// }
		}
		return false;
	}

	private int getVip(String vip_grade2) {
		if ("1".equals(vip_grade2)) {
			return R.drawable.mengdian1;
		} else if ("2".equals(vip_grade2)) {
			return R.drawable.mengdian1;
		} else if ("3".equals(vip_grade2)) {
			return R.drawable.mengdian1;
		} else if ("4".equals(vip_grade2)) {
			return R.drawable.mengdian1;
		} else if ("5".equals(vip_grade2)) {
			return R.drawable.mengdian1;
		}
		return 0;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.left:
			// TODO 跳转消息界面
			intent.setClass(getActivity(), NoticeActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.rl0:
			// TODO 跳转设置资料界面
			intent.setClass(getActivity(), SetMyDataActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.onsell:
			// TODO 在售商品
			intent.setClass(getActivity(), OnsellActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.crowd_funding:
			// TODO 众筹商品
			intent.setClass(getActivity(), CrowdFundingGoodsActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.issue:
			// TODO 发布作品
			intent.setClass(getActivity(), IssueActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_order:
			// TODO 我的订单
			intent.setClass(getActivity(), MyOrderActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.participantZC:
			// TODO 参与的众筹
			intent.setClass(getActivity(), CrowdFundingActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_fond:
			// TODO 我的喜欢
			intent.setClass(getActivity(), MyLoveActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_collection:
			// TODO 我的收藏
			intent.setClass(getActivity(), MyCollectActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_attention:
			// TODO 我的关注
			intent.setClass(getActivity(), MyAttentionActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.become_stylist:
			// TODO 成为设计师
			intent.setClass(getActivity(), BecomeStylistActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.address_manage:
			// TODO 地址管理
			intent.setClass(getActivity(), MyAddressActivity.class);
			intent.putExtra("isSee", true);
			getActivity().startActivity(intent);
			break;
		case R.id.customer_service:
			// TODO 客服咨询
			intent.setClass(getActivity(), CustomerActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.set:
			// TODO 设置
			intent.setClass(getActivity(), SetActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.channel_expand:
			// TODO 渠道拓展
			intent.setClass(getActivity(), ChannelExpandActivity.class);
			getActivity().startActivity(intent);
			break;

		default:
			break;
		}

	}
}
