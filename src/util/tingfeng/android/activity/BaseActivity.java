package util.tingfeng.android.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

public abstract class BaseActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityPool.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
	}
	/**
	 * 返回true表示双击退出;
	 * @return
	 */
	public abstract boolean isExitBy2Click();
	
	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	 // TODO Auto-generated method stub
	 if(isExitBy2Click()&&keyCode == KeyEvent.KEYCODE_BACK)
	{ 
	  exitBy2Click(); //调用双击退出函数
	}
	 return false;
	}
	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;	 
	private void exitBy2Click() {
	 Timer tExit = null;
	 if (isExit == false) {
	 isExit = true; // 准备退出
	 Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	 tExit = new Timer();
	 tExit.schedule(new TimerTask() {
	  @Override
	  public void run() {
	  isExit = false; // 取消退出
	  }
	 }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
	 
	 } else {
	  ActivityPool.getInstance().exit();
      //finish();
	 //System.exit(0);
	 }
	}
	/**
	 * 显示长提醒信息,会自动运行在ui线程
	 * @param msg
	 */
	public final void ShowToast(final String msg) {		
		this.ShowToast(msg, Toast.LENGTH_LONG);
	}
	
	/**
	 * 
	 * @param manage
	 * @param duration 1表示长提示,0表示短提示,会自动运行在ui线程
	 */
	public final void ShowToast(final String msg,final int duration) {
		runOnUiThread(new Runnable() {			
			@Override
			public void run() {	
		Toast toast = Toast.makeText(getApplicationContext(), msg,
					duration);
			toast.setGravity(Gravity.CENTER, 0, 0);// 居中显示
			toast.show();
			}
			}
		);
		}
}
