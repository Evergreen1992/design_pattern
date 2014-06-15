package com.tankwar.domain;

import java.util.ArrayList;
import java.util.List;

import com.tankwar.entity.Prop;
import com.tankwar.entity.interfaces.PropStatus;
import com.tankwar.utils.Constant;

/**
 * 道具管理器
 * @author Evergreen
 * 
 *
 */
public class PropsContainer {
	
	static List<Prop> props = new ArrayList<Prop>();
	
	public static void clearProps(){
		for(Prop p : props){
			p.setStatus(PropStatus.DEAD);
		}
	}
	
	//添加道具
	public static void newProp(){
		Prop prop = new Prop();
		props.add(prop);
	}

	//获取有效的所有道具
	public static List<Prop> getValidProps(){
		List<Prop> valid = new ArrayList<Prop>();
		Prop instance = null ;
		
		for( int i = 0 ; i < props.size(); i ++ ){
			instance = props.get(i);
			if( instance.getStatus() == PropStatus.ALIVE ){
				if( (int)(System.currentTimeMillis()/1000) - instance.getStartTime() <= Constant.MAX_PROP_TIME){
					valid.add(instance);
				}else{
					props.remove(instance);
				}
			}
		}
		return valid;
	}
}
