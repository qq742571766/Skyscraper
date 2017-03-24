package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//���볣������Ķ���
//������Ҫ����
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Line
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
	  
    private FloatBuffer mTextureBuffer;//�����������ݻ���
     
    private int vCount;//���α�����¼��������
    
    int drawableId;//ͼƬID
    
    public float mAngleX;//��X����ת�ĽǶ�
    public float mAngleY;//��Y����ת�ĽǶ�
    public float mAngleZ;//��Z����ת�ĽǶ�
    
    float mOffsetX;//X�����ƫ����
    float mOffsetY;//Y�����ƫ����
    float mOffsetZ;//Z�����ƫ����
    
    private float radius;//Բ��������Բ��
    private float heightSpan=0.1f;//�зֵĸ߶�
    private float angleSpan=30;//�зֵĽǶ�
	
    //����Բ�����з�����������    
    int col=(int) (360/angleSpan);    
    int row;
    
    public Line(float height,float radius,int drawableId)
    {
    	this.radius=radius;
    	this.drawableId=drawableId;
    	
    	row=(int) (height/heightSpan);//�����зֵ�����    	
    	
    	initVertex();//���ó�ʼ�����㷽��
   
    	initTexture();//���ó�ʼ��������
    }    
    
    public void initVertex()
	{   //��ʼ������ 	
    	ArrayList<Float> alVertex=new ArrayList<Float>();	
    	
		for(int i=-row;i<=0;i++)
		{
			float y=i*heightSpan*UNIT_SIZE;//y����
			
			float hAngle=0;
			
			for(int j=0;j<=col;j++)
			{
				hAngle=j*angleSpan;//ˮƽ���ϵĽǶ�				
				float x=(float) (radius*UNIT_SIZE*Math.cos(Math.toRadians(hAngle)));//��I��J�е�X����
				float z=(float) (radius*UNIT_SIZE*Math.sin(Math.toRadians(hAngle)));//��I��J�е�Y����
				//������õ�X,Y,Z������ӵ�ArrayList��
				alVertex.add(x);
				alVertex.add(y);
				alVertex.add(z);
			}
		}
		
		
		//������������
		ArrayList<Integer> alIndex=new ArrayList<Integer>();
		
		int ncol=col+1;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<col;j++)
			{
				int k=i*ncol+j;
					//�������н�������������ε�����
					alIndex.add(k);
					alIndex.add(k+ncol);
					alIndex.add(k+1);
					
					//�������н�������������ε�����
					alIndex.add(k+ncol);
					alIndex.add(k+ncol+1);
					alIndex.add(k+1);
			}
		}		
  		vCount=alIndex.size();//��������  		
  	
  		float vertices[]=new float[alIndex.size()*3];//����һ��СΪalIndex.size()*3�ĸ����������Ÿ���������
  		
		for(int i=0;i<vCount;i++)
		{//��ArrayList�е�ֵȫ��ֵ��������
			int k=alIndex.get(i);
			vertices[i*3]=alVertex.get(k*3);
			vertices[i*3+1]=alVertex.get(k*3+1);
			vertices[i*3+2]=alVertex.get(k*3+2);
		}
		//���������������ݻ���
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��ΪFLOAT�ͻ���
		mVertexBuffer.put(vertices);//�򻺳����з��붥����������
		mVertexBuffer.position(0);//����������ʼλ��		
	}    
     
    public void initTexture()
	{
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
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTextureBuffer=tbb.asFloatBuffer();//ת��ΪFLOAT�ͻ���
		mTextureBuffer.put(textures);//�򻺳����з��붥����������
		mTextureBuffer.position(0);//����������ʼλ��
	}
	
    
  
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//�����ֳ�
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//����ʹ�ö�������
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);//Ϊ����ָ��������������
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//��������
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//����ʹ����������
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);//Ϊ����ָ��ͼƬ�������� 
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);//��ͼƬ

		gl.glTranslatef(mOffsetX, mOffsetY, mOffsetZ);//������ϵ�ƶ���(mOffsetX, mOffsetY, mOffsetZ)��
	
		gl.glRotatef(mAngleX, 1, 0, 0);//��X����תmAngleX
 		gl.glRotatef(mAngleY, 0, 1, 0);//��Y����תmAngleY
 		gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����תmAngleZ
 		
 
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//����ͼ��
	
		gl.glPopMatrix();//�ָ��ֳ�
	
	}
    
}