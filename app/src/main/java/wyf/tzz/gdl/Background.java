package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//���뾲̬����ĳ�Ա
//�����õ�����
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Background
{	
		private FloatBuffer   mVertexBuffer;//�����������ݻ���
		  
	    private FloatBuffer mTextureBuffer;//�����������ݻ���
	
	    private int vCount;//���α���������¼��������
	    
	    int drawableId;//ͼƬ��ID 
	    
	    public float mAngleX;//��X����ת�ĽǶ�
	    public float mAngleY;//��Y����ת�ĽǶ�
	    public float mAngleZ;//��Z����ת�ĽǶ�		
	    
	    public Background(int drawableId)
	    {
	    	this.drawableId=drawableId;
	    	
	    	//��ʼ���������껺��
	    	initVertex();
	
	    	//��ʼ���������껺��
	    	initTexture();
	    }
	    
	   
	    public void initVertex()
		{
			float x=BACKGROUND_LENGTH*UNIT_SIZE/2;//��¼����X����ֵ�ı���
			
			float y=BACKGROUND_HEIGHT*UNIT_SIZE/2;//��¼����Y����ֵ�ı���
			
			float z=0;//��¼����Z����ֵ�ı���

			float[] vertices=
			{//�����Ķ��������ʼ�����������������������
				-x,y,z,
				-x,-y,z,
				x,y,z,
				
				-x,-y,z,
				x,-y,z,
				x,y,z,
		
			};
			
			vCount=vertices.length/3;//��������
			//���������������ݻ���
			//vertices.length*4����Ϊһ��Float�ĸ��ֽ�
			ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	
			vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
			mVertexBuffer=vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
			mVertexBuffer.put(vertices);//�򻺳����з��붥����������
			mVertexBuffer.position(0);//���û�������ʼλ��			
			
		}
	   
	
	    public void initTexture()
		{//ͼƬ�����ʼ��
			float[] textures=new float[vCount*2];//ͼƬ��������Ϊ����������2��
			
			int temp=0;
			
			//���Ͻ������ε���������
			textures[temp++]=0;
			textures[temp++]=0;
			
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
			
			
			//���½������ε���������
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
		
			//���������������ݻ���
			//vertices.length*4����Ϊһ��Float�ĸ��ֽ�
			ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);	
			tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
			mTextureBuffer=tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
			mTextureBuffer.put(textures);//�򻺳����з��붥����������
			mTextureBuffer.position(0);//���û�������ʼλ��	
		}
		
	    
		public void drawSelf(GL10 gl)
		{
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�������
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//Ϊ����ָ��������������
	
			gl.glEnable(GL10.GL_TEXTURE_2D);//��������
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//������������
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//Ϊ����ָ��ͼƬ��������
			gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//��ͼƬ
		
			gl.glRotatef(mAngleX, 1, 0, 0);//��X����תmAngleX��
	 		gl.glRotatef(mAngleY, 0, 1, 0);//��Y����תmAngleY��
	 		gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����תmAngleZ��
			
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//���Ʊ���
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	    
	}