package wyf.tzz.gdl;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class BoxGroup
{
	
	Box box;							//����
	
	ArrayList<SingleBox> al=new ArrayList<SingleBox>();
	
	SingleBox sb;						//��������
	
	public BoxGroup(int drawableId)
	{
		box=new Box(drawableId);		//��ʼ������
		
		sb=new SingleBox(this);		
		
		al.add(sb);						//���������Ӽ��뵽al����
	}
	
	
	public void drawSelf(GL10 gl)		//�����б��е�ÿ������
    {
    	for(int i=0;i<al.size();i++)
    	{
    		al.get(i).drawSelf(gl);		//��������
    	}
    }
	
}