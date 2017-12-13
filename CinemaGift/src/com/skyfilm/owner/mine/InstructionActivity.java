package com.skyfilm.owner.mine;

import com.skyfilm.owner.R;
import com.skyfilm.owner.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 版权说明
 * @author min.yuan
 *
 */
public class InstructionActivity extends BaseActivity{
	private TextView instruction;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_instruction);
	instruction = (TextView) findViewById(R.id.instruction);
//	instruction.setText("");
}
	@Override
	public void initTiltle(ImageView left, TextView title, ImageView right1, TextView right2) {
		super.initTiltle(left, title, right1, right2);
		title.setText("版权说明");
		right1.setVisibility(View.GONE);
		right2.setVisibility(View.INVISIBLE);
	}
}
