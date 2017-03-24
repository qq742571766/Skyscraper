package wyf.tzz.gdl;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

// ��ʾ����������ε���
public class TextureRect {
	private FloatBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//������ɫ���ݻ���
    int vCount;							//��������
    int texId;							//����ID
    
    public TextureRect(int texId,float X_UNIT_SIZE,float Y_UNIT_SIZE,float[] textures)
    {
    	this.texId=texId;
    	
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        float vertices[]=new float[]
        {
        	-1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0,
        	-1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0,
        	
        	-1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,-1*Y_UNIT_SIZE,0,
        	1*X_UNIT_SIZE,1*Y_UNIT_SIZE,0
        };
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());			//�����ֽ�˳��
        mVertexBuffer = vbb.asFloatBuffer();		//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);				//�򻺳����з��붥����������
        mVertexBuffer.position(0);					//���û�������ʼλ��
        
        
        //���������������ݻ���
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length*4);
        tbb.order(ByteOrder.nativeOrder());			//�����ֽ�˳��
        mTextureBuffer= tbb.asFloatBuffer();		//ת��ΪFloat�ͻ���
        mTextureBuffer.put(textures);				//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);					//���û�������ʼλ��
        
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FLOAT,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
		
        //��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        
        //����ʹ������ST���껺��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0,
        		vCount
        );
        
        //�ر�����
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D); 
    }
}
