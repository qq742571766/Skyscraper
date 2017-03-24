package wyf.tzz.gdl;

import static wyf.tzz.gdl.Constant.*;//���뾲̬����ĳ�Ա

//�����õ�����
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Box
{
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
	  
    private FloatBuffer mTextureBuffer;//�����������ݻ���
	    
    private int vCount=36;//�ܵ������εĶ�������
    
    int drawableId;//����ͼƬ��ID
    
    public float mAngleX;//��x����ת�ĽǶ�
    public float mAngleY;//��y����ת�ĽǶ�
    public float mAngleZ;//��z����ת�ĽǶ�
    
    float mOffsetX;
    float mOffsetY;
    float mOffsetZ;
	
    
    public Box(int drawableId)
    {
    	this.drawableId=drawableId;
    	
    	initVertex(); //���ó�ʼ������ķ���
    	
    	initTexture();//���ó�ʼ������ķ���
    }
    

    public void initVertex()
	{   //����������
		float x=BOX_SIZE*UNIT_SIZE/2;//���������ζ����x����ı���
		
		float y=x;					//���������ζ����y����ı���
		
		float z=x;					//���������ζ����z����ı���
		
		//�������껺�������ʼ��
		float[] vertices=
		{				
			//�����ϱ������������ε�����
			-x,y,-z,
			-x,y,z,
			x,y,-z,
			
			-x,y,z,
			x,y,z,
			x,y,-z,
			
			//�����±������������ε�����
			-x,-y,z,
			-x,-y,-z,
			x,-y,z,
			
			-x,-y,-z,
			x,-y,-z,
			x,-y,z,
			
			//������������������ε�����
			-x,y,-z,
			-x,-y,-z,
			-x,y,z,
			
			-x,-y,-z,
			-x,-y,z,
			-x,y,z,
			
			//�����ұ������������ε�����
			x,y,z,
			x,-y,z,
			x,y,-z,
			
			x,-y,z,
			x,-y,-z,
			x,y,-z,
			
			//����ǰ�������������ε�����
			-x,y,z,
			-x,-y,z,
			x,y,z,
			
			-x,-y,z,
			x,-y,z,
			x,y,z,
			
			//���ɺ�������������ε�����
			x,y,-z,
			x,-y,-z,
			-x,y,-z,
			
			x,-y,-z,
			-x,-y,-z,
			-x,y,-z,
			
		};
	    //���������������ݻ���
		//vertices.length*4����Ϊһ��Float�ĸ��ֽ�
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);	
		vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mVertexBuffer=vbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
		mVertexBuffer.put(vertices);//�򻺳����з��붥����������
		mVertexBuffer.position(0);//���û�������ʼλ��
		
	}
    
    //���� �������ݳ�ʼ��
    public void initTexture()
	{
    	//�����������Ӧ���������꣬�ܹ���36�����㣬Ҳ����36����������
    	float textures[] = new float[]
            {
			0.02f,0.2f,		0.02f,0.4f,		0.05f,0.4f,		0.02f,0.2f,		0.05f,0.4f,		0.05f,0.2f,
			
			0.02f,0.2f,		0.02f,0.4f,		0.05f,0.4f,		0.02f,0.2f,		0.05f,0.4f ,	0.05f,0.2f,
			
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0,
			0,0,	0,1,	1,0,	0,1		,1,1,	1,0
			
			};
    	//���������������ݻ���
		//textures.length*4����Ϊһ��Float�ĸ��ֽ�
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
		mTextureBuffer=tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
		mTextureBuffer.put(textures);//�򻺳����з��붥����������
		mTextureBuffer.position(0);//���û�������ʼλ��
	}
	
    
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();//�����ֳ�
				
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�������
		//Ϊ����ָ��������������
		gl.glVertexPointer
		(
				3, //ÿ���������������Ϊ3  xyz 
				GL10.GL_FLOAT, //��������ֵ������Ϊ GL_FLOAT��
				0, //��������֮��ļ��
				mVertexBuffer//������������
		);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);//��������
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//����ʹ���������껺��
		
		//Ϊ����ָ������ST���껺��
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,mTextureBuffer);
		//�󶨵�ǰ����
		gl.glBindTexture(GL10.GL_TEXTURE_2D, drawableId);
		//������ϵ�ƶ���mOffsetX, mOffsetY, mOffsetZλ�� 
		gl.glTranslatef(mOffsetX, mOffsetY, mOffsetZ);
		
		gl.glRotatef(mAngleX, 1, 0, 0);//�� x����תmAngleX��
 		gl.glRotatef(mAngleY, 0, 1, 0);//��y����תmAngleY��
 		gl.glRotatef(mAngleZ, 0, 0, 1);//��z����תmAngleZ��
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//����ͼ��
		
		gl.glPopMatrix();//�ָ��ֳ�
	
	}
    
}