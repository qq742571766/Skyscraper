package wyf.tzz.gdl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import static wyf.tzz.gdl.Constant.*;

public class Base
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer mTextureBuffer;//�����������ݻ���
    private int vCount=36;//����������
    int drawableId;//����ID
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ�
    public float mAngleZ;//��z����ת�Ƕ�
    public Base(int drawableId)
    {
    	this.drawableId=drawableId;
    	//��ʼ������
    	initVertex();
    	//��ʼ������
    	initTexture();
    }
    //��̨
    public void initVertex()
	{    	
		float xup=BASE_LENGTH*UNIT_SIZE/2;//�ϱ������xֵ
		float xdw=BASE_LENGTH*UNIT_SIZE*DOWN_UP_RATIO/2;//�±������xֵ,DOWN_UP_RATIOΪ�±������ϱ���Ŀ�ȱ�
		float y=BASE_HEIGHT*UNIT_SIZE/2;//���yֵ
		float zup=BASE_WIDTH*UNIT_SIZE/2;//�ϱ������zֵ
		float zdw=BASE_WIDTH*UNIT_SIZE*DOWN_UP_RATIO/2;//�±������zֵ��DOWN_UP_RATIOΪ�±������ϱ���Ŀ�ȱ�
		float[] vertices=
		{
			//��
			-xup,y,-zup,
			-xup,y,zup,
			xup,y,-zup,

			-xup,y,zup,
			xup,y,zup,
			xup,y,-zup,
			
			//��
			-xdw,-y,zdw,
			-xdw,-y,-zdw,
			xdw,-y,zdw,
			
			-xdw,-y,-zdw,
			xdw,-y,-zdw,
			xdw,-y,zdw,
			
			//��
			-xup,y,-zup,
			-xdw,-y,-zdw,
			-xup,y,zup,
			
			-xdw,-y,-zdw,
			-xdw,-y,zdw,
			-xup,y,zup,
			
			//��
			xup,y,zup,
			xdw,-y,zdw,
			xup,y,-zup,
			
			xdw,-y,zdw,
			xdw,-y,-zdw,
			xup,y,-zup,
			
			//ǰ
			-xup,y,zup,
			-xdw,-y,zdw,
			xup,y,zup,
			
			-xdw,-y,zdw,
			xdw,-y,zdw,
			xup,y,zup,
			
			//��
			xup,y,-zup,
			xdw,-y,-zdw,
			-xup,y,-zup,
			
			xdw,-y,-zdw,
			-xdw,-y,-zdw,
			-xup,y,-zup
			
		};
		
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//һ��float����4������
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
		mVertexBuffer.put(vertices);//�򻺳��з��붨����������
		mVertexBuffer.position(0);//���û�������ʼλ��
	}
    public void initTexture()
	{
		float[] textures=new float[vCount*2];
		for(int i=0,temp=0;i<6;i++)
		{
			//����
			textures[temp++]=0;
			textures[temp++]=0;
			
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
			
			
			//����
			textures[temp++]=0;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=1;
			
			textures[temp++]=1;
			textures[temp++]=0;
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//һ��float����4������
		tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTextureBuffer=tbb.asFloatBuffer();//ת��Ϊfloat����
		mTextureBuffer.put(textures);//����������������
		mTextureBuffer.position(0);//���û�������ʼλ��
	}
    
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�������
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
		gl.glEnable(GL10.GL_TEXTURE_2D);//��������
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//������������
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//Ϊ����ָ����������������
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//������

		gl.glRotatef(mAngleX, 1, 0, 0);//��x����ת
 		gl.glRotatef(mAngleY, 0, 1, 0);//��y����ת
 		gl.glRotatef(mAngleZ, 0, 0, 1);//��z����ת
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//���㷨����
	}
}