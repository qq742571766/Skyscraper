package wyf.tzz.gdl;
import javax.microedition.khronos.opengles.GL10;
import static wyf.tzz.gdl.Constant.*;
//��ʾ��þ�����������
public class Score 
{	
	TextureRect[] numbers=new TextureRect[10];		//���������������
	
	MySurfaceView msv;								//������
	
	public Score(int texId,MySurfaceView msv)
	{
		this.msv=msv;
		
		//����0-9ʮ�����ֵ��������
		for(int i=0;i<10;i++)
		{
			numbers[i]=new TextureRect
            (
            	 texId,								//����ͼƬID
            	 ICON_WIDTH*0.7f/2,					//ͼ����
            	 ICON_HEIGHT*0.7f/2,				//ͼ��߶�
           		 new float[]						//������������
		             {
		           	  0.1f*i,0, 0.1f*i,1, 0.1f*(i+1),0,
		           	  0.1f*i,1, 0.1f*(i+1),1,  0.1f*(i+1),0
		             }
             ); 
		}
	}
	
	public void drawSelf(GL10 gl)
	{		
		String scoreStr=msv.objectCount+"";				//�÷���
		
		gl.glPushMatrix();								//���浱ǰ����
		
		for(int i=0;i<scoreStr.length();i++)
		{
			char c=scoreStr.charAt(i);					//���÷��е�ÿ�������ַ�����
			
			gl.glTranslatef(ICON_WIDTH*0.7f, 0, 0);		//x����ƽ��
			
			numbers[c-'0'].drawSelf(gl);				//����ÿ������
			
		}
		
		gl.glPopMatrix();								//�ظ�֮ǰ����
	}
}
