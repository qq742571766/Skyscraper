package wyf.tzz.gdl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import static wyf.tzz.gdl.Constant.*;

import javax.microedition.khronos.opengles.GL10;

public class Floor
{
	FloatBuffer mVertexBuffer;//���㻺��
	FloatBuffer mTextureBuffer;//������
	int drawableId;//����id
	int vCount=6;//�������
	public Floor(int drawableId)
	{
		this.drawableId=drawableId;
		initVertex();//��ʼ������
		initTexture();//��ʼ������
	}
	public void initVertex()
	{
		float x=UNIT_SIZE*FLOOR_LENGTH/2;//���x����ֵ
		float y=0;//y����ֵ
		float z=UNIT_SIZE*FLOOR_WIDTH/2;//���z����ֵ
		float[] vertices=
		{
				-x,y,-z,
				-x,y,z,
				x,y,-z,
				
				-x,y,z,
				x,y,z,
				x,y,-z
		};
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//ÿ��float4��byte
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��Ϊfloat����
		mVertexBuffer.put(vertices);//���붥������
		mVertexBuffer.position(0);//������ʼλ��
	}
	public void initTexture()
	{
		float[] textures=new float[vCount*2];
		int temp=0;
		//����������
		textures[temp++]=0;
		textures[temp++]=0;
		
		textures[temp++]=0;
		textures[temp++]=1;
		 
		textures[temp++]=1;
		textures[temp++]=0;
		
		
		//����������
		textures[temp++]=0;
		textures[temp++]=1;
		
		textures[temp++]=1;
		textures[temp++]=1;
		
		textures[temp++]=1;
		textures[temp++]=0;
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//ÿ��float4��byte
		tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTextureBuffer=tbb.asFloatBuffer();//ת��Ϊfloat����
		mTextureBuffer.put(textures);//���붨������
		mTextureBuffer.position(0);//������ʼλ��
	}
	public void drawSelf(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//������������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//Ϊ����ָ����������
		gl.glEnable(GL10.GL_TEXTURE_2D);//��������
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//������������
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);	//Ϊ����ָ����������
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//������
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//����
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);//�رն�������
	}
}