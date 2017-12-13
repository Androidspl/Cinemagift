package com.skyfilm.owner.widget;

import java.lang.reflect.Method;
import java.util.List;

import com.skyfilm.owner.MainApplication;
import com.skyfilm.owner.R;
import com.skyfilm.owner.utils.StringUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyDialog  {
	private static int width;
	private static int height;
	private static TextView tipText;
	private static TextView contentTextView;
	private static Button rightButton;
	private static Button leftButton;
	private Dialog dialog;
	/**
	 * 自定义对话框
	 * @param context
	 * @param title
	 * @param desc
	 * @param leftText
	 * @param rightText
	 * @param left
	 * @param right
	 * @return
	 */
	public static Dialog show(final Context context,String title,
			String desc, String leftText, String rightText,final OnClickListener left,final OnClickListener right) {
		return show(context, title, desc, leftText, rightText, true, left, right,null,null);
	}
	public static Dialog show(final Context context,String title,
			String desc, String leftText, String rightText,boolean canCancle,final OnClickListener left,final OnClickListener right) {
		return show(context, title, desc, leftText, rightText, canCancle, left, right,null,null);
	}
	public static Dialog show(final Context context,String title,
			String desc, String leftText, String rightText,boolean canCancle,final OnClickListener left,final OnClickListener right,OnShowListener onShowListener,OnDismissListener onDismissListener) {

		final Dialog dialog =new Dialog(context, R.style.MyDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.my_dialog_layout, null);	
		tipText = (TextView) view.findViewById(R.id.title); 
		if(StringUtil.isNull(title)){
			tipText.setVisibility(View.GONE);
		}else{
			tipText.setText(title);
		}
		contentTextView = (TextView) view.findViewById(R.id.dialog_text_content);
		if(!StringUtil.isNull(desc)){
			contentTextView.setVisibility(View.VISIBLE);
			contentTextView.setText(desc);
		}
		rightButton = (Button) view.findViewById(R.id.dialog_button_cancel);
		leftButton = (Button) view.findViewById(R.id.dialog_button_ok);	
		if(leftText==null&&rightText==null){
			contentTextView .setTextColor(context.getResources().getColor(R.color.text_green));
			rightButton.setVisibility(View.GONE);
			leftButton.setVisibility(View.GONE);
		}else if(StringUtil.isNull(leftText)){
			rightButton.setVisibility(View.GONE);
		}

		rightButton.setText(rightText);
		leftButton.setText(leftText);
		// 对话框取消事件处理
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if(left != null){
						left.onClick(leftButton);
					}

				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});
		// 对话框确定事件处理
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if(right != null){
						right.onClick(rightButton);
					}
				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});
		dialog.setCancelable(canCancle);
		dialog.setContentView(view);
		dialog.setOnShowListener(onShowListener);
		dialog.setOnDismissListener(onDismissListener);
		dialog.show();
		return dialog;
	}
	/**
	 * 只显示对话框title和content
	 * @param context
	 * @param title
	 * @param content
	 * @return
	 */
	
	public static Dialog show(Context context, String title,
			String content) {
		return show(context, title, content, null, null, null, null);
	}

	public static Dialog show(final Context context,String title,
			View contentView, String leftText, String rightText,final OnClickListener left,final OnClickListener right,OnDismissListener disListener) {

		ViewGroup group = null;
		try {
			group  = (ViewGroup) contentView.getParent();
		} catch (Exception e) {
		}
		if(group != null){
			group.removeView(contentView);
		}
		final Dialog dialog =new Dialog(context, R.style.MyDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.my_dialog_layout, null);	
		tipText = (TextView) view.findViewById(R.id.title);
		if(StringUtil.isNull(title)){
			tipText.setVisibility(View.GONE);
		}else{
			tipText.setText(title);
		}
		RelativeLayout content = (RelativeLayout) view.findViewById(R.id.dialog_content);
		RelativeLayout.LayoutParams pm = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		/*content.removeView(contentView);
		if(content.getChildCount()>1){
			content.removeViewAt(0);
		}*/

		content.addView(contentView,pm);
		contentTextView = (TextView) view.findViewById(R.id.dialog_text_content);
		rightButton = (Button) view.findViewById(R.id.dialog_button_cancel);
		leftButton = (Button) view.findViewById(R.id.dialog_button_ok);	
		if(leftText==null&&rightText==null){
			contentTextView .setTextColor(context.getResources().getColor(R.color.text_green));
			rightButton.setVisibility(View.GONE);
			leftButton.setVisibility(View.GONE);
		}else if(StringUtil.isNull(leftText)){
			rightButton.setVisibility(View.GONE);
		}
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					if(right != null){
						right.onClick(rightButton);
					}

				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});

		if(leftText==null&&rightText==null){
			contentTextView .setTextColor(context.getResources().getColor(R.color.text_green));
			rightButton.setVisibility(View.GONE);
			leftButton.setVisibility(View.GONE);
		}else if(StringUtil.isNull(rightText)){
			rightButton.setVisibility(View.GONE);
		}
		leftButton.setText(leftText);
		rightButton.setText(rightText);
		// 对话框确定事件处理
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if(left != null){
						left.onClick(rightButton);
					}
				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});
		if(disListener!=null){
			dialog.setOnDismissListener(disListener);
		}
		dialog.setContentView(view);		 
		dialog.show();
		return dialog;
	}
	public static Dialog show(final Context context,String title,
			View contentView, String leftText, String rightText,OnClickListener left,OnClickListener right) {
		return show(context, title, contentView, leftText, rightText, left, right, null);
	}

	public static Dialog show(final Context context,
			String desc,int contentRes, String leftText,int leftRes, String rightText,int rightRes,final OnClickListener left,final OnClickListener right
			){
		return show(context, desc, contentRes, leftText, leftRes, rightText, rightRes, left, right,null,null);
	}
	public static Dialog show(final Context context,
			String desc,int contentRes, String leftText,int leftRes, String rightText,int rightRes,final OnClickListener left,final OnClickListener right
			,DialogInterface.OnShowListener onShow,DialogInterface.OnDismissListener onDismiss
			) {
		final Dialog dialog =new Dialog(context, R.style.MyDialog);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.dialog_with_pic, null);	

		ImageView contentImageView = (ImageView)view.findViewById(R.id.dialog_image_content);
		if(contentRes >-1){
			contentImageView.setImageResource(contentRes);
		}
		contentTextView = (TextView) view.findViewById(R.id.dialog_text_content);
		if(!StringUtil.isNull(desc)){
			contentTextView.setVisibility(View.VISIBLE);
			contentTextView.setText(desc);
		}
		final View rightButton = view.findViewById(R.id.dialog_right_Button);
		final View leftButton =  view.findViewById(R.id.dialog_left_Button);	
		TextView rightTextView = (TextView) view.findViewById(R.id.dialog_right_Text);
		TextView leftTextView = (TextView) view.findViewById(R.id.dialog_left_Text);
		rightTextView.setText(rightText);
		leftTextView.setText(leftText);
		Drawable leftD = null,rightD = null;
		if(leftRes != -1){
			leftD = context.getResources().getDrawable(leftRes);
			leftD.setBounds(0, 0, leftD.getMinimumWidth(), leftD.getMinimumHeight());  
		}
		if(rightRes != -1){
			rightD = context.getResources().getDrawable(rightRes);
			rightD.setBounds(0, 0, rightD.getMinimumWidth(), rightD.getMinimumHeight());  
		}
		leftTextView.setCompoundDrawables(leftD, null, null, null);
		rightTextView.setCompoundDrawables(rightD, null, null, null);
		// 对话框取消事件处理
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if(left != null){
						left.onClick(leftButton);
					}

				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});
		// 对话框确定事件处理
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					if(right != null){
						right.onClick(rightButton);
					}
				} catch (Exception e) {
				}finally{
					dialog.dismiss();
				}
			}
		});
		dialog.setCancelable(false);
		dialog.setContentView(view);
		if(onShow!=null)
			dialog.setOnShowListener(onShow);
		if(onDismiss!=null)
			dialog.setOnDismissListener(onDismiss);
		dialog.show();
		return dialog;
	}

	public static <T> Dialog show(final Context context,String title,List<T> data,Object2String<T> object2String,final OnItemClickListener listener){
		final Dialog dialog = new Dialog(context, R.style.MyDialog);
		dialog.setContentView(R.layout.dialog);
		ListView listView = (ListView) dialog.findViewById(R.id.lv_dialog);
		TextView titleView = (TextView) dialog.findViewById(R.id.wintitle);
		titleView.setText(title);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);

		listView.setAdapter(new DialogListAdapter<T>(context,data, object2String));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(listener != null){
					try {
						listener.onItemClick(parent, view, position, id);
					} catch (Exception e) {
					}
				}
				dialog.dismiss();
			}
		});
		dialog.findViewById(R.id.winClose).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
		return dialog;
	}


	public interface Object2String<T>{
		String object2String(T t);
	}

	private static class DialogListAdapter<T> extends BaseAdapter{
		List<T> data;
		Object2String<T> object2String;
		Context context;

		public DialogListAdapter(Context context,List<T> data, Object2String<T> object2String) {
			super();
			this.data = data;
			this.context = context;
			this.object2String = object2String;
		}

		@Override
		public int getCount() {

			return data==null?0:data.size();
		}

		@Override
		public T getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = null;
			if(convertView == null)convertView= View.inflate(context, R.layout.choose_space_dialog_item, null);
			if(object2String == null){
				((TextView)convertView).setText(getItem(position).toString());
			}else{
				((TextView)convertView).setText(object2String.object2String(getItem(position)));
			}
			return convertView;
		}

	}

	private static void  getDpi(){
		WindowManager wm = (WindowManager) MainApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics(); 
		@SuppressWarnings("rawtypes")
		Class cla;
		try {
			cla = Class.forName("android.view.Display");
			@SuppressWarnings("unchecked")
			Method method = cla.getMethod("getRealMetrics",DisplayMetrics.class);
			method.invoke(display, dm);
			width  = dm.widthPixels;
		}catch(Exception e){
		}  
	}

	public static Dialog showButtomDialog(Context c,View v){
		Window win;
		Dialog dialog = new Dialog(c, R.style.MyButtomDialog);
		dialog.setContentView(v);
		dialog.show();
		win = dialog.getWindow();
		win.setWindowAnimations(R.style.dialogButtomAnim);
		win.setGravity(Gravity.BOTTOM);
		win.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.FILL_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		win.setAttributes(lp);
		return dialog;
	}
	
}
