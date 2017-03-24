package wyf.tzz.gdl;
import static wyf.tzz.gdl.Constant.*;
public class ActionThread extends Thread{
	MySurfaceView msv;			 //������
	public boolean beginDown=false;//��־�����Ƿ��Ѿ���ʼ����
	BoxGroup bg;				//���ӹ�����
	Line line;					//����
	SingleBox singleBox;		//�������������	
	SingleBox singleBox2;		//��¥�������������
	float lineHeight=BASE_HEIGHT+LINE_LENGTH+LINE_OFF_BOX;	//���϶˵��ʼ�ĸ߶�
	float downSpan=0.01f;		//����ÿ���½��߶�
	boolean rotateLeft=false;	//�����Ƿ���ת
	boolean rotateRight=false;	//�����Ƿ���ת
	float rotateAngle=0;		//������ת�Ƕ�
	float angleSpan=downSpan/BOX_SIZE*180;			//����ÿ����ת�Ƕ�
	float xSpan=downSpan/BOX_SIZE*BASE_LENGTH/2;	//������תʱÿ����x�᷽��ƽ�Ƶľ���
	public ActionThread(BoxGroup bg,Line line,MySurfaceView msv){
		this.bg=bg;
		this.line=line;	
		this.msv=msv;
		line.mOffsetY=lineHeight;		//��������ƽ��
	}
	@Override
	public void run(){
		boolean flag=true;				//flag ��־�������������Ұڶ�
		
		while(msv.beginFlag){			//beginFlag�Ƿ�Ϊtrue���Ƿ�ʼ
			singleBox=bg.al.get(bg.al.size()-1);//�ҵ�al���������SingleBox��������������ת��������			
			if(!beginDown){				//���ӻ�û�п�ʼ����ʱ���������Ӱڶ�
				//������������һ������Z��ת��,�����˶�		
				if(flag)//���Ұڶ�
				{
					line.mAngleZ+=1;	//�Ƕȼ�һ
					
					if(line.mAngleZ>=LINE_ANGLE)	//����Ƕȴ���LINE_ANGLE��ʼ���
					{
						flag=false;
					}
				}
				else					//����ڶ�
				{
					line.mAngleZ-=1;	//�Ƕȼ�һ
					
					if(line.mAngleZ<=-LINE_ANGLE)	//����Ƕ�С��-LINE_ANGLE��ʼ�Ұ�
					{
						flag=true;
					}
				}
				calculateBoxXYZ();		//�����������ĵ������
				try
				{
					Thread.sleep(100);	//˯��100ms
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else						//beginDownΪfalse�������Ѿ���ʼ�����
			{
				//���ӿ�ʼ����
				singleBox.y-=downSpan;
				collisionTest();		//��ײ��⣬�Ƿ�ɹ������ڴ�¥��
				if(rotateLeft)			//������ת
				{
					boxRotateLeft();
				}
				if(rotateRight)			//������ת
				{
					boxRotateRight();
				}
				msv.objectCount=bg.al.size()-1;		//���·���				
				try
				{
					Thread.sleep(5);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	public void calculateBoxXYZ()		//�����������ĵ�XYZ����
	{
		//�����˶�
		//����x����
		singleBox.x=(float)(LINE_LENGTH*Math.sin(Math.toRadians(line.mAngleZ)));
		
		//����y����
		singleBox.y=(float)(line.mOffsetY-(LINE_LENGTH*Math.cos(Math.toRadians(line.mAngleZ)))-BOX_SIZE/2);
	}
	public void collisionTest()			//��ײ���
	{
		if(bg.al.size()>1)				//singleBox��������һ��ʱ
		{
			singleBox2=bg.al.get(bg.al.size()-2);				//ȡ����¥�������������
			if(	singleBox.x>=singleBox2.x-BOX_SIZE&&			
					singleBox.x<=singleBox2.x+BOX_SIZE)			//������������������������ϼ��ɳɹ�
			{
				if(singleBox.x>=singleBox2.x-BOX_SIZE/2&&
						singleBox.x<=singleBox2.x+BOX_SIZE/2)	//�ܹ���ȫ���ڴ�¥�ϣ�����ɹ�
				{	
					if((singleBox.y-singleBox2.y-BOX_SIZE)<=0f&&
							(singleBox.y-singleBox2.y-BOX_SIZE)>-0.01f)		//�������������������ʱ
						{
							bg.al.add(new SingleBox(bg));		//�ɹ�֮����������һ���µ����Ӽ������ӹ�����
							beginDown=false;					//��һ�֣����ӿ�ʼ�ڶ�������ֹͣ����
							line.mOffsetY+=BOX_SIZE;			//���ӵ�y��������
							msv.targetPointY+=BOX_SIZE;			//Ŀ������������������y��������
							msv.activity.playSound(1, 0);		//���ųɹ����������
							return;
						}
				}
				else if((singleBox.y-singleBox2.y-BOX_SIZE)<=0f&&
							(singleBox.y-singleBox2.y)>=0f)		//������û�гɹ�����ʱ
					{
						if(singleBox.x<singleBox2.x)			//��������ƫ���
						{
							rotateLeft=true;
							return;
						}
						if(singleBox.x>singleBox2.x)			//��������ƫ�ұ�
						{
							rotateRight=true;
							return;
						}
					}
			}
			//���Ӵ���
			if(singleBox.y<=BOX_SIZE/2||singleBox.y<line.mOffsetY-LINE_LENGTH-TEST_LENGTH)	
			{				
				beginDown=false;							//��һ�֣����Ӱڶ�				
				singleBox.mAngleZ=0;						//������z����ת�Ƕ���Ϊ0
				rotateAngle=0;								//������ת�Ƕ����Ƕ���Ϊ0
				singleBox.x=0;								//������x��ƽ��������Ϊ0
				msv.failCount++;
				return;
			}
		}
		else												//singleBox��������һ��ʱ								
		{
			if(singleBox.y<=BASE_HEIGHT+BOX_SIZE/2&&
					singleBox.x>=-BASE_LENGTH/2&&
					singleBox.x<=BASE_LENGTH/2)				//����������̨
				{				
					bg.al.add(new SingleBox(bg));			//�ɹ�֮����������һ���µ����Ӽ������ӹ�����
					beginDown=false;						//��һ�֣����Ӱڶ�	
					line.mOffsetY+=BOX_SIZE;				//���ӵ�y��������
					msv.targetPointY+=BOX_SIZE;				//Ŀ������������������y��������
					msv.activity.playSound(1, 0);			//���ųɹ����������
					return;
				}
		}
			if(singleBox.y<=BOX_SIZE/2)						//����
			{				
				beginDown=false;							//��һ�֣����Ӱڶ�	//return true;		
				msv.failCount++;
				return;
			}
	}
	public void boxRotateLeft()
	{
		if(singleBox.y<=singleBox2.y)						//������y����С������������y֮��
		{
			rotateLeft=false;								//ֹͣ������ת
			msv.activity.playSound(2, 0);					//����ʧ������
			return;
		}
		singleBox.x-=xSpan;					//��������ƽ��
		rotateAngle+=angleSpan;				//������z����ת�Ƕȼ���angleSpan
		singleBox.mAngleZ=rotateAngle;		//������z����ת�Ƕ�
	}
	public void boxRotateRight()			//����������ת
	{
		if(singleBox.y<=singleBox2.y)		//������y����С������������y֮��
		{
			rotateRight=false;				//ֹͣ������ת
			msv.activity.playSound(2, 0);	//����ʧ������
			return;
		}
		singleBox.x+=xSpan;					//��������ƽ��
		rotateAngle-=angleSpan;				//������z����ת�Ƕȼ�ȥangleSpan
		singleBox.mAngleZ=rotateAngle;		//������z����ת�Ƕ�
	}
}