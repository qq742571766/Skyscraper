package wyf.tzz.gdl;

import java.io.IOException;

import java.io.InputStream;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static wyf.tzz.gdl.Constant.*;

class MySurfaceView extends GLSurfaceView {
	
	GL_Demo activity;	//Activity
	
	int objectCount=0;	//�÷�
	
	int failCount=0;	//ʧ�ܴ���
	
	boolean isWin=false;//�Ƿ�ʤ�����
	
	boolean isFail=false;//�Ƿ�ʧ�ܱ��
	
	boolean beginFlag=false;		//�����߳̿�ʼ�ı�־��trueΪ��ʼ
		
	float targetPointY=(BASE_HEIGHT+LINE_OFF_BOX)*UNIT_SIZE;	//�������꣬Ŀ���y����

    float cx=-8f;					//�����x����
    float cy=targetPointY+1f;		//�����y����
    float cz=18;					//�����z����
  
    float tx=0;						//�۲�Ŀ���x����  
    float ty=targetPointY;			//�۲�Ŀ���y����
    float tz=0;						//�۲�Ŀ���z����   

    private SceneRenderer mRenderer;//������Ⱦ��
    
    float width;					//��Ļ���
    float height;					//��Ļ�߶�
    float ratio;					//��Ļ��߱�
    
    Handler hd;
    
