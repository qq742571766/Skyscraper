package wyf.tzz.gdl;

import java.util.HashMap;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;


public class GL_Demo extends Activity {
	
	SoundSurfaceView soundsv;//��ʼ��������������
	StartSurfaceView startsv;//��ʼ��ʼ�˵�����������
	LoadSurfaceView loadsv;//��ʼ�����ؽ�������
	HelpSurfaceView helpsv;//��ʼ��������������
	AboutSurfaceView aboutsv;//��ʼ�����ڽ�������
	MySurfaceView msv;//��ʼ����Ϸ��������
	WinSurfaceView winsv;//��ʼ��ʤ����������
	FailSurfaceView failsv;//��ʼ��ʧ�ܽ�������
	
	static final int START_GAME=0;//���ز���ʼ��Ϸ��Message���
	Handler hd;//��Ϣ������
	MediaPlayer mpBack;//��Ϸ�������ֲ�����
	MediaPlayer mpWin;//��ϷӮ���ֲ�����
	MediaPlayer mpFail;
	SoundPool soundPool;//������
	HashMap<Integer, Integer> soundPoolMap; //������������ID���Զ�������ID��Map
	boolean isSound=false;	//�Ƿ���������
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //����ֻ��������
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //ȫ��
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);      
        
        soundsv = new SoundSurfaceView(this);//ʵ����������������
        startsv = new StartSurfaceView(this);//ʵ������ʼ�˵���������
        msv = new MySurfaceView(this);//ʵ��������������
        helpsv=new HelpSurfaceView(this);//ʵ����������������
        aboutsv=new AboutSurfaceView(this);//ʵ�������ڽ�������
        loadsv=new LoadSurfaceView(this);//ʵ�������ؽ�������
        winsv=new WinSurfaceView(this);//ʵ����ʤ����������
        failsv=new FailSurfaceView(this);
        initSound();
        setSoundView();
     
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(msv!=null)
        {
        	msv.onResume();//��Ϸ����ָ�
        	if(isSound)
        	{
	        	if(msv.isWin)
	        	{
	        		mpWin.start();//����ʤ������
	        	}
	        	else
	        	{
	        		mpBack.start();//���ű�������
	        	}    
        	}
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(msv!=null)
        {
        	msv.onPause();//��Ϸ������ͣ
        }
        if(mpBack!=null)
		{
			mpBack.pause();//����������ͣ
		}
		if(mpWin!=null)
		{
			mpWin.pause();//ʤ��������ͣ
		}
		if(mpFail!=null)
		{
			mpFail.pause();
		}
    }  
    public void setMySurfaceView(){
    	msv.requestFocus();//��ȡ����
        msv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
    	setContentView(msv);//�л���������Ϸ����
    }
    public void setSoundView()
    {
    	soundsv.requestFocus();//��ȡ����
    	soundsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
    	setContentView(soundsv);//�л��������������ý���    	
    }
    public void setMenuView()
    {
    	startsv.requestFocus();//��ȡ����
    	startsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
    	setContentView(startsv);//�л���������ʼ�˵�����
    }
    public void setLoadView()
    {
    	 //��ʾ��Ϸ���ؽ���
    	setContentView(loadsv);  
    	
    	hd=new Handler()//��ʼ����Ϣ������
        {
        	@Override
        	public void handleMessage(Message msg)
        	{
        		super.handleMessage(msg);
        		switch(msg.what)
        		{
        		   case START_GAME:      
        			   setMySurfaceView();
        		   break; 
        		}
        	}
        };
		new Thread()
		{
			@Override
			public void run()
			{
				try {
					Thread.sleep(1500);		//�ȴ�2s
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}   	
				//����Ϣ������Ϸ
				hd.sendEmptyMessage(START_GAME);
			}
		}.start();
    }
    public void setAboutView()
    {
    	aboutsv.requestFocus();//��ȡ����
    	aboutsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
    	setContentView(aboutsv);//�л����������ڽ���
    }
    public void setHelpView()
    {
    	helpsv.requestFocus();//��ȡ����
    	helpsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
    	setContentView(helpsv);//�л���������������
    }
    public void setWinView()
    {
         winsv.requestFocus();//��ȡ����
         winsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
         setContentView(winsv);//�л�������ʤ������
    }
    public void setFailView()
    {
    	  failsv.requestFocus();//��ȡ����
          failsv.setFocusableInTouchMode(true);//����Ϊ�ɴ���
          setContentView(failsv);//�л�������ʧ�ܽ���
    }
    
    
    public void initSound()
    {
    	//��������
    	mpBack = MediaPlayer.create(this, R.raw.background); 
    	mpBack.setLooping(true);    
    	
    	//Ӯ����
    	mpWin= MediaPlayer.create(this, R.raw.win); 
    	mpWin.setLooping(true);
    	
    	//������
    	mpFail=MediaPlayer.create(this, R.raw.failbk);
    	mpWin.setLooping(true);
    			
		//������
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	    soundPoolMap = new HashMap<Integer, Integer>();   
	    
	    //�ɹ���������
	    soundPoolMap.put(1, soundPool.load(this, R.raw.success, 1));
	    
	    //����ʧ������
	    soundPoolMap.put(2, soundPool.load(this, R.raw.fail, 1));
    }
    
    public void playSound(int sound, int loop) 
    {
	    AudioManager mgr = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);   
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       
	    float volume = streamVolumeCurrent / streamVolumeMax;   
	    
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);
	}
    
}