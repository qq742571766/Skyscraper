package wyf.tzz.gdl;

import javax.microedition.khronos.opengles.GL10;

import static wyf.tzz.gdl.Constant.*;

public class SingleBox
{
	float x;					//���ӵ����ĵ�x����
	float y;					//���ӵ����ĵ�y����
	float z;					//���ӵ����ĵ�z����
	
	public float mAngleY;		//������y����ת�Ƕ�
	public float mAngleX;		//������x����ת�Ƕ�
    public float mAngleZ;		//������z����ת�Ƕ�
	
	BoxGroup bg;				//���ӹ�����
	
	public  SingleBox(BoxGroup bg)
	{
		this.bg=bg;
	}
	
	public void drawSelf(GL10 gl)
	{
		gl.glPushMatrix();				//���浱ǰ����
		
		bg.box.mOffsetX=x*UNIT_SIZE;	//������x��ƽ�ƾ���
		bg.box.mOffsetY=y*UNIT_SIZE;	//������y��ƽ�ƾ���
		bg.box.mOffsetZ=z*UNIT_SIZE;	//������z��ƽ�ƾ���
		
		bg.box.mAngleX=mAngleX;			//������x����ת�Ƕ�
		bg.box.mAngleY=mAngleY;			//������y����ת�Ƕ�
		bg.box.mAngleZ=mAngleZ;			//������z����ת�Ƕ�
		
		bg.box.drawSelf(gl);			//��������
		
		gl.glPopMatrix();				//�ָ���ǰ����
		
		bg.box.mAngleZ=0;				//��������z����ת�Ƕ�
	}
}