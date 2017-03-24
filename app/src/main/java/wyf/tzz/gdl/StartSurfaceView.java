package wyf.tzz.gdl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class StartSurfaceView extends SurfaceView
implements SurfaceHolder.Callback {

	GL_Demo myActivity;

	int screenWidth = 320;//��Ļ���
	int screenHeight = 480;//��Ļ�߶�
	int picWidth=128;//ͼƬ��
	int picHeight=32;//ͼƬ��
	int blankWidth=0;//����
	int blankHeight=220;//�߶�����
	int unit_blankHeight=5;//ͼƬ֮��߶�����
	
	final int KSMS_VIEW=0;//����ģʽ
	final int OPTION_VIEW=1;//����
	final int ABOUT_VIEW=2;//����
	final int HELP_VIEW=3;//����
	final int EXIT_VIEW=4;//�˳�
	
	Bitmap background;//����
	Bitmap ksms;//����ģʽ
	Bitmap gameOption;//����
	Bitmap gameExit;//�Ƴ�
	Bitmap gameAbout;//����
	Bitmap gameHelp;//����
	//δѡ��ʱ��ťͼƬ
	Bitmap ksmsShort;//����ģʽ
	Bitmap gameOptionShort;//����
	Bitmap gameExitShort;//�Ƴ�
	Bitmap gameAboutShort;//����
	Bitmap gameHelpShort;//����
	//ѡ��ʱ��ťͼƬ
	Bitmap ksmsLong;//����ģʽ
	Bitmap gameOptionLong;//����
	Bitmap gameExitLong;//�Ƴ�
	Bitmap gameAboutLong;//����
	Bitmap gameHelpLong;//����
	
	int index=KSMS_VIEW;
	
	boolean soundActivity=true;//����������ʾ�Ľ���ı�־
	boolean soundMarker=false;//�Ƿ������
	
	public StartSurfaceView(GL_Demo myActivity) {
		super(myActivity);		//
		this.myActivity=myActivity;
		initBitmap();			//����initBitmap����
		
		this.getHolder().addCallback(this);//ע��ص��ӿ�
		
		shiftState(index);	//����shiftState����
		
	}
	public void initBitmap()
	{ 
		background=BitmapFactory.decodeResource(getResources(), R.drawable.startbj);//��ʼ������
		ksmsShort=BitmapFactory.decodeResource(getResources(), R.drawable.ksms);//����ģʽ
		gameOptionShort=BitmapFactory.decodeResource(getResources(), R.drawable.option);//����
		gameExitShort=BitmapFactory.decodeResource(getResources(), R.drawable.exit);//�˳�
		gameAboutShort=BitmapFactory.decodeResource(getResources(), R.drawable.about);//����
		gameHelpShort=BitmapFactory.decodeResource(getResources(), R.drawable.help);//����
		
		ksmsLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_ksms);//����ģʽ
		gameOptionLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_option);//����
		gameExitLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_exit);//�˳�
		gameAboutLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_about);//����
		gameHelpLong=BitmapFactory.decodeResource(getResources(), R.drawable.long_help);//����
	}
	
	public void shiftState(int index)
	{
		ksms=ksmsShort;//���ÿ���ģʽ��ͼƬ��ťΪ��ͼƬ
		gameOption=gameOptionShort;//�������õ�ͼƬ��ťΪ��ͼƬ
		gameAbout=gameAboutShort;//���ù��ڵ�ͼƬ��ťΪ��ͼƬ
		gameHelp=gameHelpShort;//���ð�����ͼƬ��ťΪ��ͼƬ
		gameExit=gameExitShort;//�����˳���ͼƬ��ťΪ��ͼƬ
		
		//��ѡ����ť�䳤
		 switch(index)
		  {
			  case KSMS_VIEW:	//ѡ�п���ģʽ
				  ksms=ksmsLong;//���ÿ���ģʽͼƬΪ��ͼƬ
				  break;
			   case OPTION_VIEW://ѡ������ģʽ
				   gameOption=gameOptionLong;//�������õ�ͼƬ��ťΪ��ͼƬ
				   break;
			   case ABOUT_VIEW://ѡ�й���ģʽ
				   gameAbout=gameAboutLong;//���ù��ڵ�ͼƬ��ťΪ��ͼƬ
				   break;
			   case HELP_VIEW://ѡ�а���ģʽ
				   gameHelp=gameHelpLong;////���ð�����ͼƬ��ťΪ��ͼƬ
				   break;
			   case EXIT_VIEW://ѡ���˳�ģʽ
				   gameExit=gameExitLong;//�����˳���ͼƬ��ťΪ��ͼƬ
				   break;
		  }
		 //�ػ�
		 Canvas canvas=null;			//	����
		 SurfaceHolder holder=this.getHolder();//���岢ʵ����SurfaceHolder����
			
			try
			{
				canvas=holder.lockCanvas();//��������
				
				synchronized(holder)//��holder����
				{
					onDraw(canvas);//�ػ滭��
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
	public void onDraw(Canvas canvas)//�ػ�
	{
		canvas.drawBitmap(background, 0, 0, null);//���Ʊ���ͼƬ
		canvas.drawBitmap(ksms, blankWidth, blankHeight,null);//���ƿ���ģʽ��ťͼƬ
		canvas.drawBitmap(gameOption,blankWidth,blankHeight+1*(picHeight+unit_blankHeight),null);//����
		canvas.drawBitmap(gameAbout, blankWidth, blankHeight+2*(picHeight+unit_blankHeight),null);//����
		canvas.drawBitmap(gameHelp, blankWidth, blankHeight+3*(picHeight+unit_blankHeight),null);//����
		canvas.drawBitmap(gameExit, blankWidth, blankHeight+4*(picHeight+unit_blankHeight),null);//�˳�
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();//�����Ļx����
		int y = (int) event.getY();//�����Ļy����
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight&&
					y<blankHeight+picHeight)
			{//����ģʽ��ť
				index=KSMS_VIEW;
				shiftState(index);		//����ť�л���ѡ��״̬
				shiftView(index);		//�������л���ѡ������
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+1*(picHeight+unit_blankHeight)&&
					y<blankHeight+1*(picHeight+unit_blankHeight)+picHeight	
			)
			{//���ð�ť
				index=OPTION_VIEW;
				shiftState(index);		//����ť�л���ѡ��״̬
				shiftView(index);		//�������л���ѡ������
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+2*(picHeight+unit_blankHeight)&&
					y<blankHeight+2*(picHeight+unit_blankHeight)+picHeight)
			{//���ڰ�ť
				index=ABOUT_VIEW;
				shiftState(index);		//����ť�л���ѡ��״̬
				shiftView(index);		//�������л���ѡ������
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+3*(picHeight+unit_blankHeight)&&
					y<blankHeight+3*(picHeight+unit_blankHeight)+picHeight)
			{//������ť
				index=HELP_VIEW;
				shiftState(index);		//����ť�л���ѡ��״̬
				shiftView(index);		//�������л���ѡ������
			}
			else if
			(
					x>blankWidth&&
					x<blankWidth+picWidth&&
					y>blankHeight+4*(picHeight+unit_blankHeight)&&
					y<blankHeight+4*(picHeight+unit_blankHeight)+picHeight)
			{//�˳���ť
				System.exit(0);
			}
			
			break;
		}
		return super.onTouchEvent(event);
	}
	 @Override
	 public boolean onKeyDown(int keyCode,KeyEvent event)
	   {
			  switch(keyCode)
			  {
				  case KeyEvent.KEYCODE_DPAD_CENTER:
					  //�л���ָ������ 
					  shiftView(index);
					   return true;
				   case KeyEvent.KEYCODE_DPAD_DOWN:
					   //ѡ����һ����ť
					   index=(index+1)%5;//index��0-5֮��
					   shiftState(index);//����shiftState�������л���ťͼƬ
					   return true;
				   case KeyEvent.KEYCODE_DPAD_UP:
					   //ѡ����һ����ť
					   index=(index-1+5)%5;//index��1-5֮��任
					   shiftState(index);//����shiftState�������л���ťͼƬ
					   return true;
			  }
		   return false;
	   }
	 //�л�����
	 public void shiftView(int index)
	 {
		 switch(index)
		  {
			  case KSMS_VIEW:
				  myActivity.setLoadView();//���ؽ���
				  break;
			   case OPTION_VIEW:
				   myActivity.setSoundView();//���ý���
				   break;
			   case ABOUT_VIEW:
				   myActivity.setAboutView();//���ڽ���
				   break;
			   case HELP_VIEW:
				   myActivity.setHelpView();//��������
				   break;
			   case EXIT_VIEW:
				   System.exit(0);//�˳�����
				   break;
		  }
	 }
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	 @Override
	public void surfaceCreated(SurfaceHolder holder) {
	
		shiftState(index);	//����shiftState����
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}