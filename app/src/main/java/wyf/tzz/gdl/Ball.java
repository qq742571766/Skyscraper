package wyf.tzz.gdl;

import java.nio.ByteBuffer;

import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball 
{
	private FloatBuffer mVertexBuffer;//����
	private FloatBuffer mTextureBuffer;//����
	int vCount;//�������
	int drawableId;//����id
	float radius;//�뾶
	public Ball(float radius,int drawableId)
	{
		this.radius=radius;
		this.drawableId	= drawableId;
		final int angleSpan=50;
		ArrayList<Float> alVertex=new ArrayList<Float>();//��Ŷ���
		
		for(int vAngle=90;vAngle>=-90;vAngle-=angleSpan)
		{
			float y=(float) (radius*Math.sin(Math.toRadians(vAngle)));//���y����
			double temp=radius*Math.cos(Math.toRadians(vAngle));
			for(int hAngle=360;hAngle>=0;hAngle-=angleSpan)
			{
				float x=(float) (temp*Math.cos(Math.toRadians(hAngle)));//���x����
				float z=(float) (temp*Math.sin(Math.toRadians(hAngle)));//���z����
				alVertex.add(x);//�������������alVertex
				alVertex.add(y);//�������������alVertex
				alVertex.add(z);//�������������alVertex
			}
		}
		ArrayList<Integer> alIndex=new ArrayList<Integer>();
		
		int row=180/angleSpan;
		int col=360/angleSpan;
		int ncol=col+1;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				int k=i*ncol+j;
					//��������
					alIndex.add(k);
					alIndex.add(k+ncol);
					alIndex.add(k+1);
					
					//��������
					alIndex.add(k+ncol);
					alIndex.add(k+ncol+1);
					alIndex.add(k+1);
			}
		}
		
  		vCount=alIndex.size();
  	
  		float vertices[]=new float[alIndex.size()*3];
		for(int i=0;i<vCount;i++)
		{
			int k=alIndex.get(i);
			vertices[i*3]=alVertex.get(k*3);
			vertices[i*3+1]=alVertex.get(k*3+1);
			vertices[i*3+2]=alVertex.get(k*3+2);
		}
		
		//�������㻺��
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//һ��float�ĸ�byte
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer(); //ת��Ϊfloat����
		mVertexBuffer.put(vertices);//���붨����������
		mVertexBuffer.position(0);//������ʼλ��
		
	
	
		float[] textures=generateTextures(row,col);
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);//һ��float�ĸ�byte
		tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTextureBuffer=tbb.asFloatBuffer();//ת��Ϊfloat����
		mTextureBuffer.put(textures);//���붨����������
		mTextureBuffer.position(0);//������ʼλ��
	}
	
	
	public float[] generateTextures(int row,int col)
	{//�Զ���������ķ���
		////row*col�����Σ�һ������2�������Σ�һ��������3�����㣬һ������2����������ֵ
		int tCount=row*col*2*3*2;
		float[] textures=new float[tCount];
		
		float sizew=1.0f/col;
		float sizeh=1.0f/row;
		
		for(int i=0,temp=0;i<row;i++)
		{
			
			float t=i*sizeh;
			for(int j=0;j<col;j++)
			{
				float s=j*sizew;
				//����
				textures[temp++]=s;
				textures[temp++]=t;
				
				textures[temp++]=s;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t;
				
				//����
				textures[temp++]=s;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t+sizeh;
				
				textures[temp++]=s+sizew;
				textures[temp++]=t;
			}
		
		}
			
		return textures;
	}
	
	public void drawSelf(GL10 gl)
	{
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//������������		
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,mVertexBuffer);//Ϊ����ָ��������������
		gl.glEnable(GL10.GL_TEXTURE_2D);//��������
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//������������
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//Ϊ����ָ��������������
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//������
		gl.glDrawArrays(GL10.GL_TRIANGLES,0, vCount);//����ͼ��
		
	}
}