    ActionThread actionThread;		//�����̣߳����Ӱڶ�����������
   
    
	public MySurfaceView(Context context) {
        super(context);
        
        activity=(GL_Demo)context;
        
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
        

        hd=new Handler()			//��ʼ����Ϣ������
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		
        		switch(msg.what)		
        		{
        		   case 0:     	//�ﵽӮ��Ŀ������
    			   
        			   isWin=true;			//��ʤ����־��Ϊfalse
    			   
	    			   beginFlag=false;		//�ö����߳�ֹͣ   
	                   	
	                   	if(activity.isSound)	//�Ƿ��������  
	                   	{	                   	
	                   		activity.mpWin.start();//����ʤ������
	                   		
	                   		activity.mpBack.pause();//��������ֹͣ
	                   	}
	                   	
	                   	activity.setWinView();  //�л���ʤ���Ļ���         
	                   	
        		   break; 
        		   
        		   case 1:
        			   isFail=true;
        			   
        			   beginFlag=false;		//�ö����߳�ֹͣ
        			   
        			   if(activity.isSound)	//�Ƿ��������  
	                   	{	                   	
	                   		activity.mpFail.start();//����ʤ������
	                   		
	                   		activity.mpBack.pause();//��������ֹͣ
	                   	}
        			   
        			   activity.setFailView();  //�л���ʧ�ܵĻ���       
        			   
        			   
        		}
        	}
        };
        
   	
		new Thread()						//����һ���µ��̣߳������Ƿ�ʤ��
		{
			@Override
			public void run()
			{
				while(!isWin&&!isFail)		//�����û��ʤ��
				{
					//����Ϣ������Ϸ
					if(objectCount==GOAL_COUNT)
					{
						hd.sendEmptyMessage(0);//�������ڵ÷ּ�Ϊ�ɹ�������������
					}
					else if(failCount==GOAL_COUNT)
					{
						hd.sendEmptyMessage(1);//�������ڵ÷ּ�Ϊ�ɹ�������������
					}
					
					try {
						Thread.sleep(200);	//˯��200ms
					} 
					catch (InterruptedException e) {	
						e.printStackTrace();
					}  	
				}
				
			}
		}.start();	//�����߳�
		
    }
	
   @Override
   public boolean onKeyDown(int keyCode,KeyEvent event)		//Ϊ������Ӽ���
   { 
		  switch(keyCode)
		  {  
			   case KeyEvent.KEYCODE_DPAD_CENTER:			//�������OK��
				
					   actionThread.beginDown=true;			//�������־���ӿ�ʼ����
					
					   return true;
					   
			   case KeyEvent.KEYCODE_BACK:			//������·��ؼ�
					
				   beginFlag=false;		//�ö����߳�ֹͣ  
                 
				   activity.msv=new MySurfaceView(activity);
					
					activity.setMenuView();		//�л������˵�����
				
				   return true;
		  }
		  
	   return false;										//false��������������ϵͳ����
	   
   }
	
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        
    	float y = e.getY();		//����x����
        float x = e.getX();		//����y����
   
        switch(e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
        
        if(x>width-BUTTON_WIDTH*UNIT_SIZE*240&&x<width
    			&&y>height-BUTTON_HEIGHT*UNIT_SIZE*240&&y<height)		//�����������OK����
    		{
    			actionThread.beginDown=true;							//���ӿ�ʼ����
    			
    			return true;
    		}
			break;
		}
        
        return super.onTouchEvent(e);				
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 		//����������Ⱦ����
    {   
  	
		Base base;							//��̨
		
		Background bk;						//����
		
		Floor floor;						//����
		
		BoxGroup bg;						//�������ӵ�����
		
		Line line;							//����
	
		Tree tree;							//��
		
		Score score;						//�÷�
		
		TextureRect mOKButton;				//����OK��
	
		
		
        public void onDrawFrame(GL10 gl) {  
        	
        	
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ��������Ȼ���
        	
            gl.glMatrixMode(GL10.GL_MODELVIEW);		//���õ�ǰ����Ϊģʽ����
           
            gl.glLoadIdentity();  					//���õ�ǰ����Ϊ��λ����
  
            ty=targetPointY;						//�۲�Ŀ�������  Y
            
            cy=ty+1f;								//���������
         
            GLU.gluLookAt
            ( 
            		gl, 
            		cx,   							//���������x
            		cy, 							//���������Y
            		cz,  							//���������Z
            		tx, 							//�۲�Ŀ�������  X
            		ty,   							//�۲�Ŀ�������  Y
            		tz,   							//�۲�Ŀ�������  Z
            		0, 
            		1, 								//���������
            		0
            );   

            
    		 //������
    		gl.glPushMatrix();										//������ǰ����
    		
    		gl.glTranslatef(0,BACKGROUND_HEIGHT*UNIT_SIZE/2,0);   	//��Y��ƽ��
    
    		gl.glTranslatef(0,0,-BASE_WIDTH/2-0.5f);				//��z������ƽ��
    		
    		bk.drawSelf(gl);										//������
    		
    		gl.glPopMatrix();										//�ظ�֮ǰ�任����
    		
    		
    		//������
    		gl.glPushMatrix();										//������ǰ����
    		
    		gl.glTranslatef(0, 0,FLOOR_WIDTH*UNIT_SIZE/3);			//��z������ƽ��
    	
    		floor.drawSelf(gl);										//���ذ�
    		
    		gl.glPopMatrix();										//�ظ�֮ǰ�任����
    	
    		//����̨
    		gl.glPushMatrix();										//������ǰ����
    		
    		gl.glTranslatef(0,BASE_HEIGHT*UNIT_SIZE/2,0);			//��x������ƽ��
    		
    		base.drawSelf(gl);										//����̨
    		
    		gl.glPopMatrix();										//�ظ�֮ǰ�任����
    		
    		
    		//���ߺ�����
    		gl.glPushMatrix();    									//������ǰ����
    		
    		bg.drawSelf(gl);										//������
    		
    		line.drawSelf(gl);										//������
    		
    		gl.glPopMatrix();										//�ظ�֮ǰ�任����
    		
    		
    		//����
    		gl.glPushMatrix();										//������ǰ����
    		
    		gl.glTranslatef(1.5f, 0, 0f);							//��x������ƽ��
    		
    		tree.drawSelf(gl);										//����
    		
    		gl.glTranslatef(-3f, 0, 0f);							//��x������ƽ��
    		
    		tree.drawSelf(gl);										//����
    		
    		gl.glPopMatrix();										//�ظ�֮ǰ�任����
    	
    		
    		gl.glLoadIdentity();									//���õ�ǰ����Ϊ��λ����
    		
    		gl.glEnable(GL10.GL_BLEND); 							//�������
           
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA); //����Դ���������Ŀ��������
            
            gl.glPushMatrix();										//������ǰ����
            
            //ƽ�Ƶ����������λ��
            gl.glTranslatef((ratio-BUTTON_WIDTH/2)*UNIT_SIZE, -(1-BUTTON_HEIGHT/2)*UNIT_SIZE,-NEAR);
            
            mOKButton.drawSelf(gl);									//������OK��
            
            gl.glPopMatrix();										//�ظ�֮ǰ�任����
            
            //ƽ�Ƶ����������λ��
            gl.glTranslatef(-(ratio-ICON_WIDTH)*UNIT_SIZE,(1-ICON_HEIGHT)*UNIT_SIZE,-NEAR);
            
            score.drawSelf(gl); 									//���÷�
           
            gl.glDisable(GL10.GL_BLEND); 							//��ֹ���   
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
           
        	gl.glViewport(0, 0, width, height); 					//�����Ӵ���С��λ�� 
        	
            gl.glMatrixMode(GL10.GL_PROJECTION);					//���õ�ǰ����ΪͶӰ����
           
            gl.glLoadIdentity(); 									//���õ�ǰ����Ϊ��λ����
           
            float ratio = (float) width / height; 					//����͸��ͶӰ�ı�������߱�
            
            gl.glFrustumf(-ratio, ratio, -1, 1,NEAR, 100);			//����ͶӰ����
            
            
            //������ߣ���߱�
            MySurfaceView.this.width=width;
            
            MySurfaceView.this.height=height;
            
            MySurfaceView.this.ratio=ratio;       
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            
        	gl.glDisable(GL10.GL_DITHER);							//�رտ����� 
        	
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            
            gl.glClearColor(0,0,0,0);           					//������Ļ����ɫ��ɫRGBA 
           
            gl.glEnable(GL10.GL_DEPTH_TEST); 						//������Ȳ���
           
            gl.glShadeModel(GL10.GL_SMOOTH);					 	//����ƽ��
            
            //��ʼ��ͼƬ����
            int baseId=initTexture(gl,R.drawable.base);				//��̨����ͼƬ
            int boxId=initTexture(gl,R.drawable.box);				//��������ͼƬ
            int floorId=initTexture(gl,R.drawable.floor);			//��������ͼƬ
            int backgroundId=initTexture(gl,R.drawable.background); //��������ͼƬ
            int steelId=initTexture(gl,R.drawable.line);			//��������ͼƬ
            int leafId=initTexture(gl,R.drawable.leaf);				//��Ҷ����ͼƬ
            int trunkId=initTexture(gl,R.drawable.trunk);				//��������ͼƬ
            int numberTexId=initTexture(gl,R.drawable.number);		//�÷���������ͼƬ
            int buttonId=initTexture(gl,R.drawable.okbutton);		//OK������ͼƬ
           
            
            bk=new Background(backgroundId);						//��ʼ������
            
            floor=new Floor(floorId);								//��ʼ������
            
            base=new Base(baseId);									//��ʼ����̨
         
            bg=new BoxGroup(boxId);									//��ʼ�����ӹ�����
         
            line=new Line(LINE_LENGTH,LINE_RADIUS,steelId);			//��ʼ������
            
            tree=new Tree(0.3f,leafId,trunkId);								//��ʼ����
           
            actionThread=new ActionThread(bg,line,MySurfaceView.this); //��ʼ�������߳�
            
            beginFlag=true;											//��ʼ��־��Ϊtrue
            
            actionThread.start();									//���������߳�
    
            score=new Score(numberTexId,MySurfaceView.this);		//��ʼ���÷�
            
            mOKButton=new TextureRect								//��ʼ��OK�����
            (
            	 buttonId,											//OK������ͼƬ
            	 BUTTON_WIDTH*UNIT_SIZE/2,							//OK������
            	 BUTTON_HEIGHT*UNIT_SIZE/2,							//OK���߶�
           		 new float[]										//OK��������������
    	             {
            			 0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
    	             }
             ); 

        }
        
    	public int initTexture(GL10 gl ,int drawableId)
		{
			int[] textures=new int[1];								//�洢����ID
			
			gl.glGenTextures(1, textures, 0);						//��������ID
			
			int currTextureId=textures[0];
			
			gl.glBindTexture(GL10.GL_TEXTURE_2D,currTextureId);		//������ID
			
			InputStream is=getResources().openRawResource(drawableId);//��ʼ��ͼƬ
			
			//�����˲���ʽ
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
	        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
			
			Bitmap bitmapTmp;										//����λͼ
			
			try
			{
				bitmapTmp=BitmapFactory.decodeStream(is);			//ת��Ϊλͼ
				
			}
			finally
			{
				try
				{
					is.close();										//�ر���
				}
				catch(IOException e)
				{
					e.printStackTrace();							//��ӡ�쳣
				}
			}
			
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);//ת��Ϊλͼ
			
			bitmapTmp.recycle();									//�ͷ���Դ
			
			return currTextureId;									//����ͼƬ����ID
		}
	}


}
