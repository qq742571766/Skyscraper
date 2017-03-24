package wyf.tzz.gdl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SoundSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {

	GL_Demo myActivity;

	Paint paint;
	int screenWidth = 320;//��Ļ���
	int screenHeight = 480;//��Ļ�߶�
	int hintWidth=100;//��ʾ���
	int hintHeight=20;//��ʾ�߶�
	
	Bitmap soundYN;//�Ƿ��������ʾ�ı�־
	Bitmap soundYes;//������
	Bitmap soundNo;//�ر�����
	Bitmap background;//����

	public SoundSurfaceView(GL_Demo myActivity) {
		super(myActivity);
		this.myActivity=myActivity;
		initBitmap();
		this.getHolder().addCallback(this);
	}
	public void initBitmap()
	{ //��ʼ��ͼƬ
		soundYN=BitmapFactory.decodeResource(getResources(), R.drawable .soundyn);
		soundYes=BitmapFactory.decodeResource(getResources(), R.drawable.soundyes);
		soundNo=BitmapFactory.decodeResource(getResources(), R.drawable.soundno);
		background=BitmapFactory.decodeResource(getResources(), R.drawable.sound_bg);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		//����������ʾ		
		canvas.drawBitmap(background, 0, 0, null);//����
		canvas.drawBitmap(soundYN,screenWidth/2-hintWidth/2,screenHeight/2-hintHeight/2, null);
		canvas.drawBitmap(soundYes, screenWidth-32, screenHeight-16,null);//���ƴ���Ч
		canvas.drawBitmap(soundNo, 0, screenHeight-16,null);//���ƹر���Ч                                                                                                                                                                                                                                                                                                                             
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();//�����Ļ�����ص�x����
		int y = (int) event.getY();//�����Ļ�����ص�y����
		
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		//������
			if(x<screenWidth&&x>screenWidth-32
		    	  &&y<screenHeight&&y>screenHeight-16)
			{//����
				myActivity.isSound=true;
				myActivity.mpBack.start();//������Ϸ��������
				myActivity.setMenuView();//�л����˵���
			}
			//�ر�����
			if(x<32&&x>0
		  	    	  &&y<screenHeight&&y>screenHeight-16)
			{//����
				myActivity.isSound=false;
				myActivity.mpBack.pause();//��ͣ��Ϸ��������
				myActivity.mpWin.pause();//��ͣ��Ϸʤ������
				myActivity.setMenuView();//�л������˵���
			}
			break;
		}
			
		return super.onTouchEvent(event);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		Canvas canvas=null;//��ʼ������
		
		try
		{
			canvas=holder.lockCanvas();//��������
			
			synchronized(holder)
			{
				onDraw(canvas);//�ػ�
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(canvas!=null)
			{
				holder.unlockCanvasAndPost(canvas);//���ƺ���������ƺ�������������ʾ
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	
	 @Override
	   public boolean onKeyDown(int keyCode,KeyEvent event)		//Ϊ������Ӽ���
	   { 
			  switch(keyCode)
			  {     
				   case KeyEvent.KEYCODE_BACK:					//������·��ؼ�
						
				   myActivity.setMenuView();					//�л������˵�����
				
				   return true;
			  }
			  
		   return false;										//false��������������ϵͳ����
	   }
		   
}